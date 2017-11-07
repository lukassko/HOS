<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
<head>
	<%@ page isELIgnored="false" %>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	
<script src="//netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css">

	<!-- WebSocket -->
	<script src="<c:url value="/webjars/sockjs-client/1.0.2/sockjs.min.js" />"></script>
	<script src="<c:url value="/webjars/stomp-websocket/2.3.3/stomp.min.js" />"></script>

	<!-- font awesome -->
	<link rel="stylesheet" href="<c:url value="/webjars/font-awesome/4.7.0/css/font-awesome.min.css" />">  

	<!-- jQuery -->
	<script src="<c:url value="/webjars/jquery/3.1.1/jquery.min.js" />"></script>
	
	<!-- bootstrap
	<link rel="stylesheet" href="<c:url value="/webjars/bootstrap/3.3.7-1/css/bootstrap.min.css" />">  
	<script src="<c:url value="/webjars/bootstrap/3.3.7-1/js/bootstrap.min.js" />"></script>   -->

	<!-- attach other css and script -->
	<link rel="stylesheet" href="<c:url value="/resources/css/main.css" />">
	<link rel="stylesheet" href="<c:url value="/resources/css/progress.css" />">
	<link rel="stylesheet" href="<c:url value="/resources/css/carousel.css" />">
	
	<script src="<c:url value="/resources/scripts/progressCircle.js" />"></script>
	<script src="<c:url value="/resources/scripts/carousel.js" />"></script>
	<script src="<c:url value="/resources/scripts/devicesManager.js" />"></script>
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
		        <td>Device name</td>
		        <td id="device-name">NoDevice</td>
		      </tr>
		      <tr>
		        <td>Serial number</td>
		        <td id="device-serial">NoDevice</td>
		      </tr>
		      <tr>
		        <td> IP address</td>
		        <td id="device-ip">NoDevice </td>
		      </tr>
		      <tr>
		        <td>Last activity</td>
		        <td id="device-time">NoDevice</td>
		      </tr>
      	  </table>
		</div>
		<div class="device-detail device-prop" style="width: 200px;height:200px ">
			<div class="usage-info">CPU</div>
			<div class="my-progress" data-usage-type="cpu">
			 	<canvas id="bar-cpu" class = "bar" width="150" height="150"></canvas>
			</div>
		</div>
		<div id="parent" class="device-detail device-prop" style="width: 200px;height:200px ">
			<div class="usage-info">RAM</div>
			<div class="my-progress" data-usage-type="ram">
			 	<canvas id="bar-ram" class = "bar" width="150" height="150"></canvas>
			</div>
		</div>
	</div>
<!--  	<div style="height: 75%; width:100%; margin: 20px;">
		 <button onclick="deviceManager.setProgress('ram',67)">RAM</button> 
		 <button onclick="deviceManager.setProgress('cpu',56)">CPU</button> 
	</div>-->
	<nav class="navbar navbar-inverse">
  <div class="container-fluid">
    <!-- Brand and toggle get grouped for better mobile display -->
    <div class="navbar-header">
      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand" href="#">Brand</a>
    </div>

    <!-- Collect the nav links, forms, and other content for toggling -->
    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
      <ul class="nav navbar-nav">
        <li class="active"><a href="#">Link <span class="sr-only">(current)</span></a></li>
        <li><a href="#">Link</a></li>
        <li class="dropdown">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Dropdown <span class="caret"></span></a>
          <ul class="dropdown-menu">
            <li><a href="#">Action</a></li>
            <li><a href="#">Another action</a></li>
            <li><a href="#">Something else here</a></li>
            <li role="separator" class="divider"></li>
            <li><a href="#">Separated link</a></li>
            <li role="separator" class="divider"></li>
            <li><a href="#">One more separated link</a></li>
          </ul>
        </li>
      </ul>
      <form class="navbar-form navbar-left">
        <div class="form-group">
          <input type="text" class="form-control" placeholder="Search">
        </div>
        <button type="submit" class="btn btn-default">Submit</button>
      </form>
      <ul class="nav navbar-nav navbar-right">
        <li><a href="#">Link</a></li>
        <li class="dropdown">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Dropdown <span class="caret"></span></a>
          <ul class="dropdown-menu">
            <li><a href="#">Action</a></li>
            <li><a href="#">Another action</a></li>
            <li><a href="#">Something else here</a></li>
            <li role="separator" class="divider"></li>
            <li><a href="#">Separated link</a></li>
          </ul>
        </li>
      </ul>
    </div><!-- /.navbar-collapse -->
  </div><!-- /.container-fluid -->
</nav>

</div>

</body>
</html>