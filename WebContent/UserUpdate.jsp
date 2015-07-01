<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Update Personal Information</title>
</head>
<body>
<jsp:include page="/UserHome.jsp"></jsp:include>
	<h1>Update User</h1>
	<form action="UserUpdate" method="post">
		<p>
			<label for="username">UserName you wish to update</label>
			<input id="username" name="username" value="${username}">
		</p>
		<p> New FirstName
			<input type="text" id="firstname" name="firstname" value="" />
		</p>
		<p> New LastName
			<input type="text" id="lastname" name="lastname" value="" />
		</p>
		<p> New Email
			<input type="text" id="email" name="email" value="" />
		</p>
		<p>
			New Phone
			<input type="text" id="phone" name="phone" value="" />
		</p>
		<p>
			<input type="submit" />
		</p>
	</form>
	<br />
	<p>
		${messages.success}
	</p>
</body>
</html>