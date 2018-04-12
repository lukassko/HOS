<%@ page session="false"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	
	<style>
		body {
			background-color: #2e353d;
		}
		
		.login-pane {
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
	
	<script type="text/javascript">
	
		function doCall(url,arg, callback) {
		    var xmlhttp = new XMLHttpRequest();
		    xmlhttp.onreadystatechange = function() {
		        if (xmlhttp.readyState == XMLHttpRequest.DONE) {   // XMLHttpRequest.DONE == 4
		           if (xmlhttp.status == 200) {
		        	   if (callback !== undefined) {
		        		   callback(xmlhttp.response);
		        	   }
		           }
		           else if (xmlhttp.status == 400) {
		           		alert('There was an error 400');
		           }
		           else {
		           		alert('something else other than 200 was returned');
		           }
		        }
		    };
		
		    xmlhttp.open("POST", url, true);
		    xmlhttp.send(arg);
		}
		
		function doChallenge() {
			var user = document.getElementById('name').value;
			var params = "user=" + user;
			var url = "/challenge";
			doCall(url, params, doAuthentication);
		}
		
		function doAuthentication (response) {
			alert(response);
			var password = document.getElementById('password').value;
			var challengeresponse = calculateOneTimeChallnege();
			//var params = JSON!
			var url = "/login";
			doCall();
		}
		
	</script>
</head>
<body>
	<div class="login-pane">
		<form id="login-form" class="form" action="challenge" method="POST" onsubmit="return doChallenge();">
			<input id="name" type="text" name="user" placeholder="username"/>
			<input id="password" type="password" name="password"  placeholder="password"/>
			<input type="submit" value="LOG IN"/>
		</form>
	</div>
</body>
</html>