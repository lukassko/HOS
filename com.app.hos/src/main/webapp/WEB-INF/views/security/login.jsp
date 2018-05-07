<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page session="false"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	
	<style>
		body {
			background-color: #2e353d;
			box-sizing: border-box;
			font-family: Arial, Helvetica, sans-serif;
		}
		.pane {
			padding: 8% 0 0;
			width: 360px;
			margin: auto;
		}
		
		.form {
			background-color: #23282e;
			padding: 45px;
		}
		
		.form input {
			box-sizing: border-box;
			width: 100%;
			padding: 15px;
			font-size: 14px;
			margin: 0 0 15px;
		}
		
		.form button {
			width: 100%;
			text-transform: uppercase;
			font-size: 14px;
			padding: 15px;
			cursor: pointer;
		}
		
		#error-msg {
			background-color: #23282e;
			color: yellow;
			font-size: 20px;
			width: 100%;
			display: inline-block;
			text-align: center;
		}

		#loader {
			height: 4px;
			width: 100%;
			position: relative;
			background-color: #DDD;
			overflow: hidden;
		}
		
		#loader:before{
		  	display: block;
		  	position: absolute;
		  	content: "";
		  	left: -200px;
		  	width: 200px;
		  	height: 4px;
		  	background-color: orange;
		  	animation: loading 2s linear infinite;
		}

		@keyframes loading {
		    from {left: -200px; width: 30%;}
		    50% {width: 30%;}
		    70% {width: 70%;}
		    80% {left: 50%;}
		    95% {left: 120%;}
		    to {left: 100%;}
		}
		
	</style>
	<script src="<c:url value="/resources/scripts/security.js" />"></script>
	<script type="text/javascript">

		window.history.replaceState("", "", "/HOS/");
		
		function doCall(type,url,callback,arg) {
			var xmlhttp = new XMLHttpRequest();
			xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState == XMLHttpRequest.DONE) {   // XMLHttpRequest.DONE == 4
					callback(xmlhttp.status,xmlhttp.response);
				}
			};
			xmlhttp.open(type, url, true);
			xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
					    
			if (arg !== undefined) {
				xmlhttp.send(arg);
			} else {
				xmlhttp.send();
			}
		}
		
		function doChallenge() {
			showLoader();
			var user = document.getElementById('name').value;
			var params = "user=" + user;
			doCall("POST","challenge",doAuthentication,params);
			return false;
		}
		
		function doAuthentication (status,response) {
			if (status == 200) {
				var params = "challenge=" + calculateOneTimeChallnege(response);
				doCall("POST","login",getMain,params);
			} else {
				hideLoader();
				showErrorMessage("Invalid user name.");
			}
		}
		
		function getMain (status,response) {
			if (status == 200) {
				window.location.replace("/HOS/");
			} else {
				hideLoader();
				showErrorMessage("Invalid user password.");
			}
		}

		function calculateOneTimeChallnege(response) {
			var password = document.getElementById('password').value;
			var hashing = JSON.parse(response);
			var toHash = password + hashing.salt;
			var hash = sha256(toHash);
			return sha256(hash + hashing.challenge);
		};
		
		function showErrorMessage(msg) {
			document.getElementById("error-msg").innerHTML = msg;
		}
		
		function showLoader() {
		    document.getElementById("loader").style.display = "block";
		}
		
		function hideLoader() {
			document.getElementById("loader").style.display = "none";
		}
		
	</script>
</head>
<body onload="hideLoader()">
	<div class="pane">
		<div id="loader"></div>
		<form id="login-form" class="form" action="challenge" method="POST" onsubmit="return doChallenge();">
			<input id="name" type="text" name="user" placeholder="username"/>
			<input id="password" type="password" name="password"  placeholder="password"/>
			<input type="submit" value="LOG IN"/>
		</form>
		
		<div id="error-msg">
			<c:choose>
			    <c:when test= "${response.getStatus() == 401}">
			        Session expired
			    </c:when>
			</c:choose>
		</div>
	</div>
</body>
</html>