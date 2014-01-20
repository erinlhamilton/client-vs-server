/**
 * @fileoverview Sends data to database and retrieves results from database
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
	
	return (data.split(",").length - 1) - (data.split("),(").length - 1);

}

/**
 * Takes output from algorithms.js and formats into string
 *
 * @param {results} data to put into database
 * @returns a string of data to be sent to server
 */

function formatResults(algorithm, inputBytes, inputNodes, requestTime, processTime, parseTime, totalTime, outputBytes, outputNodes){

	var platform = "client";
	var responseTime = 0;
	var valid = true;
	return "date=" + dateToday +
		"&latency=" + latencyTime +
		"&bandwidth=" + bandwidthTime + 
		"&platform=" +platform+
		"&algorithm=" +algorithm+
		"&input(bytes)=" +inputBytes+
		"&input(nodes)=" +inputNodes+
		"&request(ms)=" +requestTime+
		"&geoprocess(ms)=" +processTime+
		"&parse(ms)=" +parseTime+
		"&response(ms)=" + responseTime +
		"&total(ms)=" +totalTime+
		"&valid=" + valid+
		"&output(bytes)=" +outputBytes+
		"&output(nodes)=" + outputNodes;
}

/**
 * Called by algorithms.js to retrieve wkt file from the server
 *
 * @param {results} data to put into database
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
 * Called by algorithms.js to retrieve wkt file from the server
 * 
 * @returns the results of test in txt
 *
 */
function getResults() {
	
	//TODO:send request to server to retrieve data from database
	
}