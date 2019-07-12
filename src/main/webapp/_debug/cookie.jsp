<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<h1>Cookie Scope</h1>
<table>
	<c:forEach var="item" items="${cookie}" varStatus="idx">
		<tr>
			<td><c:out value="${item.key}"/></td>
			<td><c:out value="${item.value}"/></td>
			<td><c:out value="${item.value.name}"/></td>
			<td><c:out value="${item.value.value}"/></td>
		</tr>
	</c:forEach>
</table>