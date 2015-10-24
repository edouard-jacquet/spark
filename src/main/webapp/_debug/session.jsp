<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<h1>Session Scope</h1>
<table>
	<c:forEach var="item" items="${sessionScope}" varStatus="idx">
		<tr>
			<td><c:out value="${item.key}"/></td>
			<td><c:out value="${item.value}"/></td>
		</tr>
	</c:forEach>
</table>