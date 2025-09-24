package com.edumanage.web.filter;

import com.edumanage.model.User;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebFilter("/*")
public class AuthorizationFilter implements Filter {

    // List of URLs that are restricted to the ADMIN role
    private static final List<String> ADMIN_URLS = List.of(
            "/students", "/teachers", "/courses", "/enrollments", "/fees", "/salaries", "/reports"
    );

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);

        String path = httpRequest.getServletPath();

        // Don't filter the login page or CSS resources
        if (path.equals("/login") || path.endsWith(".css")) {
            chain.doFilter(request, response);
            return;
        }

        User user = (session != null) ? (User) session.getAttribute("user") : null;

        // If it's a POST request (form submission), let it through for now if the user is logged in.
        // A more advanced implementation might check the specific action.
        if (user != null && httpRequest.getMethod().equalsIgnoreCase("POST")) {
            chain.doFilter(request, response);
            return;
        }

        // Check if the requested URL is an admin-only URL
        boolean isAdminUrl = ADMIN_URLS.stream().anyMatch(url -> path.startsWith(url));

        if (isAdminUrl) {
            if (user != null && user.getRole() == User.UserRole.ADMIN) {
                // User is an admin and is accessing an admin page, allow it
                chain.doFilter(request, response);
            } else {
                // User is not an admin, or not logged in, deny access
                httpResponse.sendRedirect(httpRequest.getContextPath() + "/access-denied.jsp");
            }
        } else {
            // It's not an admin-specific URL, so let any logged-in user access it
            chain.doFilter(request, response);
        }
    }
}

