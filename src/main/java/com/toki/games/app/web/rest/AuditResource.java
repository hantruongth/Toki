package com.toki.games.app.web.rest;

import com.toki.games.app.service.AuditEventService;
import com.toki.games.app.web.rest.util.Response;
import com.toki.games.app.web.rest.util.ResponseUtil;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZoneId;

@RestController
@RequestMapping("/management/audits")
public class AuditResource {

    private final AuditEventService auditEventService;

    public AuditResource(AuditEventService auditEventService) {
        this.auditEventService = auditEventService;
    }

    @GetMapping
    public Response getAll(Pageable pageable) {
        Page<AuditEvent> page = auditEventService.findAll(pageable);
        //HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/management/audits");
        return new Response(page.getContent());
    }

    @GetMapping(params = {"fromDate", "toDate"})
    public Response getByDates(
        @RequestParam(value = "fromDate") LocalDate fromDate,
        @RequestParam(value = "toDate") LocalDate toDate,
        Pageable pageable) {

        Page<AuditEvent> page = auditEventService.findByDates(
            fromDate.atStartOfDay(ZoneId.systemDefault()).toInstant(),
            toDate.atStartOfDay(ZoneId.systemDefault()).plusDays(1).toInstant(),
            pageable);
        //HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/management/audits");
        return new Response(page, "/management/audits");
    }

    @GetMapping("/{id:.+}")
    public Response get(@PathVariable Long id) {
        return ResponseUtil.wrapOrNotFound(auditEventService.find(id));
    }
}
