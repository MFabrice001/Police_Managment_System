<%@ include file="/components/taglib.jsp" %>
<%@ include file="/components/header.jsp" %>
<div class="register-container">
    <h2>Register</h2>
    <form action="${pageContext.request.contextPath}/register" method="post">
        <input type="text" name="username" placeholder="Username" required>
        <input type="email" name="email" placeholder="Email" required>
        <input type="password" name="password" placeholder="Password" required>
        <button type="submit">Register</button>
    </form>
</div>
<%@ include file="/components/footer.jsp" %>
