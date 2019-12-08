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
 * Servlet implementation class PrivateChatDiscuss
 */
@WebServlet("/PrivateChatDiscuss")
@MultipartConfig(fileSizeThreshold=1024*1021*2,maxFileSize=1024*1024*10,maxRequestSize=1024*1024*50) /*Annotation*/
public class PrivateChatDiscuss extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String SAVE_DIR="UploadedPictures";

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String imgpath = null;
		
		HttpSession discuss_topic_id,userid,discuss_category;
		String chat_with="",user_id="",discussCategory="",chat_content="",relpath="0";
		try
		{
			userid = request.getSession(false);
			user_id = (String)userid.getAttribute("userid");
			discuss_category = request.getSession(false);
			discussCategory = (String)discuss_category.getAttribute("discuss_category");
			HttpSession chatwith = request.getSession(false);
			chat_with = (String)chatwith.getAttribute("chat_with");
			
			chat_content = request.getParameter("chat_content");
			if(chat_content==null)
			{
				chat_content="";
			}
			
			Part part = request.getPart("picture");
			String path = extractpath(part);
			if(path.equalsIgnoreCase("1"))
			{
				relpath="0";
			}
			else
			{
				String filepath = "D:"+File.separator+"Advanced JAVA"+File.separator+"TalkMart"+File.separator+"WebContent"+File.separator+SAVE_DIR;
				File directory = new File(filepath);
				if(!directory.exists())
				{
					directory.mkdir();
				}
				String filename = path.substring(path.lastIndexOf("\\")+1,path.length());
				imgpath = filepath+File.separator+filename;
				part.write(imgpath);
				
				/*Google Chrome cannot show images in html(local resources) for security purposes. But it can be sorted 
				if we change the absolute path of the image into its corresponding relative path. Then it will show.*/
				String imgname,s,foldername;
				imgname = imgpath.substring(imgpath.lastIndexOf("\\")+1);  /*Ex- abc.jpg will be obtained*/
				s = imgpath.substring(0,imgpath.lastIndexOf("\\"));
				foldername = s.substring(s.lastIndexOf("\\")+1);  /*The folder in which the image is stored*/
				relpath = foldername+File.separator+imgname;  /*The relative path of image is obtained*/
			}
			
			String sql = "select id,topic,chat_with from "+discussCategory+" where chat_by=?";
			Connection cn = Db2Connector.getCn();
			PreparedStatement ps = cn.prepareStatement(sql);
			ps.setString(1, user_id);
			ResultSet rs = ps.executeQuery();
			String id="";
			while(rs.next())
			{
				if(rs.getString(2).equalsIgnoreCase("No_Content") && rs.getString(3).equalsIgnoreCase(chat_with))
				{
					id = rs.getString(1);
				}
			}
			
			sql = "select id,topic,chat_with from "+discussCategory+" where chat_by=?";
			cn = Db2Connector.getCn();
			ps = cn.prepareStatement(sql);
			ps.setString(1, chat_with);
			rs = ps.executeQuery();
			String id2="";
			while(rs.next())
			{
				if(rs.getString(2).equalsIgnoreCase("No_Content") && rs.getString(3).equalsIgnoreCase(user_id))
				{
					id2 = rs.getString(1);
				}
			}
			
			sql = "select count(uid) from "+discussCategory;
			cn = Db2Connector.getCn();
			ps = cn.prepareStatement(sql);
			rs = ps.executeQuery();
			int count=0;
			while(rs.next())
			{
				count = Integer.parseInt(rs.getString(1));
			}
			count++;
	
			sql = "insert into "+discussCategory+"(id,type,name,posted_by,topic,picture,uid,chat_by,chat_with) values(?,?,?,?,?,?,?,?,?)";
			ps = cn.prepareStatement(sql);
			ps.setString(1, id);
			ps.setString(2, "PrivateChat");
			ps.setString(3, chat_content);
			ps.setString(4, user_id);
			ps.setString(5, "Content");
			ps.setString(6, relpath);
			ps.setString(7, String.valueOf(count));
			ps.setString(8, user_id);
			ps.setString(9, chat_with);
			ps.execute();
			
			sql = "insert into "+discussCategory+"(id,type,name,posted_by,topic,picture,uid,chat_by,chat_with) values(?,?,?,?,?,?,?,?,?)";
			ps = cn.prepareStatement(sql);
			ps.setString(1, id2);
			ps.setString(2, "PrivateChat");
			ps.setString(3, chat_content);
			ps.setString(4, "0");
			ps.setString(5, "Content");
			ps.setString(6, relpath);
			ps.setString(7, String.valueOf(count+1));
			ps.setString(8, chat_with);
			ps.setString(9, user_id);
			ps.execute();
			
			RequestDispatcher rd = request.getRequestDispatcher("privateChatDiscuss.jsp");
			rd.forward(request, response);

		}
		catch(Exception e)
		{
			//System.out.println(e);
			try
			{
				String sql = "select id,topic,chat_with from "+discussCategory+" where chat_by=?";
				Connection cn = Db2Connector.getCn();
				PreparedStatement ps = cn.prepareStatement(sql);
				ps.setString(1, user_id);
				ResultSet rs = ps.executeQuery();
				String id="";
				while(rs.next())
				{
					if(rs.getString(2).equalsIgnoreCase("No_Content") && rs.getString(3).equalsIgnoreCase(chat_with))
					{
						id = rs.getString(1);
					}
				}
				
				sql = "select id,topic,chat_with from "+discussCategory+" where chat_by=?";
				cn = Db2Connector.getCn();
				ps = cn.prepareStatement(sql);
				ps.setString(1, chat_with);
				rs = ps.executeQuery();
				String id2="";
				while(rs.next())
				{
					if(rs.getString(2).equalsIgnoreCase("No_Content") && rs.getString(3).equalsIgnoreCase(user_id))
					{
						id2 = rs.getString(1);
					}
				}
				
				sql = "select count(uid) from "+discussCategory;
				cn = Db2Connector.getCn();
				ps = cn.prepareStatement(sql);
				rs = ps.executeQuery();
				int count=0;
				while(rs.next())
				{
					count = Integer.parseInt(rs.getString(1));
				}
				count++;
		
				sql = "insert into "+discussCategory+"(id,type,name,posted_by,topic,picture,uid,chat_by,chat_with) values(?,?,?,?,?,?,?,?,?)";
				ps = cn.prepareStatement(sql);
				ps.setString(1, id);
				ps.setString(2, "PrivateChat");
				ps.setString(3, chat_content);
				ps.setString(4, user_id);
				ps.setString(5, "Content");
				ps.setString(6, relpath);
				ps.setString(7, String.valueOf(count));
				ps.setString(8, user_id);
				ps.setString(9, chat_with);
				ps.execute();
				
				sql = "insert into "+discussCategory+"(id,type,name,posted_by,topic,picture,uid,chat_by,chat_with) values(?,?,?,?,?,?,?,?,?)";
				ps = cn.prepareStatement(sql);
				ps.setString(1, id2);
				ps.setString(2, "PrivateChat");
				ps.setString(3, chat_content);
				ps.setString(4, "0");
				ps.setString(5, "Content");
				ps.setString(6, relpath);
				ps.setString(7, String.valueOf(count+1));
				ps.setString(8, chat_with);
				ps.setString(9, user_id);
				ps.execute();
				
				RequestDispatcher rd = request.getRequestDispatcher("privateChatDiscuss.jsp");
				rd.forward(request, response);

			}
			catch(Exception d)
			{
				System.out.println(d);
			}
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
		return "1"; /*Initial return*/
		
	}
}
