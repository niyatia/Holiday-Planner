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
<h1>User Reviews</h1>
        <table border="1">
            <tr>
                <th>ReviewId</th>
                <th>Review</th>
                <th>UserName</th>
                <th>CityName</th>               
                <th>Delete Review</th>
            </tr>
            <c:forEach items="${reviews}" var="review" >
                <tr>
                    <td><c:out value="${review.getReviewId()}" /></td>
                    <td><c:out value="${review.getReview()}" /></td>
                    <td><c:out value="${review.getUserName().getUserName()}" /></td>
                    <td><c:out value="${review.getCityName().getCityName()}" /></td>                  
                    <td><a href="ReviewDelete?reviewid=<c:out value="${review.getReviewId()}"/>">Delete</a></td>
                </tr>
            </c:forEach>
       </table>
</body>
</html>