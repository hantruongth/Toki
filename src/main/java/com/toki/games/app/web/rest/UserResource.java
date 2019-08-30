package com.toki.games.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.toki.games.app.config.Constants;
import com.toki.games.app.domain.User;
import com.toki.games.app.repository.UserRepository;
import com.toki.games.app.security.AuthoritiesConstants;
import com.toki.games.app.service.MailService;
import com.toki.games.app.service.UserService;
import com.toki.games.app.service.dto.UserDTO;
import com.toki.games.app.web.rest.errors.BadRequestAlertException;
import com.toki.games.app.web.rest.errors.EmailAlreadyUsedException;
import com.toki.games.app.web.rest.errors.LoginAlreadyUsedException;
import com.toki.games.app.web.rest.util.Response;
import com.toki.games.app.web.rest.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserResource {

    private final Logger log = LoggerFactory.getLogger(UserResource.class);

    private final UserService userService;

    private final UserRepository userRepository;

    private final MailService mailService;

    public UserResource(UserService userService, UserRepository userRepository, MailService mailService) {

        this.userService = userService;
        this.userRepository = userRepository;
        this.mailService = mailService;
    }

    @PostMapping("/users")
    @Timed
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
    public Response createUser(@Valid @RequestBody UserDTO userDTO) throws URISyntaxException {
        log.debug("REST request to save User : {}", userDTO);

        if (userDTO.getId() != null) {
            throw new BadRequestAlertException("A new user cannot already have an ID", "userManagement", "idexists");
            // Lowercase the user login before comparing with database
        } else if (userRepository.findOneByLogin(userDTO.getLogin().toLowerCase()).isPresent()) {
            throw new LoginAlreadyUsedException();
        } else if (userRepository.findOneByEmailIgnoreCase(userDTO.getEmail()).isPresent()) {
            throw new EmailAlreadyUsedException();
        } else {
            User newUser = userService.createUser(userDTO);
            mailService.sendCreationEmail(newUser);
//            return ResponseEntity.created(new URI("/api/users/" + newUser.getLogin()))
//                .headers(HeaderUtil.createAlert( "userManagement.created", newUser.getLogin()))
//                .body(newUser);
            return new Response(newUser);
        }
    }

    @PutMapping("/users")
    @Timed
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
    public Response updateUser(@Valid @RequestBody UserDTO userDTO) {
        log.debug("REST request to update User : {}", userDTO);
        Optional<User> existingUser = userRepository.findOneByEmailIgnoreCase(userDTO.getEmail());
        if (existingUser.isPresent() && (!existingUser.get().getId().equals(userDTO.getId()))) {
            throw new EmailAlreadyUsedException();
        }
        existingUser = userRepository.findOneByLogin(userDTO.getLogin().toLowerCase());
        if (existingUser.isPresent() && (!existingUser.get().getId().equals(userDTO.getId()))) {
            throw new LoginAlreadyUsedException();
        }
        Optional<UserDTO> updatedUser = userService.updateUser(userDTO);

//        return ResponseUtil.wrapOrNotFound(updatedUser,
//            HeaderUtil.createAlert("userManagement.updated", userDTO.getLogin()));
        return new Response(updatedUser);
    }

    @GetMapping("/users")
    @Timed
    public Response getAllUsers(Pageable pageable) {
        final Page<UserDTO> page = userService.getAllManagedUsers(pageable);
        //HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/users");
        return new Response(page, "/api/users");
    }

    @GetMapping("/users/authorities")
    @Timed
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
    public Response getAuthorities() {
        return new Response(userService.getAuthorities());
    }

    @GetMapping("/users/{login:" + Constants.LOGIN_REGEX + "}")
    @Timed
    public Response getUser(@PathVariable String login) {
        log.debug("REST request to get User : {}", login);
        return ResponseUtil.wrapOrNotFound(
            userService.getUserWithAuthoritiesByLogin(login)
                .map(UserDTO::new));
    }

    @DeleteMapping("/users/{login:" + Constants.LOGIN_REGEX + "}")
    @Timed
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
    public Response deleteUser(@PathVariable String login) {
        log.debug("REST request to delete User: {}", login);
        userService.deleteUser(login);
        //return ResponseEntity.ok().headers(HeaderUtil.createAlert( "userManagement.deleted", login)).build();

        return new Response(true);
    }
}
