<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/css/bootstrap.min.css" integrity="sha384-PsH8R72JQ3SOdhVi3uxftmaW6Vc51MKb0q5P2rRUpPvrszuE4W1povHYgTpBfshb" crossorigin="anonymous">

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js">
</script>
<script type="text/javascript">
	$(document).ready(
			function() {
				$("#countryList").change(
						function() {
							$("#stateList").empty();
							var country = $("#countryList").val()
							$.get("${pageContext.request.contextPath}/noaa/stations/filter/state?country="
							+ country, function(data, status) { 
										$.each(data, function(i, field) {
											$("#stateList").append(
													"<option value="+field+">"
															+ field
															+ "</option>");
										});
									});
						});
			});
</script> 
<script type="text/javascript">
	$(document).ready(
			function() {
				$("#startDateList").change(
						function() {
							$("#endDateList").empty();
							var date = $("#startDateList").val()
							$.get("${pageContext.request.contextPath}/noaa/gsod/filter/date?date="
							+ date, function(data, status) {
										$.each(data, function(i, field) {
											$("#endDateList").append(
													"<option value="+field+">"
															+ field
															+ "</option>");
										});
									});
						});
			});
</script> 

<script type="text/javascript">
var pageGlobal=1;
function getGsodRecords(pageLocal) {
	pageGlobal=pageLocal;
	var country = $("#countryList").val();
	var state = $("#stateList").val();
	var startDate=$("#startDateList").val();
	var endDate =$("#endDateList").val();
	$("#tableDiv").empty();
	$.get("${pageContext.request.contextPath}/noaa/gsod/filter/country?countrycode="+country+"&stateCode="+state+"&startDate="+startDate+"&endDate="+endDate+"&page="+pageLocal
			, function(data, status) {
		$("#tableDiv").append("<table class='table' id='table1'> <tr><th scope='col' class='usaf'>ID.USAF</th><th scope='col' class='wban'>ID.WBAN</th><th scope='col' class='ctry'>Country</th><th scope='col' class='state'>State</th><th scope='col' class='temp'>Temp</th></tr></table>")
		$.each(data, function(i, summery) {
						$("#table1").append("<tr><td class='usaf'>"+summery.id.usaf+"</td><td class='wban'>"+summery.id.wban
			+"</td><td class='ctry'>"+summery.country+"</td>"
			+"<td class='state'>"+summery.state+"</td>"
            +"<td class='temp'>"+summery.temp+"</td></tr>");
		});
		if(pageLocal==1)
			getNumber(country,state,startDate,endDate);

	});
}

</script>

<script type="text/javascript">
function getNumber(country,state,startDate,endDate){
	$.get("${pageContext.request.contextPath}/noaa/gsod/filter/numOfRecords?countrycode="+country+"&stateCode="+state+"&startDate="+startDate+"&endDate="+endDate,
	function(data){
		var res = data.split(",");
		var recordSize = res[0];
		var pagesNumber = res[1];
		$("#pagingBar").empty();
		for(var i =1;i<=pagesNumber;i++){
			$("#pagingBar").append("<button onclick=getGsodRecords("+i+")>"+i+"</button>");
		}
		$("#sizeDiv").empty();
		$("#sizeDiv").append("<h5> Number Of records To be Loaded As Csv File ("+recordSize+")</h5>");
	}		
	);
}
</script>

<script type="text/javascript">
function hideCol(id,col){
	var classCol = "."+col;
	if(document.getElementById(id).checked){
		 $(classCol).hide();

	}else {
		 $(classCol).show();

	}
}
</script>

</head>
<body>
	<h1>Filter By Country, State and Date</h1>

	<h3>select country:</h3>
	<select class="custom-select" id="countryList" name="country">
		<option disabled="true" selected="true">Choose Country Pleas</option>
		<c:forEach items="${countries}" var="country">
			<option value="${country}">${country}</option>
		</c:forEach>
	</select>
	<br>

	<h3>Select State:</h3>
	<select class="custom-select" id="stateList" name="state">
		<option disabled="true" selected="true">Choose state Pleas</option>
	</select>
<br>
<h3>select Start Date:</h3>
	<select class="custom-select" id="startDateList" name="startdate">
		<option disabled="true" selected="true">choose start date</option>
		<c:forEach items="${startDates}" var="date">
			<option value="${date}">${date}</option>
		</c:forEach>
	</select>
	<br>
	<h3>select End Date:</h3>
	<select class="custom-select" id="endDateList" name="state">
		<option disabled="true" selected="true">Choose state Pleas</option>
	</select>
	<br>
	
	<button type="button" onclick="getGsodRecords(1)" >submit</button>
	<br>
		<br>
	
		<br>
		<input   type="checkbox" name="columns" id="st_number" value="usaf" onchange="hideCol(this.id,'usaf')">Station Number<br> 
		<input   type="checkbox" name="columns" id="wban"  value="wban" onchange="hideCol(this.id,'wban')">WBAN<br> 
		<input   type="checkbox" name="columns" id="ctry" value="country" onchange="hideCol(this.id,'ctry')"> Country<br>
		<input   type="checkbox" name="columns" id="state" value="state" onchange="hideCol(this.id,'state')">State<br>
		<input   type="checkbox" name="columns" id="temp" value="temp" onchange="hideCol(this.id,'temp')">Temperature<br>
		
		
	<div id="downloadDiv">
	<button class="btn btn-primary" type="button" onclick="download()"  >Download As CSV File</button>
	<div id="sizeDiv"></div>
	</div>
		
	<br>
	
	<div id="pagingBar"></div>
		
<div id="tableDiv"></div>


<script type="text/javascript">
function download(){
	var country = $("#countryList").val();
	var state = $("#stateList").val();
	var startDate=$("#startDateList").val();
	var endDate =$("#endDateList").val();
	var hreff ="${pageContext.request.contextPath}/noaa/gsod/filter/download?"
			+"countrycode="+country+"&stateCode="+state+"&startDate="+startDate+"&endDate="+endDate+"&page="+pageGlobal
	
	if($("#st_number").is(":checked")){
		var usaf_column = $("#st_number").val();	
		hreff += "&usaf_column="+usaf_column;
	}
	if($("#wban").is(":checked")){
	var wban_column= $("#wban").val();
	hreff +="&wban_column="+wban_column
	}
	if($("#ctry").is(":checked")){
	var ctry_column= $("#ctry").val();		
	hreff += "&ctry_column="+ctry_column;
	}
	if($("#state").is(":checked")){
	var state_column= $("#state").val();		
	hreff += "&state_column="+state_column;
	}
	if($("#temp").is(":checked")){
	var temp_column= $("#temp").val();
	hreff +="&temp_column="+temp_column;
	}
	
	$("#downloadDiv").html("<a href='"+hreff+"'>Start Download Here</a>");
}
</script>	
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.3/umd/popper.min.js" integrity="sha384-vFJXuSJphROIrBnz7yo7oB41mKfc8JzQZiCq4NCceLEaO4IHwicKwpJf9c9IpFgh" crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/js/bootstrap.min.js" integrity="sha384-alpBpkh1PFOepccYVYDB4do5UnbKysX5WZXm3XxPqe5iKTfUKjNkCk9SaVuEZflJ" crossorigin="anonymous"></script>

</body>

</html>