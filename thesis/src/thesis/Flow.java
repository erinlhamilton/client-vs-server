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
		//TODO: controls data and flow of execution
	}
	
	public static void callBuff(String dataType, int id){
		String geoprocess = "Buffer";

		//Retrieve data from db
		Storage storageData = new Storage();
		long startData = System.nanoTime();
		String data = storageData.fetchWKT(dataType, id);
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
		
		String result = dataFormat.resultString(geoprocess, inputBytes, inputNodes, dataTime, inParseTime, geoprocessTime, outParseTime, totalTime, outputValid, outputBytes, outputNodes);
		System.out.print(result);
	}
	
	public static void callTriangulation(){
			
		}
	
	public static void callUnion(){
		
	}

	


}
