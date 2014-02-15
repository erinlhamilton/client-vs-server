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
var serverlocation = "http://localhost:8080";


/**
 *  Bandwidth and latency test from Yahoo! Boomerang library
 *  Puts result in variable to be sent in string to server
 */
BOOMR.subscribe('before_beacon', function(o) {

	//determine bandwidth
	if(o.bw) { 
		bwResult = parseInt(o.bw/1024); 
		bwError = parseInt(o.bw_err/o.bw);
	}
	//determine latency
	if(o.lat) {
		latResult = parseInt(o.lat); 
		latError = o.lat_err; 
	}

});

/**
 *  On window load, start Boomerang
 */
BOOMR.init({
	site_domain: serverlocation+'/thesis/', 
	BW: {
		base_url: serverlocation+'/thesis/images/'
	}
	
});


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
 * Record metadata of test and store in a JSON
 * object
 *
*/
function testParams(){
	
	mdJSON.ID = testID();
	mdJSON.Date = createDate();
	mdJSON.Browser = whichBrowser();
	mdJSON.OS = operatingSystem();
	mdJSON.Hardware = whatHardware();
	storeMetadata();

}

/**
 * Store the bandwidth and latency results to server
 *
*/
function networkTest(){
	
	var idTest = testID();
	var networkResult = formatNetworkTest(idTest);
	storeNetworkTest(networkResult);

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
 * Click function for html button that starts server tests.
 *
*/
function runServerBuffer(dataType){
	var idTest = testID();
	
	microAjax(serverlocation + "/thesis/rest/services/server/" + dataType +"/" + idTest, 
			function (err) {
				console.log(err); 
			});
}


/**
 * Click function for html button that starts server tests.
 *
*/
function runServerTriangulation(){
	var idTest = testID();
	
	microAjax(serverlocation + "/thesis/rest/services/server/triangulation/" + idTest, 
			function (err) {
				console.log(err); 
			});
}


/**
 * Click function for html button that starts server tests.
 *
*/
function runServerUnion(){
	var idTest = testID();
	
	microAjax(serverlocation + "/thesis/rest/services/server/union/" + idTest, 
			function (err) {
				console.log(err); 
			});
}
