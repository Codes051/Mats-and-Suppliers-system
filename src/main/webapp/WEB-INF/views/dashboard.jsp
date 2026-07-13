<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Dashboard - Cleaning Inventory System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<%@ include file="fragments/header.jspf" %>

<main class="app-content">
    <h1>Dashboard</h1>

    <c:if test="${not empty error}">
        <div class="alert error">${error}</div>
    </c:if>

    <div class="stat-grid">
        <div class="stat-card">
            <div class="stat-value">${totalMaterials}</div>
            <div class="stat-label">Total Materials</div>
        </div>
        <div class="stat-card low-stock">
            <div class="stat-value">${lowStockCount}</div>
            <div class="stat-label">Low Stock Items</div>
        </div>
        <div class="stat-card">
            <div class="stat-value">${totalCleaners != null ? totalCleaners : '—'}</div>
            <div class="stat-label">Total Cleaners</div>
        </div>
        <div class="stat-card">
            <div class="stat-value">${recentIssuances != null ? recentIssuances.size() : '—'}</div>
            <div class="stat-label">Recent Issuances</div>
        </div>
    </div>

    <div class="card">
        <p style="color:#666;font-size:0.9rem;">
            Cleaners and Issuance stats will populate once CleanerDAO and IssuanceDAO
            are wired into DashboardServlet (see the TODOs in that file).
        </p>
    </div>
</main>
</body>
</html>
