<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<c:set var="context" value="${pageContext.request.contextPath}" scope="request"/>
<c:set var="guest" value="${sessionScope['guest']}" scope="request"/>