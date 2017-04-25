<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
<head>
	<%@ page isELIgnored="false" %>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	
	<!-- WebSockets -->
	<script src="https://cdn.jsdelivr.net/sockjs/1/sockjs.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"></script>

	<!-- bootstrap / jQuery -->
	<link href="http://maxcdn.bootstrapcdn.com/font-awesome/4.1.0/css/font-awesome.min.css" rel="stylesheet">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

	<!-- attach other css classes and script -->
	<link rel="stylesheet" href="<c:url value="/resources/css/main.css" />">
	<link rel="stylesheet" href="<c:url value="/resources/css/controls.css" />">
	<script src="<c:url value="/resources/scripts/progressCircle.js" />"></script>
	<script src="<c:url value="/resources/scripts/websocket.js" />"></script>
	<script>
	
		var progressMap = new Object();
		
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
		};

		
		function setProgress(type,value) {
			var bars = document.querySelectorAll('.my-progress');
			bars.forEach(function (bar,index) {
				var data = $(bar).attr('data-usage-type');
				if (type === data) {
					var progressBar = progressMap[data];
					progressBar.draw(value);
				}
			})
		};
		
		(function () {
			var bars = progressBars.getProgresBarArray;
			$('.my-progress').each(function( index ) {
				var data = $(this).attr('data-usage-type');
				progressMap[data] = bars[index];
			});
		})();
		
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
		<div class="device-detail"></div>
		<div class="device-detail" style="width: 200px;height:200px ">
			<div class="my-progress" data-usage-type="cpu">
			 	<canvas id="bar-cpu" class = "bar" width="150" height="150"></canvas>
			</div>
		</div>
		<div id="parent" class="device-detail" style="width: 200px;height:200px ">
			<div class="my-progress" data-usage-type="ram">
			 	<canvas id="bar-ram" class = "bar" width="150" height="150"></canvas>
			</div>
		</div>
	</div>
	<div style="height: 75%; width:100%; margin: 20px;">
		 <button onclick="setProgress('ram',67)">RAM</button> 
		  <button onclick="setProgress('cpu',56)">CPU</button> 
	</div>
</div>

</body>
</html>