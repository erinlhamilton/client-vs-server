/**
 * @fileoverview Main JavaScript file for application. Tests for
 * bandwidth and latency on load and initializes server/client
 * tests on button click.
 * @author <a href="mailto:erin@erinhamilton.me">Erin Hamilton</a>
 * @version 1.0
*/

/*
 * Global Variables
 */
//var imageAddr = "../img/2012_7.png"; //TODO: + "?n=" + Math.random();
var startTime, endTime;
var downloadSize = 1531904;
var download = new Image();
var mdJSON = {};


/**
 *  Determine the current network latency
 */
function testBaseLatency(){
	
	window.startTime = new Date();
	var totalTime = 0;
	microAjax("/", function () {
		window.endTime = new Date();
		totalTime = window.endTime - window.startTime;
		return totalTime;
		});
	
}

/**
 *  Determine the current network bandwidth based on downloading
 *  an image from the server of a known size
 */
function testBaseBandwidth(){

	download.onload = function () {
		endTime = (new Date()).getTime();
		var bandwidth = showResults();
		return bandwidth;
	};
	startTime = (new Date()).getTime();
	download.src = imageAddr;
}

/**
 *  Record results of the bandwidth test
 */
function showResults() {
    var duration = (endTime - startTime) / 1000; //Math.round()
    var bitsLoaded = downloadSize * 8;
    var speedBps = (bitsLoaded / duration).toFixed(2);
    var speedKbps = (speedBps / 1024).toFixed(2);
    var speedMbps = (speedKbps / 1024).toFixed(2);
    return speedMbps;
}


/**
 * Record metadata of test and store in a JSON
 * object
 *
*/
function initialize(){
	
	mdJSON.ID = testID();
	mdJSON.Date = createDate();
	mdJSON.Browser = whichBrowser();
	mdJSON.OS = operatingSystem();
	mdJSON.Hardware = whatHardware();
	mdJSON.FirstLatency = "";//testBaseLatency();
	mdJSON.FirstBandwidth = "";//testBaseBandwidth();

}

/**
 * Run second set of latency and bandwidth tests,
 * store in json object
 *
*/
function secondTest(){
	
	mdJSON.SecondLatency = "";//testBaseLatency();
	mdJSON.SecondBandwidth = "";//testBaseBandwidth();
	
}

/**
 * Run a third set of latency and bandwidth tests,
 * store in json object
 *
*/
function thirdTest(){
	
	mdJSON.ThirdLatency = "";//testBaseLatency();
	mdJSON.ThirdBandwidth = "";//testBaseBandwidth();
	storeMetadata();
	
}


/**
 * Gets current date and returns readable string format
 * @returns formatted date of year-month-day hour:minute
 *
*/
function createDate(){
    var d = new Date();
    var curr_date = d.getDate();
    var curr_month = d.getMonth() + 1; //Months are zero based
    var curr_year = d.getFullYear();
    var curr_hour = d.getHours();
    var ampm = curr_hour >= 12 ? 'PM' : 'AM';
    var curr_min = d.getMinutes();
    return curr_year + "-" + curr_month + "-" + curr_date + " " + curr_hour + ":" + curr_min + ampm;
}

/**
 * Returns the value in the text input form for the ID
 * of the test
 * @returns ID of the test 
 *
*/

function testID(){
	
	return document.getElementById("testID").value;

}

/**
 * Returns result of browser dropdown
 * @returns returns the browser selected
 *
*/

function whichBrowser(){
	
	var value=document.getElementById("browser");
	return value.options[value.selectedIndex].text;

}

/**
 * Returns result of operating system dropdown
 * @returns returns the OS selected
 *
*/

function operatingSystem(){
	
	var value=document.getElementById("OS");
	return value.options[value.selectedIndex].text;

}

/**
 * Returns result of hardware dropdown
 * @returns returns the hardware selected
 *
*/

function whatHardware(){
	
	var value=document.getElementById("hardware");
	return value.options[value.selectedIndex].text;

}

/**
 * Click function for HTML button that starts client tests
 * usign async.js to run tests in series
 *
*/
function runClient(){
	
	async.series([
		function(callback){
			callBuf("points", callback);
		}//,
//		 function(callback){
//			callBuf("Lines", callback);
//		},
//		function(callback){
//			callBuf("Polygons", callback);
//		},
//		function(callback){
//			callTriangulation(callback);
//		},
//		function(callback){
//			callIntersect(callback);
//		}	
		]);
}

/**
 * Click function for html button that starts server tests.
 *
*/
function runServer(){
	
	microAjax("http://localhost:8080/thesis/rest/services/server/" + mdJSON.ID, 
			function (err) {
				console.log(err); 
			});
}
