<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page session="false"%>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
	
	<!-- jQuery -->
	<script src="<c:url value="/webjars/jquery/3.1.1/jquery.min.js" />"></script>
	
	<!-- bootstrap -->	
	<link rel="stylesheet" href="<c:url value="/webjars/bootstrap/3.3.7-1/css/bootstrap.min.css" />">  
	<script src="<c:url value="/webjars/bootstrap/3.3.7-1/js/bootstrap.min.js" />"></script>
	
	<!-- DateRangePicker -->
	<link rel="stylesheet" href="<c:url value="/webjars/bootstrap-daterangepicker/2.1.19/css/bootstrap-daterangepicker.css" />">  	
	
	<!-- font awesome -->
	<link rel="stylesheet" href="<c:url value="/webjars/font-awesome/4.7.0/css/font-awesome.min.css" />">  
	
	<!-- WebSocket -->
	<script src="<c:url value="/webjars/sockjs-client/1.0.2/sockjs.min.js" />"></script>
	<script src="<c:url value="/webjars/stomp-websocket/2.3.3/stomp.min.js" />"></script>

	<!-- HighCharts -->
	<script src="<c:url value="/webjars/highcharts/5.0.1/highcharts.js" />"></script>

	<!-- MomentJs -->
	<script src="<c:url value="/webjars/momentjs/2.20.1/moment.js" />"></script>
	
	<!-- attach other css -->
	<link rel="stylesheet" href="<c:url value="/resources/css/main.css" />">
	<link rel="stylesheet" href="<c:url value="/resources/css/tooltip.css" />">
	
	<!-- attach other script -->
	<script src="<c:url value="/resources/scripts/utils.js" />"></script>
	<script src="<c:url value="/resources/scripts/socket/websocket.js" />"></script>
	<script src="<c:url value="/resources/scripts/command/webCommands.js" />"></script>
	<script src="<c:url value="/resources/scripts/managers/commandManager.js" />"></script>
	<script src="<c:url value="/resources/scripts/managers/devicesManager.js" />"></script>
	<script src="<c:url value="/resources/scripts/charts/chart.js" />"></script>
	<script src="<c:url value="/resources/scripts/doAjaxCall.js" />"></script>
	<script>
		
	var activePage = 'dashboard';
	
	$(document).ready(function() {
		
		window.history.replaceState("", "", "/HOS/");
		
		// loaded here beacuse it use jQuery, therefore must be load after jQuery is loaded
		$.getScript("webjars/bootstrap-daterangepicker/2.1.19/js/bootstrap-daterangepicker.js", function() {
			$.getScript("resources/scripts/prototyping.js");
		});
		$.getScript("resources/scripts/plugins.js");
		
		$('ul.collapse li').not('li.collapsed').each(function() {
			var page = $(this).attr("data-target");
		    $(this).on("click", function() {
		    	$('li.active').removeClass('active');
		    	$(this).addClass('active');
		    	var page = $(this).attr("data-target");
		    	var request = $.get(page);

		    	$.get(page, function(data) {
		    		$('#container').html(data)
		    		activePage = page;
				}).fail(function(jqXHR, textStatus, errorThrown) {
					setSystemInfo("Error requesting page: " + page);
				});
		    	
		    	var title = $(this).text();
		    	$('#active-page').text(title);
		    });
		});
		hosWebsocket.connect(getAllDevices);
		
		new GetActiveUserCall(
			function(status, response) {
			console.log("active user " + response.name);
			}
		).send();
		
	});
	
	var intervalID = setInterval(function(){
		getAllDevices();
	}, 5000);
	
	function getAllDevices() {
		var builder = new WebCommandBuilder();
        var command = builder.construct(new GetAllDeviceCommandBuilder());
        hosWebsocket.sendCommand(command);
	}
	
	window.addEventListener("beforeunload", function (e) {
		var dialogText = 'Are you sure to leave page?';
		e.returnValue = dialogText;
		return dialogText;                             
	}); 
	
	window.addEventListener("unload", function (e) {
		clearInterval(intervalID);
		hosWebsocket.disconnect();
	}); 

	</script>
	<title>HOS</title>
</head>
<body>

<!--  <div style="width: 100%; height: 100%;">-->
	<div class="nav-side-menu">
	    <div class="brand">Home Operation System</div>
	    <i class="fa fa-bars fa-2x toggle-btn" data-toggle="collapse" data-target="#menu-content"></i>
	  
	        <div class="menu-list">
	  
	            <ul id="menu-content" class="menu-content collapse out">
	                <li class="active" data-target="dashboard">
	                  <i class="fa fa-dashboard fa-lg"></i> Dashboard
	                </li>
	
	                <li data-toggle="collapse" data-target="#system" class="collapsed">
	                  <i class="fa fa-android fa-lg"></i> System Elements <span class="arrow"></span>
	                </li>
	                <ul class="sub-menu collapse" id="system">
	                    <li data-target="general">General</li>
	                    <li data-target="devices">Connected Devices</li>
					</ul>
	
	                <li data-toggle="collapse" data-target="#service" class="collapsed">
	                  <i class="fa fa-globe fa-lg"></i> Services <span class="arrow"></span>
	                </li>  
	                <ul class="sub-menu collapse" id="service">
	                  <li data-target="weather">Weather</li>
	                </ul>
	
	                 <li data-target="profile">
	                  <i class="fa fa-user fa-lg"></i> Profile
	                  </li>
	
	                 <li data-target="users">
	                  <i class="fa fa-users fa-lg"></i> Users
	                </li>
	            </ul>
	     </div>
	</div>
	
	<div class="main-container">
		<div id="user-bar" class="user-bar">
			<span> Active page: </span>
			<span id="active-page"> Dashboard </span>
			
			<div style="display:flex; float: right;">
				<span id="system-info"></span>
				<ul class="nav navbar-nav navbar-right" style="padding: 0px 10px 0px 0px;">
					<li class="dropdown">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">${user-name} <span class="caret"></span></a>
						<ul class="dropdown-menu">
							<li><a href="#">Logout</a></li>
						</ul>
					</li>
				</ul>
			</div>
			

		</div>
		<div id="container">
			Hello WORLD
		</div> 
	</div>

</body>
</html>
