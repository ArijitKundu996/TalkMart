package controller;
import connector.*;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class DiscussTopics2
 */
@WebServlet("/DiscussTopics2")
public class DiscussTopics2 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try
		{
			String topic_id= request.getParameter("discuss_topic_id");
			HttpSession discuss_topic_id = request.getSession();
			discuss_topic_id.setAttribute("discuss_topic_id", topic_id);
			
			RequestDispatcher rd = request.getRequestDispatcher("discussBox.jsp");
			rd.forward(request,response);
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}

}
