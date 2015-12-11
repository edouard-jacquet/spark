<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:import url="include/var.jsp"/>

<!DOCTYPE html>
<html lang='fr'>
	<head>
		<meta charset='utf-8'>
		<meta name='viewport' content='width=device-width, initial-scale=1'>
		<title>Administration - Spark</title>
		<c:import url="include/dependence-css.jsp"/>
		<link rel='stylesheet' type='text/css' href='${context}/css/administration.css'>
		<c:import url="include/dependence-js.jsp"/>
		<script src='${context}/js/administration.js'></script>
	</head>

	<body>
		<header id='header' class='header'>
			<div class='navigation-bar navigation-bar--inverse'>
				<div class='navigation-bar__header'>
					<button class='button navigation-bar__toggle' type='button'><span class='bootypo bootypo--menu-hamburger '></span></button>
					<a class='navigation-bar__brand' href='${context}/home'>Spark</a>
				</div>
				<div class='navigation-bar__body'>
					<c:import url="include/guest.jsp"/>
				</div>
			</div>
		</header>
		<section id='body' class='body'>
			<c:import url="include/notification.jsp"/>
			<div class='grid'>
				<div class='row'>
					<div class='column small-12'>
						<div class='tabs grid'>
							<div class='row'>
								<div class='column small-12 medium-3 large-2'>
									<ul class='tabs__nav navigation navigation--pills navigation--stacked'>
										<li class='active'><a href='#statistic'>Statistic</a></li>
										<li><a href='#indexer'>Indexer</a></li>
										<li><a href='#schedule'>Schedule</a></li>
									</ul>
								</div>
								<div class='column small-12 medium-9 large-10'>
									<div class='tabs__content'>
										<c:import url="include/administration-statistic.jsp"/>
										<c:import url="include/administration-indexer.jsp"/>
										<c:import url="include/administration-shedule.jsp"/>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</section>
		<footer id='footer' class='footer'>
		
		</footer>
	</body>
</html>