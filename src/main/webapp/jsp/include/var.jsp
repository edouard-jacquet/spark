<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<c:set var="context" value="${pageContext.request.contextPath}" scope="request"/>
<c:set var="constant" value="${applicationScope.constant}" scope="application"/>
<c:set var="user" value="${sessionScope[constant.SESSION_USER_NAME]}" scope="request"/>

<script>
	var _JAVA_context = "${context}";
	var _JAVA_regexQuery = "^${constant.REGEX_QUERY}$"
</script>