<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	
	<div id='statistic' class='statistic tabs__pane open'>
		<div class='grid'>
			<div class='row'>
				<div class='column small-12'>
					<div class='statistic-header page-header'>
						<h1>Storage <span class='small'>Space</span></h1>
					</div>
					<div class='grid'>
						<div class='row'>
							<div class='column small-12 medium-6 large-4'>
								<div class='graph graph--rounded' data-title='Storage' data-color='#5cb85c' data-value='<c:out value="${statistics['storageSpace']}"/>' data-max='<c:out value="${statistics['storageSpace']}"/>' data-unit='<c:out value="${statistics['storageUnitSpace']}"/>'></div>
							</div>
							<div class='column small-12 medium-6 large-4'>
								<div class='graph graph--rounded' data-title='Document' data-color='#f0ad4e' data-value='<c:out value="${statistics['documentSpace']}"/>' data-max='<c:out value="${statistics['storageSpace']}"/>' data-unit='<c:out value="${statistics['documentUnitSpace']}"/>'></div>
							</div>
							<div class='column small-12 medium-6 large-4'>
								<div class='graph graph--rounded' data-title='Index' data-color='#d9534f' data-value='<c:out value="${statistics['indexSpace']}"/>' data-max='<c:out value="${statistics['storageSpace']}"/>' data-unit='<c:out value="${statistics['indexUnitSpace']}"/>'></div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class='row'>
				<div class='column small-12'>
					<div class='statistic-header page-header'>
						<h1>Database <span class='small'>Instance</span></h1>
					</div>
					<div class='grid'>
						<div class='row'>
							<div class='column small-12 medium-6 large-4'>
								<div class='graph graph--rounded' data-title='Document' data-color='#428bca' data-value='<c:out value="${statistics['documentCount']}"/>' data-max='<c:out value="${statistics['documentCount']}"/>'></div>
							</div>
							<div class='column small-12 medium-6 large-4'>
								<div class='graph graph--rounded' data-title='Suggestion' data-color='#5bc0de' data-value='<c:out value="${statistics['suggestionCount']}"/>' data-max='<c:out value="${statistics['suggestionCount']}"/>'></div>
							</div>
							<div class='column small-12 medium-6 large-4'>
								<div class='graph graph--rounded' data-title='User' data-color='#9a9a9a' data-value='<c:out value="${statistics['userCount']}"/>' data-max='<c:out value="${statistics['userCount']}"/>'></div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>