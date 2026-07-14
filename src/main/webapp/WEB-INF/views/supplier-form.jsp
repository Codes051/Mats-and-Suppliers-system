<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>${supplier != null ? 'Edit' : 'Add'} Supplier - Cleaning Inventory System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<%@ include file="fragments/header.jspf" %>

<main class="app-content">
    <h1>${supplier != null ? 'Edit' : 'Add'} Supplier</h1>

    <c:if test="${not empty error}">
        <div class="alert error">${error}</div>
    </c:if>

    <div class="card" style="max-width:520px;">
        <form method="post"
              action="${pageContext.request.contextPath}/suppliers">

            <c:if test="${supplier != null && supplier.supplierId > 0}">
                <input type="hidden"
                       name="supplierId"
                       value="${supplier.supplierId}">
            </c:if>

            <div class="form-group">
                <label for="name">Supplier Name</label>
                <input type="text"
                       id="name"
                       name="name"
                       value="${supplier.name}"
                       maxlength="100"
                       required>
            </div>

            <div class="form-group">
                <label for="contactPerson">Contact Person</label>
                <input type="text"
                       id="contactPerson"
                       name="contactPerson"
                       value="${supplier.contactPerson}"
                       maxlength="100">
            </div>

            <div class="form-group">
                <label for="phone">Phone Number</label>
                <input type="text"
                       id="phone"
                       name="phone"
                       value="${supplier.phone}"
                       maxlength="30">
            </div>

            <div class="form-group">
                <label for="email">Email Address</label>
                <input type="email"
                       id="email"
                       name="email"
                       value="${supplier.email}"
                       maxlength="100">
            </div>

            <div class="form-group">
                <label for="address">Address</label>
                <textarea id="address"
                          name="address"
                          rows="3"
                          maxlength="255">${supplier.address}</textarea>
            </div>

            <button type="submit" class="btn">Save Supplier</button>

            <a href="${pageContext.request.contextPath}/suppliers"
               class="btn"
               style="background:#888;">
                Cancel
            </a>
        </form>
    </div>
</main>
</body>
</html>
