    
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add a City</title>
</head>
<body>
<jsp:include page="/AdminHome.jsp"></jsp:include>
	<h1>Create City for - A Perfect Holiday</h1>
	<form action="CityCreate" method="post">
		<p>
			<label for="city">City</label>
			<input id="city" name="city" value="">
		</p>
		<p>
			Description
			<input id="description" name="description" value="" />
		</p>
		<p>
			<label for="photo">Photo</label>
			<input id="photo" name="photo" value="">
		</p>
		<p>
			Region
			<select id="region" name="region">
				<c:forEach items="${regions}" var="region">
					<option <c:if test="${selectedRegion == region.toString()}"> selected="selected" </c:if> > 
						<c:out value="${region}"> </c:out> 
					</option>
				</c:forEach>
			</select>
		</p>		
		<p>
			<input type="submit">
		</p>
	</form>
	<br/><br/>
	<p>
		<span id="successMessage"><b>${messages.success}</b></span>		
	</p>
</body>
</html>