<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

	<form id='drop-notsupported' class='panel panel-upload' method='post' action='${context}/resource/upload' enctype='multipart/form-data'>
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
						<label class='form__label small-only--show' for=''>Title</label>
						<div class='input-group'>
							<span class='input-group__addon'><span class='bootypo bootypo--header'></span></span>
							<input class='form__control' type='text' name='title' required/>
						</div>
					</div>
				</div>
			</div>
			<div class='row'>
				<div class='column small-12'>
					<div class='form__group'>
						<label class='form__label small-only--show' for=''>Publication date</label>
						<div class='input-group'>
							<span class='input-group__addon'><span class='bootypo bootypo--calendar'></span></span>
							<input class='form__control' type='text' name='publicationdate' required pattern='${constant.REGEX_YEAR}' title='Please enter a valid Year'/>
						</div>
					</div>
				</div>
			</div>
			<div class='row'>
				<div class='column small-12'>
					<div class='form__group'>
						<label class='form__label small-only--show' for=''>Authors</label>
						<div class='input-group'>
							<span class='input-group__addon'><span class='bootypo bootypo--user'></span></span>
							<input class='form__control' type='text' name='authors' placeholder='separate by ${constant.AUTHOR_SEPARATOR}' required/>
						</div>
					</div>
				</div>
			</div>
			<div class='row'>
				<div class='column small-12'>
					<div class='form__group'>
						<label class='form__label small-only--show' for=''>Abstract</label>
						<div class='input-group'>
							<span class='input-group__addon'><span class='bootypo bootypo--text-background'></span></span>
							<textarea class='form__control' name='summary' rows='10'></textarea>
						</div>
					</div>
				</div>
			</div>
			<div class='row'>
				<div class='column small-12'>
					<div class='form__group'>
						<label class='form__label small-only--show' for=''>File</label>
						<div class='input-group'>
							<span class='input-group__addon'><span class='bootypo bootypo--file'></span></span>
							<input class='form__control' type='file' name='file' required title='Please select a file'/>
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