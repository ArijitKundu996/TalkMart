package controller;
import javax.servlet.annotation.MultipartConfig; /*Needs to be present*/
import connector.*;

import java.io.File;
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
import javax.servlet.http.Part;

/**
 * Servlet implementation class SignUp
 */
@WebServlet("/SignUp")
@MultipartConfig(fileSizeThreshold=1024*1021*2,maxFileSize=1024*1024*10,maxRequestSize=1024*1024*50) /*Annotation*/
public class SignUp extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String SAVE_DIR="User";

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String imgpath = null;
		try
		{	
			String uid="USER_",name,email,nationality,password;
			name = request.getParameter("name");
			email = request.getParameter("email");
			nationality = request.getParameter("nationality");
			password = request.getParameter("password");
			
			String filepath = "D:"+File.separator+"Advanced JAVA"+File.separator+"TalkMart"+File.separator+"WebContent"+File.separator+SAVE_DIR;
			File directory = new File(filepath);
			if(!directory.exists())
			{
				directory.mkdir();
			}
			Part part = request.getPart("picture");
			String path = extractpath(part);
			String filename = path.substring(path.lastIndexOf("\\")+1,path.length());
			imgpath = filepath+File.separator+filename;
			part.write(imgpath);
			
			/*Google Chrome cannot show images in html(local resources) for security purposes. But it can be sorted 
			if we change the absolute path of the image into its corresponding relative path. Then it will show.*/
			String imgname,s,foldername,relpath;
			imgname = imgpath.substring(imgpath.lastIndexOf("\\")+1);  /*Ex- abc.jpg will be obtained*/
			s = imgpath.substring(0,imgpath.lastIndexOf("\\"));
			foldername = s.substring(s.lastIndexOf("\\")+1);  /*The folder in which the image is stored*/
			relpath = foldername+File.separator+imgname;  /*The relative path of image is obtained*/
			
			String sql = "select count(*) from users";
			Connection cn = Db2Connector.getCn();
			PreparedStatement ps = cn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			int count=0;
			while(rs.next())
			{
				count = Integer.parseInt(rs.getString(1)) + 1;
			}
			if(count<10)
			{
				uid = uid+"00"+count;
			}
			else if(count<100)
			{
				uid = uid+"0"+count;
			}
			else
			{
				uid = uid+count;
			}
			
			HttpSession userid = request.getSession();
			userid.setAttribute("userid",uid);
			
			sql = "insert into users values(?,?,?,?,?,?)";
			cn = Db2Connector.getCn();
			ps = cn.prepareStatement(sql);
			ps.setString(1, uid);
			ps.setString(2, name);
			ps.setString(3, email);
			ps.setString(4, nationality);
			ps.setString(5, relpath);
			ps.setString(6, password);
			ps.execute();
			
			RequestDispatcher rd = request.getRequestDispatcher("signSuccess.jsp");
			rd.forward(request, response);
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
	private String extractpath(Part part)
	{
		String content_dis = part.getHeader("Content-Disposition");
		String items[] = content_dis.split(";");
		for(String x:items)
		{
			if(x.trim().startsWith("filename"))
			{
				return x.substring(x.indexOf("=")+2,x.length()-1);
			}
		}
		return null; /*Initial return*/
		
	}

}
