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
<jsp:include page="/UserHome.jsp"></jsp:include>
<h1>City Hotels</h1>
        <table border="1">
            <tr>
                <th>Hotel</th>
                 <th>Address</th>
                <th>ZipCode</th>
                 <th>CityName</th>
                <th>Phone</th>
                <th>Website</th> 
          </tr>
            <c:forEach items="${hotels}" var="hotel" >
                <tr>
                    <td><c:out value="${hotel.getHotelName()}" /></td>
                    <td><c:out value="${hotel.getAddress()}" /></td>
                    <td><c:out value="${hotel.getZipCode()}" /></td>
                     <td><c:out value="${hotel.getCity().getCityName()}" /></td>
                    <td><c:out value="${hotel.getPhone()}" /></td>
                    <td><c:out value="${hotel.getWebsite()}" /></td> 
                </tr>
            </c:forEach>
       </table>
</body>
</html>