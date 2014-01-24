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
var latencyTime;
var bandwidthTime;
var dateToday = "";
var hardware;
var os;
var browser;


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
 *  Record results of the bandwidth test
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

	//testBaseLatency();
	//testBaseBandwidth();
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
 * Gets current date and returns readable format
 * @returns formatted date  
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
    return curr_year + "-" + curr_month + "-" + curr_date + curr_hour + ":" + curr_min + ampm;
}

function whichBrowser(){
	
	var value=document.getElementById("browser");
	browser = value.options[value.selectedIndex].text;

}

function operatingSystem(){
	
	var value=document.getElementById("OS");
	os = value.options[value.selectedIndex].text;

}

function whatHardware(){
	
	var value=document.getElementById("hardware");
	hardware = value.options[value.selectedIndex].text;

}

/**
 * Click function for HTML button that starts client tests. 
 *
*/
function runClient(){
	
	dateToday = createDate();
	
	async.series([
		function(callback){
			callBuf("points", callback);
		}//,
//		 function(callback){
//			callBuf("Polylines", callback);
//		},
//		function(callback){
//			callBuf("Polygon", callback);
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
