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
	<link rel="stylesheet" href="<c:url value="/resources/css/progress.css" />">
	<script src="<c:url value="/resources/scripts/progressCircle.js" />"></script>
	<script src="<c:url value="/resources/scripts/websocket.js" />"></script>
	<script>
	
		var deviceManager = (function() {
			
			var progressMap = new Object();
			var deviceMap = new Map();
			var selectedDeivce = null;
			
			var bars = progressBars.getProgresBarArray;
			
			$('.my-progress').each(function( index ) {
				var data = $(this).attr('data-usage-type');
				progressMap[data] = bars[index];
			});
			
			function addDevices(devices) {
				var devices = JSON.parse(devices);
				for (var device of devices) { 
					addDevice(device);
				}
			};
			
			function addDevice(device) {
				var name = device.name;
				var id= device.id;
				var connectionId= device.connectionId;
				createDeviceButton(id,name,connectionId);
				var devId = '#device' + id;
				$(devId).css('cursor','pointer');
				$('.device-container').on('click', devId, function(){
					$('.device-container div.activeDevice').removeClass('activeDevice');
			    	$(this).addClass('activeDevice'); 
			    	selectedDeivce = id;
				});
			};
	
			function createDeviceButton (devId,devName,devConnectionId) {
				var deviceButton = '<div id="device' + devId +
					'" class="device"><i class="fa fa-desktop fa-2x device-icon-text"></i> '+
					'<div class="device-icon-text">' + devName + '</div></div>';
				
				$('.device-container').append(deviceButton);
			};
			
			function setDeviceStatus(id,status) {
				myMap.set(id,status);
			};
			
			function setProgress(type,value) {
				var bars = document.querySelectorAll('.my-progress');
				bars.forEach(function (bar,index) {
					var data = $(bar).attr('data-usage-type');
					if (type === data) {
						var progressBar = progressMap[data];
						progressBar.draw(value);
					}
				});
			};
			
			return {
				setProgress: setProgress,
				addDevices: addDevices,
				setDeviceStatus: setDeviceStatus
			}
		})();

	</script>
</head>
<body>

<div class="device-container device-prop">
	<script>
		var devices ='${devices}';
		deviceManager.addDevices(devices);
    </script>
</div>

<div class="device-panel">
	<div style="height: 30%; width:100%; margin: 5px;">
		<div class="device-detail device-prop" style="width: 35%;height:200px">
		  <table class="device-table">
		      <tr>
		        <td>
		          Device name
		        </td>
		        <td id="device-name">
		          Gateway_1
		        </td>
		      </tr>
		       <tr>
		        <td>
		          Serial number
		        </td>
		        <td id="device-serial">
		          123bfhg5662
		        </td>
		      </tr>
		       <tr>
		        <td>
		          IP address
		        </td>
		        <td id="device-ip">
		          192.168.0.167:1389
		        </td>
		      </tr>
		       <tr>
		        <td>
		          Last activity
		        </td>
		        <td id="device-time"> 
		          2017-05-05 12:06:33
		        </td>
		      </tr>
      	  </table>
		</div>
		<div class="device-detail device-prop" style="width: 200px;height:200px ">
			<div class="usage-info">
	        	CPU
	        </div>
			<div class="my-progress" data-usage-type="cpu">
			 	<canvas id="bar-cpu" class = "bar" width="150" height="150"></canvas>
			</div>
		</div>
		<div id="parent" class="device-detail device-prop" style="width: 200px;height:200px ">
			<div class="usage-info">
	        	RAM
	        </div>
			<div class="my-progress" data-usage-type="ram">
			 	<canvas id="bar-ram" class = "bar" width="150" height="150"></canvas>
			</div>
		</div>
	</div>
	<div style="height: 75%; width:100%; margin: 20px;">
		 <button onclick="deviceManager.setProgress('ram',67)">RAM</button> 
		 <button onclick="deviceManager.setProgress('cpu',56)">CPU</button> 
	</div>
</div>

</body>
</html>