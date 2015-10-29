<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:import url="include/var.jsp"/>

<!DOCTYPE html>
<html lang='fr'>
	<head>
		<meta charset='utf-8'>
		<meta name='viewport' content='width=device-width, initial-scale=1'>
		<title>Upload - Spark</title>
		<link rel='stylesheet' type='text/css' href='css/bootstrap.css'>
		<link rel='stylesheet' type='text/css' href='css/style.css'>
		<link rel='stylesheet' type='text/css' href='css/upload.css'>
		<script src='js/jquery.js'></script>
		<script src='js/bootstrap.js'></script>
		<script src='js/script.js'></script>
		<script src='js/upload.js'></script>
	</head>

	<body>
		<header id='header' class='header'>
			<div class='navigation-bar navigation-bar--inverse'>
				<div class='navigation-bar__header'>
					<button class='button navigation-bar__toggle' type='button'><span class='bootypo bootypo--menu-hamburger '></span></button>
					<a class='navigation-bar__brand' href='home'>Spark</a>
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
					<div class='column small-12 medium-6 medium--centered large-4'>
						<div id='drop-supported' class='panel panel-upload'>
							<div class='panel__header grid'>
								<div class='row'>
									<div class='column small-12 text-center'>
										<h1 class='small-only--show'>Upload</h1>
										<span class='bootypo bootypo--cloud-upload panel__icon medium-up--show'></span>
									</div>
								</div>
							</div>
							<div class='panel__body grid'>
								<div class='row row--bottom-15'>
									<div class='column small-12'>
										<div id='drop' class='drop'>
											<span class='drop__instruction'>Drop your file here.</span>
										</div>
									</div>
								</div>
								<div class='row'>
									<div class='column small-12'>
										<div class='progress-container'>
											<div id='progress-upload' class='progress-bar progress-bar--warning progress-bar--striped progress-upload' style='width:0%'>0%</div>
										</div>
									</div>
								</div>
							</div>
						</div>
						<form id='drop-notsupported' class='panel panel-upload' method='post' action='upload' enctype='multipart/form-data'>
							<div class='panel__header grid'>
								<div class='row'>
									<div class='column small-12 text-center'>
										<h1 class='small-only--show'>Upload</h1>
										<span class='bootypo bootypo--cloud-upload panel__icon medium-up--show'></span>
									</div>
								</div>
							</div>
							<div class='panel__body grid'>
								<div class='row'>
									<div class='column small-12'>
										<div class='form__group'>
											<label class='form__label small-only--show' for=''>File</label>
											<div class='input-group'>
												<span class='input-group__addon'><span class='bootypo bootypo--file'></span></span>
												<input class='form__control' type='file' name='file'/></input>
											</div>
										</div>
									</div>
								</div>
								<div class='row'>
									<div class='column small-12'>
										<button class='button button--block button--primary'>Upload</button>
									</div>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</section>
		<footer id='footer' class='footer'>
		
		</footer>
	</body>
</html>