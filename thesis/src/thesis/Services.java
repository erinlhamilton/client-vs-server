package thesis;

import javax.ws.rs.*;

@Path("/services")
public class Services {
	
	//Return WKT data by id
	@GET
	@Produces("text/plain")
	public String returnWKT(){
		return "Returns the WKT";
	}
		
//Start server test
//	@Path("/server")
//	public String serverGeoProcess(){
//		return "Starts the server geoprocessing";
//	}
	
	
//Store results in DB
//	@Path("/store")
//	public String storeRestuls(){
//		return "Stores the result in DB";
//	}
	
//Return CSV of results
//	@Path("/results")
//	public String returnResults(){
//		return "Returns the results";
//	}
}
