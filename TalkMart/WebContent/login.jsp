<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Login</title>
<link rel="stylesheet" type="text/css"  href="css/style.css">
</head>

<body background="img/login.jpg" style="background-size:1366px 720px;">
<form action="LogIn">
<%
try
{
	String topic = request.getParameter("discuss_topic");
	HttpSession discuss_category = request.getSession();
	discuss_category.setAttribute("discuss_category", topic);
}
catch(Exception e)
{
	out.println(e);
}
%>
<br><br>
<h1 style="color:#FFFFFF;text-align:center;font-size:500%">Log IN</h1>

<center>
<input type="text" name="userid" placeholder="Enter User ID"><br><br>
<input type="password" name="password" placeholder="Enter Password"><br><br>
<input type="submit" class="btn btn-default btn-lg page-scroll" value="Log IN"><br><br>
<a href="signup.html"><h3 style="color:silver;font-size:35px;text-align:center;"><i>Become a Member</i></h3></a>
</center>
</form>
</body>
</html>