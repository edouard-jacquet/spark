<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:import url="include/var.jsp"/>

<!DOCTYPE html>
<html lang='fr'>
	<head>
		<meta charset='utf-8'>
		<meta name='viewport' content='width=device-width, initial-scale=1'>
		<title>Login - Spark</title>
		<c:import url="include/dependence-css.jsp"/>
		<link rel='stylesheet' type='text/css' href='${context}/css/login.css'>
		<c:import url="include/dependence-js.jsp"/>
		<script src='${context}/js/login.js'></script>
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
					<div class='column small-12 medium-6 medium--centered large-4'>
						<form class='panel panel-login' method='post' action='${context}/login'>
							<div class='panel__header grid'>
								<div class='row'>
									<div class='column small-12 text-center'>
										<h1 class='small-only--show'>Authentication</h1>
										<span class='bootypo bootypo--unlock panel__icon medium-up--show'></span>
									</div>
								</div>
							</div>
							<div class='panel__body grid'>
								<div class='row'>
									<div class='column small-12'>
										<div class='form__group'>
											<label class='form__label small-only--show' for=''>User</label>
											<div class='input-group'>
												<span class='input-group__addon'><span class='bootypo bootypo--user'></span></span>
												<input class='form__control' type='text' name='login' pattern='${constant.REGEX_LOGIN}' required title='Please enter a valid login'/>
											</div>
										</div>
									</div>
								</div>
								<div class='row'>
									<div class='column small-12'>
										<div class='form__group'>
											<label class='form__label small-only--show' for=''>Password</label>
											<div class='input-group'>
												<span class='input-group__addon'><span class='bootypo bootypo--lock'></span></span>
												<input class='form__control' type='password' name='password' pattern='${constant.REGEX_PASSWORD}' required title='Please enter a valid password'/>
											</div>
										</div>
									</div>
								</div>
								<div class='row row--bottom-15'>
									<div class='column small-12'>
										<button class='button button--block button--primary'>Log in</button>
									</div>
								</div>
								<div class='row'>
									<div class='column small-6 text-left'>
										<input id='remember' class='switch switch--primary' type='checkbox' name='remember' checked/>
										<label for='remember'>Remember me</label>
										
									</div>
									<div class='column small-6 text-right'>
										<a href='${context}/signup'>Create an account</a>
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