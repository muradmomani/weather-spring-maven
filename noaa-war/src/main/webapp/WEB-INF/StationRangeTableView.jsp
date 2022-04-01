<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/css/bootstrap.min.css" integrity="sha384-PsH8R72JQ3SOdhVi3uxftmaW6Vc51MKb0q5P2rRUpPvrszuE4W1povHYgTpBfshb" crossorigin="anonymous">

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<c:choose>
		<c:when test="${not empty stations}">


			<table border="1" width="100%">
				<tr>
					<th>USAF</th>
					<th>WBAN</th>
					<th>STATIONName</th>
					<th>LATITUDE</th>
					<th>LONGITUDE</th>
					<th>latest Gsod record</th>
				</tr>
				<c:forEach items="${stations}" var="station">

					<c:if test="${not empty station.lat}">
						<c:if test="${not empty station.lang}">
							<tr>
								<td>${station.id.usaf}</td>
								<td>${station.id.wban}</td>
								<td>${station.stationName}</td>
								<td>${station.lat}</td>
								<td>${station.lang}</td>
								<td><a target='_blank'
									href=${pageContext.request.contextPath}/noaa/stations/GsodLatestRecord/view?wban=${station.id.wban}&usaf=${station.id.usaf}>Latest
										GSOD Record</a>
							</tr>
						</c:if>
					</c:if>
				</c:forEach>

			</table>
		</c:when>
		<c:when test="${empty stations}">
			<h1>no records for this Point !</h1>
		</c:when>
	</c:choose>
	<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.3/umd/popper.min.js" integrity="sha384-vFJXuSJphROIrBnz7yo7oB41mKfc8JzQZiCq4NCceLEaO4IHwicKwpJf9c9IpFgh" crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/js/bootstrap.min.js" integrity="sha384-alpBpkh1PFOepccYVYDB4do5UnbKysX5WZXm3XxPqe5iKTfUKjNkCk9SaVuEZflJ" crossorigin="anonymous"></script>
	
</body>
</html>