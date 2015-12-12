<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

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
					<div id='upload-progresscontainer' class='progress-container'>
						<div id='upload-progress' class='upload-progress progress-bar progress-bar--warning progress-bar--striped' style='width:0%'>0%</div>
					</div>
				</div>
			</div>
		</div>
		<div id='drop-modal'class='drop-modal modal'>
			<div class='modal__dialog'>
				<div class='modal__header'>
					<h4 class='modal__title'>Document Information</h4>
				</div>
				<div class='modal__body'>
					<form id='drop-form' class='drop-form grid'>
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
					</form>
				</div>
				<div class='modal__footer'>
					<button id='drop-confirm' class='drop-confirm button button--success'>Confirm</button>
					<button id='drop-cancel' class='drop-cancel button button--alert'>Cancel</button>
				</div>
			</div>
			<div class='modal__backdrop'></div>
		</div>
	</div>