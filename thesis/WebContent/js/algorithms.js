/**
 * @fileoverview Performs the buffer, union, and triangulation 
 * geoprocessing of various geometries and returns a geometry
 * result
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

	//buffer distance in arc seconds
	var bufferDist = 100;
    return input.buffer(bufferDist); 
}


/**
 *  Receives two polygon geometries and
 *  returns one union polygon geometry
 *  
 * @param: {polyOne} first polygon geometry
 * @param: {polyTwo} second polygon geometry
 * @returns one polygon geometry
 * 
 */
function unionGeom(polyOne, polyTwo){
	
	//CascadedPolygonUnion instead?

    return polyOne.union(polyTwo);

}

/**
 *  Take points geometry and returns a geometry
 *  collection of polygon geometries
 *  
 * @param: {input} point geometries
 * @returns geometry collection of polygons
 *
 */
function voronoiTriGeom(input){

    var geomFact = new jsts.geom.GeometryFactory();
	var builder = new jsts.triangulate.VoronoiDiagramBuilder();
    builder.setSites(input);
    return builder.getDiagram(geomFact);

}