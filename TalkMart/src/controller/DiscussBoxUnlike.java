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
 * Servlet implementation class DiscussBoxUnlike
 */
@WebServlet("/DiscussBoxUnlike")
public class DiscussBoxUnlike extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try
		{
			String uid = request.getParameter("uid");
			
			HttpSession discuss_topic_id = request.getSession(false);
			String discussTopicId = (String)discuss_topic_id.getAttribute("discuss_topic_id");
			HttpSession discuss_category = request.getSession(false);
			String discussCategory = (String)discuss_category.getAttribute("discuss_category");
			
			String sql = "select dislike from "+discussCategory+" where uid=?";
			Connection cn = Db2Connector.getCn(); 
			PreparedStatement ps = cn.prepareStatement(sql);
			ps.setString(1, uid);
			ResultSet rs = ps.executeQuery();
			int count=0;
			
			while(rs.next())
			{
					if(rs.getString(1)==null || rs.getString(1).equals(""))
					{
						count = 0;
					}		
					else
					{
						count = Integer.parseInt(rs.getString(1));
					}
			
			}
			count++;
			
			sql = "update "+discussCategory+" set dislike=? where uid=?";
			ps = cn.prepareStatement(sql);
			ps.setString(1, String.valueOf(count));
			ps.setString(2, uid);
			ps.execute();
			
			RequestDispatcher rd = request.getRequestDispatcher("discussBox.jsp");
			rd.forward(request, response);
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}

}
