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
				<div class='navigation-bar__body'>
					<c:import url="include/navbar-access.jsp"/>
				</div>
			</div>
		</header>
		<section id='body' class='body'>
			<c:import url="include/notification.jsp"/>
			<c:if test="${document != null}">
				<div class='grid'>
					<div class='row'>
						<div class='column small-8'>
							<h3><c:out value="${document.title}"/></h3>
							<div class='embed-responsive embed-responsive--4by3'>
								<object class='embed-responsive__item' data='<c:out value="${context}/${constant.PUBLICSTORAGE_DOCUMENT_FOLDER}${document.attachment}.pdf"/>' type='application/pdf'>
									<embed src='<c:out value="${context}/${constant.PUBLICSTORAGE_DOCUMENT_FOLDER}${document.attachment}.pdf"/>'/>
								</object>
							</div>
						</div>
						<div class='column small-4'>
							<h3>Similar documents</h3>
							<div class='grid'>
								<c:if test="${recommendations != null && recommendations.size() > 0}">
									<c:forEach var="recommendation" items="${recommendations}" varStatus="idx">
										<div class='row'>
											<div class='column small-12'>
												<div class='thumbnail'>
													<div class='thumbnail__caption'>
														<h4><c:out value="${recommendation.title}"/></h4>
														<p>
															<a class='button button--default' href='${context}/resource/open?id=<c:out value="${recommendation.id}"/>'>Readme</a>
														</p>
													</div>
												</div>
											</div>
										</div>
									</c:forEach>
								</c:if>
							</div>
						</div>
					</div>
				</div>
			</c:if>
		</section>
		<footer id='footer' class='footer'>
		
		</footer>
	</body>
</html>