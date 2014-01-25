/**
 * Controls the flow the server-side processing code.
 *
 * <p>Bugs: (a list of bugs and other problems)
 *
 * @author Erin Hamilton (erin@erinhamilton.me)
 */

package thesis;

import thesis.Storage;
import thesis.Algorithms;
import thesis.Format;

import com.vividsolutions.jts.geom.*;

public class Flow {
	
	
	/**
	 * Main function of server-side code. Called from web service
	 * from the client. Begins the processing.
	 *
	 */
	public static void main(String[] args){
		String results = callTriangulation(1, 0);
		System.out.print(results);
	}
	
	
	/**
	 * Controls the flow of buffer processing, records and returns results 
	 *
	 * @param {testID} ID of current test
	 * @param {dataType} point, line, or polygon Geometry
	 * @param {wktID} ID of wkt in database
	 * @return a string of the results to insert into database
	 */
	public static String callBuff(int testID, String dataType, int wktID){
		String geoprocess = "Buffer";

		//Retrieve data from db
		Storage storageData = new Storage();
		long startData = System.nanoTime();
		String data = storageData.fetchWKT(dataType, wktID);
		long dataTime = (System.nanoTime()-startData)/1000000;
		
		//Length of input wkt (bytes and # nodes)
		Format dataFormat = new Format();
		int inputBytes = data.length();
		int inputNodes = dataFormat.countNodes(data);//--> Format.java
		
		//Parse wkt to geometry
		long startInParse = System.nanoTime();
		Geometry geom = dataFormat.parseWKT(data);//--> Format.java
		long inParseTime = (System.nanoTime()-startInParse)/1000000;
		
		//Run Buffer on geometry
		Algorithms buffer = new Algorithms();
		long startGeoprocess = System.nanoTime();
		Geometry buff = buffer.bufferGeom(geom);//-->Algorithms.java
		long geoprocessTime = (System.nanoTime()-startGeoprocess)/1000000;
		
		//Is output a valid geometry?
		Boolean outputValid = buff.isValid();
		
		//Parse geometry back to wkt
		long startOutParse = System.nanoTime();
		String output = dataFormat.parseGeom(buff);//--> Format.java
		long outParseTime = (System.nanoTime()-startOutParse)/1000000;
		
		//Length of output wkt (bytes and #nodes)
		int outputBytes = output.length();
		int outputNodes = dataFormat.countNodes(output);//-->Format.java
		
		long totalTime = dataTime+ inParseTime + geoprocessTime + outParseTime;
		
		return dataFormat.resultString(testID, geoprocess, dataType, inputBytes, inputNodes, dataTime, inParseTime, geoprocessTime, outParseTime, totalTime, outputValid, outputBytes, outputNodes);
	}
	
	/**
	 * Controls the flow of traingulation processing, records and 
	 * returns results 
	 *
	 * @param {testID} ID of current test
	 * @param {wktID} ID of wkt in database
	 * @return a string of the results to insert into database
	 */
	public static String callTriangulation(int testID, int wktID){
		String geoprocess = "Triangulation";
		String dataType = "points";

		//Retrieve data from db
		Storage storageData = new Storage();
		long startData = System.nanoTime();
		String data = storageData.fetchWKT(dataType, wktID);
		long dataTime = (System.nanoTime()-startData)/1000000;
		
		//Length of input wkt (bytes and # nodes)
		Format dataFormat = new Format();
		int inputBytes = data.length();
		int inputNodes = dataFormat.countNodes(data);//--> Format.java
		
		//Parse wkt to geometry
		long startInParse = System.nanoTime();
		Geometry geom = dataFormat.parseWKT(data);//--> Format.java
		long inParseTime = (System.nanoTime()-startInParse)/1000000;
		
		//Run Buffer on geometry
		Algorithms triangulate = new Algorithms();
		long startGeoprocess = System.nanoTime();
		Geometry voronoi = triangulate.voronoiGeom(geom);//-->Algorithms.java
		long geoprocessTime = (System.nanoTime()-startGeoprocess)/1000000;
		
		//Is output a valid geometry?
		Boolean outputValid = voronoi.isValid();
		
		//Parse geometry back to wkt
		long startOutParse = System.nanoTime();
		String output = dataFormat.parseGeom(voronoi);//--> Format.java
		long outParseTime = (System.nanoTime()-startOutParse)/1000000;
		
		//Length of output wkt (bytes and #nodes)
		int outputBytes = output.length();
		int outputNodes = dataFormat.countNodes(output);//-->Format.java
		
		long totalTime = dataTime+ inParseTime + geoprocessTime + outParseTime;
		
		return dataFormat.resultString(testID, geoprocess, dataType, inputBytes, inputNodes, dataTime, inParseTime, geoprocessTime, outParseTime, totalTime, outputValid, outputBytes, outputNodes);

			
		}
	
	/**
	 * Controls the flow of union processing, records and 
	 * returns results 
	 *
	 * @param {testID} ID of current test
	 * @param {wktID} ID of wkt in database
	 * @return a string of the results to insert into database
	 */
	public static String callUnion(int testID, int wktID){
		String geoprocess = "Union";
		String dataType = "polygons";

		//Retrieve data from db
		Storage storageData = new Storage();
		long startData = System.nanoTime();
		String dataOne = storageData.fetchWKT(dataType, wktID);
		String dataTwo = storageData.fetchWKT(dataType, wktID);
		long dataTime = (System.nanoTime()-startData)/1000000;
		
		//Length of input wkt (bytes and # nodes)
		Format dataFormat = new Format();
		int inputBytes = dataOne.length() + dataTwo.length();
		int inputNodes = (dataFormat.countNodes(dataOne)) + (dataFormat.countNodes(dataTwo));//--> Format.java
		
		//Parse wkt to geometry
		long startInParse = System.nanoTime();
		Geometry geomOne = dataFormat.parseWKT(dataOne);//--> Format.java
		Geometry geomTwo = dataFormat.parseWKT(dataTwo);//--> Format.java
		long inParseTime = (System.nanoTime()-startInParse)/1000000;
		
		//Run union on geometry
		Algorithms union = new Algorithms();
		long startGeoprocess = System.nanoTime();
		Geometry unionResult = union.unionGeom(geomOne, geomTwo);//-->Algorithms.java
		long geoprocessTime = (System.nanoTime()-startGeoprocess)/1000000;
		
		//Is output a valid geometry?
		Boolean outputValid = unionResult.isValid();
		
		//Parse geometry back to wkt
		long startOutParse = System.nanoTime();
		String output = dataFormat.parseGeom(unionResult);//--> Format.java
		long outParseTime = (System.nanoTime()-startOutParse)/1000000;
		
		//Length of output wkt (bytes and #nodes)
		int outputBytes = output.length();
		int outputNodes = dataFormat.countNodes(output);//-->Format.java
		
		long totalTime = dataTime+ inParseTime + geoprocessTime + outParseTime;
		
		return dataFormat.resultString(testID, geoprocess, dataType, inputBytes, inputNodes, dataTime, inParseTime, geoprocessTime, outParseTime, totalTime, outputValid, outputBytes, outputNodes);
		
	}

	


}
