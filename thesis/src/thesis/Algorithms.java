/**
 * The geoprocessing meat of the application. Buffer, voronoi triangulation,
 * and union process here. All methods accept and return JTS geometry.
 *
 * <p>Bugs: (a list of bugs and other problems)
 *
 * @author Erin Hamilton (erin@erinhamilton.me)
 */
package thesis;

import com.vividsolutions.jts.geom.*;
import com.vividsolutions.jts.triangulate.*;

public class Algorithms {	
	
	/**
	 * Runs a JTS buffer on geometry at a distance of
	 * 100 Arc Seconds
	 *
	 * @param {data} point, line, or polygon Geometry
	 * @return the buffered geometry polygon
	 */
	public Geometry bufferGeom(Geometry data){
			Geometry buff = null;
			int buffDistance = 100;
		try {
			
			buff = data.buffer(buffDistance);
			
		} catch ( Exception e ) {
		      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		      System.exit(0);
		}
		
		return buff;
	}
	
	/**
	 * Runs a JTS union on two geometry polygons
	 *
	 * @param {geomOne} geometry polygon
	 * @param {geomTwo} geometry polygon
	 * @returns one union geometry polygon
	 */
	public Geometry unionGeom(Geometry geomOne, Geometry geomTwo){
		
		Geometry output = null;
		try {

			output = geomOne.union(geomTwo);

			
		} catch ( Exception e ) {
		      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		      System.exit(0);
		}
		return output;
	}
	
	/**
	 * Creates Voronoi Triangles from geometry points
	 *
	 * @param {geom} geometry of points
	 * @return geometry of polygon triangles
	 */
	public Geometry voronoiGeom(Geometry geom){

		VoronoiDiagramBuilder vdb = new VoronoiDiagramBuilder();
		GeometryFactory gf = new GeometryFactory();
		Geometry output = null;
		
		try {
			 vdb.setSites(geom);
			 output = vdb.getDiagram(gf);

		} catch ( Exception e ) {
		      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		      System.exit(0);
		}
		
		return output;
	}

}
