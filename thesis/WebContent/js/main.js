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
//var time = performanceTiming.navigationStart;
var mdJSON = {};
var bwResult; //bandwidth result
var bwError; //bandwidth error
var latResult; //latency result
var latError; //latency error
var testSite = "thesis";
var runToTestNo = 10; //the test number you want the program to run to.

var serverlocation = "http://localhost:8080/"+testSite;


/**
 *  If browser does not support W3C High Resolution Time,
 *  Use date().getTime instead.
 */
window.performance = window.performance || {};
performance.now = (function() {
  return performance.now       ||
         performance.mozNow    ||
         performance.msNow     ||
         performance.oNow      ||
         performance.webkitNow ||
         function() { return new Date().getTime(); };
})();

/**
 * Gets current date and returns readable string format
 * @returns formatted date of year-month-day hour:minute
 *
*/
function createDate(){
	Date.prototype.timeNow = function(){ return ((this.getHours() < 10)?"0":"") + ((this.getHours()>12)?(this.getHours()-12):this.getHours()) +":"+ ((this.getMinutes() < 10)?"0":"") + this.getMinutes() +":"+ ((this.getSeconds() < 10)?"0":"") + this.getSeconds() + ((this.getHours()>12)?("PM"):"AM"); };
    var d = new Date();
    var curr_date = d.getDate();
    var curr_month = d.getMonth() + 1; //Months are zero based
    var curr_year = d.getFullYear();
   	return curr_year + "-" + curr_month + "-" + curr_date + " " + new Date().timeNow();
}


/**
 * Record metadata of test and store in a JSON
 * object
 *
*/
function testParams(){
	
	async.series([
  		function(callback){
  			getTestID(callback); //--> data.js
  		},
  		function(){
			mdJSON.Date = createDate();
			mdJSON.Browser = "Chrome";
			mdJSON.OS = "Windows";
			mdJSON.Hardware = "Laptop";
			if(mdJSON.ID <=runToTestNo){
				storeMetadata();
				setTimeout(function(){
					runClient();
				},5000);
			}else{
				alert("Testing Complete!");
			}
	     }
	]);

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
		},
		 function(callback){
			callBuf("lines", callback);
		},
		function(callback){
			callBuf("polygons", callback);
		},
		function(callback){
			callTriangulation(callback);
		},
		function(callback){
			callUnion(callback);
		}	
		]);
}

/**
 * On window load begin tests.
 *
*/

window.onload = function(){
	testParams();
};
