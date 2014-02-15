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
	static final int wktIDs[] = {10, 20, 30, 40, 50, 60, 70, 80, 90, 100};
	
//	int wktIDs[] = {10, 20, 30,	40,	50,	60,	70,	80,	90,	100, 200, 300, 400, 500, 600,
//	700, 800, 900, 1000, 2000, 3000, 4000, 5000, 6000, 7000, 8000, 9000, 10000,
//	20000, 30000, 40000, 50000, 60000, 70000, 80000, 90000, 100000, 150000, 
//	200000, 250000, 300000, 350000, 400000, 450000, 500000};
	
	/**
	 * Main function of server-side code. Called from web service
	 * from the client. Begins the server processing.
	 *
	 */
	public String runBuffer(int testID, String dataType){

		String results = "";
		Storage storeResults = new Storage();
		//Buffer points
		for (int i = 0; i < wktIDs.length; i++){
			results = callBuff(testID, dataType, wktIDs[i]);
			storeResults.insertResults(results);
		}
		
		return "Buffer " + dataType + " complete.";
		
	}
	
	/**
	 * Main function of server-side code. Called from web service
	 * from the client. Begins the server processing.
	 *
	 */
	public String runTriangulation(int testID){

		String results = "";
		Storage storeResults = new Storage();
		for (int i = 0; i < wktIDs.length; i++){
		results = callTriangulation(testID, wktIDs[i]);
		storeResults.insertResults(results);
	}
		
		return "Triangulation complete.";
		
	}
		
	public String runUnion(int testID){

		String results = "";
		Storage storeResults = new Storage();
		for (int i = 0; i < wktIDs.length; i++){
			results = callUnion(testID, wktIDs[i]);
		storeResults.insertResults(results);
		}
		return "Union complete.";
	}	


	/**
	 * Controls the flow of buffer processing, records and returns results 
	 *
	 * @param {testID} ID of current test
	 * @param {dataType} point, line, or polygon Geometry
	 * @param {wktID} ID of wkt in database
	 * @return a string of the results to insert into database
	 */
	public String callBuff(int testID, String dataType, int wktID){
		String geoprocess = "Buffer";

		//Retrieve data from db
		Storage storageData = new Storage();
		double startData = System.nanoTime();
		String data = storageData.fetchWKT(dataType, wktID);
		double dataTime = (System.nanoTime()-startData)/1000000;
		
		//Length of input wkt (bytes and # nodes)
		Format dataFormat = new Format();
		int inputBytes = data.length();
		int inputNodes = wktID;
		
		//Parse wkt to geometry
		double startInParse = System.nanoTime();
		Geometry geom = dataFormat.parseWKT(data);//--> Format.java
		double inParseTime = (System.nanoTime()-startInParse)/1000000;
		
		//Run Buffer on geometry
		Algorithms buffer = new Algorithms();
		double startGeoprocess = System.nanoTime();
		Geometry buff = buffer.bufferGeom(geom);//-->Algorithms.java
		double geoprocessTime = (System.nanoTime()-startGeoprocess)/1000000;
		
		//Parse geometry back to wkt
		double startOutParse = System.nanoTime();
		String output = dataFormat.parseGeom(buff);//--> Format.java
		double outParseTime = (System.nanoTime()-startOutParse)/1000000;
		
		//Length of output wkt (bytes and #nodes)
		int outputBytes = output.length();
		int outputNodes = dataFormat.countNodes(output);//-->Format.java
		
		double totalTime = dataTime+ inParseTime + geoprocessTime + outParseTime;
		
		return dataFormat.resultString(testID, geoprocess, dataType, inputBytes, inputNodes, dataTime, inParseTime, geoprocessTime, outParseTime, totalTime, outputBytes, outputNodes);
	}
	
	/**
	 * Controls the flow of traingulation processing, records and 
	 * returns results 
	 *
	 * @param {testID} ID of current test
	 * @param {wktID} ID of wkt in database
	 * @return a string of the results to insert into database
	 */
	public String callTriangulation(int testID, int wktID){
		String geoprocess = "Triangulation";
		String dataType = "Points";

		//Retrieve data from db
		Storage storageData = new Storage();
		double startData = System.nanoTime();
		String data = storageData.fetchWKT(dataType, wktID);
		double dataTime = (System.nanoTime()-startData)/1000000;
		
		//Length of input wkt (bytes and # nodes)
		Format dataFormat = new Format();
		int inputBytes = data.length();
		int inputNodes = wktID;
		
		//Parse wkt to geometry
		double startInParse = System.nanoTime();
		Geometry geom = dataFormat.parseWKT(data);//--> Format.java
		double inParseTime = (System.nanoTime()-startInParse)/1000000;
		
		//Run Buffer on geometry
		Algorithms triangulate = new Algorithms();
		double startGeoprocess = System.nanoTime();
		Geometry voronoi = triangulate.voronoiGeom(geom);//-->Algorithms.java
		double geoprocessTime = (System.nanoTime()-startGeoprocess)/1000000;
		
		//Parse geometry back to wkt
		double startOutParse = System.nanoTime();
		String output = dataFormat.parseGeom(voronoi);//--> Format.java
		double outParseTime = (System.nanoTime()-startOutParse)/1000000;
		
		//Length of output wkt (bytes and #nodes)
		int outputBytes = output.length();
		int outputNodes = dataFormat.countNodes(output);//-->Format.java
		
		double totalTime = dataTime+ inParseTime + geoprocessTime + outParseTime;
		
		return dataFormat.resultString(testID, geoprocess, dataType, inputBytes, inputNodes, dataTime, inParseTime, geoprocessTime, outParseTime, totalTime, outputBytes, outputNodes);

			
		}
	
	/**
	 * Controls the flow of union processing, records and 
	 * returns results 
	 *
	 * @param {testID} ID of current test
	 * @param {wktID} ID of wkt in database
	 * @return a string of the results to insert into database
	 */
	public String callUnion(int testID, int wktID){
		String geoprocess = "Union";
		String dataType = "Polygons";

		//Retrieve data from db
		Storage storageData = new Storage();
		double startData = System.nanoTime();
		String dataOne = storageData.fetchWKT("PolygonsA", wktID);
		String dataTwo = storageData.fetchWKT("PolygonsB", wktID);
		double dataTime = (System.nanoTime()-startData)/1000000;
		
		//Length of input wkt (bytes and # nodes)
		Format dataFormat = new Format();
		int inputBytes = dataOne.length() + dataTwo.length();
		int inputNodes = wktID;
		
		//Parse wkt to geometry
		double startInParse = System.nanoTime();
		Geometry geomOne = dataFormat.parseWKT(dataOne);//--> Format.java
		Geometry geomTwo = dataFormat.parseWKT(dataTwo);//--> Format.java
		double inParseTime = (System.nanoTime()-startInParse)/1000000;
		
		//Run union on geometry
		Algorithms union = new Algorithms();
		double startGeoprocess = System.nanoTime();
		Geometry unionResult = union.unionGeom(geomOne, geomTwo);//-->Algorithms.java
		double geoprocessTime = (System.nanoTime()-startGeoprocess)/1000000;
		
		//Parse geometry back to wkt
		double startOutParse = System.nanoTime();
		String output = dataFormat.parseGeom(unionResult);//--> Format.java
		double outParseTime = (System.nanoTime()-startOutParse)/1000000;
		
		//Length of output wkt (bytes and #nodes)
		int outputBytes = output.length();
		int outputNodes = dataFormat.countNodes(output);//-->Format.java
		
		double totalTime = dataTime+ inParseTime + geoprocessTime + outParseTime;
		
		return dataFormat.resultString(testID, geoprocess, dataType, inputBytes, inputNodes, dataTime, inParseTime, geoprocessTime, outParseTime, totalTime, outputBytes, outputNodes);
		
	}

	


}
