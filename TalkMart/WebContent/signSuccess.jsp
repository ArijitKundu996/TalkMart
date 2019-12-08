<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Welcome..!!</title>
</head>
<body background="img/signsuccess.jpg" style="background-size:1366px 700px;">
<%@page import="connector.*,java.sql.*" %>
<%
try
{
	HttpSession userid = request.getSession();
	String uid = (String)userid.getAttribute("userid");%>
	
	<br><br>
	<h1 style="color: #FFFFFF;font-size: 500%;text-align: center;">WELCOME</h1>
	<pre style="color: #FFFFFF;text-align:center;font-size:20px;">
	 
Here at TalkMart, we provide a quick and direct way of expressing the seemingly simplistic
ideas that crop up, the minute they crop up, which would also obtain immediate response
from fellow listeners and give a means for moulding them into plans and executions eventually.
No matter how bizarre the topic is or how childish your views may seem, this is a platform
where they will be respected and welcome.
	    
Your journey has just begun. And more than the destination, it is important that you enjoy the ride! 

The <b><u>USER ID</u></b> is needed for signing into our website.
			Your <b><u>USER ID</u></b> is : <b><%=uid %></b>                        
	</pre>
	<a href="discussHome.jsp"><h1 style="color:pink;text-align:center">Enter the WORLD OF DISCUSSION</h1></a><br>
<%	
}
catch(Exception e)
{
	out.println(e);
}
%>



</body>
</html>