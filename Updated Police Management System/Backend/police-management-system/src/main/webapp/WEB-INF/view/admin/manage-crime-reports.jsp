<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<h2>Manage Crime Reports</h2>

<table>
    <thead>
        <tr>
            <th>ID</th>
            <th>User</th>
            <th>Description</th>
            <th>Date</th>
            <th>Actions</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach items="${crimeReports}" var="report">
            <tr>
                <td>${report.id}</td>
                <td>${report.user.username}</td>
                <td>${report.description}</td>
                <td>${report.date}</td>
                <td>
                    <form action="/admin/crime-reports/delete/${report.id}" method="post">
                        <button type="submit">Delete</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </tbody>
</table>
