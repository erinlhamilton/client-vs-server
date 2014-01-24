/**
 * @fileoverview Runs a serious of geometry tests using the Buffer, Union,
 * and Voronoi Triangulation algorithms using jsts.js
 * @author <a href="mailto:erin@erinhamilton.me">Erin Hamilton</a>
 * @version 1.0
 */

/**
 * Receives a geometry point, line, or polygon
 * and returns a buffered geometry polygon
 *
 * @param {input} geometry to be buffered
 * @returns a geometry of the buffer results
 *
 */
function bufferGeom(input){

	var bufferDist = 100;
    return input.buffer(bufferDist); 
}


/**
 *  
 * @param:  
 *
 */
function intersectGeom(polyOne, polyTwo, inputBytes, inputNodes, requestTime, dataType){

	var geoprocess = 'Intersect';
	
	//parse the input WKT to geometry
	var inputParseStart = new Date();
	var a = parseInput(polyOne); //-->data.js
	var b = parseInput(polyTwo); //-->data.js
	var inputParseTime = new Date() - inputParseStart;//TODO: add input parse time to result string
	
	//Union Geoprocess
    var intersectStart = new Date();
    var intersect = a.union(b);
	var unionTime = new Date() - intersectStart;
	
	//TODO: add valid variable to result string
	var unionValid = intersect.isValid();
    
	//parse the output geometry to WKT
	var parseStart = new Date();
	var unionResult = parseOutput(intersect);//-->data.js
	var parseTime = new Date() - parseStart;
	
	//Get the size in both bytes and # nodes of result
	var outputBytes = unionResult.length;
	var outputNodes = getNodeSize(unionResult);//-->data.js
	
	var totalTime = requestTime + inputParseTime + unionTime + parseTime;
	
	var results = formatResults(geoprocess, inputBytes, inputNodes, requestTime, unionTime, parseTime, totalTime, outputBytes, outputNodes);//-->results.js

	storeResults(results); //--> results.js
}


/**
 *  
 * @param:  
 *
 */
function voronoiTriGeom(requestTime, inputBytes, inputNodes, wkt, dataType){

	var geoprocess = 'Voronoi';
	
	//parse the input WKT to geometry
	var inputParseStart = new Date();
	var input = parseInput(wkt); //-->data.js
	var inputParseTime = new Date() - inputParseStart;//TODO: add input parse time to result string
	
	//Voronoi Triangulation Geoprocess
    var voronoiStart = new Date();
    var geomFact = new jsts.geom.GeometryFactory();
	var builder = new jsts.triangulate.VoronoiDiagramBuilder();
    builder.setSites(input);
    var voronoi = builder.getDiagram(geomFact);
	var voronoiTime = new Date() - voronoiStart;
	
	var parseStart = new Date();
    var voronoiResult = parserOutput(voronoi);
	var parseTime = new Date() - parseStart;
	
	//Get the size in both bytes and # nodes of result
	var outputBytes = unionResult.length;
	var outputNodes = getNodeSize(voronoiResult);//-->data.js

	var totalTime = voronoiTime + parseTime + requestTime;
	
	getResults(dateToday, platform, dataType, inputSize, byteSize, geoprocess, voronoiTime, parseTime, totalTime, resultSize, requestTime);
}