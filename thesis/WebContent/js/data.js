/**
 * @fileoverview Handles formatting and server storage/response of
 * data and results
 * @author <a href="mailto:erin@erinhamilton.me">Erin Hamilton</a>
 * @version 1.0
 */


/**
 * Determines the size (in nodes) of well-known text
 *
 * @param {data} well-known text
 * @returns the size (in nodes) of the wkt
 *
 */
function getNodeSize(data){
	
	return (data.split(",").length) - (data.split("),(").length);

}

/**
 * Parses well-known text to geometry
 *
 * @param {wkt} string of well-known text
 * @returns jsts geometry
 *
 */
function parseInput(wkt){
	
	var reader = new jsts.io.WKTReader();
	return reader.read(wkt);
	
}

/**
 * Parses geometry to well-known text
 *
 * @param {result} jsts geometry
 * @returns well-known text
 *
 */
function parseOutput(result){
	
	var parser = new jsts.io.WKTWriter();
	return parser.write(result);
	
}

/**
 * Stores result string to DB using POST
 *
 * @param {data} string of data to put into database
 *
 */
function storeResults(data) {
	
	microAjax("http://localhost:8080/thesis/rest/services/store", 
			function (err) {
				console.log(err); 
			}, 
			data);
}

/**
 * Stores result string to DB using POST
 *
 * @param {data} string of data to put into database
 *
 */
function storeMetadata() {
	
	var metadata = formatMetadata();
	
	microAjax("http://localhost:8080/thesis/rest/services/storeMetadata", 
			function (err) {
				console.log(err); 
			}, 
			metadata);
}

/**
 * Called by algorithms.js to retrieve wkt file from the server
 * 
 * @returns the results of test in txt
 *
 */
function getResults() {
	
	microAjax("http://localhost:8080/thesis/rest/services/results", 
			function () {});
	
	//TODO:send request to server to retrieve data from database
	
}