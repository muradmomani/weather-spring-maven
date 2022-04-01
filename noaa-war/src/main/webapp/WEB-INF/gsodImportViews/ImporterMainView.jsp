<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/css/bootstrap.min.css" integrity="sha384-PsH8R72JQ3SOdhVi3uxftmaW6Vc51MKb0q5P2rRUpPvrszuE4W1povHYgTpBfshb" crossorigin="anonymous">

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>

<script type="text/javascript">
	$(document).ready(
			function() {
				$("#dateList").change(
						function() {
						$("statusButton").visiblity="visible";
							var fileName = $("#dateList").val()
							$.get("${pageContext.request.contextPath}/noaa/import/importGsod/thread?fileName="
											+ fileName);});});
</script> 
<script type="text/javascript">
						function rebort() {
							$("#reportTable").empty();
							$.get("${pageContext.request.contextPath}/noaa/import/importGsod/rebort"
									, function(data, status) { 
									$("#reportTable").append(
									"<tr><th scope='col'>Job Name</th>"
									+"<th scope='col'>queuingTime</th>"
									+"<th scope='col'>startTime</th>"
									+"<th scope='col'>endTime</th>"
									+"<th scope='col'>totalTime</th>"
									+"<th scope='col'>message</th>"
									+"<th scope='col'>status</th>"
									+"</tr>"		
									);
											$.each(data, function(i, record) {
											$("#reportTable").append(
											"<tr><td>"+record.jobName+"</td>"
											+"<td>"+record.queuingTime.time.hour+":"+record.queuingTime.time.minute+":"+record.queuingTime.time.second+"</td>"
											+"<td>"+record.startTime.time.hour+":"+record.startTime.time.minute+":"+record.startTime.time.second+"</td>"
											+"<td>"+record.endTime.time.hour+":"+record.endTime.time.minute+":"+record.endTime.time.second+"</td>"
											+"<td>"+record.totalTime+"</td>"
											+"<td>"+record.message+"</td>"
											+"<td>"+record.status+"</td></tr>");
										});
									});
						}
</script>
</head>
<body>

		<h3>Select File To Be Imported To Database:</h3>
		<select class="custom-select" id="dateList" name="dates">
			<option disabled="true" selected="true">Choose Date Peals</option>
			<c:forEach items="${dates}" var="date">
				<option value="${date}">${date}</option>
			</c:forEach>
		</select> <br>
		<br>
		<button class="btn btn-primary" type="button" id="statusButton" onclick="rebort()">Show Import Status Report</button>
		<div id="tableDiv">
		
	<table class="table table-dark" border="3" style="width:100%" id="reportTable">
	
	
	</table>
		</div>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.3/umd/popper.min.js" integrity="sha384-vFJXuSJphROIrBnz7yo7oB41mKfc8JzQZiCq4NCceLEaO4IHwicKwpJf9c9IpFgh" crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/js/bootstrap.min.js" integrity="sha384-alpBpkh1PFOepccYVYDB4do5UnbKysX5WZXm3XxPqe5iKTfUKjNkCk9SaVuEZflJ" crossorigin="anonymous"></script>

</body>
</html>