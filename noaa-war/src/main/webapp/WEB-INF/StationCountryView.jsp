<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/css/bootstrap.min.css" integrity="sha384-PsH8R72JQ3SOdhVi3uxftmaW6Vc51MKb0q5P2rRUpPvrszuE4W1povHYgTpBfshb" crossorigin="anonymous">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js">
</script> <!-- script type="text/javascript"  src="${pageContext.request.contextPath}/js/stats.js"></script> -->
<script type="text/javascript">
	$(document).ready(
			function() {
				$("#countryList").change(
						function() {
							$("#stateList").empty();
							var country = $("#countryList").val()
							alert("country : ",country);
							alert("context path :","${pageContext.request.contextPath}/noaa/stations/filter/state")
							$.get(
							"${pageContext.request.contextPath}/noaa/stations/filter/state?country="
											+ country, function(data, status) { //alert("Data: " + data + "\nStatus: " + status);
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

						function getStations() {
							var country = $("#countryList").val();
							var state = $("#stateList").val();
							$("#table").empty();
							$.get("${pageContext.request.contextPath}/noaa/stations/state/model?country="+country+"&state="+state
									, function(data, status) { 
											$.each(data, function(i, station) {
											$("#table").append("<tr><td>"+station.id.usaf+"</td><td>"+station.id.wban
											+"</td><td>"+station.stationName+"</td>"
											+"<td><a target='_blank' href='https://www.google.com/maps/search/?api=1&query="+station.lang+","+station.lat+"'>LOCATION</a></td>"
											+"<td><a target='_blank' href='${pageContext.request.contextPath}/noaa/stations/GsodLatestRecord/view?wban="+station.id.wban+"&usaf="+station.id.usaf+"'>Latest GSOD Record</a></td>"+"</tr>");
						});	});}
</script>
</head>
<body>

	<form >
		<h1>Listing Station Depend On Country and State Code ....</h1>
		<h3>Select Country:</h3>
		<select class="custom-select" id="countryList" name="country">
			<option disabled="true" selected="true">Chose Country Pleas</option>
			<c:forEach items="${countries}" var="country">
				<option value="${country}">${country}</option>
			</c:forEach>
		</select> <br>
		<h3>Select State:</h3>
		<select class="custom-select" id="stateList" name="state">
			<option>State</option>
		</select>
	</form>
	
	<button class="btn btn-primary" id="sb1" onclick="getStations()">Get Data</button>
	
	<table class="table table-dark" id="table">
	<tr>
	<th scope="col">usaf</th>
	<th scope="col">wban</th>
	<th scope="col">Station Name</th>
	</tr>
	</table>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.3/umd/popper.min.js" integrity="sha384-vFJXuSJphROIrBnz7yo7oB41mKfc8JzQZiCq4NCceLEaO4IHwicKwpJf9c9IpFgh" crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/js/bootstrap.min.js" integrity="sha384-alpBpkh1PFOepccYVYDB4do5UnbKysX5WZXm3XxPqe5iKTfUKjNkCk9SaVuEZflJ" crossorigin="anonymous"></script>

</body>

</html>


