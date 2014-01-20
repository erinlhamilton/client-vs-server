/**
 * @fileoverview Sends data to database and retrieves results from database
 * @author <a href="mailto:erin@erinhamilton.me">Erin Hamilton</a>
 * @version 1.0
 */


/**
 * Called by algorithms.js to retrieve wkt file from the server
 *
 * @param {results} data to put into database
 *
 */
function storeResults(data) {
	
	var results = "Date Today: " + dateToday+ latencyTime + " " + bandwidthTime + " " + data;
	
	console.log(results);
	//TODO:send data to the server to go in database
}

/**
 * Called by algorithms.js to retrieve wkt file from the server
 * 
 * @returns the results of test in txt
 *
 */
function getResults() {
	
	//TODO:send request to server to retrieve data from database
	
}