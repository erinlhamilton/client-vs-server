/**
 * @fileoverview Sends data to database and retrieves results from database
 * @author <a href="mailto:erin@erinhamilton.me">Erin Hamilton</a>
 * @version 1.0
 */

/**
 * Records time of all steps and returns a string of the results
 *
 * @param {dataTime} Time to retrieve data on server
 * @param {wkt} well-known text from the server
 * @returns a string of the results to be sent to the server
 */
function bufferResults(dataTime, wkt){

	var geoprocess = 'Buffer';
	
	//Input wkt length in bytes and # nodes
	var inputBytes = wkt.length;
	var inputNodes = getNodeSize(wkt);//-->data.js
	
	//parse the input WKT to geometry
	var inputParseStart = new Date();
	var input = parseInput(wkt); //-->data.js
	var inputParseTime = new Date() - inputParseStart;
	
	//Buffer the geometry
    var buffStart = new Date();
    var buffer = bufferGeom(input); //-->algorithm.js
    var buffTime = new Date() - buffStart;
    
    //Validate output geometry
	var buffValid = buffer.isValid(); 
	
	//Parse the buffer geometry results back to WKT
	var parseStart = new Date();
	var bufferResult = parseOutput(buffer);//-->data.js
	var parseTime = new Date() - parseStart;
		
	//Get the size in both bytes and # nodes of WKT result
	var outputBytes = bufferResult.length;
	var outputNodes = getNodeSize(bufferResult);//-->data.js
	
	var totalTime = dataTime + inputParseTime + buffTime + parseTime;
	
	var results = formatResults(geoprocess, inputBytes, inputNodes, dataTime, inputParseTime, buffTime, parseTime, totalTime, buffValid, outputBytes, outputNodes);//-->results.js

	return results;
}

/**
 * Takes output from algorithms.js and formats into string
 *
 * @param {results} data to put into database
 * @returns a string of data to be sent to server
 */

function formatResults(algorithm, inputBytes, inputNodes, dataTime, inputParseTime, processTime, parseTime, totalTime, outputValid, outputBytes, outputNodes){

	var platform = "client";
	return "date=" + dateToday +
		"&platform=" +platform+
		"&algorithm=" +algorithm+
		"&input(bytes)=" +inputBytes+
		"&input(nodes)=" +inputNodes+
		"&serverData(ms)=" +dataTime+
		"&inputParse(ms)="+inputParseTime+
		"&geoprocess(ms)=" +processTime+
		"&parse(ms)=" +parseTime+
		"&total(ms)=" +totalTime+
		"&valid=" + outputValid+
		"&output(bytes)=" +outputBytes+
		"&output(nodes)=" +outputNodes;
}

