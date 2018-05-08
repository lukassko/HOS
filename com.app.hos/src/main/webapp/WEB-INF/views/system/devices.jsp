<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page session="false"%>

<!DOCTYPE html>
<html>
<head>
	<%@ page isELIgnored="false" %>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	
	<!-- attach other css and script -->
	<link rel="stylesheet" href="<c:url value="/resources/css/progress.css" />">
	<link rel="stylesheet" href="<c:url value="/resources/css/carousel.css" />">
	<link rel="stylesheet" href="<c:url value="/resources/css/overwright-boostrap.css" />">
	
	<script src="<c:url value="/resources/scripts/objects/progressCircle.js" />"></script>
	<script src="<c:url value="/resources/scripts/objects/datePicker.js" />"></script>
	<script src="<c:url value="/resources/scripts/managers/deviceControlsManager.js" />"></script>
	<script src="<c:url value="/resources/scripts/charts/chartsApi.js" />"></script>

	<script>
	
		$(document).ready(function() {
			
			deviceManager.drawDevices();
			
			window.statusDatePicker = new DatePicker("#report-range",
					function onDateSelect(type, start, end) {
						var device = deviceManager.getActiveDevice();
						if (device != null) {
							new DeviceStatusCall(device.serial,start.unix(),end.unix(), function(status, response) {
								chartsApi.setStatus(response);
								var series = chartsApi.getStatusSeries();
								ramChart.removeSeries();
								cpuChart.removeSeries();
								var cpuData = series[0];
								var ramData = series[1];
								ramChart.addSerie(ramData.name, ramData.data);
								cpuChart.addSerie(cpuData.name, cpuData.data);
		
							}).send();
						}
					});

			var ramChart = new Chart("#ram-chart",{
	            title: 'RAM usage'
	        });
			
			var cpuChart = new Chart("#cpu-chart",{
	            title: 'CPU usage'
	        });
			
			deviceManager.selectFirst();
	
			$('.nav > li > a').each(function() {
				$(this).on("click", function() {
					var layout = $(this).attr("data-target");
					$('div.container').addClass('hide');
					$('#' + layout).removeClass('hide');
				});
			});
			$('.dropdown > .dropdown-menu  a').each(function() {
				$(this).on("click", function() {
					$('.view-container').showModal();
				});
			});
		});
		
	</script>
</head>
<body> 

<div class="view-container">
	<div class="device-container device-property">
	</div>
	
	<div class="device-panel">
		<nav class="navbar navbar-inverse device-property">
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
						<li><a href="#" data-target="status-layout">Status</a></li>
						<li><a href="#">History</a></li>
						<li class="dropdown">
							<a href="#" class="dropdown-toggle"W data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Commands <span class="caret"></span></a>
							<ul class="dropdown-menu">								
								<li><a href="#">Show commands</a>
								</li>
								<li><a href="#">Commands history</a></li>
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
		<div id="status-layout" class="container" style="height:100%; width:100%">
			<div class="row">
				<div class="col-12">
					<div class="device-block device-property" style="width: 200px;height:200px ">
						<div class="usage usage-info">
				        	CPU
				        </div>
						<div class="my-progress" data-usage-type="cpu">
						 	<canvas id="bar-cpu" class = "bar" width="150" height="150"></canvas>
						</div>
					</div>
					<div class="device-block device-property" style="width:200px;height:200px ">
						<div class="usage usage-info">
				        	RAM
				        </div>
						<div class="my-progress" data-usage-type="ram">
						 	<canvas id="bar-ram" class = "bar" width="150" height="150"></canvas>
						</div>
					</div>
					<table class="detail-table">
						<tr class="detail-table-row">
							<td class="detail-table-cell" style="width:50%;">
								<div class="device-property">
							        <div class="usage usage-info">Connection time</div>
							        <div id="connection-time" class="usage usage-info-value">xxxx-xx-xx xx:xx:xx</div>
								</div>
							</td>
							<td class="detail-table-cell" style="width:30%;">
								<div class="device-property">
							        <div class="usage usage-info ">IP Address</div>
							        <div id="ip-address" class="usage usage-info-value">xxx.xxx.xxx.xxx</div>
								</div>
							</td>
							<td class="detail-table-cell" style="width:20%;">
								<div class="device-property">
							        <div class="usage usage-info">Port no</div>
							        <div id="port-number" class="usage usage-info-value">xxx</div>
								</div>
							</td>
						</tr>
						<tr class="detail-table-row">
							<td colspan="3" class="detail-table-cell">
								<div class="device-property"></div>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<div class="row" style="margin-top:10px; height: calc(100% - 280px);">
				<div class="col-12 device-property" style="height:100%;">
					<div class="center-left" style="padding-left: 10px; height: 50px;">
						<div id="report-range" data-type="status"></div>
					</div>
					<div style="height:calc(100% - 50px);">
						<div id="ram-chart" style="height:50%;"></div>
						<div id="cpu-chart" style="height:50%;"></div>
					</div>
				</div>
			</div>
		</div>	
		<!-- Modal window -->
		<div id="commands-poopup" class="modal fade" role="dialog">
			<div class="modal-dialog">		
				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Modal Header</h4>
					</div>
					<div class="modal-body">
						<p>Some text in the modal.</p>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					</div>
				</div>
			</div>
		</div>
		</div>
</div>
</body>
</html>