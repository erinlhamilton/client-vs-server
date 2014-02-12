/**
 * Performs all formatting methods for application. Parses well-known text and geometry
 * counts the number of nodes in well-known text, and converts results to string
 * for the database
 *
 * <p>Bugs: (a list of bugs and other problems)
 *
 * @author Erin Hamilton (erin@erinhamilton.me)
 */

package thesis;

import org.springframework.util.StringUtils;

import com.vividsolutions.jts.geom.*;
import com.vividsolutions.jts.io.WKTReader;
import com.vividsolutions.jts.io.WKTWriter;

public class Format {

	/**
	 * Parses well-known text to JTS geometry
	 *
	 * @param {wkt} String of well-known text
	 * @return JTS geometry
	 */
	public Geometry parseWKT(String wkt){
		
		Geometry geom = null;
		WKTReader rdr = new WKTReader();

		try {
			geom = rdr.read(wkt);
		} catch  ( Exception e ) {
		      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		      System.exit(0);
		} 

		return geom;

	}
	
	/**
	 * Parses JTS geometry to well-known text
	 *
	 * @param {geom} JTS geometry
	 * @return String of well-known text
	 */
	public String parseGeom(Geometry geom){
			
			WKTWriter parse = new WKTWriter();
			String output = "";
			try {
				
				output = parse.write(geom);
				
			} catch  ( Exception e ) {
			      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			      System.exit(0);
			}
			
			return output;
	
		}

	/**
	 * Determines number of nodes in well-known text by
	 * counting the number of commas
	 *
	 * @param {wkt} String of well-known text
	 * @return an Integer of number of nodes
	 */
	public Integer countNodes(String wkt){
		
		int output = 0;
		
		try {
			
			output = (StringUtils.countOccurrencesOf(wkt, ",") - StringUtils.countOccurrencesOf(wkt, "),(")) + 2;
			
		} catch  ( Exception e ) {
		      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		      System.exit(0);
		}
		
		return output;
	}
	
	/**
	 * Takes the results from the geoprocessing and puts it into a string
	 * that is sent to the database
	 *
	 * @param {}
	 * @returns string of results to insert into database
	 */
	public String resultString(int testID, String geoprocess, String dataType, int inputBytes, int inputNodes, long dataTime, long inParseTime, long geoprocessTime, long outParseTime, long totalTime, int outputBytes, int outputNodes){

		return "id=" + testID +
				"&platform=Server" +
				"&algorithm=" +geoprocess+
				"&dataType=" + dataType+
				"&input(bytes)=" +inputBytes+
				"&input(nodes)=" +inputNodes+
				"&serverData(ms)=" +dataTime+
				"&inputParse(ms)="+inParseTime+
				"&geoprocess(ms)=" +geoprocessTime+
				"&outputParse(ms)=" +outParseTime+
				"&total(ms)=" +totalTime+
				"&output(bytes)=" +outputBytes+
				"&output(nodes)=" +outputNodes;
		
		

	}
}
