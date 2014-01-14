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
 *		The WKT point, line, or polygon
 * @param {dataType}
 *		Is the WKT a point, line, or polygon?
 * @param {inputLength}
 *		Length (in bytes) of the input data.
 *
 */
function bufferGeom(wkt, dataType, inputLength){

	var bufferDist = 100;
	//var dateToday = new Date();
	var platform = 'Client';
	var geoprocess = 'Buffer';
	var inputSize = inputLength;
	
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
	
	var buffergeom = reader.read(buffer);
	
	var parseEnd = new Date();
	var parseTime = parseEnd - parseStart;
	
	//Get the size in both bytes and # coordinates of result
	var resultSize = buffer.length;
	
	var geomList = [];
	//var geomList = new jsts.geom.util.PointExtracter();
	var output = jsts.geom.util.PointExtracter.getPoints(geomList, buffer);
	//var geomList = new jsts.geom.util.PointExtracter();
	//var hello = jsts.geom.util.PointExtracter.getPoints(buffer, geomList);
	var totalTime = buffTime + parseTime;
	
	console.log(platform + "," + geoprocess + ", Inpute Size: " + inputSize + ", Total Time: " + totalTime + ", Result Size" + resultSize);
	console.log(output);
	
}