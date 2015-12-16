<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	
	<div id='indexer' class='index tabs__pane'>
		<div class='grid'>
			<div class='row'>
				<div class='column small-12'>
					<table>
						<thead>
							<tr>
								<th>Indexer</th>
								<th class='text-right'>Action</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>Document</td>
								<td class='text-right'><button id='indexer-document' class='indexer-document indexer__rebuild button button--primary'>Rebuild</button></td>
							</tr>
							<tr>
								<td>Suggestion</td>
								<td class='text-right'><button id='indexer-suggestion' class='indexer-suggestion indexer__rebuild button button--primary'>Rebuild</button></td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
			<div class='row'>
				<div class='column small-12'>
					<span id='indexer-left' class='indexer-left label label--warning'>00:00:00</span>
					<div id='indexer-progresscontainer' class='indexer-progresscontainer progress-container'>
						<div id='indexer-progress' class='indexer-progress progress-bar progress-bar--warning progress-bar--striped' style='width:0%'>0%</div>
					</div>
				</div>
			</div>
		</div>
	</div>