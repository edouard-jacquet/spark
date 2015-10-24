<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:import url="include/var.jsp"/>

<!DOCTYPE html>
<html lang='fr'>
	<head>
		<meta charset='utf-8'>
		<meta name='viewport' content='width=device-width, initial-scale=1'>
		<title>Logout - Spark</title>
		<link rel='stylesheet' type='text/css' href='css/bootstrap.css'>
		<link rel='stylesheet' type='text/css' href='css/style.css'>
		<link rel='stylesheet' type='text/css' href='css/logout.css'>
		<script src='js/jquery.js'></script>
		<script src='js/bootstrap.js'></script>
		<script src='js/script.js'></script>
		<script src='js/logout.js'></script>
	</head>

	<body>
		<header id='header' class='header'>
			<div class='navigation-bar navigation-bar--inverse'>
				<div class='navigation-bar__header'>
					<button class='button navigation-bar__toggle' type='button'><span class='bootypo bootypo--menu-hamburger '></span></button>
					<a class='navigation-bar__brand' href='home'>Spark</a>
				</div>
			</div>
		</header>
		<section id='body' class='body'>
			<div class='grid'>
				<div class='row'>
					<div class='column small-12'>
						<div class='jumbotron'>
							<h1>Goodbye</h1>
							<p>
								You have been logged out.<br/>
								Automatically redirect on home page in <span id='left-time'></span>.
							</p>
							<p><a class='button button--primary button--large' href='home'>Go home</a></p>
						</div>
					</div>
				</div>
			</div>
		</section>
		<footer id='footer' class='footer'>
		
		</footer>
	</body>
</html>