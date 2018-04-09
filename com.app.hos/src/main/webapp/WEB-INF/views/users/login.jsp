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
</head>
<body>
	<div class="login-pane">
		<form id="login-form" class="form" action="challenge" method="POST">
			<input type="text" name="user" placeholder="username"/>
			<input type="password" name="password"  placeholder="password"/>
			<input type="submit" value="LOG IN"/>
		</form>
	</div>
</body>
</html>