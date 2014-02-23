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
	 // JDBC driver name and database URL
	   static final String JDBC_DRIVER = "org.sqlite.JDBC";  
	   static final String DB_URL = "C:/Users/Erin/Documents/GitHub/client-vs-server/thesis/WebContent/db/";
	   static final String resultCSVLocation = "C:/Users/Erin/Documents/GitHub/client-vs-server/thesis/WebContent/results/";
	   //static final String DB_URL = "webapps/thesis/db/";
	   //static final String resultCSVLocation = "webapps/thesis/results/";
	   
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
	      Class.forName(JDBC_DRIVER);
	      c = DriverManager.getConnection("jdbc:sqlite:" + DB_URL + "wktData.db");
	      c.setAutoCommit(false);    
	      
	      ResultSet rs = null;
	      String sql = "SELECT "+wktTable+ "WKT FROM "+ wktTable +" WHERE "+wktTable+"ID = ?";
			PreparedStatement pstmt = c.prepareStatement(sql);
			pstmt.setInt(1, id);
			try {
			  rs = pstmt.executeQuery();
			  result = rs.getString(wktTable+"WKT");
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
	 * Accepts a string of the results and stores
	 * in the database
	 *
	 * @param (data) string of results
	 */
	 public void insertResults(String data)
	  {
	    Connection c = null;
	    Statement stmt = null;
	    try {
	      Class.forName(JDBC_DRIVER);
	      c = DriverManager.getConnection("jdbc:sqlite:" + DB_URL + "wktData.db");
	      c.setAutoCommit(false);
	      stmt = c.createStatement();
	      String sql = "INSERT INTO Results (ID, Platform, Geoprocess, DataType, InputBytes, InputNodes, ServerDataMS," +
	      		"InputParseMS, GeoprocessMS, OutputParseMS, TotalTimeMS, OutputBytes, OutputNodes)" +
	    		  		"VALUES " + data + ";";
	      stmt.executeUpdate(sql);

	      stmt.close();
	      c.commit();
	      c.close();

	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	}
	 
		/**
		 * Accepts a string of the metadata results and stores in database
		 *
		 * @param (data) string of results
		 */
		 public void insertMetadata(String data)
		  {
		    Connection c = null;
		    Statement stmt = null;
		    try {
		      Class.forName(JDBC_DRIVER);
		      c = DriverManager.getConnection("jdbc:sqlite:" + DB_URL + "wktData.db");
		      c.setAutoCommit(false);
		      stmt = c.createStatement();
		      String sql = "INSERT INTO Metadata (MID, Date, Browser, OperatingSystem, Hardware) " +
		    		  		"VALUES " + data + ";";
		      stmt.executeUpdate(sql);

		      stmt.close();
		      c.commit();
		      c.close();

		    } catch ( Exception e ) {
		      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		      System.exit(0);
		    }
		}
	 
			/**
			 * Accepts a string of the network test results and stores in db
			 *
			 * @param (data) string of network results
			 */
			 public void insertNetwork(String data)
			  {
			    Connection c = null;
			    Statement stmt = null;
			    try {
			      Class.forName(JDBC_DRIVER);
			      c = DriverManager.getConnection("jdbc:sqlite:" + DB_URL + "wktData.db");
			      c.setAutoCommit(false);
			      stmt = c.createStatement();
			      String sql = "INSERT INTO Network (ID, Latency, LatError, Bandwidth, BwError) " +
			    		  		"VALUES " + data + ";";
			      stmt.executeUpdate(sql);

			      stmt.close();
			      c.commit();
			      c.close();

			    } catch ( Exception e ) {
			      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			      System.exit(0);
			    }
			}
		 
		 
	 /**
		 * Returns a csv of the test results when called.
		 *
		 * @return File of the results
		 */
		 public File retrieveResults() 
		  {
			    Connection c = null;
			    File fw = new File(resultCSVLocation + "results.csv");
			    try {
			      Class.forName(JDBC_DRIVER);
			      c = DriverManager.getConnection("jdbc:sqlite:" + DB_URL + "wktData.db");
			      c.setAutoCommit(false);
			      ResultSet rs = null;
			      
			      String sql ="SELECT *"+
			      " FROM Results" +
			      " INNER JOIN Metadata" +
			      " On Results.ID = Metadata.MID";
			 

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
		 
		 /**
			 * Returns a csv of the network test results
			 *
			 * @return File of the results
			 */
			 public File retrieveNetworkResults() 
			  {
				    Connection c = null;
				    File fw = new File(resultCSVLocation + "networkResults.csv");
				    try {
				      Class.forName(JDBC_DRIVER);
				      c = DriverManager.getConnection("jdbc:sqlite:" + DB_URL + "wktData.db");
				      c.setAutoCommit(false);
				      ResultSet rs = null;
				      
				      String sql ="SELECT * FROM Network";
				 

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
