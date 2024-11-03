<%@ include file="/components/taglib.jsp" %>
<%@ include file="/components/header.jsp" %>
<%@ include file="/components/navbar.jsp" %>
<div class="main-content">
    <%@ include file="/components/sidebar.jsp" %>
    <section>
        <h2>Manage Users</h2>
        <!-- User management table -->
        <table class="user-table">
            <tr><th>ID</th><th>Name</th><th>Email</th><th>Role</th></tr>
            <!-- Sample user row -->
            <tr><td>1</td><td>John Doe</td><td>john@example.com</td><td>Admin</td></tr>
        </table>
    </section>
</div>
<%@ include file="/components/footer.jsp" %>
