<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="<c:url value="/resources/css/carousel.css" />">
<script src="<c:url value="/resources/scripts/objects/carousel.js" />"></script>

</head>
<body>
	GENERAL INFORMATIONS

	<div id="carousel">
		<div id="carousel-buttons">
			<a href="#" id="carousel-prev">prev</a> <a href="#"
				id="carousel-next">next</a>
		</div>
		<div class="clear"></div>
		<div id="carousel-slides">
			<ul>
				<li>TEST_1</li>
				<li>TEST_2</li>
				<li>TEST_3</li>
			</ul>
		</div>
	</div>

</body>
</html>