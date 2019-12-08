package controller;
import connector.*;

import java.io.IOException;
import java.io.PrintWriter;
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
 * Servlet implementation class LogIn
 */
@WebServlet("/LogIn")
public class LogIn extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try
		{
			String uid,password,a,b;
			uid = request.getParameter("userid");
			password = request.getParameter("password");
			
			String sql = "select userid,password from users";
			Connection cn = Db2Connector.getCn();
			PreparedStatement ps = cn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{	
				if(uid.equalsIgnoreCase(rs.getString(1)) && password.equalsIgnoreCase(rs.getString(2)))
				{
					a = uid.substring(0, uid.indexOf('_')).toUpperCase();
					b = uid.substring(uid.indexOf('_'));
					HttpSession userid = request.getSession();
					userid.setAttribute("userid", a+b);
					RequestDispatcher rd = request.getRequestDispatcher("discussHome.jsp");
					rd.forward(request, response);
				}
			}
			PrintWriter out = response.getWriter();
			out.println("<h1><font color='white'>        Invalid Login Details..!!</font></h1>");
			RequestDispatcher rd2 = request.getRequestDispatcher("login.html");
			rd2.include(request, response);
			
		}
		catch(Exception e)
		{
			System.out.println(e);	
		}
	}

}
