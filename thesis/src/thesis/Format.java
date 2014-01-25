package thesis;
import org.springframework.util.StringUtils;

import com.vividsolutions.jts.geom.*;
import com.vividsolutions.jts.io.WKTReader;
import com.vividsolutions.jts.io.WKTWriter;

public class Format {

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

	public Integer countNodes(String wkt){
		
		int output = 0;
		
		try {
			
			output = StringUtils.countOccurrencesOf(wkt, ",");
			
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
	 */
	public String resultString(String geoprocess, int inputBytes, int inputNodes, long dataTime, long inParseTime, long geoprocessTime, long outParseTime, long totalTime, boolean outputValid, int outputBytes, int outputNodes){
		System.out.print(geoprocess);
		System.out.printf("M%d%n", inputBytes);
		return "";
	}
}
