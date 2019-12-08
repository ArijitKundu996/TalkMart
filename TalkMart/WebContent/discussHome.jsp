<!DOCTYPE html>
<%@page import="connector.*,java.sql.*"%>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Discuss Box</title>
<link href="CompanyLogo/theme/theme2.css" rel="stylesheet" type="text/css"/>
<%
try
{
	HttpSession userid = request.getSession(false);
	String user_id = (String)userid.getAttribute("userid");
	HttpSession discuss_category = request.getSession(false);
	String discussTopic = (String)discuss_category.getAttribute("discuss_category");
	String topic = Character.toUpperCase(discussTopic.charAt(0)) + discussTopic.substring(1);
		
	Connection cn = Db2Connector.getCn();
	String sql = "select name from users where userid=?";
	PreparedStatement ps = cn.prepareStatement(sql);
	ps.setString(1,user_id);
	ResultSet rs = ps.executeQuery();
	String name="";
	while(rs.next())
	{
		name = rs.getString(1);
	}
	%>
<p style="font-size:17px;">&nbsp;&nbsp;&nbsp;&nbsp; Welcome &nbsp;&nbsp;<b><i><%=name %></i></b></p>
<p style="font-size:17px;">&nbsp;&nbsp;&nbsp;&nbsp; Active Category : <b><i><%=topic %></i></b></p>	
</head>
<body background="CompanyLogo/discusshome.jpg" style="background-size:1366px 720px;">
<img src="CompanyLogo/talkmart.jfif" height="108px" width="400px" style="position:absolute; left:35%;top:0px; ">
<table width="100%" height="100%" align="center">
	 <tr>
		<td height="10px"></td>
	</tr>
	<tr id="navigation">
		<td><a href="discussHome.jsp">Home</a></td>
		<td><a href="userProfile.jsp" target="iframe2">My Profile</a></td>
		<td><a href="" target="iframe2">Dashboard</a></td>
		<td><a href="discussBox.jsp" target="iframe2">Discuss Box</a></td>
		<td><a href="discussTopics.jsp" target="iframe2">Topics</a></td>
		<td><a href="privateChatDiscuss.jsp" target="iframe2">Private Chat</a></td>
		<td><a href="" target="iframe2">Trending</a></td>
		<td><a href="changeCategory.html" target="iframe2">Change Category</a></td>
		<td><a href="home.html">Sign Out</a></td>
	</tr>
</table>
	
<center><iframe src="iframeDesign.html" style="border:none;" height="509px" width="1350px" name="iframe2"> </iframe></center>
<%}
catch(Exception e)
{
	out.println(e);
}
%>
</body>
</html>