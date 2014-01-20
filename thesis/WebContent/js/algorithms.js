/**
 * @fileoverview Runs a serious of geometry tests using the Buffer, Union,
 * and Voronoi Triangulation algorithms using jsts.js
 * @author <a href="mailto:erin@erinhamilton.me">Erin Hamilton</a>
 * @version 1.0
 */

/**
 * Receives WKT data (points, multilinestrings, and polygons) from the server
 * and runs a geometry buffer on them.
 *
 * @param {wkt}
 *		The WKT point, line, or polygon from server
 * @param {dataType}
 *		Is the WKT a point, line, or polygon?
 * @param {inputLength}
 *		Length (in bytes) of the input data.
 *
 */
function bufferGeom(requestTime, inputSize, wkt, dataType){

	var bufferDist = 100;
	var platform = 'Client';
	var geoprocess = 'Buffer';
	
	var reader = new jsts.io.WKTReader();
	var input = reader.read(wkt);
	
	//Buffer the WKT
    var buffStart = new Date();
    var buffer = input.buffer(bufferDist); 
	var buffEnd = new Date();
	var buffTime = buffEnd - buffStart;
	
	//Parse the buffer results back to WKT
	var parseStart = new Date();
	var parser = new jsts.io.WKTParser();
    buffer = parser.write(buffer);
	
	reader.read(buffer);
	
	var parseEnd = new Date();
	var parseTime = parseEnd - parseStart;
	
	console.log(buffer.isValid());
	
	
	//Get the size in both bytes and # nodes of result
	var resultBytes = buffer.length;
	var resultNodes = (buffer.split(",").length - 1) - (buffer.split("),(").length - 1);
	
	var totalTime = requestTime + buffTime + parseTime;
	
	var results = "Platform: " + platform + ", Geoprocess: " + geoprocess + inputSize + 
	", Request Time(ms): " + requestTime + ", Geoprocess Time(MS): " + buffTime + 
	", Parse Time(MS): " + parseTime + ", Total Time(MS): " + totalTime + 
	", Result Size(bytes): " + resultBytes + ", Result Size(nodes): " + resultNodes;
	
	storeResults(results); //--> to results.js
	//console.log(jsts.operation.valid.isValidOp.checkValidMultiPoint(output));
}


/**
 *  
 * @param:  
 *
 */
function intersectGeom(polyOne, polyTwo, inputLength, byteSize, requestTime){
	var dateToday = new Date();
	var platform = 'Client';
	var geoprocess = 'Intersect';
	var dataType = 'polygon';
	var inputSize = inputLength;
	
	var reader = new jsts.io.WKTReader();

	var a = reader.read(polyOne);
	var b = reader.read(polyTwo);
	
    var intersectStart = new Date();
    var intersect = a.union(b);
	var intersectEnd = new Date();
	var intersectTime = intersectEnd - intersectStart;
    
	var parseStart = new Date();
	var parser = new jsts.io.WKTParser();
    intersect = parser.write(intersect);
	var parseEnd = new Date();
	var parseTime = parseEnd - parseStart;
	var resultSize = intersect.length;
	
	var totalTime = parseTime + intersectTime+ requestTime;
	
	getResults(dateToday, platform, dataType, inputSize, byteSize, geoprocess, intersectTime, parseTime, totalTime, resultSize, requestTime);

}

/**
 *  
 * @param:  
 *
 */
function voronoiTriGeom(wicket, inputLength, byteSize, requestTime){

	var dateToday = new Date();
	var dataType = 'points';
	var platform = 'Client';
	var geoprocess = 'Voronoi';
	var inputSize = inputLength;
	
	var geomFact = new jsts.geom.GeometryFactory();
	var reader = new jsts.io.WKTReader();
	
	var input = reader.read(wicket);
    var voronoiStart = new Date();
	var builder = new jsts.triangulate.VoronoiDiagramBuilder();
    builder.setSites(input);
    var voronoi = builder.getDiagram(geomFact);
	var voronoiEnd = new Date();
	var voronoiTime = voronoiEnd - voronoiStart;
	
	var parseStart = new Date();
	var parser = new jsts.io.WKTParser();
    input = parser.write(input);
    voronoi = parser.write(voronoi);
	 
	var parseEnd = new Date();
	var parseTime = parseEnd - parseStart;
	var resultSize = voronoi.length;

	var totalTime = voronoiTime + parseTime + requestTime;
	
	getResults(dateToday, platform, dataType, inputSize, byteSize, geoprocess, voronoiTime, parseTime, totalTime, resultSize, requestTime);
}