<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Private Chat</title>
<style>
.button {
    background-color: #4CAF50; /* Green */
    border: none;
    color: white;
    padding: 4px 8px;
    text-align: center;
    text-decoration: none;
    display: inline-block;
    font-size: 12px;
    margin: 4px 2px;
    -webkit-transition-duration: 0.4s; /* Safari */
    transition-duration: 0.4s;
    cursor: pointer;
}
.button2 {
    background-color: white; 
    color: black; 
    border: 2px solid #008CBA;
}

.button2:hover {
    background-color: #008CBA;
    color: white;
}

</style>
</head>
<body background="CompanyLogo/dischomeback.jfif" style="background-size:1366px 720px;">
<%@page import="connector.*,java.sql.*" %>
<%
try
{	
	HttpSession userid = request.getSession(false);
	String user_id = (String)userid.getAttribute("userid");
	HttpSession discuss_category = request.getSession(false);
	String discussCategory = (String)discuss_category.getAttribute("discuss_category");
	
	String sql = "select chat_with,topic from "+discussCategory+" where chat_by=?";
	Connection cn = Db2Connector.getCn();
	PreparedStatement ps = cn.prepareStatement(sql);
	ps.setString(1, user_id);
	ResultSet rs = ps.executeQuery();
	int temp=0,count=0;
	while(rs.next())
	{
		temp = 1;
		if(count==0)
		{
			count=1;	%>
			
			<table width="80%" align="center">	
			<h2 style="color:red;text-align:center;"><i>Choose to start Private Chat</i></h2>
	<%	}
		if(rs.getString(2).equalsIgnoreCase("Content"))
		{
			continue;
		}
		
		String sql2 = "select name,picture from users where userid=?";
		cn = Db2Connector.getCn();
		PreparedStatement ps2 = cn.prepareStatement(sql2);
		ps2.setString(1, rs.getString(1));
		ResultSet rs2 = ps2.executeQuery();
		while(rs2.next())
		{	%>
			
			<tr height="70px">
				<td width="20%" align="center"><img src="<%=rs2.getString(2) %>" height="60" width="60" id="<%=rs.getString(1) %>" onclick="viewDetails(this)"></td>
				<td width="60%" align="center" style="font-size:20px;" id="<%=rs.getString(1) %>" onclick="viewDetails(this)"><%=rs2.getString(1) %></td>
				<td width="20%" align="center"><input type="button" value="Start Private Chat" class="button button2" id="" onclick="startChat(this)"></td>
			</tr>

<script type="text/javascript">
function viewDetails(obj) {
	var rid = obj.getAttribute("id");
	window.location.href='userProfile2.jsp?user_id='+rid;
	return false;
}
</script>
<script type="text/javascript">
function startChat(obj)
{
	var rid = obj.getAttribute("id");
	window.location.href='PrivateChat?user_id='+rid;
	return false;
}
</script>

	<%	}
	}
	
	if(temp == 0)
	{ %>
		<h1 style="color:red;text-align:center;"><i>You have no Private Chats with anyone..</i></h1>	
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