/**
 * @fileoverview The gate keeper between algorithms.js and flow.js
 * @author <a href="mailto:erin@erinhamilton.me">Erin Hamilton</a>
 * @version 1.0
 */

/**Global Varriables*/
var sizeArray = ["50", "100", "200", "300", "400", "500", "600", "700", "800" , "900", "1000"];

/**
 * Calls the buffer function in algorithms.js on all data.
 * @param (dataType) Point, Line, or Polygon
 * @param (callback) When function finishes, return to main.js
 *
*/
function callBuf(dataType, callback) {
	
	async.eachSeries(Object.keys(sizeArray), function(item, done){
		//var size = sizeArray[item];
		var requestStart = new Date();
		microAjax("http://localhost:8080/thesis/rest/services/" + dataType + "/" + id, function (data) {
			var requestEnd = new Date();
			var requestTime = requestEnd - requestStart;
			var byteSize = data.length;
			//Do something with data size and time of request
			bufferGeom(data, dataType, 1000);
			done();
		});
			
	}, function(err){
		console.log(err);
		callback();//-> return to main.js
	});



}