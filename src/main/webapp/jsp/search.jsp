<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:import url="include/var.jsp"/>

<!DOCTYPE html>
<html lang='fr'>
	<head>
		<meta charset='utf-8'>
		<meta name='viewport' content='width=device-width, initial-scale=1'>
		<title>Search - Spark</title>
		<c:import url="include/dependence-css.jsp"/>
		<link rel='stylesheet' type='text/css' href='${context}/css/search.css'>
		<c:import url="include/dependence-js.jsp"/>
		<script src='${context}/js/search.js'></script>
	</head>

	<body>
		<header id='header' class='header'>
			<div class='navigation-bar navigation-bar--inverse'>
				<div class='navigation-bar__header'>
					<button class='button navigation-bar__toggle' type='button'><span class='bootypo bootypo--menu-hamburger '></span></button>
					<a class='navigation-bar__brand' href='${context}/home'>Spark</a>
				</div>
				<div class='navigation-bar__body'>
					<form id='search' class='navigation-bar__form navigation-bar--left search' method='get' action='${context}/search'>
						<div class='form__group'>
							<div class='input-group'>
								<input id='query' class='form__control query' type='text' name='query' value='<c:out value="${query}"/>'/>
								<span class='input-group__button'>
									<button class='button button--primary'>
										<span class='bootypo bootypo--search'></span>
									</button>
								</span>
							</div>
						</div>
						<div id='suggestions' class='list-group suggestions'></div>
					</form>
					<c:import url="include/guest.jsp"/>
				</div>
			</div>
		</header>
		<section id='body' class='body'>
			<div class='grid'>
				<div id='controls' class='row row--bottom-15 controls'>
					<div class='column small-6 small--offset-6 text-right'>
						<div class='button-group'>
							<button id='control-settings' class='button button--default'><span class='bootypo bootypo--cogwheel'></span></button>
						</div>
					</div>
				</div>
				<div id='options' class='row row--bottom-15 options'>
					<div class='column small-12'>
						<div class='grid'>
							<div id='settings' class='row settings'>
								<div class='column small-6 medium-3'>
									<input id='recommendation' class='switch switch--primary' type='checkbox' name='recommendation' checked/>
									<label for='recommendation'>Recommendation</label>
								</div>
								<div class='column small-6 medium-3'>
									<input id='personalization' class='switch switch--primary' type='checkbox' name='personalization' checked/>
									<label for='personalization'>Personalization</label>
								</div>
							</div>
							<c:if test="${documents != null && documentCount > 0}">
								<div id='details' class='row details'>
									<div class='column small-12'>
										About <c:out value="${documentCount}"/> document<c:if test="${documentCount > 1}">s</c:if>.
									</div>	
								</div>
							</c:if>
						</div>
					</div>
				</div>
				<div class='row'>
					<div class='column small-12'>
						<div class='grid'>
							<c:if test="${documents != null && documentCount > 0}">
								<c:forEach var="document" items="${documents}" varStatus="idx">
									<div class='row'>
										<div class='column small-12'>
											<div class='thumbnail'>
												<div class='thumbnail__caption'>
													<h3><c:out value="${document.title}"/></h3>
													<p class='text-justify'>
														
													</p>
													<p>
														<a class='button button--default' href='${context}/resource/open?id=<c:out value="${document.id}"/>'>Readme</a>
													</p>
												</div>
											</div>
										</div>
									</div>
								</c:forEach>
							</c:if>
							<c:if test="${documents != null && documentCount == 0}">
								<div class='row'>
									<div class='column small-12'>
										<h3>No Documents Found.</h3>
										<p>
											Your search : <span class='bold'><c:out value="${query}"/></span>, did not match any documents.
										</p>
									</div>
								</div>
							</c:if>
						</div>
					</div>
				</div>
			</div>
		</section>
		<footer id='footer' class='footer text-center'>
			<c:if test="${documents != null && documentCount > 0}">
				<ul class='pagination'>
					<c:if test="${currentPage == 1}">
						<li class='disabled'><a href='#'>&larr;</a></li>
					</c:if>
					<c:if test="${currentPage > 1}">
						<li><a href='${context}/search?query=<c:out value="${query}"/>&page=<c:out value="${currentPage - 1}"/>'>&larr;</a></li>
					</c:if>
					<c:forEach begin="1" end="${maxPage}" step="1" var="index">
						<c:if test="${index == currentPage}">
							<li class='active'><a href='#'><c:out value="${index}"/></a></li>
						</c:if>
						<c:if test="${index != currentPage}">
							<li><a href='${context}/search?query=<c:out value="${query}"/>&page=<c:out value="${index}"/>'><c:out value="${index}"/></a></li>
						</c:if>
					</c:forEach>
					<c:if test="${currentPage == maxPage}">
						<li class='disabled'><a href='#'>&rarr;</a></li>
					</c:if>
					<c:if test="${currentPage < maxPage}">
						<li><a href='${context}/search?query=<c:out value="${query}"/>&page=<c:out value="${currentPage + 1}"/>'>&rarr;</a></li>
					</c:if>
				</ul>
			</c:if>
		</footer>
	</body>
</html>