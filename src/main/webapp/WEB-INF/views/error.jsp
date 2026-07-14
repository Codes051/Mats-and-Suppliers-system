<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="java.io.StringWriter" %>

<%
    Throwable error = (Throwable) request.getAttribute(
            "jakarta.servlet.error.exception"
    );

    Integer statusCode = (Integer) request.getAttribute(
            "jakarta.servlet.error.status_code"
    );

    String requestUri = (String) request.getAttribute(
            "jakarta.servlet.error.request_uri"
    );

    String message = (String) request.getAttribute(
            "jakarta.servlet.error.message"
    );

    String stackTrace = "";

    if (error != null) {
        StringWriter writer = new StringWriter();
        error.printStackTrace(new PrintWriter(writer));
        stackTrace = writer.toString();
    }
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Application Error</title>
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/style.css">

    <style>
        .diagnostic-box {
            white-space: pre-wrap;
            overflow-wrap: anywhere;
            background: #f4f4f4;
            border: 1px solid #ccc;
            padding: 16px;
            margin-top: 16px;
            font-family: Consolas, monospace;
            font-size: 13px;
        }
    </style>
</head>

<body>
<main class="app-content">
    <div class="alert error" style="margin-top:60px;">
        <h2>Application Error</h2>

        <p><strong>Status:</strong> <%= statusCode %></p>
        <p><strong>Request:</strong> <%= requestUri %></p>
        <p><strong>Message:</strong> <%= message %></p>

        <% if (error != null) { %>
            <p>
                <strong>Exception:</strong>
                <%= error.getClass().getName() %>
            </p>

            <div class="diagnostic-box"><%= stackTrace %></div>
        <% } else { %>
            <p>No exception object was supplied by Tomcat.</p>
        <% } %>

        <p>
            <a href="${pageContext.request.contextPath}/dashboard"
               class="btn">
                Back to Dashboard
            </a>
        </p>
    </div>
</main>
</body>
</html>