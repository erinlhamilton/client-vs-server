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
	
	return (data.split(",").length) - (data.split("),(").length) + 2;

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
 * Get the last Test ID and add one to create the latest test ID
 *
 * @return current test ID
 *
 */
function getTestID(callback) {
	
		microAjax(serverlocation + "/rest/services/testID", 
				function (data) {
					var dataJSON = JSON.parse(data);
					var TID = parseInt(dataJSON.TID);
					var div = document.getElementById('currTest');
					var thisTest = TID + 1;
					div.innerHTML = thisTest;
					mdJSON.ID = thisTest;
					callback();
			});
		
}

/**
 * Stores string of results from geoprocessing to DB using POST
 *
 * @param {data} string of data to put into database
 *
 */
function storeResults(data, returnToProcess) {
	
		microAjax(serverlocation + "/rest/services/store", 
			function (err) {
				console.log(err); 
				returnToProcess(); //--> flow.js
			}, 
		data);
}


/**
 * Stores string of metadata from mdJSON to DB using POST
 *
 * @param {data} string of data to put into database
 *
 */
function storeMetadata() {
	
	var metadata = formatMetadata();
	
	microAjax(serverlocation + "/rest/services/storeMetadata", 
			function (err) {
				console.log(err); 
			}, 
			metadata);
}

/**
 * Store the latency and bandwidth test results
 *
 * @param {data} string of data to put into database
 *
 */
function storeNetworkTest(networkResult) {

	
	microAjax(serverlocation +"/rest/services/storeNetwork", 
			function (err) {
				console.log(err); 
			}, 
			networkResult);
}

