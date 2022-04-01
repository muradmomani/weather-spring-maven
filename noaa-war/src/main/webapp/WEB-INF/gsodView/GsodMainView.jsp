<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/css/bootstrap.min.css" integrity="sha384-PsH8R72JQ3SOdhVi3uxftmaW6Vc51MKb0q5P2rRUpPvrszuE4W1povHYgTpBfshb" crossorigin="anonymous">

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

<script type="text/javascript">
	$(document).ready(function() {
		$("#select_option").change(function() {
			if (this.value == "state_select") {
				var  state_dive=document.getElementById('state_dive')
				state_dive.style.visibility = 'visible';
				
				var date_dive=document.getElementById('date_dive')
				date_dive.style.visibility = 'visible';
				
				var country_dive =document.getElementById('country_dive')
				country_dive.style.visibility = 'hidden';
				
		
			} else {
				if (this.value == "country_select") {
					
					var country_dive =document.getElementById('country_dive')
					country_dive.style.visibility = 'visible';
					
					var date_dive=document.getElementById('date_dive')
					date_dive.style.visibility = 'visible';
					
					
					var state_dive =document.getElementById('state_dive')
					state_dive.style.visibility = 'hidden';
				} 
			}
		});
	});
</script>

<script type="text/javascript">
function getGsodRecords() {
	var country = $("#countryCode").val();
	var state = $("#stateCode").val();
	var startDate=$("#StartDate").val();
	var endDate =$("#EndDate").val();
	$("#table").empty();
    alert("Go To ajax");
	$.get("${pageContext.request.contextPath}/noaa/gsod/filter/country?countrycode="+country+"&stateCode="+state+"&startDate="+startDate+"&endDate="+endDate
			, function(data, status) {
		
		$.each(data, function(i, summery) {
			$("#table").append("<tr><td>"+summery.id.usaf+"</td><td>"+summery.id.wban
			+"</td><td>"+summery.country+"</td>"
			+"<td>"+summery.state+"</td>"
            +"<td>"+summery.temp+"</td></tr>");
		});
	});
	alert("Out of ajax");
}

function hideCol(id,col){
	var classCol = "."+col;
	
	 $(classCol).show();
}
</script>
</head>
<body>
	<h1>Choose Filtering option :</h1>
	<select class="custom-select" id="select_option">
		<option disabled="true" selected="true">Chose here Pleas</option>
		<option value="country_select">Filter By Country and Date</option>
		<option value="state_select">Filter By state and Date</option>
	</select>

	<form action="" method="POST">
		<h2>choose columns to be shown :</h2>
		<input class="form-control" type="checkbox" name="columnOption" value="stationNumber_check" onchange="hideCol(this.id,'usafCol')">Station Number<br> 
		<input class="form-control" type="checkbox" name="columnOption" value="wban_check">WBAN<br> 
		<input class="form-control" type="checkbox"name="columnOption" value="country_check"> Country<br>
		<input class="form-control" type="checkbox" name="columnOption" value="state_check">State<br>
		<input class="form-control" type="checkbox" name="columnOption" value="temp_check">Temperature<br>
	</form>

<div>
<form >
<div id="country_dive" style="visibility: hidden">
<h2>Filter By Country Code :</h2>
Country Code :<input class="form-control" id="countryCode" type="text" name="countrycode"><br>
</div>


<div id="state_dive" style="visibility: hidden">
<h2>Filter By State Code :</h2>
state Code :<input class="form-control" id="stateCode" type="text" name="stateCode"><br>
</div>


<div id="date_dive" style="visibility: hidden">
Start Date -yyyy/mm/dd- :<input id="StartDate" type="text" name="startDate"><br> 
EndDate -yyyy/mm/dd-:<input class="form-control" id="EndDate" type="text" name="endDate"><br>
</div>

<button class="form-control" type="button" onclick="getGsodRecords()">submit</button>

</form>

</div>
<div id="tableDiv"></div>
<table class="table" border="1" id="table">
                <tr>
				<th scope="col" class="usafCol">USAF</th>
				<th scope="col">WBAN</th>
				<th scope="col">Country</th>
				<th scope="col">State</th>
				<th scope="col">Temp</th>
				</tr>
</table>
				

<!-- action="${pageContext.request.contextPath}/noaa/gsod/filter/country" method="POST" -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.3/umd/popper.min.js" integrity="sha384-vFJXuSJphROIrBnz7yo7oB41mKfc8JzQZiCq4NCceLEaO4IHwicKwpJf9c9IpFgh" crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/js/bootstrap.min.js" integrity="sha384-alpBpkh1PFOepccYVYDB4do5UnbKysX5WZXm3XxPqe5iKTfUKjNkCk9SaVuEZflJ" crossorigin="anonymous"></script>


</body>
</html>