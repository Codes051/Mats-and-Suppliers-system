<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Error - Cleaning Inventory System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<main class="app-content">
    <div class="alert error" style="margin-top:60px;">
        <h2 style="margin-top:0;">Something went wrong</h2>
        <p>Please go back and try again, or contact a team member if this keeps happening.</p>
        <a href="${pageContext.request.contextPath}/dashboard" class="btn">Back to Dashboard</a>
    </div>
</main>
</body>
</html>
