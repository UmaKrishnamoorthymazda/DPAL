<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="/WEB-INF/util.tld" prefix="interra"%>

<%@ page import="com.mazdausa.dealer.parts.dpal.applications.util.WebUtils"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="<%=WebUtils.getCssFolder(/*request*/)%>/mazdaMaster.css" rel="stylesheet" type="text/css">
<link href="<%=WebUtils.getCssFolder(/*request*/)%>/custom.css" rel="stylesheet" type="text/css">
<script language="JavaScript" src="<%=WebUtils.getJsFolder(/*request*/)%>/scripts.js" type="text/javascript"></script>
<script language="JavaScript" src="<%=WebUtils.getJsFolder(/*request*/)%>/custom.js" type="text/javascript"></script>
<style>
#FormTable tr:hover {
	background-color: #EEE;
	cursor: pointer;
}
</style>
<script>
	<%-- Modified to fix Bug id: 36 --%>
	function validateEmail(wslId, email) {
		var addRow = false;
		var emailRegex = /^[a-zA-Z0-9]+[\._-]?[a-zA-Z0-9]+@[a-zA-Z0-9]+[\._-]?[a-zA-Z0-9]+\.[a-zA-Z]{2,5}$/;
		
		if(!emailRegex.test(email)) {
			alert("That email id does not appear to be valid. Please select a different user.");
			return;
		}
		if(window.opener.isDuplicateEntry(wslId)) {
			alert("The id you've selected has already been added to your list. Please select a different id.");
			return;
		} 

		window.opener.addNewRow(wslId, email);
	}
</script>
</head>
<body>
<form >
<table width=100% border="2" cellpadding="0" cellspacing="0"
	id="FormTable">
		<tr class="column-header font2">
		<th>User Id</th>
		<th>Name</th>
		<th>E-mail Address</th>
		<th>Job Code/Description</th>
	</tr>

	<c:forEach var="dealerPerson" items="${dealerDetailForm.dealerListDTO.ldapPersonList}" varStatus="counter">
		<tr class="table-font" onclick="validateEmail('<c:out value="${dealerPerson.userid}"/>', '<c:out value="${dealerPerson.email}"/>')">
			<td><c:out value="${dealerPerson.userid}"/></td>
			<td><c:out value="${dealerPerson.lastName}"/> <c:out value="${dealerPerson.initialNm}"/>.</td>
			<td><c:out value="${dealerPerson.email}"/></td>
			<td><c:out value="${dealerPerson.jobCtgryCd}"/></td>
		</tr>
	</c:forEach>
</table>
</form>
</body>
</html>