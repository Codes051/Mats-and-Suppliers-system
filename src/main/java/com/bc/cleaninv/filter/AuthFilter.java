package com.bc.cleaninv.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Set;

@WebFilter("/*")
public class AuthFilter implements Filter {

    private static final Set<String> PUBLIC_PATHS = Set.of(
            "/",
            "/login",
            "/register"
    );

    @Override
    public void init(FilterConfig filterConfig) {
        // No initialization required.
    }

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain
    ) throws IOException, ServletException {

        HttpServletRequest httpRequest =
                (HttpServletRequest) request;

        HttpServletResponse httpResponse =
                (HttpServletResponse) response;

        String contextPath = httpRequest.getContextPath();
        String requestUri = httpRequest.getRequestURI();

        String path = requestUri.substring(contextPath.length());

        boolean publicPage = PUBLIC_PATHS.contains(path);

        boolean publicResource =
                path.startsWith("/css/")
                        || path.startsWith("/js/")
                        || path.startsWith("/images/")
                        || path.startsWith("/favicon");

        HttpSession session = httpRequest.getSession(false);

        boolean loggedIn =
                session != null
                        && session.getAttribute("user") != null;

        if (publicPage || publicResource || loggedIn) {
            chain.doFilter(request, response);
            return;
        }

        httpResponse.sendRedirect(
                contextPath + "/login"
        );
    }

    @Override
    public void destroy() {
        // No cleanup required.
    }
}