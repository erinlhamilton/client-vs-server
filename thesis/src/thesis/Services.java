/**
 * Restful web services class. Controls the web services used for
 * returning the well-known text files, starting the server tests,
 * storing the results data, and returning the results data.
 *
 * <p>Bugs: (a list of bugs and other problems)
 *
 * @author Erin Hamilton (erin@erinhamilton.me)
 */
package thesis;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import java.io.File;

import thesis.Storage;
import thesis.Flow;

@Path("/services")
public class Services {
	
	/**
	 * Accepts a valid ID for the points table, sends that to
	 * the Storage class to fetch the data, and returns
	 * a well-known text file to the client
	 *
	 * @param (id) The id of the points wkt file
	 * @return JSON containing the wkt file
	 */
	@Path("/points/{id}")
	@GET
	@Produces("text/plain")
	public String returnPoint(@PathParam("id") int id){
		Storage storagePoints = new Storage();
		double startTime = System.nanoTime();
		String wktResults = storagePoints.fetchWKT("Points", id);
		double totalTime = (System.nanoTime()-startTime)/1000000;
		String resultJSON = "{ \"wkt\": \"" + wktResults + "\", \"time\": \"" + totalTime + "\"}";
		return resultJSON;
	}
	
	/**
	 * Accepts a valid ID for the lines table and returns
	 * lines well-known text
	 *
	 * @param (id) The ID of the lines wkt file
	 * @return JSON containing the wkt file
	 */
	@Path("/lines/{id}")
	@GET
	@Produces("text/plain")
	public String returnLine(@PathParam("id") int id){
		Storage storagePoints = new Storage();
		double startTime = System.nanoTime();
		String wktResults = storagePoints.fetchWKT("Lines", id);
		double totalTime = (System.nanoTime()-startTime)/1000000;
		String resultJSON = "{ \"wkt\": \"" + wktResults + "\", \"time\": \"" + totalTime + "\"}";
		return resultJSON;
	}
	
	/**
	 * Accepts a valid ID for the polygon table and returns
	 * polygon well-known text
	 *
	 * @param (id) The ID of the polygon wkt file
	 * @return JSON containing the wkt file
	 */
	@Path("/polygons/{id}")
	@GET
	@Produces("text/plain")
	public String returnPolygon(@PathParam("id") int id){
		Storage storagePoints = new Storage();
		double startTime = System.nanoTime();
		String wktResults = storagePoints.fetchWKT("Polygons", id);
		double totalTime = (System.nanoTime()-startTime)/1000000;
		String resultJSON = "{ \"wkt\": \"" + wktResults + "\", \"time\": \"" + totalTime + "\"}";
		return resultJSON;
	}
	
	/**
	 * Accepts a valid ID for the table containing the union
	 * polygons for polygons in table A
	 *
	 * @param (id) The ID of the polygon wkt file
	 * @return JSON containing the wkt file
	 */
	@Path("/union/{id}")
	@GET
	@Produces("text/plain")
	public String returnUnionWKT(@PathParam("id") int id){
		String tableAName = "PolygonsA";
		String tableBName = "PolygonsB";
		Storage storagePoints = new Storage(); 
		double startTime = System.nanoTime();
		String wktAResults = storagePoints.fetchWKT(tableAName, id);  
		String wktBResults = storagePoints.fetchWKT(tableBName, id);
		double totalTime = (System.nanoTime()-startTime)/1000000;
		String resultJSON = "{ \"wktA\": \"" + wktAResults + "\", \"wktB\": \"" + wktBResults + "\", \"time\": \"" + totalTime + "\"}";
		return resultJSON;
	} 
	
	/**
	 * Returns the current testID
	 *
	 * @return JSON containing the ID of current Test
	 */
	@Path("/testID")
	@GET
	@Produces("text/plain")
	public static String returnTestID(){
		Storage storageID = new Storage(); 
		String TID = storageID.fetchTestID();  
		String resultJSON = "{\"TID\":\"" + TID + "\"}";
		return resultJSON;
	} 

		
	/**
	 * Runs buffer on points on the server.
	 *
	 * @param {testID} the ID of the current test
	 * @return String to let say test complete
	 */
	@GET
	@Path("/server/points/{id}")
	@Produces("text/plain")
	public String serverBuffPoints(@PathParam("id") int testID){
		Flow startServer = new Flow();
		String done = startServer.runBuffer(testID, "Points");
		return done;
	}
	
	/**
	 * Runs buffer on lines on the server
	 *
	 * @param {testID} the ID of the current test
	 * @return String to let say test complete
	 */
	@GET
	@Path("/server/lines/{id}")
	@Produces("text/plain")
	public String serverBuffLines(@PathParam("id") int testID){
		Flow startServer = new Flow();
		String done = startServer.runBuffer(testID, "Lines");
		return done;
	}
	
	/**
	 * Runs buffer on polygons on the server
	 *
	 * @param {testID} the ID of the current test
	 * @return String to let say test complete
	 */
	@GET
	@Path("/server/polygons/{id}")
	@Produces("text/plain")
	public String serverBuffPoly(@PathParam("id") int testID){
		Flow startServer = new Flow();
		String done = startServer.runBuffer(testID, "Polygons");
		return done;
	}
	
	/**
	 * Runs union test on the server
	 *
	 * @param {testID} the ID of the current test
	 * @return String to let say test complete
	 */
	@GET
	@Path("/server/union/{id}")
	@Produces("text/plain")
	public String serverUnion(@PathParam("id") int testID){
		Flow startServer = new Flow();
		String done = startServer.runUnion(testID);
		return done;
	}
	
	/**
	 * Runs voronoi triangulation on the server
	 *
	 * @param {testID} the ID of the current test
	 * @return String to let say test complete
	 */
	@GET
	@Path("/server/triangulation/{id}")
	@Produces("text/plain")
	public String serverTriangulation(@PathParam("id") int testID){
		Flow startServer = new Flow();
		String done = startServer.runTriangulation(testID);
		return done;
	}
	
	
	/**
	 * Post web services that takes results from clients and sends them
	 * to Storage.java to be stored in db
	 *
	 * @param (parameter name) (Describe the first parameter here)
	 * @param (parameter name) (Do the same for each additional parameter)
	 * @return (description of the return value)
	 */
	@Path("/store")
	@POST
	@Consumes("application/x-www-form-urlencoded")
	@Produces("text/plain")
	public void storeResults(@FormParam("id") int rID,
			@FormParam("platform") String platform,
			@FormParam("algorithm") String algorithm,
			@FormParam("dataType") String dataType,
			@FormParam("input(bytes)") int inputBytes,
			@FormParam("input(nodes)") int inputNodes,
			@FormParam("serverData(ms)") double dataTime,
			@FormParam("inputParse(ms)") double inputParseTime,
			@FormParam("geoprocess(ms)") double processTime,
			@FormParam("parse(ms)") double parseTime,
			@FormParam("total(ms)") double totalTime,
			@FormParam("output(bytes)") int outputBytes,
			@FormParam("output(nodes)") int outputNodes){
	
		String result = 
				"('" + rID + "','"
				+ platform + "','"
				+algorithm+ "','"
				+dataType+ "','"
				+inputBytes+ "','"
				+inputNodes+ "','"
				+dataTime+ "','"
				+inputParseTime+ "','"
				+processTime+ "','"
				+parseTime+ "','"
				+totalTime+ "','"
				+outputBytes+ "','"
				+ outputNodes +"')";
		
		Storage storeResults = new Storage();
		storeResults.insertResults(result);
		//System.out.print(success);

	}
	
	/**
	 * Post web services that takes results from clients and sends them
	 * to Storage.java to be stored in db
	 *
	 * @param (parameter name) (Describe the first parameter here)
	 * @param (parameter name) (Do the same for each additional parameter)
	 * @return (description of the return value)
	 */
	@Path("/storeMetadata")
	@POST
	@Consumes("application/x-www-form-urlencoded")
	@Produces("text/plain")
	public void storeMetadata(@FormParam("id") int ID,
			@FormParam("date") String dateToday,
			@FormParam("browser") String browser,
			@FormParam("os") String operatingSystem,
			@FormParam("hardware") String hardware){
	
		String result = 
				"('" + ID + "','"
		 		+dateToday + "','"
				+browser + "','"
				+ operatingSystem + "','"
				+hardware+ "')";
		
		Storage storeResults = new Storage();
		storeResults.insertMetadata(result);
	}
	
	/**
	 * Post web services that takes results from clients and sends them
	 * to Storage.java to be stored in db
	 *
	 * @param (parameter name) (Describe the first parameter here)
	 * @param (parameter name) (Do the same for each additional parameter)
	 * @return (description of the return value)
	 */
	@Path("/storeNetwork")
	@POST
	@Consumes("application/x-www-form-urlencoded")
	@Produces("text/plain")
	public void storeNetwork(@FormParam("id") int ID,
			@FormParam("latency") int latency,
			@FormParam("latError") double latError,
			@FormParam("bandwidth") int bandwidth,
			@FormParam("bwError") double bwError){
	
		String result = 
				"('" + ID + "','"
		 		+latency + "','"
				+latError + "','"
				+bandwidth + "','"
				+bwError + "')";
		
		Storage storeResults = new Storage();
		storeResults.insertNetwork(result);
	}
	
	/**
	 * Retrieves the results table from the database and
	 * downloads it to the client
	 *
	 * @return a csv of results
	 */
	@Path("/results")
	@GET
	@Produces("application/vnd.ms-excel")
	public Response returnResults(){
		Storage retrieveResults = new Storage();
		File file = retrieveResults.retrieveResults();
		ResponseBuilder response = Response.ok(file);
		response.header("Content-Disposition",
			"attachment; filename=\"results.csv\"");
		return response.build();
	}
	
	/**
	 * Retrieves the network results table from the database
	 * and returns a csv
	 *
	 * @return a csv of results
	 */
	@Path("/networkResults")
	@GET
	@Produces("application/vnd.ms-excel")
	public Response returnNetworkResults(){
		Storage retrieveResults = new Storage();
		File file = retrieveResults.retrieveNetworkResults();
		ResponseBuilder response = Response.ok(file);
		response.header("Content-Disposition",
			"attachment; filename=\"networkResults.csv\"");
		return response.build();
	}
}
