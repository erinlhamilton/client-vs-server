/**
 * @fileoverview The gate keeper between algorithms.js, data.js, and results.js
 * @author <a href="mailto:erin@erinhamilton.me">Erin Hamilton</a>
 * @version 1.0
 */

/**Global Varriables*/
//var sizeArray = [10, 20, 30, 40, 50, 60, 70, 80, 90, 100, 200, 300, 400, 500, 600,
 //				700, 800, 900, 1000, 2000, 3000, 4000, 5000, 6000, 7000, 8000, 9000, 10000,
// 				20000, 30000, 40000, 50000, 60000, 70000, 80000, 90000, 100000];

var sizeArray = new Array();

/**
 * Controls the flow the buffer testing.
 * @param (dataType) Point, Line, or Polygon
 *
*/
function callBuf(dataType, callback) {
	var div;
	var div2;
	
	switch(dataType)
	{
	case 'points':
//	  	sizeArray = [10, 20, 30, 40, 50, 60, 70, 80, 90, 100, 200, 300, 400, 500, 600,
//	   				700, 800, 900, 1000, 2000, 3000, 4000, 5000, 6000, 7000, 8000, 9000, 10000,
//	  				20000, 30000];
	  	sizeArray = [10, 20, 30, 40, 50];
	  	div = document.getElementById(dataType);
		div.innerHTML = 'Processing';
	  break;
	case 'lines':
//		sizeArray = [10, 20, 30, 40, 50, 60, 70, 80, 90, 100, 200, 300, 400, 500, 600,
//		             700, 800, 900, 1000, 2000, 3000, 4000, 5000, 6000, 7000, 8000, 9000, 10000,
//		  			20000, 30000];
		sizeArray = [10, 20, 30, 40, 50];
		div = document.getElementById(dataType);
		div.innerHTML = 'Processing';
	  break;
	case 'polygons':
//		sizeArray = [10, 20, 30, 40, 50, 60, 70, 80, 90, 100, 200, 300, 400, 500, 600,
//		  			700, 800, 900, 1000, 2000, 3000, 4000, 5000, 6000, 7000, 8000, 9000];
		sizeArray = [10, 20, 30, 40, 50];
		div = document.getElementById(dataType);
		div.innerHTML = 'Processing';
	break;
	default:
	  console.log("switch statement blew up!");
	}
	
	async.eachSeries(Object.keys(sizeArray), function(item, done){
		var geoprocess = "Buffer";
		var id = sizeArray[item];
		div2 = document.getElementById(dataType +"ID");
		div2.innerHTML = id;
		microAjax(serverlocation +"/rest/services/" + dataType + "/" + id, function (data) {
			var dataJSON = JSON.parse(data);
			var dataTime = dataJSON.time;//Time, on server, to retrieve data from db
			var wkt = dataJSON.wkt;
			results = getResults(geoprocess, id, dataType, dataTime, wkt, null);//--> results.js
			async.series([
	      		function(returnToProcess){
	      			storeResults(results, returnToProcess);//--> data.js
	      		}	
	      	]);
			done();
		});
			
	}, function(err){
		console.log(err);
		div = document.getElementById(dataType);
		div.innerHTML = 'Complete!';
		callback();
	});

}

/**
 * Controls the flow of the voronoi triangulation tests on points
 *
*/
function callTriangulation(callback){
	var div;
	var div2;
//	sizeArray = [10, 20, 30, 40, 50, 60, 70, 80, 90, 100, 200, 300, 400, 500, 600,
//	   				700, 800, 900, 1000, 2000, 3000, 4000, 5000, 6000, 7000, 8000, 9000, 10000,
//	   				20000, 30000, 40000, 50000, 60000];
	
	sizeArray = [10, 20, 30, 40, 50];
	
	div = document.getElementById("voronoiTri");
	div.innerHTML = 'Processing';
	
	async.eachSeries(Object.keys(sizeArray), function(item, done){
		var geoprocess = "Voronoi";
		var id = sizeArray[item];
		//let html page know what test number we're on
		div2 = document.getElementById("voronoiID");
		div2.innerHTML = id;
		microAjax(serverlocation +"/rest/services/points/" + id, function (data) {
			var dataJSON = JSON.parse(data);
			var dataTime = dataJSON.time;//Time, on server, to retrieve data from db
			var wkt = dataJSON.wkt;
			var results = getResults(geoprocess, id, "points",  dataTime, wkt, null);
			async.series([
	      		function(returnToProcess){
	      			storeResults(results, returnToProcess);//--> data.js
	      		}	
	      	]);
			done();//callback
		});
			
	}, function(err){
		console.log(err);
		div = document.getElementById('voronoiTri');
		div.innerHTML = 'Complete!';
		callback();
	});
	
}

/**
 * Controls the flow of the union testing
 *
*/
function callUnion(callback){
	var div;
	var div2;
//	sizeArray = [10, 20, 30, 40, 50, 60, 70, 80, 90, 100, 200, 300, 400, 500, 600,
//	   				700, 800, 900, 1000, 2000, 3000, 4000, 5000, 6000, 7000, 8000, 9000, 10000,
//	   				20000];
	
	sizeArray = [10, 20, 30, 40, 50];
	
	div = document.getElementById("polyUnion");
	div.innerHTML = 'Processing';
	
	async.eachSeries(Object.keys(sizeArray), function(item, done){
		var geoprocess = "Union";
		var id = sizeArray[item];
		//let html page know what test number we're on
		div2 = document.getElementById("unionID");
		div2.innerHTML = id;
		microAjax(serverlocation +"/rest/services/union/" + id, function (data) {
			var dataJSON = JSON.parse(data);
			var dataTime = dataJSON.time;//Time, on server, to retrieve data from db
			var dataOne = dataJSON.wktA;
			var dataTwo = dataJSON.wktB;
			var results = getResults(geoprocess, id, "polygon", dataTime, dataOne, dataTwo);
			async.series([
			     function(returnToProcess){
	      			storeResults(results, returnToProcess);//--> data.js
	      		}	
	      	]);
			done();
		});
			
	}, function(err){
		console.log(err);
		div = document.getElementById('polyUnion');
		div.innerHTML = 'Complete!';
		setTimeout(function(){
			location.reload();
		},10000);
	});
	
}