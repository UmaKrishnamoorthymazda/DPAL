<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ page import="com.mazdausa.dealer.parts.dpal.applications.util.WebUtils"%>

<html:html>
<HEAD>
	<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
	<META http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<TITLE>An unexpected error has occured</TITLE>
	<link href="<%=WebUtils.getCssFolder(/*request*/)%>/mazdaMaster.css" rel="stylesheet" type="text/css">
	<link href="<%=WebUtils.getCssFolder(/*request*/)%>/custom.css" rel="stylesheet" type="text/css">
</HEAD>

<BODY>
	<bean:define id="pageKey" toScope="request" type="java.lang.String" value="error.page"></bean:define>
	<jsp:include page="header.jsp">
		<jsp:param name="pageType" value="error" />
	</jsp:include>
<html:errors/>
<c:if test="${not empty requestScope.error}">
	<div class="warning-error">
		<div style="font-family:courier;font-size:10px;margin-left:35px">
			<span style="font-size:12px;font-weight:bold;"><c:out value="${requestScope.error.message}"/><br/></span>
			<c:forEach var="stackTraceElem" items="${requestScope.error.stackTrace}">
				<c:out value="${stackTraceElem}"/><br/>
			</c:forEach>
		</div>
	</div>
</c:if>
</BODY>
</html:html>
