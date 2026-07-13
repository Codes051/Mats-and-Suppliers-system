<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Register - Cleaning Inventory System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="auth-wrapper">
    <h2>Register</h2>

    <c:if test="${not empty error}">
        <div class="alert error">${error}</div>
    </c:if>

    <form method="post" action="${pageContext.request.contextPath}/register">
        <div class="form-group">
            <label for="username">Username</label>
            <input type="text" id="username" name="username" required>
        </div>
        <div class="form-group">
            <label for="email">Email</label>
            <input type="email" id="email" name="email" required>
        </div>
        <div class="form-group">
            <label for="password">Password</label>
            <input type="password" id="password" name="password" required>
        </div>
        <div class="form-group">
            <label for="role">Role</label>
            <select id="role" name="role" required>
                <option value="STOREKEEPER">Storekeeper</option>
                <option value="SUPERVISOR">Supervisor</option>
            </select>
        </div>
        <button type="submit" class="btn">Create Account</button>
    </form>

    <p style="margin-top:14px;font-size:0.88rem;">
        Already have an account? <a href="${pageContext.request.contextPath}/login">Login here</a>
    </p>
</div>
</body>
</html>
