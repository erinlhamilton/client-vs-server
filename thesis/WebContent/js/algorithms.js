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
function unionGeom(wktOne, wktTwo){

    return wktOne.union(wktTwo);

}


/**
 *  
 * @param:  
 *
 */
function voronoiTriGeom(input){

    var geomFact = new jsts.geom.GeometryFactory();
	var builder = new jsts.triangulate.VoronoiDiagramBuilder();
    builder.setSites(input);
    return builder.getDiagram(geomFact);

}