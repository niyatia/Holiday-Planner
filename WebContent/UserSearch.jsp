<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<form action="UserSearch" method="post">
		<h1>Search for a User by FirstName</h1>
		<p>
			<label for="firstname">FirstName</label>
			<input id="firstname" name="firstname" value="${fn:escapeXml(param.firstname)}">
		</p>
		<p>
			<input type="submit">
			<br/><br/><br/>
			<span id="successMessage"><b>${messages.success}</b></span>
		</p>
	</form>
	<br/>
	<div id="userCreate"><a href="UserCreate">Create User</a></div>
	<br/>
	<h1>Matching Users</h1>
        <table border="1">
            <tr>
                <th>UserName</th>
                <th>FirstName</th>
                <th>LastName</th>
                <th>DoB</th>
                <th>Email</th>
                <th>Phone</th>
                <th>Reviews</th>
                <th>Delete User</th>
                <th>Update User</th>
            </tr> 
            <c:forEach items="${users}" var="user" >
                <tr>
                    <td><c:out value="${user.getUserName()}" /></td>
                    <td><c:out value="${user.getFirstName()}" /></td>
                    <td><c:out value="${user.getLastName()}" /></td>
                    <td><fmt:formatDate value="${user.getDob()}" pattern="yyyy-MM-dd"/></td>
                    <td><c:out value="${user.getEmail()}" /></td>
                    <td><c:out value="${user.getPhone()}" /></td>
                    <td><a href="UserReview?username=<c:out value="${user.getUserName()}"/>">Reviews</a></td>
                    <td><a href="UserDelete?username=<c:out value="${user.getUserName()}"/>">Delete</a></td>
                    <td><a href="UserUpdate?username=<c:out value="${user.getUserName()}"/>">Update</a></td>
                </tr>
            </c:forEach>
       </table>

</body>
</html>