<%@ include file="/components/taglib.jsp" %>
<%@ include file="/components/header.jsp" %>
<div class="login-container">
    <h2>Login</h2>
    <form action="${pageContext.request.contextPath}/login" method="post">
        <input type="text" name="username" placeholder="Username" required>
        <input type="password" name="password" placeholder="Password" required>
        <button type="submit">Login</button>
    </form>
</div>
<%@ include file="/components/footer.jsp" %>
