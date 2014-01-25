/**
 * @fileoverview The gate keeper between algorithms.js, data.js, and results.js
 * @author <a href="mailto:erin@erinhamilton.me">Erin Hamilton</a>
 * @version 1.0
 */

/**Global Varriables*/
var sizeArray = ["0", "1"];

//var sizeArray = ["50", "100", "200", "300", "400", "500", "600", "700", "800" , "900", "1000"];

/**
 * Controls the flow the buffer testing.
 * @param (dataType) Point, Line, or Polygon
 * @param (callback) When function finishes, return to main.js
 *
*/
function callBuf(dataType, callback) {
	
	async.eachSeries(Object.keys(sizeArray), function(item, done){
		var geoprocess = "Buffer";
		var id = sizeArray[item];
		microAjax("http://localhost:8080/thesis/rest/services/" + dataType + "/" + id, function (data) {
			var dataJSON = JSON.parse(data);
			var dataTime = dataJSON.time;//Time, on server, to retrieve data from db
			var wkt = dataJSON.wkt;
			results = getResults(geoprocess, dataType, dataTime, wkt, null);
			console.log(results);
			//storeResults(results);
			done();
		});
			
	}, function(err){
		console.log(err);
	});

}

/**
 * Controls the flow of the voronoi triangulation tests on points
 * @param (callback) When function finishes, return to main.js
 *
*/
function callTriangulation(callback){
	
	async.eachSeries(Object.keys(sizeArray), function(item, done){
		var geoprocess = "Voronoi";
		var id = sizeArray[item];
		microAjax("http://localhost:8080/thesis/rest/services/points/" + id, function (data) {
			var dataJSON = JSON.parse(data);
			var dataTime = dataJSON.time;//Time, on server, to retrieve data from db
			var wkt = dataJSON.wkt;
			var results = getResults(geoprocess, "points",  dataTime, wkt, null);
			console.log(results);
			//storeResults(results);
			done();
		});
			
	}, function(err){
		console.log(err);
	});
	
}

/**
 * Controls the flow of the union testing
 * @param (callback) When function finishes, return to main.js
 *
*/
function callUnion(callback){
	
	async.eachSeries(Object.keys(sizeArray), function(item, done){
		var geoprocess = "Union";
		var id = sizeArray[item];
		microAjax("http://localhost:8080/thesis/rest/services/union/" + id, function (dataOne) {
			var jsonOne = JSON.parse(data);
			var dataTime = ParseInt(jsonOne.time);//Time, on server, to retrieve data from db
			var dataOne = dataJSON.wkt;
			microAjax("http://localhost:8080/thesis/rest/services/union/" + id+"b", function (data) {
				var jsonTwo = JSON.parse(data);
				dataTime += ParseInt(jsonTwo.time);//Time, on server, to retrieve data from db
				var dataTwo = jsonTwo.wkt;
				var results = getResults(geoprocess, "polygon", dataTime, dataOne, dataTwo);
				console.log(results);
				//storeResults(results);
			});
			done();
		});
			
	}, function(err){
		console.log(err);
	});
	
}