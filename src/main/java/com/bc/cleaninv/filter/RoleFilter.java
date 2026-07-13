package com.bc.cleaninv.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Owner: Member 2 (Authentication & Security Developer)
 *
 * Example role gate: only SUPERVISOR may hit any URL containing
 * "/delete". Extend the SUPERVISOR_ONLY_MARKERS array as your group
 * agrees which actions are Supervisor-only.
 *
 * This filter must run AFTER AuthFilter (alphabetical class name order
 * or explicit web.xml ordering both work — AuthFilter already redirects
 * unauthenticated users, so by the time this filter runs we can assume
 * session.getAttribute("user") is not null on protected paths).
 */
@WebFilter("/*")
public class RoleFilter implements Filter {

    private static final String[] SUPERVISOR_ONLY_MARKERS = {
            "/delete"
    };

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String path = req.getRequestURI().substring(req.getContextPath().length());

        boolean restricted = false;
        for (String marker : SUPERVISOR_ONLY_MARKERS) {
            if (path.contains(marker)) {
                restricted = true;
                break;
            }
        }

        if (restricted) {
            HttpSession session = req.getSession(false);
            String role = (session != null) ? (String) session.getAttribute("role") : null;
            if (!"SUPERVISOR".equalsIgnoreCase(role)) {
                resp.sendError(HttpServletResponse.SC_FORBIDDEN,
                        "Only a Supervisor may perform this action.");
                return;
            }
        }

        chain.doFilter(request, response);
    }
}
