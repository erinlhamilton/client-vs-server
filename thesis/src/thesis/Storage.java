/**
 * (Write a succinct description of this class here. You should avoid
 * wordiness and redundancy. If necessary, additional paragraphs should
 * be preceded by <p>, the html tag for a new paragraph.)
 *
 * <p>Bugs: (a list of bugs and other problems)
 *
 * @author (your name)
 */
package thesis;

import java.sql.*;

public class Storage {
	
	public static void main( String args[] )
	{
		String table = "Points";
		int id = 1;
		Storage myself = new Storage();
		String wktResults = myself.fetchWKT(table, id);
		System.out.print(wktResults);
	/**
	 * Accepts parameters from web services and returns well-known
	 * text file from given database table.
	 *
	 * @param (table) the table of the wkt file
	 * @param (id) The id of the wkt file
	 * @return JSON containing the wkt file
	 */
	 // 
	}
	 public String fetchWKT(String wktTable, int id)
	  {
	    Connection c = null;
	    String result ="";
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:WebContent/db/spatial.db");
	      c.setAutoCommit(false);
	      System.out.println("Opened database successfully");
	      ResultSet rs = null;
	      String sql = "SELECT PointWKT FROM "+ wktTable +" WHERE ID = ?";
			PreparedStatement pstmt = c.prepareStatement(sql);
			pstmt.setInt(1, id);
			try {
			  rs = pstmt.executeQuery();
			  String  wkt = rs.getString("PointWKT");
			  result = wkt;
			} finally {  
			  rs.close();  
			  pstmt.close();
			  c.close(); 
			}

	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	      return "Unsuccessful attempt";
	    }
	    System.out.println("Operation done successfully");
	    return result;
	}
	 
}
