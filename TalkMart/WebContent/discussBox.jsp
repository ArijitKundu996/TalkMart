<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Discuss Box</title>
<style>
.topleft{
	position: absolute;
    top: 8px;
    left: 16px;
    
}
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
.noborders td {
        border:0;
        
    }
</style>
</head>
<body background="CompanyLogo/dischomeback.jfif" style="background-size:1366px 720px;">

<%@page import="connector.*,java.sql.*" %>
<%
try
{
	HttpSession discuss_topic_id = request.getSession(false);
	String discussTopicId = (String)discuss_topic_id.getAttribute("discuss_topic_id");
	if(discussTopicId==null)
	{
		out.println("<h1 class='topleft'>Please choose a topic to discuss</h1><br><br>");
		RequestDispatcher rd = request.getRequestDispatcher("discussTopics.jsp");
		rd.include(request,response);
		return;
	}
	
	HttpSession userid = request.getSession(false);
	String user_id = (String)userid.getAttribute("userid");
	HttpSession discuss_category = request.getSession(false);
	String discussCategory = (String)discuss_category.getAttribute("discuss_category");
	
	String sql = "select count(*) from "+discussCategory+" where topic=?";
	Connection cn = Db2Connector.getCn();
	PreparedStatement ps = cn.prepareStatement(sql);
	ps.setString(1, discussTopicId);
	ResultSet rs = ps.executeQuery();
	int n=0;
	while(rs.next())
	{
		n = Integer.parseInt(rs.getString(1));
	}
	String arr[][] = new String[n][5];
	int k=0;
	
	sql = "select name,posted_by,id,uid,picture from "+discussCategory+" where topic=?";
	cn = Db2Connector.getCn();
	ps = cn.prepareStatement(sql);
	ps.setString(1, discussTopicId);
	rs = ps.executeQuery();
	while(rs.next())
	{	
		arr[k][0] = rs.getString(3).substring(rs.getString(3).indexOf('_')+1);
		arr[k][1] = rs.getString(2);
		arr[k][2] = rs.getString(1);
		arr[k][3] = rs.getString(4);
		arr[k++][4] = rs.getString(5);
	}
	
	String temp;
	for(int i=0 ; i<n-1 ; i++)
	{
		for(int j=0 ; j<n-1-i ; j++)
		{
			if(Integer.parseInt(arr[j][0]) > Integer.parseInt(arr[j+1][0]))
			{
				temp = arr[j][0];
				arr[j][0] = arr[j+1][0];
				arr[j+1][0] = temp;
				
				temp = arr[j][1];
				arr[j][1] = arr[j+1][1];
				arr[j+1][1] = temp;
				
				temp = arr[j][2];
				arr[j][2] = arr[j+1][2];
				arr[j+1][2] = temp;
				
				temp = arr[j][3];
				arr[j][3] = arr[j+1][3];
				arr[j+1][3] = temp;
				
				temp = arr[j][4];
				arr[j][4] = arr[j+1][4];
				arr[j+1][4] = temp;
			}
		}
	}	%>
	<br><br>
	<table width="97%" border="11" align="center">
	
<%	for(int i=0 ; i<n ; i++)
	{	
		sql = "select name,picture from users where userid=?";
		cn = Db2Connector.getCn();
		ps = cn.prepareStatement(sql);
		ps.setString(1, arr[i][1]);
		rs = ps.executeQuery();
		while(rs.next())
		{	
			if(arr[i][1].equalsIgnoreCase(user_id))
			{	%>
				
				<tr height="30px" class="noborders">
			<tr class="noborders">
				<td  width="8%" align="center" rowspan="3"></td>
				<td align="left" width="50%"></td>
				<td align="right" width="50%"><div style="margin-right:2%; color:red;">Me</div></td>
				<td  width="8%" align="center" rowspan="3"><img src="<%=rs.getString(2)%>" height="50" width="50"></td>
			</tr>
			<tr class="noborders"><td width="50%"></td>
				<td width="50%">
				
				<%
					if(! arr[i][4].equals("0"))
					{	%>
						<center><img src="<%=arr[i][4] %>" height="300" width="400"></center><br>
				<%	}
				%>
					
					<div style="font-size:20px;margin-right: 4%;text-align: right;"><%=arr[i][2] %></div>
				</td>
			</tr>
			<tr class="noborders">
				<td width="50%"></td>
				<td width="50%"></td>
			</tr>
		</tr>
	<%			continue;
			}
			%>
		
		<tr height="30px" class="noborders">
			<tr class="noborders">
				<td  width="8%" align="center" rowspan="3"><img src="<%=rs.getString(2)%>" height="60" width="60" id="<%=arr[i][1]%>" onclick="viewDetails(this)"></td>
				<td align="left" width="50%"><div style="color:red;" id="<%=arr[i][1] %>" onclick="viewDetails(this)"><%=rs.getString(1) %></div></td>
				<td width="50%"></td>
				<td rowspan="3" width="8%"></td>
			</tr>
			<tr class="noborders">
				<td width="50%">
				
				<%
					if(! arr[i][4].equals("0"))
					{	%>
						<center><img src="<%=arr[i][4] %>" height="300" width="400"></center><br>
				<%	}
				%>
				
				<div style="font-size:20px;margin-left: 2%;text-align: justify;"><%=arr[i][2] %></div></td>
				<td width="50%"></td>
			</tr>
			<tr class="noborders">
				<td width="50%"><input type="image" style="margin-left: 4%;margin-right: 4%;margin-top: 1%;" src="img/like.png" height="20" width="20" alt="Like" id="<%=arr[i][3] %>" onclick="like(this)">
								<input type="image" style="margin-top: 1%;" src="img/unlike.jpg" height="20" width="25" alt="Unlike" id="<%=arr[i][3] %>" onclick="unlike(this)">
								<input type="button" class="button button2" style="margin-left: 4%;font-size:10px;" value="Start Private Chat" id="<%=arr[i][1] %>" onclick="startChat(this)">
				</td>
				<td width="50%"></td>
			</tr>
		</tr>
<script type="text/javascript">
function viewDetails(obj) {
	var rid = obj.getAttribute("id");
	window.location.href='userProfile2.jsp?user_id='+rid;
	return false;
}
</script>
<script type="text/javascript">
function like(obj) {
	var rid = obj.getAttribute("id");
	window.location.href='DiscussBoxLike?uid='+rid;
	return false;
}
</script>
<script type="text/javascript">
function unlike(obj) {
	var rid = obj.getAttribute("id");
	window.location.href='DiscussBoxUnlike?uid='+rid;
	return false;
}
</script>
<script type="text/javascript">
function startChat(obj) {
	var rid = obj.getAttribute("id");
	window.location.href='PrivateChat?user_id='+rid;
	return false;
}
</script>
<%		}		
	}	
}
catch(Exception e)
{
	out.println(e);
}
%>
<form action="DiscussBox" method="post" enctype="multipart/form-data">
<tr height="20px"></tr>
<tr class="noborders">
<td align="center" colspan="4"><textarea rows="4" cols="170" placeholder="Write anything here" name="discuss_content"></textarea></td>
</tr>

<tr class="noborders">
<td align="center" colspan="4"><input type="submit" value="Send" class="button button2" style="margin-bottom: 2%;">
	<input type="file" name="picture" style="margin-left:1%; margin-top: 1%;" height="25" width="25">
</td>
</tr>
</table>
<br><br>
</form>
</body>
</html>