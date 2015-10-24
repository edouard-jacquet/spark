<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:if test="${notifications != null && notifications.size() > 0}">
	<div class='grid'>
		<c:forEach var="notification" items="${notifications}">
			<div class='row'>
				<div class='column small-12'>
					<div class='notification notification--<c:out value="${notification.type}"/>'>
						<c:out value="${notification.message}"/>
						<button class='button notification__close'><span class='bootypo bootypo--remove-2'></span></button>
					</div>
				</div>
			</div>
		</c:forEach>
	</div>
</c:if>