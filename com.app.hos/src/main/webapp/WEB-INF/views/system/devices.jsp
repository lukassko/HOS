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
	<!--<script src="resources/sockjs-0.3.4.js"></script>
    <script src="resources/stomp.js"></script>-->
    
	<!-- bootstrap / jQuery -->
	<link href="http://maxcdn.bootstrapcdn.com/font-awesome/4.1.0/css/font-awesome.min.css" rel="stylesheet">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

	<!-- attach other css classes and script -->
	<link rel="stylesheet" href="<c:url value="/resources/css/main.css" />">
	<link rel="stylesheet" href="<c:url value="/resources/css/progress.css" />">
	<script src="<c:url value="/resources/scripts/progressCircle.js" />"></script>
	
	<script src="<c:url value="/resources/scripts/devicesManager2.js" />"></script>
	<script src="<c:url value="/resources/scripts/webCommands.js" />"></script>
	<script>
	
		$(document).ready(function() {
			var builder = new WebCommandBuilder();
	        var command = builder.construct(new GetAllDeviceCommandBuilder());
	        hosWebsocket.sendCommand(command);
		});
	
	</script>
</head>
<body> 

<div class="device-container device-prop">
<!--  	<script>
		var devices ='${devices}';
		deviceManager.addDevices(devices);
    </script> 		-->
</div>

<div class="device-panel">
	<div style="height: 30%; width:100%; margin: 5px;">
		<div class="device-detail device-prop" style="width: 550px;height:200px">
		  <table class="device-table">
		      <tr>
		        <td>
		          Device name
		        </td>
		        <td id="device-name">
		          NoDevice
		        </td>
		      </tr>
		       <tr>
		        <td>
		          Serial number
		        </td>
		        <td id="device-serial">
		          NoDevice
		        </td>
		      </tr>
		       <tr>
		        <td>
		          IP address
		        </td>
		        <td id="device-ip">
		          NoDevice
		        </td>
		      </tr>
		       <tr>
		        <td>
		          Last activity
		        </td>
		        <td id="device-time"> 
		          NoDevice
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