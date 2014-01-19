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

@Path("/services")
public class Services {
	
	/**
	 * Accepts a valid ID for the points table and returns
	 * points well-known text
	 *
	 * @param (id) The id of the points wkt file
	 * @return JSON containing the wkt file
	 */
	@Path("/points/{id}")
	@GET
	@Produces("text/plain")
	public String returnPoint(@PathParam("id") int id){
		return "Return point wkt: " + id;
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
		return "Returns the lines: " + id;
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
		return "Returns the polygons: " + id;
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
		return "Starts the server geoprocessing";
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
	@Path("/store")
	public String storeRestuls(){
		return "Stores the result in DB";
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
	@Path("/results")
	public String returnResults(){
		return "Returns the results";
	}
}
