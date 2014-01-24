/**
 * @fileoverview Sends data to database and retrieves results from database
 * @author <a href="mailto:erin@erinhamilton.me">Erin Hamilton</a>
 * @version 1.0
 */

/**
 * Records time of all steps of geoprocess and returns a string
 * of the results
 *
 * @param {geoprocess} Buffer, Union, or Voronoi
 * @param {dataTime} Time to retrieve data on server
 * @param {wkt} well-known text from the server
 * @param {wktTwo} if union, second wkt polygon, otherwise null
 * @returns a string of the results to be sent to the server
 */
function getResults(geoprocess, dataTime, wkt, wktTwo){

	var inputBytes = 0;
	var inputNodes = 0;
	var inputParseTime = 0;
	var output = "";
	var geoprocessTime = 0;
	
	switch(geoprocess)
	{
	case "Buffer":
		//Input wkt length in bytes and # nodes
		inputBytes = wkt.length;
		inputNodes = getNodeSize(wkt);//-->data.js
		
		//parse the input WKT to geometry
		var inputParseStart = new Date();
		var input = parseInput(wkt); //-->data.js
		inputParseTime = new Date() - inputParseStart;
		
		//Buffer the geometry
	   var geoprocessStart = new Date();
	   output = bufferGeom(input); //-->algorithm.js
	   geoprocessTime = new Date() - geoprocessStart;
		
	  	break;
	  	
	case "Union":
		//Input wkt length in bytes and # nodes
		inputBytes = wkt.length + wktTwo.length;
		inputNodes = (getNodeSize(wkt)) + (getNodeSize(wktTwo));//-->data.js
		
		//parse the input WKT to geometry
		var inputParseStart = new Date();
		var a = parseInput(wktOne); //-->data.js
		var b = parseInput(wktTwo); //-->data.js
		inputParseTime = new Date() - inputParseStart;
		
		//Union Geoprocess
	    var geoprocessStart = new Date();
	    output = unionGeom(a, b);//-->algorithm.js
		geoprocessTime = new Date() - geoprocessStart;
	  break;
	  
	case "Voronoi":
		//Input wkt length in bytes and # nodes
		inputBytes = wkt.length;
		inputNodes = getNodeSize(wkt);//-->data.js
		
		//parse the input WKT to geometry
		var inputParseStart = new Date();
		var input = parseInput(wkt); //-->data.js
		inputParseTime = new Date() - inputParseStart;
		
		//Voronoi Triangulation Geoprocess
	    var geoprocessStart = new Date();
	    output = voronoiTriGeom(input);//-->algorithms.js
		geoprocessTime = new Date() - geoprocessStart;
	break;
	default:
	  "error";
	}
	
    //Validate output geometry
	var outputValid = output.isValid(); 
	
	//Parse the buffer geometry results back to WKT
	var parseStart = new Date();
	var outputResult = parseOutput(output);//-->data.js
	var parseTime = new Date() - parseStart;
		
	//Get the size in both bytes and # nodes of WKT result
	var outputBytes = outputResult.length;
	var outputNodes = getNodeSize(outputResult);//-->data.js
	
	var totalTime = dataTime + inputParseTime + geoprocessTime + parseTime;
	
	var results = formatResults(geoprocess, inputBytes, inputNodes, dataTime, inputParseTime, geoprocessTime, parseTime, totalTime, outputValid, outputBytes, outputNodes);//-->results.js

	return results;
}


/**
 * Takes output from getResults and formats into string
 *
 * @param {results} data to put into database
 * @returns a string of data to be sent to server
 */

function formatResults(algorithm, inputBytes, inputNodes, dataTime, inputParseTime, processTime, parseTime, totalTime, outputValid, outputBytes, outputNodes){

	return "date=" + dateToday +
		"&platform=Client" +
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

