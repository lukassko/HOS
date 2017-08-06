<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>

	<link rel="stylesheet" href="<c:url value="/resources/css/main.css" />">
	<link href="http://maxcdn.bootstrapcdn.com/font-awesome/4.1.0/css/font-awesome.min.css" rel="stylesheet">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	
	<script src="https://cdn.jsdelivr.net/sockjs/1/sockjs.min.js"></script> 
	<script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"></script> 
	
	<script src="<c:url value="/resources/scripts/websocket.js" />"></script>
	
	<script>
	$(document).ready(function() {
		
		$('ul.collapse li').not('li.collapsed').each(function() {
			var page = $(this).attr("data-target");
		    $(this).on("click", function() {
		    	$('li.active').removeClass('active');
		    	$(this).addClass('active');
		    	var page = $(this).attr("data-target");
		    	$.get(page, function(data){
				    $('#container').html(data);
				});
		    	var title = $(this).text();
		    	$('#active-page').text(title);
		    });
		});
		hosWebsocket.connect();
	});

	function setSystemInfo(message) {
		$("#system-info").text(message);
		$("#system-info").fadeIn(1000);
		setTimeout(function(){
			$("#system-info").fadeOut(2000)
		}, 3000 );
	};
	
	var intervalID = setInterval(function(){
		var builder = new WebCommandBuilder();
        var command = builder.construct(new GetAllDeviceCommandBuilder());
        hosWebsocket.sendCommand(command);
	}, 5000);
	
	window.addEventListener("beforeunload", function (e) {
		var dialogText = 'Dialog text here';
		e.returnValue = dialogText;
		clearInterval(intervalID);
		hosWebsocket.disconnect();
		return dialogText;                             
	}); 
	
	window.addEventListener("onunload", function (e) {
		console.log('EXIT');
		hosWebsocket.disconnect();
	}); 

	</script>
	<title>HOS</title>
</head>
<body>


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
		<span id="system-info">Testowa wiadomosc: polaczone 5 urzadzen</span>
	</div>
	<div id="container" >
		Hello WORLD
	</div> 
</div>



</body>
</html>
