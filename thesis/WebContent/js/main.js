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
var imageAddr = "../WebContent/img/2012_7.png" + "?n=" + Math.random();
var startTime, endTime;
var downloadSize = 1531904;
var download = new Image();
var latencyTime = "";
var bandwidthTime = "";
var dateToday = "";


/**
 *  Test the base latency before testing begins
 */
function testBaseLatency(){
	
	window.startTime = new Date();
	var totalTime = "";
	
	microAjax("/", function () {
		window.endTime = new Date();
		totalTime = window.endTime - window.startTime;
		});
	
     latencyTime = "Latency(ms): " + totalTime;
}

/**
 *  Test the base bandwidth by loading an img from the server.
 */
function testBaseBandwidth(){

	download.onload = function () {
		endTime = (new Date()).getTime();
		showResults();
	};
	startTime = (new Date()).getTime();
	download.src = imageAddr;
}

/**
 *  print results of bandwidth test to console
 */
function showResults() {
    var duration = (endTime - startTime) / 1000; //Math.round()
    var bitsLoaded = downloadSize * 8;
    var speedBps = (bitsLoaded / duration).toFixed(2);
    var speedKbps = (speedBps / 1024).toFixed(2);
    var speedMbps = (speedKbps / 1024).toFixed(2);
    bandwidthTime = "Bandwidth(Mbps): " + speedMbps;
}


/**
 * Test base latency and bandwidth
 *
*/
function initialize(){

	testBaseLatency();
	testBaseBandwidth();
}

/**
 * On page load, run bandwidth and latency tests.
 *
*/
//window.onload = function()
//                {
//                   initialize();
//                };

/**
 * Click function for html button that starts client tests.
 * @param:  
 *
*/
function runClient(){
	dateToday = new Date();
	async.series([
		function(callback){
			callBuf("points", callback);
		}//,
//		 function(callback){
//			callBuf("polylines", callback);
//		},
//		function(callback){
//			callBuf("polygon", callback);
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
 * @param:  
 *
*/
function runServer(){
	//TODO: function to start server test
}
