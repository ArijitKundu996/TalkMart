<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>My Profile</title>
</head>
<body background="CompanyLogo/dischomeback.jfif" style="background-size:1366px 720px;">
<table width="100%">
<%@page import="connector.*,java.sql.*" %>
<%
try
{
	HttpSession userid = request.getSession(false);
	String user_id = (String)userid.getAttribute("userid");
	
	String sql = "select * from users where userid=?";
	Connection cn = Db2Connector.getCn();
	PreparedStatement ps = cn.prepareStatement(sql);
	ps.setString(1, user_id);
	ResultSet rs = ps.executeQuery();
	while(rs.next())
	{	%>
		<tr><td colspan="2" align="center"><img src="<%=rs.getString(5)%>" width="280" height="300" border="1" alt="User profile picture not available"></td></tr>
		<tr height=""><td colspan="2" align="center">Profile Picture</td></tr>
		<tr height="35px">
		<td width="50%" align="center" style="font-size:20px;color:red;"><b>User ID</b></td>
		<td width="50%" align="center" style="font-size:20px;"><%=rs.getString(1) %></td>
		</tr>
		<tr height="35px">
		<td width="50%" align="center" style="font-size:20px;color:red;"><b>Name</b></td>
		<td width="50%" align="center" style="font-size:20px;"><%=rs.getString(2) %></td>
		</tr>
		<tr height="35px">
		<td width="50%" align="center" style="font-size:20px;color:red;"><b>Email ID</b></td>
		<td width="50%" align="center" style="font-size:20px;"><%=rs.getString(3) %></td>
		</tr>
		<tr height="35px">
		<td width="50%" align="center" style="font-size:20px;color:red;"><b>Nationality</b></td>
		<td width="50%" align="center" style="font-size:20px;"><%=rs.getString(4) %></td>
		</tr>
		
<%	}
}
catch(Exception e)
{
	out.println(e);
}
%>
</table>
</body>
</html>