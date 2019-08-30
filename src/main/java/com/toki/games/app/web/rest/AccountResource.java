package com.toki.games.app.web.rest;

import com.codahale.metrics.annotation.Timed;

import com.toki.games.app.domain.User;
import com.toki.games.app.repository.UserRepository;
import com.toki.games.app.security.SecurityUtils;
import com.toki.games.app.service.MailService;
import com.toki.games.app.service.UserService;
import com.toki.games.app.service.dto.PasswordChangeDTO;
import com.toki.games.app.service.dto.UserDTO;
import com.toki.games.app.web.rest.errors.*;
import com.toki.games.app.web.rest.util.Response;
import com.toki.games.app.web.rest.vm.KeyAndPasswordVM;
import com.toki.games.app.web.rest.vm.ManagedUserVM;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/api")
public class AccountResource {

    private final Logger log = LoggerFactory.getLogger(AccountResource.class);

    private final UserRepository userRepository;

    private final UserService userService;

    private final MailService mailService;

    public AccountResource(UserRepository userRepository, UserService userService, MailService mailService) {

        this.userRepository = userRepository;
        this.userService = userService;
        this.mailService = mailService;
    }

    @PostMapping("/register")
    @Timed
    @ResponseStatus(HttpStatus.CREATED)
    public Response registerAccount(@Valid @RequestBody ManagedUserVM managedUserVM) {
        if (!checkPasswordLength(managedUserVM.getPassword())) {
            throw new InvalidPasswordException();
        }
        User user = userService.registerUser(managedUserVM, managedUserVM.getPassword());
        mailService.sendActivationEmail(user);
        return new Response(true);
    }

    @GetMapping("/activate")
    @Timed
    public Response activateAccount(@RequestParam(value = "key") String key) {
        Optional<User> user = userService.activateRegistration(key);
        if (!user.isPresent()) {
            throw new InternalServerErrorException("No user was found for this activation key");
        }
        return new Response(true);
    }

    @GetMapping("/authenticate")
    @Timed
    public Response isAuthenticated(HttpServletRequest request) {
        log.debug("REST request to check if the current user is authenticated");
        return new Response(request.getRemoteUser());
    }

    @GetMapping("/account")
    @Timed
    public Response getAccount() {
        return new Response(userService.getUserWithAuthorities()
            .map(UserDTO::new)
            .orElseThrow(() -> new InternalServerErrorException("User could not be found")));
    }

    @PostMapping("/account")
    @Timed
    public Response saveAccount(@Valid @RequestBody UserDTO userDTO) {
        final String userLogin = SecurityUtils.getCurrentUserLogin().orElseThrow(() -> new InternalServerErrorException("Current user login not found"));
        Optional<User> existingUser = userRepository.findOneByEmailIgnoreCase(userDTO.getEmail());
        if (existingUser.isPresent() && (!existingUser.get().getLogin().equalsIgnoreCase(userLogin))) {
            throw new EmailAlreadyUsedException();
        }
        Optional<User> user = userRepository.findOneByLogin(userLogin);
        if (!user.isPresent()) {
            throw new InternalServerErrorException("User could not be found");
        }
        userService.updateUser(userDTO.getFirstName(), userDTO.getLastName(), userDTO.getEmail(),
            userDTO.getLangKey(), userDTO.getImageUrl());
        return new Response(true);
    }

    @PostMapping(path = "/account/change-password")
    @Timed
    public Response changePassword(@RequestBody PasswordChangeDTO passwordChangeDto) {
        if (!checkPasswordLength(passwordChangeDto.getNewPassword())) {
            throw new InvalidPasswordException();
        }
        userService.changePassword(passwordChangeDto.getCurrentPassword(), passwordChangeDto.getNewPassword());
        return new Response(true);
    }

    @PostMapping(path = "/account/reset-password/init")
    @Timed
    public Response requestPasswordReset(@RequestBody String mail) {
       mailService.sendPasswordResetMail(
           userService.requestPasswordReset(mail)
               .orElseThrow(EmailNotFoundException::new)
       );
       return new Response(true);
    }

    @PostMapping(path = "/account/reset-password/finish")
    @Timed
    public Response finishPasswordReset(@RequestBody KeyAndPasswordVM keyAndPassword) {
        if (!checkPasswordLength(keyAndPassword.getNewPassword())) {
            throw new InvalidPasswordException();
        }
        Optional<User> user =
            userService.completePasswordReset(keyAndPassword.getNewPassword(), keyAndPassword.getKey());

        if (!user.isPresent()) {
            throw new InternalServerErrorException("No user was found for this reset key");
        }
        return new Response(true);
    }

    private static boolean checkPasswordLength(String password) {
        return !StringUtils.isEmpty(password) &&
            password.length() >= ManagedUserVM.PASSWORD_MIN_LENGTH &&
            password.length() <= ManagedUserVM.PASSWORD_MAX_LENGTH;
    }
}
