package com.bc.cleaninv.controller;

import com.bc.cleaninv.dao.UserDAO;
import com.bc.cleaninv.model.User;
import com.bc.cleaninv.util.PasswordUtil;
import com.bc.cleaninv.util.ValidationUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Owner: Member 2 (Authentication & Security Developer)
 *
 * Demonstrates the required validation & business rules:
 *  - prevent duplicate usernames
 *  - prevent duplicate emails
 *  - validate required fields
 *  - meaningful error messages
 */
@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String username = req.getParameter("username");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String role = req.getParameter("role");

        // Required field validation
        if (ValidationUtil.isBlank(username) || ValidationUtil.isBlank(email)
                || ValidationUtil.isBlank(password) || ValidationUtil.isBlank(role)) {
            req.setAttribute("error", "All fields are required.");
            req.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(req, resp);
            return;
        }

        if (!ValidationUtil.isValidEmail(email)) {
            req.setAttribute("error", "Please enter a valid email address.");
            req.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(req, resp);
            return;
        }

        try {
            if (userDAO.usernameExists(username)) {
                req.setAttribute("error", "That username is already taken.");
                req.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(req, resp);
                return;
            }
            if (userDAO.emailExists(email)) {
                req.setAttribute("error", "An account with that email already exists.");
                req.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(req, resp);
                return;
            }

            User newUser = new User();
            newUser.setUsername(username);
            newUser.setEmail(email);
            newUser.setPasswordHash(PasswordUtil.hash(password));
            newUser.setRole(role);
            userDAO.insert(newUser);

            resp.sendRedirect(req.getContextPath() + "/login");

        } catch (SQLException e) {
            req.setAttribute("error", "A database error occurred. Please try again.");
            req.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(req, resp);
        }
    }
}
