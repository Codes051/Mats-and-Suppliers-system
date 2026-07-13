<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>${material != null ? 'Edit' : 'Add'} Material - Cleaning Inventory System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<%@ include file="fragments/header.jspf" %>

<main class="app-content">
    <h1>${material != null ? 'Edit' : 'Add'} Material</h1>

    <c:if test="${not empty error}">
        <div class="alert error">${error}</div>
    </c:if>

    <div class="card" style="max-width:480px;">
        <form method="post" action="${pageContext.request.contextPath}/materials">
            <c:if test="${material != null}">
                <input type="hidden" name="materialId" value="${material.materialId}">
            </c:if>

            <div class="form-group">
                <label for="name">Name</label>
                <input type="text" id="name" name="name" value="${material.name}" required>
            </div>
            <div class="form-group">
                <label for="description">Description</label>
                <textarea id="description" name="description" rows="2">${material.description}</textarea>
            </div>
            <div class="form-group">
                <label for="unit">Unit (e.g. bottle, roll, case)</label>
                <input type="text" id="unit" name="unit" value="${material.unit}" required>
            </div>
            <div class="form-group">
                <label for="quantity">Quantity</label>
                <input type="number" id="quantity" name="quantity" min="0"
                       value="${material.quantity != null ? material.quantity : 0}" required>
            </div>
            <div class="form-group">
                <label for="reorderLevel">Reorder Level</label>
                <input type="number" id="reorderLevel" name="reorderLevel" min="0"
                       value="${material.reorderLevel != null ? material.reorderLevel : 0}" required>
            </div>
            <div class="form-group">
                <label for="supplierId">Supplier ID (optional — Suppliers dropdown comes from SupplierDAO)</label>
                <input type="number" id="supplierId" name="supplierId" value="${material.supplierId}">
            </div>

            <button type="submit" class="btn">Save</button>
            <a href="${pageContext.request.contextPath}/materials" class="btn" style="background:#888;">Cancel</a>
        </form>
    </div>
</main>
</body>
</html>
