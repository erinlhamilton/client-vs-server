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
		long startTime = System.nanoTime();
		String wktResults = storagePoints.fetchWKT("Points", id);
		long totalTime = (System.nanoTime()-startTime)/1000000;
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
		long startTime = System.nanoTime();
		String wktResults = storagePoints.fetchWKT("Lines", id);
		long totalTime = (System.nanoTime()-startTime)/1000000;
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
		long startTime = System.nanoTime();
		String wktResults = storagePoints.fetchWKT("Polygons", id);
		long totalTime = (System.nanoTime()-startTime)/1000000;
		String resultJSON = "{ \"wkt\": \"" + wktResults + "\", \"time\": \"" + totalTime + "\"}";
		return resultJSON;
	}
	
	/**
	 * Accepts a valid ID for the table containing the union
	 * polygons
	 *
	 * @param (id) The ID of the polygon wkt file
	 * @return JSON containing the wkt file
	 */
	@Path("/union/{id}")
	@GET
	@Produces("text/plain")
	public String returnUnionWKT(@PathParam("id") int id){
		Storage storagePoints = new Storage();
		long startTime = System.nanoTime();
		String wktResults = storagePoints.fetchWKT("UnionPoly", id);
		long totalTime = (System.nanoTime()-startTime)/1000000;
		String resultJSON = "{ \"wkt\": \"" + wktResults + "\", \"time\": \"" + totalTime + "\"}";
		return resultJSON;
	}
		
	
	/**
	 * When called, runs all the processes on the server side.
	 *
	 * @param {testID} the ID of the current test
	 * @return String to let say test complete
	 */
	@GET
	@Path("/server/{id}")
	@Produces("text/plain")
	public String serverGeoProcess(@PathParam("id") int testID){
		Flow startServer = new Flow();
		String done = startServer.runServer(testID);
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
	public String storeResults(@FormParam("id") int testID,
			@FormParam("platform") String platform,
			@FormParam("algorithm") String algorithm,
			@FormParam("dataType") String dataType,
			@FormParam("input(bytes)") int inputBytes,
			@FormParam("input(nodes)") int inputNodes,
			@FormParam("serverData(ms)") int dataTime,
			@FormParam("inputParse(ms)") int inputParseTime,
			@FormParam("geoprocess(ms)") int processTime,
			@FormParam("parse(ms)") int parseTime,
			@FormParam("total(ms)") int totalTime,
			@FormParam("valid") boolean valid,
			@FormParam("output(bytes)") int outputBytes,
			@FormParam("output(nodes)") int outputNodes){
	
		String result = 
				"('" + testID + "','"
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
				+ valid + "','"
				+outputBytes+ "','"
				+ outputNodes +"')";
		
		Storage storeResults = new Storage();
		String success = storeResults.insertResults(result);
		return success;
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
	public String storeMetadata(@FormParam("id") int ID,
			@FormParam("date") String dateToday,
			@FormParam("browser") String browser,
			@FormParam("os") String operatingSystem,
			@FormParam("hardware") String hardware,
			@FormParam("firstLatency") int firstLatency,
			@FormParam("firstBandwidth") int firstBandwidth,
			@FormParam("secondLatency") int secondLatency,
			@FormParam("secondBandwidth") int secondBandwidth,
			@FormParam("thirdLatency") int thirdLatency,
			@FormParam("thirdBandwidth") int thirdBandwidth){
	
		String result = 
				"('" + ID + "','"
		 		+dateToday + "','"
				+browser + "','"
				+ operatingSystem + "','"
				+hardware+ "','"
				+firstLatency+ "','"
				+firstBandwidth+ "','"
				+secondLatency+ "','"
				+secondBandwidth+ "','"
				+thirdLatency+ "')";
		
		//Storage storeResults = new Storage();
		//String success = storeResults.insertMetadata(result);
		return result;
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
		ResponseBuilder response = Response.ok((Object) file);
		response.header("Content-Disposition",
			"attachment; filename=\"results.csv\"");
		return response.build();
	}
}
