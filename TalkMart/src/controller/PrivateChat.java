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
 * Servlet implementation class PrivateChat
 */
@WebServlet("/PrivateChat")
public class PrivateChat extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try
		{
			HttpSession userid = request.getSession(false);
			String userID = (String)userid.getAttribute("userid");
			HttpSession discuss_category = request.getSession(false);
			String discussCategory = (String)discuss_category.getAttribute("discuss_category");
			
			String chat_user_id = request.getParameter("user_id");
			
			HttpSession chat_with = request.getSession();
			chat_with.setAttribute("chat_with", chat_user_id);
			
			String sql = "select chat_with from "+discussCategory+" where chat_by=?";
			Connection cn = Db2Connector.getCn();
			PreparedStatement ps = cn.prepareStatement(sql);
			ps.setString(1, userID);
			ResultSet rs = ps.executeQuery();
			int temp=0;
			while(rs.next())
			{
				if(rs.getString(1).equalsIgnoreCase(chat_user_id))
				{
					temp = 1;
					break;
				}
			}
			if(temp == 0)
			{
				sql = "select count(id) from "+discussCategory+" where type=?";
				cn = Db2Connector.getCn();
				ps = cn.prepareStatement(sql);
				ps.setString(1, "PrivateChat");
				rs = ps.executeQuery();
				int count=0;
				while(rs.next())
				{
					count = Integer.parseInt(rs.getString(1));
				}
				count++;
				
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
				
				sql = "insert into "+discussCategory+" (uid,id,type,chat_by,chat_with,topic) values (?,?,?,?,?,?)";
				cn = Db2Connector.getCn();
				ps = cn.prepareStatement(sql);
				ps.setString(1, String.valueOf(count2));
				ps.setString(2, "PrivateChat_"+count);
				ps.setString(3, "PrivateChat");
				ps.setString(4, userID);
				ps.setString(5, chat_user_id);
				ps.setString(6, "No_Content");
				ps.execute();
				
				sql = "insert into "+discussCategory+" (uid,id,type,chat_by,chat_with,topic) values (?,?,?,?,?,?)";
				cn = Db2Connector.getCn();
				ps = cn.prepareStatement(sql);
				ps.setString(1, String.valueOf(count2+1));
				ps.setString(2, "PrivateChat_"+(count+1));
				ps.setString(3, "PrivateChat");
				ps.setString(4, chat_user_id);
				ps.setString(5, userID);
				ps.setString(6, "No_Content");
				ps.execute();
			}
			
			RequestDispatcher rd = request.getRequestDispatcher("privateChatDiscuss.jsp");
			rd.forward(request, response);
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}

}
