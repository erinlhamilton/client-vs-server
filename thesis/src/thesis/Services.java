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
		String wktResults = storagePoints.fetchWKT("Points", id);
		return wktResults;
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
		String wktResults = storagePoints.fetchWKT("Lines", id);
		return wktResults;
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
		String wktResults = storagePoints.fetchWKT("Polygon", id);
		return wktResults;
	}
		
	
	/**
	 * (Write a succinct description of this method here.  If necessary,
	 * additional paragraphs should be preceded by <p>, the html tag for
	 * a new paragraph.)
	 *
	 * @param (parameter name) (Describe the first parameter here)
	 * @param (parameter name) (Do the same for each additional parameter)
	 * @return (description of the return value)
	 */
	@GET
	@Produces("text/plain")
	@Path("/server")
	public String serverGeoProcess(){
		//TODO:
		return "Starts the server geoprocessing";
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
	public String storeRestuls(@FormParam("date") String dateToday,
			@FormParam("latency") int latencyTime,
			@FormParam("bandwidth") int bandwidthTime,
			@FormParam("platform") String platform,
			@FormParam("algorithm") String algorithm,
			@FormParam("input(bytes)") int inputBytes,
			@FormParam("input(nodes)") int inputNodes,
			@FormParam("request(ms)") int requestTime,
			@FormParam("geoprocess(ms)") int processTime,
			@FormParam("parse(ms)") int parseTime,
			@FormParam("response(ms)") int responseTime,
			@FormParam("total(ms)") int totalTime,
			@FormParam("valid") boolean valid,
			@FormParam("output(bytes)") int outputBytes,
			@FormParam("output(nodes)") int outputNodes){
	
		String result = 
				"('" + dateToday + "','"
		 		+latencyTime + "','"
				+bandwidthTime + "','"
				+ platform + "','"
				+algorithm+ "','"
				+inputBytes+ "','"
				+inputNodes+ "','"
				+requestTime+ "','"
				+processTime+ "','"
				+parseTime+ "','"
				+ responseTime+ "','"
				+totalTime+ "','"
				+ valid + "','"
				+outputBytes+ "','"
				+ outputNodes +"')";
		
		Storage storeResults = new Storage();
		String success = storeResults.insertResults(result);
		return success;
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
