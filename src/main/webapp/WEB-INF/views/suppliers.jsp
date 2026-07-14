<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Suppliers - Cleaning Inventory System</title>
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<%@ include file="fragments/header.jspf" %>

<main class="app-content">
    <h1>Suppliers</h1>

    <c:if test="${not empty error}">
        <div class="alert error">
            <c:out value="${error}" />
        </div>
    </c:if>

    <form class="search-bar"
          method="get"
          action="${pageContext.request.contextPath}/suppliers">

        <input type="text"
               name="q"
               placeholder="Search suppliers..."
               value="${keyword}">

        <button type="submit" class="btn">Search</button>

        <a href="${pageContext.request.contextPath}/suppliers?action=new"
           class="btn">
            + Add Supplier
        </a>
    </form>

    <table class="data-table">
        <thead>
        <tr>
            <th>Name</th>
            <th>Contact Person</th>
            <th>Phone</th>
            <th>Email</th>
            <th>Address</th>
            <th>Actions</th>
        </tr>
        </thead>

        <tbody>
        <c:forEach var="supplier" items="${suppliers}">
            <tr>
                <td><c:out value="${supplier.name}" /></td>
                <td><c:out value="${supplier.contactPerson}" /></td>
                <td><c:out value="${supplier.phone}" /></td>
                <td><c:out value="${supplier.email}" /></td>
                <td><c:out value="${supplier.address}" /></td>
                <td>
                    <a href="${pageContext.request.contextPath}/suppliers?action=edit&id=${supplier.supplierId}">
                        Edit
                    </a>

                    &nbsp;|&nbsp;

                    <a href="${pageContext.request.contextPath}/suppliers/delete?id=${supplier.supplierId}"
                       onclick="return confirm('Delete this supplier?');"
                       style="color:#a4262c;">
                        Delete
                    </a>
                </td>
            </tr>
        </c:forEach>

        <c:if test="${empty suppliers}">
            <tr>
                <td colspan="6"
                    style="text-align:center;color:#888;">
                    No suppliers found.
                </td>
            </tr>
        </c:if>
        </tbody>
    </table>
</main>

</body>
</html>
