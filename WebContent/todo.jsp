<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Todo Manage Page</title>
<style>
table,th,td {
	border: .1px solid black;
}
</style>
</head>
<script>
function newDoc() {
    window.location.assign("http://localhost:8080/MyMongoWeb")
}
</script>
<body>
	<%-- Person Add/Edit logic --%>
	<c:if test="${requestScope.error ne null}">
		<strong style="color: red;"><c:out
				value="${requestScope.error}"></c:out></strong>
	</c:if>
	<c:if test="${requestScope.success ne null}">
		<strong style="color: green;"><c:out
				value="${requestScope.success}"></c:out></strong>
	</c:if>
	<c:url value="/addTask" var="addURL"></c:url>
	<c:url value="/editTask" var="editURL"></c:url>

	
	<%-- Add Request --%>
	<c:if test="${requestScope.task eq null}">
		<form action='<c:out value="${addURL}"></c:out>' method="post">
			Task: <input type="text" name="task"><br>Status: <input
				type="text" name="status"><br><br>Priority: <input
				type="text" name="priority"><br><br>DueDate: <input
				type="text" name="duedate"><br><input type="submit"
				value="Add Task">
		</form>
	</c:if>
	
	
	<%-- Edit Request --%>
	<c:if test="${requestScope.task ne null}">
		<form action='<c:out value="${editURL}"></c:out>' method="post">
			ID: <input type="text" value="${requestScope.task.id}"
				readonly="readonly" name="id"><br> Task: <input
				type="text" value="${requestScope.task.task}" name="task"><br>
			Status: <input type="text" value="${requestScope.task.status}"
				name="status"><br> Priority: <input type="text" value="${requestScope.task.priority}"
				name="priority"><br> Duedate: <input type="text" value="${requestScope.task.duedate}"
				name="duedate"><br> <input type="submit"
				value="Edit Task">
		</form>
	</c:if>
	<input type="button" value="Back" onclick="newDoc()">

	<%-- Persons List Logic --%>
	<c:if test="${not empty requestScope.tasks}">
		<table>
			<tbody>
				<tr>
					<th>ID</th>
					<th>Task</th>
					<th>Status</th>
					<th>Priority</th>
					<th>Duedate</th>
					<th>Edit</th>
					<th>Delete</th>
				</tr>
				<c:forEach items="${requestScope.tasks}" var="tasks">
					<c:url value="/editTask" var="editURL">
						<c:param name="id" value="${tasks.id}"></c:param>
					</c:url>
					<c:url value="/deleteTask" var="deleteURL">
						<c:param name="id" value="${tasks.id}"></c:param>
					</c:url>
					<tr>
						<td><c:out value="${tasks.id}"></c:out></td>
						<td><c:out value="${tasks.task}"></c:out></td>
						<td><c:out value="${tasks.status}"></c:out></td>
						<td><c:out value="${tasks.priority}"></c:out></td>
						<td><c:out value="${tasks.duedate}"></c:out></td>
						<td><a
							href='<c:out value="${editURL}" escapeXml="true"></c:out>'>Edit</a></td>
						<td><a
							href='<c:out value="${deleteURL}" escapeXml="true"></c:out>'>Delete</a></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</c:if>
</body>
</html>