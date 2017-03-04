<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<%@ page isELIgnored="false" %>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link rel="stylesheet" href="<c:url value="/resources/css/main.css" />">
	<link href="http://maxcdn.bootstrapcdn.com/font-awesome/4.1.0/css/font-awesome.min.css" rel="stylesheet">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	
	<script>
	
	function addDevice(dev) {
		var device = JSON.parse(dev);
		var name = device.name;
		var devId = device.connectionId
		var deviceContainer = '<div id="device' + devId +'" class="device"><i class="fa fa-desktop fa-3x"></i> '+ name +'</div>'
		$('.device-container').append(deviceContainer);
		
		var devId = '#device' + devId
		$(devId).css('cursor','pointer');
		$('.device-container').on('click', devId, function(){
			$('.device-container div.activeDevice').removeClass('activeDevice');
	    	$(this).addClass('activeDevice');
	    	
		});
	}
	</script>
</head>
<body>

<div class="device-container">
	<c:forEach var="device" items="${devices}">
		<script>
			var device = '${device}';
			addDevice(device);
        </script>
	</c:forEach>
</div>

<div class="device-panel">
	<div style="height: 30%; width:100%; margin: 5px;">
		<div class="device-detail">elo</div>
		<div class="device-detail" style="width: 100px;">melo</div>
	</div>
	<div style="height: 75%; width:100%; margin: 20px;">
	eslo
	</div>
</div>

</body>
</html>