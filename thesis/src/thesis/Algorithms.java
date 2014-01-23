/**
 * (Write a succinct description of this class here. You should avoid
 * wordiness and redundancy. If necessary, additional paragraphs should
 * be preceded by <p>, the html tag for a new paragraph.)
 *
 * <p>Bugs: (a list of bugs and other problems)
 *
 * @author (your name)
 */
package thesis;

import com.vividsolutions.jts.geom.*;
import com.vividsolutions.jts.triangulate.*;
import com.vividsolutions.jts.io.*;

public class Algorithms {
	
	public static void main(String[] args){
		String wkt = "MULTIPOINT ( (155674.37460511914 -443046.74597655982), (162733.5692522134 -428927.53813346522), (162505.15310308756 -428761.34844572423), (156826.22761864113 -458734.64822297962), (185814.69948796701 -452759.25800804654), (138805.49294724246 -416601.70107035851), (145754.76565974296 -397274.44175968971), (198432.74587975419 -429401.35659425473), (188396.06962267045 -445478.06185319694))";
		String wkt2 = "MULTIPOLYGON (((199202.86210764822 -431933.98831948638,199203.3831462689 -431933.97727687191,199203.36609454974 -431933.3190141595,199217.7810539426 -431932.97689810814,199217.91574606794 -431938.61476262566,199216.58128865238 -431938.64914659457,199216.70393907206 -431943.86042081425,199218.04146130229 -431943.82907294389,199218.28999052604 -431954.25765421288,199216.67505409257 -431954.29488147702,199216.73812232004 -431956.98275924008,199205.81498679228 -431957.24176820787,199205.78156476904 -431955.80638823844,199204.07832137326 -431955.84858795814,199204.06856679055 -431955.38833812112,199203.27641202975 -431955.40512662381,199203.14381466349 -431949.72450411227,199203.90551522636 -431949.70536001865,199203.88484607 -431948.87641599774,199203.30899008736 -431948.89172143769,199203.17539625565 -431943.30565183097,199203.80603525278 -431943.28918534843,199203.78811503784 -431942.58522555232,199205.22020796817 -431942.55187351396,199205.16327495596 -431940.15346383583,199203.92628733988 -431940.18578197155,199203.9094941619 -431939.53971797554,199202.99544294373 -431939.56219094014,199202.86210764822 -431933.98831948638)),((148820.10331359919 -447918.37031158665,148819.90668739766 -447911.42977683712,148826.3081534933 -447911.24740393786,148826.50477998549 -447918.1879386832,148820.10331359919 -447918.37031158665)),((162906.93193164084 -426069.71873401385,162906.31315407064 -426069.72249958618,162905.74127691184 -426069.48748690542,162905.30236540784 -426069.05460919347,162905.06487037955 -426068.48325611372,162899.95657771835 -426067.78530073166,162900.15197598792 -426066.34803121537,162890.99923645295 -426065.09758956823,162891.26870080779 -426063.11915147537,162884.81536765478 -426062.239002496,162885.79572552608 -426055.0557851484,162886.90228400836 -426055.20646205777,162887.67409197259 -426049.55832870351,162893.77279710403 -426050.39098846633,162893.61200141386 -426051.58963761618,162898.08140684577 -426052.20034371642,162898.21104525158 -426051.26155147282,162902.17606377232 -426051.80349733913,162902.04687387723 -426052.76368875382,162903.49280020248 -426052.95907333959,162903.64864639658 -426051.81534990435,162910.32826778106 -426052.7273699753,162908.58682838353 -426065.48826756142,162909.22252333906 -426065.56638033688,162908.70514409512 -426069.09603383858,162907.97712317569 -426068.97413785663,162907.93577929327 -426069.03903004713,162907.50310329979 -426069.48107214831,162906.93193164084 -426069.71873401385)),((147922.31598265283 -445443.61583835119,147921.67476296023 -445430.55973968469,147923.25535944014 -445430.48106030934,147923.18697233131 -445429.10140413558,147934.30925984326 -445428.55543711828,147934.63111951092 -445435.06665728567,147934.11335337622 -445435.09276065743,147934.22995559039 -445437.46521433676,147935.01563513238 -445437.42742122896,147935.63009128909 -445449.92926372262,147926.95329840726 -445450.35410522064,147926.57255599601 -445442.62156428723,147924.69345484092 -445442.71267153043,147924.73108545615 -445443.49534683162,147922.31598265283 -445443.61583835119)),((170528.57611454854 -448909.58810423734,170529.5603668628 -448902.59847138636,170536.9632506654 -448903.64107053028,170535.97599717119 -448910.63066636678,170528.57611454854 -448909.58810423734)))";
		String unionOut = unionGeom(wkt, wkt2);
		System.out.println(unionOut);
		//		String result = bufferGeom(wkt);
//		System.out.println(result);
//		String voronoiOut = voronoiGeom(wkt);
//		System.out.println(voronoiOut);
		
		//TODO: controls data and flow of execution
	}
	
	
	/**
	 * Runs a JTS buffer on wkt data. Returns when done.
	 *
	 * @param {data} Well-known text geometry
	 */
	public static String bufferGeom(String data){
		
		WKTReader rdr = new WKTReader();
		WKTWriter parse = new WKTWriter();
		String output = "";
		try {
			Geometry geom = rdr.read(data);
			Geometry buff = geom.buffer(100);
			output = parse.write(buff);
			
		} catch ( Exception e ) {
		      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		      System.exit(0);
		}
		
		return output;
	}
	
	/**
	 * Runs a JTS union on two WKT polygons
	 *
	 * @param {data} Well-known text polygons
	 */
	public static String unionGeom(String dataOne, String dataTwo){
		WKTReader rdr = new WKTReader();
		WKTWriter parse = new WKTWriter();
		String output = "";
		try {
			Geometry geomOne = rdr.read(dataOne);
			Geometry geomTwo = rdr.read(dataTwo);
			Geometry union = geomOne.union(geomTwo);
			output = parse.write(union);
			
		} catch ( Exception e ) {
		      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		      System.exit(0);
		}
		return output;
	}
	
	/**
	 * Creates Voronoi Triangles from WKT points
	 *
	 * @param {data} Well-known text points
	 */
	public static String voronoiGeom(String data){
		//TODO
		WKTReader rdr = new WKTReader();
		WKTWriter parse = new WKTWriter();
		VoronoiDiagramBuilder vdb = new VoronoiDiagramBuilder();
		GeometryFactory gf = new GeometryFactory();
		String output = "";
		try {
			Geometry geom = rdr.read(data);
			 vdb.setSites(geom);
			 Geometry resultGeom = vdb.getDiagram(gf);
			 output = parse.write(resultGeom);
			
		} catch ( Exception e ) {
		      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		      System.exit(0);
		}
		return output;
	}

}
