<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:import url="include/var.jsp"/>

<!DOCTYPE html>
<html lang='fr'>
	<head>
		<meta charset='utf-8'>
		<meta name='viewport' content='width=device-width, initial-scale=1'>
		<title>Open - Spark</title>
		<c:import url="include/dependence-css.jsp"/>
		<link rel='stylesheet' type='text/css' href='${context}/css/open.css'>
		<c:import url="include/dependence-js.jsp"/>
		<script src='${context}/js/open.js'></script>
	</head>

	<body>
		<header id='header' class='header'>
			<div class='navigation-bar navigation-bar--inverse'>
				<div class='navigation-bar__header'>
					<button class='button navigation-bar__toggle' type='button'><span class='bootypo bootypo--menu-hamburger '></span></button>
					<a class='navigation-bar__brand' href='${context}/home'>Spark</a>
				</div>
			</div>
		</header>
		<section id='body' class='body'>
			<c:import url="include/notification.jsp"/>
			<div class='grid'>
				<div class='row'>
					<div class='column small-12'>
						<c:if test="${document != null}">
							<h3><c:out value="${document.title}"/></h3>
							<div class='embed-responsive embed-responsive--16by9'>
								<object class='embed-responsive__item' data='<c:out value="${document.attachment}"/>' type='application/pdf'>
									<embed src='<c:out value="${document.attachment}"/>'/>
								</object>
							</div>
						</c:if>
					</div>
				</div>
			</div>
			
		</section>
		<footer id='footer' class='footer'>
		
		</footer>
	</body>
</html>