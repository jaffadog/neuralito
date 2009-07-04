// google search box
function startSearch() {
	searchString = document.getElementById('nav_search_box').value;
	if (searchString != "") {
		var url = 'http://www.surfline.com/find/index.cfm?q='+escape(searchString)+'&ie=&site=entire_surfline_site&output=xml_no_dtd&client=entire_surfline_site&lr=&proxystylesheet=entire_surfline_site&oe=';
		window.location = url;
	}
}
// clears google search box
function clearSearchText(thefield){
	if (thefield.value == " Search Surfline")
		thefield.value = ""
}

// button toggles
function showLogIn() {
	navObj = document.getElementById('logInDiv');
	navObj.style.display = "block";
	//linkObj = document.getElementById('log_link');
	//linkObj.innerHTML = '<a href="#" id="log_link" onclick="hideLogIn();">Hide Log In</a>';
}
function hideLogIn() {
	navObj = document.getElementById('logInDiv');
	navObj.style.display = "none";
	linkObj = document.getElementById('log_link');
	linkObj.innerHTML = '<strong><a href="#" id="log_link" onclick="showLogIn();">Log In</a></strong>';
}
function showSearch() {
	navObj = document.getElementById('searchDiv');
	navObj.style.display = "block";
	linkObj = document.getElementById('search_link');
	linkObj.innerHTML = '<a href="#" onclick="hideSearch();return false;">Hide Search Box</a><br>';
}
function hideSearch() {
	navObj = document.getElementById('searchDiv');
	navObj.style.display = "none";
	linkObj = document.getElementById('search_link');
	linkObj.innerHTML = '<a href="#" onclick="showSearch();return false;">Find a Surf Spot</a><br>';
}

// log in code
function showResponse(responseText, statusText) { 
	//alert('statusText: ' + statusText  + ' responseText: ' + responseText + ' index: ' + responseText.indexOf('true'));
	if (responseText.indexOf('true') == -1) { showFail(); }
	else { reloadPage(); }
}
function showFail() {
	//alert('fail');
	document.getElementById('logInDiv').style.height = 55 + 'px';
	document.getElementById('failDiv').innerHTML = "<span style='color:yellow'>We're sorry, that login info is incorrect.</span> Please try again or sign up for Surfline Premium";
}
function reloadPage() {
	document.getElementById('logInContent').innerHTML = "<div style='font-size:10px;font-weight:bold;padding-top:10px;color:#dddddd'>Log in successful! Page reloading...</div>";
	document.getElementById('failDiv').innerHTML = "";
	window.location.reload();
}