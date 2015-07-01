<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<script type="text/javascript">
function cursorOnUsername() {
	document.getElementById("username").focus();
}
</script>

<title>A Perfect Holiday - Login</title>
</head>
<body onload="cursorOnUsername()">
	<h1>Login</h1>
	<form action="HomePage" method="post">
		<p>
			UserName : 
			<input id="username" name="username" value="">
		</p>
		<p>
			Password : 
			<input id="password" name="password" type="password" value="" />
		</p>
		<p>
			<input type="submit" />
		</p>
		<br/>		
		<p>
			<a href="UserCreate"> SignUp </a>
		</p>	
	</form>
	
	<p style="color: red">
		${failure}
	</p>
</body>
</html>