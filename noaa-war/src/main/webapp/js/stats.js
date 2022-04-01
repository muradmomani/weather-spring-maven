$(document).ready(function(){
    $("#countryList").stateFun(function(){
        $.get("${pageContext.request.contextPath}/noaa/stations/filter/state?country=US", function(data, status){
        	
            alert("Data: " + data + "\nStatus: " + status);
            $.each(data, function(i, field){
                $("#stateList").append("<option value="+field+">"+field+"</option>");
            });
        });
    });
});