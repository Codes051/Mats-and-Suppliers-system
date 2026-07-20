<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Materials - Cleaning Inventory System</title>
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<%@ include file="fragments/header.jspf" %>

<main class="app-content">
    <h1>Materials</h1>

    <c:if test="${not empty error}">
        <div class="alert error">
            <c:out value="${error}" />
        </div>
    </c:if>

    <form class="search-bar"
          method="get"
          action="${pageContext.request.contextPath}/materials">

        <input type="text"
               name="q"
               placeholder="Search materials by name..."
               value="${keyword}">

        <label style="display:flex;align-items:center;gap:6px;">
            <input type="checkbox"
                   name="lowStockOnly"
                   value="true"
                   ${lowStockOnly ? 'checked' : ''}>
            Show low stock only
        </label>

        <button type="submit" class="btn">
            Search
        </button>

        <a href="${pageContext.request.contextPath}/materials"
           class="btn"
           style="background:#888;">
            Clear
        </a>

        <a href="${pageContext.request.contextPath}/materials?action=new"
           class="btn">
            + Add Material
        </a>
    </form>

    <table class="data-table">
        <thead>
        <tr>
            <th>Name</th>
            <th>Description</th>
            <th>Supplier</th>
            <th>Unit</th>
            <th>Quantity</th>
            <th>Reorder Level</th>
            <th>Status</th>
            <th>Actions</th>
        </tr>
        </thead>

        <tbody>
        <c:forEach var="m" items="${materials}">
            <tr class="${m.lowStock ? 'low-stock-row' : ''}">
                <td><c:out value="${m.name}" /></td>
                <td><c:out value="${m.description}" /></td>

                <td>
                    <c:choose>
                        <c:when test="${not empty m.supplierName}">
                            <c:out value="${m.supplierName}" />
                        </c:when>
                        <c:otherwise>
                            —
                        </c:otherwise>
                    </c:choose>
                </td>

                <td><c:out value="${m.unit}" /></td>
                <td><c:out value="${m.quantity}" /></td>
                <td><c:out value="${m.reorderLevel}" /></td>

                <td>
                    <c:choose>
                        <c:when test="${m.lowStock}">
                            <span class="badge low">Low Stock</span>
                        </c:when>
                        <c:otherwise>
                            <span class="badge">In Stock</span>
                        </c:otherwise>
                    </c:choose>
                </td>

                <td>
                    <a href="${pageContext.request.contextPath}/materials?action=edit&id=${m.materialId}">
                        Edit
                    </a>

                    &nbsp;|&nbsp;

                    <a href="${pageContext.request.contextPath}/materials/delete?id=${m.materialId}"
                       onclick="return confirm('Delete this material?');"
                       style="color:#a4262c;">
                        Delete
                    </a>
                </td>
            </tr>
        </c:forEach>

        <c:if test="${empty materials}">
            <tr>
                <td colspan="8"
                    style="text-align:center;color:#888;">
                    No materials found.
                </td>
            </tr>
        </c:if>
        </tbody>
    </table>
</main>

</body>
</html>
