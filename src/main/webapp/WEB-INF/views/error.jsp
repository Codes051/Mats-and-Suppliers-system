<%@ page contentType="text/html;charset=UTF-8"
         pageEncoding="UTF-8"
         isErrorPage="true" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">

    <title>Error - Cleaning Inventory System</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/style.css">
</head>

<body>

<main class="app-content">

    <div class="card"
         style="max-width:600px;
                margin:60px auto;
                text-align:center;">

        <h1>Something went wrong</h1>

        <p>
            The requested page could not be loaded.
            Please return to the dashboard and try again.
        </p>

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