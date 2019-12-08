package connector;

import java.sql.*;

public class Db2Connector {
	static Connection cn = null;
	static String driver = "com.ibm.db2.jcc.DB2Driver";
	static String url = "jdbc:db2://localhost:50000/talkmart";
	static String user = "";
	static String pass = "";
	public static Connection getCn()
	{
		try
		{
			Class.forName(driver);
			cn = DriverManager.getConnection(url,user,pass);
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		return cn;
	}

}
