package controller;
import connector.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class DiscussTopics
 */
@WebServlet("/DiscussTopics")
public class DiscussTopics extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try
		{
			HttpSession userid = request.getSession(false);
			String user_id = (String)userid.getAttribute("userid");
			HttpSession discuss_category = request.getSession(false);
			String discussCategory = (String)discuss_category.getAttribute("discuss_category");
			String topic_name = request.getParameter("topic_name");
			
			String sql = "select count(id) from "+discussCategory+" where type=?";
			Connection cn = Db2Connector.getCn();
			PreparedStatement ps = cn.prepareStatement(sql);
			ps.setString(1, "Topic");
			ResultSet rs = ps.executeQuery();
			int count = 0;
			while(rs.next())
			{
				count = Integer.parseInt(rs.getString(1)) + 1;
			}
			
			sql = "select count(uid) from "+discussCategory;
			cn = Db2Connector.getCn();
			ps = cn.prepareStatement(sql);
			rs = ps.executeQuery();
			int count2=0;
			while(rs.next())
			{
				count2 = Integer.parseInt(rs.getString(1));
			}
			count2++;
			
			sql = "insert into "+discussCategory+" (id,type,name,posted_by,uid) values(?,?,?,?,?)";
			ps = cn.prepareStatement(sql);
			ps.setString(1, "Topic_"+count);
			ps.setString(2, "Topic");
			ps.setString(3, topic_name);
			ps.setString(4, user_id);
			ps.setString(5, String.valueOf(count2));
			ps.execute();
			
			RequestDispatcher rd = request.getRequestDispatcher("discussTopics.jsp");
			rd.forward(request, response);
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}

}
