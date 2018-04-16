<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page session="false"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	
	<style>
		body {
			background-color: #2e353d;
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
	</style>
	<script src="<c:url value="/resources/scripts/security.js" />"></script>
	<script type="text/javascript">
	
		function doCall(type,url,callback,arg) {
			var xmlhttp = new XMLHttpRequest();
			xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState == XMLHttpRequest.DONE) {   // XMLHttpRequest.DONE == 4
					var status = xmlhttp.status;
					if (status == 200) {
						callback(status,xmlhttp.response);
					}
					else if (status == 400) {
						console.log('There was an error 400');
					}
					else if (status == 401) {
						showErrorMessage(xmlhttp.responseText);
						console.log('There was an error 401' + xmlhttp.responseText);
					}
					else {
						console.log('something else other than 200 was returned');
					}
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
			var user = document.getElementById('name').value;
			var params = "user=" + user;
			doCall("POST",url,doAuthentication,params);
			return false;
		}
		
		function doAuthentication (status,response) {
			var url = "login";
			var params = "challenge=" + calculateOneTimeChallnege(response);
			doCall("POST",url,verifyAuthentication,params);
		}
		
		function verifyAuthentication (status,response) {
			doCall("GET","/",loadHtml);
		}
		
		function loadHtml (status,response) {
			document.write(response);
		}

		function calculateOneTimeChallnege(response) {
			var password = document.getElementById('password').value;
			var hashing = JSON.parse(response);
			var toHash = password + hashing.salt;
			var hash = sha256(toHash);
			return sha256(hash + hashing.challenge);
		};
		
		function showErrorMessage (message) {
			document.getElementById("error-msg").innerHTML = message;
		}
		
	</script>
</head>
<body>
	<div class="pane">
		<form id="login-form" class="form" action="challenge" method="POST" onsubmit="return doChallenge();">
			<input id="name" type="text" name="user" placeholder="username"/>
			<input id="password" type="password" name="password"  placeholder="password"/>
			<input type="submit" value="LOG IN"/>
		</form>
		<span id="error-msg"></span>
	</div>
	<!--  <div id="error-msg" class="pane"></div> -->
</body>
</html>