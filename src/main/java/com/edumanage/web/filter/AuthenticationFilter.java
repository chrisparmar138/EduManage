package com.edumanage.web.filter;

import com.edumanage.model.User;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

// This filter protects all pages except for the login page.
@WebFilter("/*")
public class AuthenticationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false); // Do not create a new session if one doesn't exist

        String loginURI = httpRequest.getContextPath() + "/login";

        boolean loggedIn = session != null && session.getAttribute("user") != null;
        boolean loginRequest = httpRequest.getRequestURI().equals(loginURI);

        if (loggedIn || loginRequest) {
            // User is logged in or is trying to log in, so let them proceed.
            chain.doFilter(request, response);
        } else {
            // User is not logged in and is trying to access a protected page.
            // Redirect them to the login page.
            httpResponse.sendRedirect(loginURI);
        }
    }

    // Other filter methods (init, destroy) can be left empty for this example.
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void destroy() {}
}
