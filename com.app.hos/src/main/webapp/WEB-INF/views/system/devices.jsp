<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
<head>
	<%@ page isELIgnored="false" %>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

	<!-- attach other css and script -->
	<link rel="stylesheet" href="<c:url value="/resources/css/progress.css" />">
	<link rel="stylesheet" href="<c:url value="/resources/css/carousel.css" />">
	<link rel="stylesheet" href="<c:url value="/resources/css/overwright-boostrap.css" />">
	
	
	<script src="<c:url value="/resources/scripts/progressCircle.js" />"></script>
	<script src="<c:url value="/resources/scripts/carousel.js" />"></script>
	<script src="<c:url value="/resources/scripts/devicesManager.js" />"></script>
	<script>

		$(document).ready(function() {
			$('ul.nav li').each(function() {
			    $(this).on("click", function() {
			    	$('ul.nav li').removeClass('active');
			    	$(this).addClass('active');
			    	//var page = $(this).attr("data-target");
			    	//$.get(page, function(data){
					//    $('#container').html(data);
					//});
			    	//var title = $(this).text();
			    	//$('#active-page').text(title);
			    });
			});
		});
	
	</script>
</head>
<body> 

<div class="view-container">
	<div class="device-container device-prop">
	<!--  	<script>
			var devices ='${devices}';
			deviceManager.addDevices(devices);
	    </script> 		-->
	</div>
	
	<div class="device-panel">
		<nav class="navbar navbar-inverse device-prop">
		  <div class="container-fluid">
		    <!-- Brand and toggle get grouped for better mobile display -->
		    <div class="navbar-header">
		      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
		        <span class="sr-only">Toggle navigation</span>
		        <span class="icon-bar"></span>
		        <span class="icon-bar"></span>
		        <span class="icon-bar"></span>
		      </button>
		      <div class="navbar-brand">Brand</div>
		    </div>
		
		    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
		      <ul class="nav navbar-nav">
		      
		      <!-- class="active" -->
		        <li><a href="#">Status</span></a></li>
		        <li><a href="#">History</a></li>
		        <li class="dropdown">
		          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Commands <span class="caret"></span></a>
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
		        <li class="dropdown">
		          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Options <span class="caret"></span></a>
		          <ul class="dropdown-menu">
		            <li><a href="#">Disconnect</a></li>
		            <li><a href="#">Remove</a></li>
		            <li><a href="#">Block</a></li>
		            <li role="separator" class="divider"></li>
		            <li><a href="#">Separated link</a></li>
		          </ul>
		        </li>
		      </ul>
		    </div>
		  </div>
		</nav>
		<div class="device-detail device-prop" style="width: 200px;height:200px ">
			<div class="usage-info">
	        	CPU
	        </div>
			<div class="my-progress" data-usage-type="cpu">
			 	<canvas id="bar-cpu" class = "bar" width="150" height="150"></canvas>
			</div>
		</div>
		<div id="parent" class="device-detail device-prop" style="width:200px;height:200px ">
			<div class="usage-info">
	        	RAM
	        </div>
			<div class="my-progress" data-usage-type="ram">
			 	<canvas id="bar-ram" class = "bar" width="150" height="150"></canvas>
			</div>
		</div>
		<div class="device-detail device-prop" style="width: 300px;height:200px ">
	        	IP Address
		</div>
		<div class="device-detail device-prop" style="width: 300px;height:200px ">
		</div>
		<div class="device-detail device-prop" style="width: 300px;height:200px ">
		</div>
	</div>
	
</div>
</body>
</html>