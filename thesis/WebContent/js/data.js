/**
 * @fileoverview Handles formatting and server storage/response of
 * data and results
 * @author <a href="mailto:erin@erinhamilton.me">Erin Hamilton</a>
 * @version 1.0
 */


/**
 * Determines the size (in nodes) of well-known text
 *
 * @param {data} well-known text
 * @returns the size (in nodes) of the wkt
 *
 */
function getNodeSize(data){
	
	return (data.split(",").length) - (data.split("),(").length) + 2;

}

/**
 * Parses well-known text to geometry
 *
 * @param {wkt} string of well-known text
 * @returns jsts geometry
 *
 */
function parseInput(wkt){
	
	var reader = new jsts.io.WKTReader();
	return reader.read(wkt);
	
}

/**
 * Parses geometry to well-known text
 *
 * @param {result} jsts geometry
 * @returns well-known text
 *
 */
function parseOutput(result){
	
	var parser = new jsts.io.WKTWriter();
	return parser.write(result);
	
}

/**
 * Stores string of results from geoprocessing to DB using POST
 *
 * @param {data} string of data to put into database
 *
 */
function storeResults(callback) {
	
	async.eachSeries(Object.keys(resultArray), function(item, done){
		var data = resultArray[item];
		microAjax(serverlocation + "/thesis/rest/services/store", 
			function (err) {
				console.log(err); 
				done();	
			}, 
		data);
	}, function(err){
		console.log(err);
		callback();//-> return to main.js
	});
}


/**
 * Stores string of metadata from mdJSON to DB using POST
 *
 * @param {data} string of data to put into database
 *
 */
function storeMetadata() {
	
	var metadata = formatMetadata();
	
	microAjax(serverlocation + "/thesis/rest/services/storeMetadata", 
			function (err) {
				console.log(err); 
			}, 
			metadata);
}

