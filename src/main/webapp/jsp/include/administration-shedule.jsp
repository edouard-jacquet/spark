<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
	
	<div id='schedule' class='schedule tabs__pane'>
		<div class='grid'>
			<div class='row'>
				<div class='column small-12 large-6'>
					<div class='schedule-header page-header'>
						<h1><span class='small'>Configuration</span></h1>
					</div>
					<form id='schedule-configuration' class='' method='post' action=''>
						<div class='grid'>
							<div class='row row--bottom-15'>
								<div class='column small-12'>
									<c:choose>
										<c:when test="${scheduleConf['active'] == true}">
											<input id='schedule-active' class='schedule-active switch switch--success' type='checkbox' name='active' checked/>
										</c:when>
										<c:otherwise>
											<input id='schedule-active' class='schedule-active switch switch--success' type='checkbox' name='active'/>
										</c:otherwise>
									</c:choose>
									<label for='schedule-active'>Active</label>
								</div>
							</div>
							<div class='row'>
								<div class='column small-12'>
									<div class='form__group'>
										<label class='form__label small-only--show' for=''>Trigger</label>
										<div class='input-group'>
											<span class='input-group__addon'><span class='bootypo bootypo--calendar'></span></span>
											<input class='form__control' type='text' name='trigger' required value='<c:out value="${scheduleConfiguration['trigger']}"/>'/>
										</div>
										<span class='form__help'><span class='bootypo bootypo--circle-question-mark'></span>Use unix cron syntax</span>
									</div>
								</div>
							</div>
							<div class='row'>
								<div class='column small-12'>
									<table>
										<thead>
											<tr>
												<th>Source</th>
												<th class='text-right'>Action</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach var="source" items="${sources}" varStatus="idx">
												<c:if test="${source.name != 'personal'}">
													<tr>
														<td><c:out value="${source.name}"/></td>
														<td class='text-right'>
															<c:choose>
																<c:when test="${source.active == 1}">
																	<input id='schedule-source${source.id}' class='schedule-source switch switch--primary' type='checkbox' name='source${source.id}' checked/>
																</c:when>
																<c:otherwise>
																	<input id='schedule-source${source.id}' class='schedule-source switch switch--primary' type='checkbox' name='source${source.id}'/>
																</c:otherwise>
															</c:choose>
															<label for='schedule-source${source.id}'></label>
														</td>
												</c:if>
											</c:forEach>
										</tbody>
									</table>
								</div>
							</div>
							<div class='row row--bottom-40'>
								<div class='column small-12'>
									<button id='schedule-save' class='schedule-save button button--primary'>Save</button>
								</div>
							</div>
						</div>
					</form>
				</div>
				<div class='column small-12 large-6'>
					<div class='schedule-header page-header'>
						<h1><span class='small'>Manual</span></h1>
					</div>
					<div class='grid'>
						<div class='row row--bottom-15'>
							<div class='column small-12'>
								<button id='schedule-launch' class='schedule-launch button button--primary'>Launch</button>
							</div>
						</div>
						<div class='row'>
							<div class='column small-12'>
								<span id='schedule-left' class='schedule-left label label--warning'>00:00:00</span>
								<div id='schedule-progresscontainer' class='schedule-progresscontainer progress-container'>
									<div id='schedule-progress' class='schedule-progress progress-bar progress-bar--warning progress-bar--striped' style='width:0%'>0%</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>