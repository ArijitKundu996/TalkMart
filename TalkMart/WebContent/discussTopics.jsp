<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Discuss Topics</title>
<style>
.button {
    background-color: #4CAF50; /* Green */
    border: none;
    color: white;
    padding: 4px 8px;
    text-align: center;
    text-decoration: none;
    display: inline-block;
    font-size: 13px;
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
.header {
  padding: 10px 16px;
  background: #B0C1CE;
}

.content {
  padding: 16px;
}

.sticky {
  position: fixed;
  top: 0%;
  left: 0%;
  width: 100%;
  text-align:center;
}

.sticky + .content {
  padding-top: 100px;
}
</style>
</head>
<body background="CompanyLogo/dischomeback.jfif" style="background-size:1366px 720px;">
<form action="DiscussTopics">
<table width="80%" align="center">
<%@page import="connector.*,java.sql.*" %>
<%
try
{
	HttpSession discuss_category = request.getSession(false);
	String discussCategory = (String)discuss_category.getAttribute("discuss_category");
	
	String sql = "select id,name from "+discussCategory.toLowerCase()+" where type=?";
	Connection cn = Db2Connector.getCn();
	PreparedStatement ps = cn.prepareStatement(sql);
	ps.setString(1, "Topic");
	ResultSet rs = ps.executeQuery();
	int temp=0;
	while(rs.next())
	{	
		if(temp==0)
		{  %>
			<div class="header" id="myHeader">
			<h2 style="color:red;text-align:center;"><i>Choose a Topic to start discussion</i></h2>
			</div>
			<tr height="40px">
			<th width="20%" align="center" style="font-size:20px;color:red;">ID</th>
			<th width="60%" align="center" style="font-size:20px;color:red;">Name</th>
			<th width="20%"></th>
			</tr>
			
<%		}
		temp=1;%>
		<tr height="20px">
		<td width="20%" align="center" style="font-size:20px;"><%=rs.getString(1).substring(rs.getString(1).indexOf('_')+1) %></td>
		<td width="60%" align="center" style="font-size:20px;"><%=rs.getString(2) %></td>
		<td width="20%" align="center" style="font-size:20px;"><input type="button" onclick="discuss(this)" id="<%=rs.getString(1)%>" class="button button2" value="Discuss"></td>	
<%	}	%>

	</table>
<%	if(temp==0)
	{	%>
		<h1 style="color:red;text-align:center;"><i>Add a new topic to start discussion</i></h1>
		<br><br><br><br><br><br><br>
<%	} %>

<script type="text/javascript">
function discuss(obj) {
	var rid = obj.getAttribute("id");
	window.location.href='DiscussTopics2?discuss_topic_id='+rid;
	return false;
}
</script>
<%
}
catch(Exception e)
{
	out.println(e);
}
%>
<br><br><br><br>
<center>
<textarea rows="1" cols="70" name="topic_name" placeholder="New topic name"></textarea><br><br>
<input type="submit" value="Add Topic" class="button button2"><br><br>
</center>

</form>
<script>
window.onscroll = function() {myFunction()};

var header = document.getElementById("myHeader");
var sticky = header.offsetTop;

function myFunction() {
  if (window.pageYOffset >= sticky) {
    header.classList.add("sticky");
  } else {
    header.classList.remove("sticky");
  }
}
</script>
</body>
</html>