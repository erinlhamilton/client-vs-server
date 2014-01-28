/**
 * Controls reading and writing to database. Methods return well-known
 * text files, save results to, and return results from spatial.db
 *
 * <p>Bugs: (a list of bugs and other problems)
 *
 * @author Erin Hamilton (erin@erinhamilton.me
 */
package thesis;

import java.sql.*;
import java.io.*;
import au.com.bytecode.opencsv.*;

public class Storage {
	
	
	/**
	 * Accepts parameters from web services and returns well-known
	 * text file from given database table.
	 *
	 * @param (wktTable) the table of the wkt file
	 * @param (id) The id of the wkt file
	 * @return JSON containing the wkt file
	 */
	 
	 public String fetchWKT(String wktTable, int id)
	  {
	    Connection c = null;
	    String result ="";
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:C:/Users/Erin/Documents/GitHub/erinlhamilton/client-vs-server/thesis/WebContent/db/wktData.db");
	      c.setAutoCommit(false);
	      ResultSet rs = null;
	      String sql = "SELECT WKT FROM "+ wktTable +" WHERE ID = ?";
			PreparedStatement pstmt = c.prepareStatement(sql);
			pstmt.setInt(1, id);
			try {
			  rs = pstmt.executeQuery();
			  String  wkt = rs.getString("WKT");
			  result = wkt;
			} finally {  
			  rs.close();  
			  pstmt.close();
			  c.close(); 
			}

	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    
	    return result;
	};
	 
	/**
	 * Accepts a string of the results and notifies client of success
	 *
	 * @param (data) string of results
	 * @return String of status
	 */
	 public String insertResults(String data)
	  {
	    Connection c = null;
	    Statement stmt = null;
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:C:/Users/Erin/Documents/GitHub/erinlhamilton/client-vs-server/thesis/WebContent/db/wktData.db");
	      c.setAutoCommit(false);
	      stmt = c.createStatement();
	      String sql = "INSERT INTO Results (ID, Platform, Geoprocess, DataType, InputBytes, InputNodes, ServerDataMS," +
	      		"InputParseMS, GeoprocessMS, OutputParseMS, TotalTimeMS, OutputValid, OutputBytes, OutputNodes)" +
	    		  		"VALUES " + data + ";";
	      stmt.executeUpdate(sql);

	      stmt.close();
	      c.commit();
	      c.close();

	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    return"Records stored successfully";
	}
	 
		/**
		 * Accepts a string of the metadata results and notifies client of success
		 *
		 * @param (data) string of results
		 * @return String of status
		 */
		 public String insertMetadata(String data)
		  {
		    Connection c = null;
		    Statement stmt = null;
		    try {
		      Class.forName("org.sqlite.JDBC");
		      c = DriverManager.getConnection("jdbc:sqlite:C:/Users/Erin/Documents/GitHub/erinlhamilton/client-vs-server/thesis/WebContent/db/wktData.db");
		      c.setAutoCommit(false);
		      stmt = c.createStatement();
		      String sql = "INSERT INTO Metadata (ID , Date, Browser, OperatingSystem, Hardware, FirstLatency, FirstBandwidth, " +
		    		  		"SecondLatency, SecondBandwidth, ThirdLatency, ThirdBandwidth)" +
		    		  		"VALUES " + data + ";";
		      stmt.executeUpdate(sql);

		      stmt.close();
		      c.commit();
		      c.close();

		    } catch ( Exception e ) {
		      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		      System.exit(0);
		    }
		    return"Records stored successfully";
		}
	 
	 /**
		 * Accepts a string of the results and notifies client of success
		 *
		 * @param (data) string of results
		 * @return String of status
		 */
		 public File retrieveResults()
		  {
			    Connection c = null;
			    File fw = new File("C:/Users/Erin/Documents/Thesis/results/results.csv");
			    try {
			      Class.forName("org.sqlite.JDBC");
			      c = DriverManager.getConnection("jdbc:sqlite:C:/Users/Erin/Documents/GitHub/erinlhamilton/client-vs-server/thesis/WebContent/db/wktData.db");
			      c.setAutoCommit(false);
			      ResultSet rs = null;
			      
			      String sql ="SELECT *"+
			      "FROM Results" +
			      "INNER JOIN Metadata" +
			      "On Results.ID = Metadata.ID";

					PreparedStatement pstmt = c.prepareStatement(sql);
					try {
					  rs = pstmt.executeQuery();
					  CSVWriter writer = new CSVWriter(new FileWriter(fw), ',');
					  writer.writeAll(rs, true);
					  writer.close();
					} finally {  
					  rs.close();  
					  pstmt.close();
					  c.close(); 
					}
		    } catch ( Exception e ) {
		      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		      System.exit(0);
		    }
		    return fw;
		}
	 
}
