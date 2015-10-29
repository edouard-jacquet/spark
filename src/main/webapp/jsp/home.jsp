<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<c:import url="include/var.jsp"/>

<!DOCTYPE html>
<html lang='fr'>
	<head>
		<meta charset='utf-8'>
		<meta name='viewport' content='width=device-width, initial-scale=1'>
		<title>Home - Spark</title>
		<link rel='stylesheet' type='text/css' href='${context}/css/bootstrap.css'>
		<link rel='stylesheet' type='text/css' href='${context}/css/style.css'>
		<link rel='stylesheet' type='text/css' href='${context}/css/home.css'>
		<script src='${context}/js/jquery.js'></script>
		<script src='${context}/js/bootstrap.js'></script>
		<script src='${context}/js/script.js'></script>
		<script src='${context}/js/home.js'></script>
	</head>

	<body>
		<header id='header' class='header'>
			<div class='navigation-bar navigation-bar--inverse'>
				<div class='navigation-bar__header'>
					<button class='button navigation-bar__toggle' type='button'><span class='bootypo bootypo--menu-hamburger '></span></button>
				</div>
				<div class='navigation-bar__body'>
					<c:import url="include/guest.jsp"/>
				</div>
			</div>
		</header>
		<section id='body' class='body'>
			<div class='grid'>
				<div class='row'>
					<div class='column small-12 text-center'>
						<span class='letter'>S</span>
						<span class='letter'>p</span>
						<span class='letter'>a</span>
						<span class='letter'>r</span>
						<span class='letter'>k</span>
					</div>
				</div>
				<div class='row'>
					<div class='column small-10 medium-6 small--centered'>
						<form id='search' class='search' method='get' action='${context}/search'>
							<div class='form__group'>
								<input id='query' class='form__control query' type='search' name='query'/></input>
							</div>
							<div id='suggestions' class='list-group suggestions'></div>
						</form>
					</div>
				</div>
			</div>
		</section>
		<footer id='footer' class='footer'>
		
		</footer>
	</body>
</html>