<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="/WEB-INF/util.tld" prefix="dpal"%>

<%@ page import="com.mazdausa.dealer.parts.dpal.applications.util.WebUtils"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
	<title>Dealer Parts Availability Locator</title>
	<link href="<%=WebUtils.getCssFolder(/*request*/)%>/mazdaMaster.css" rel="stylesheet" type="text/css">
	<link href="<%=WebUtils.getCssFolder(/*request*/)%>/custom.css" rel="stylesheet" type="text/css">
	<script language="JavaScript" src="<%=WebUtils.getJsFolder(/*request*/)%>/scripts.js" type="text/javascript"></script>
	<script language="JavaScript" src="<%=WebUtils.getJsFolder(/*request*/)%>/custom.js" type="text/javascript"></script>
</head>

<body>
	<form method="post" name="form1">
	<bean:define id="pageKey" toScope="request" type="java.lang.String" value="menu.admin"></bean:define>
	<jsp:include page="header.jsp">
		<jsp:param name="admin" value="admin" />
	</jsp:include>

	<c:if test="${dealerDetailForm.showUpdateStatus}">
	<div id="status-container">
		<c:choose>
			<c:when test="${dealerDetailForm.updateStatus}">
				<div id="status" class="success">The information was updated successfully</div>
			</c:when>
			<c:otherwise>
				<div id="status" class="failure">The information was not updated successfully. Please try again later.</div>
			</c:otherwise>
		</c:choose>
	</div>
	</c:if>

	<table id="admin-table" width="100%" border="0" cellpadding="0" cellspacing="0" align="center">
		<bean:define name="dealerDetailForm" property="dealerDetailDTO.dealerDataBean" id="data"/>
		<tr>
			<td align="left" valign="middle" style="background-color:#EEE;height:25px;"><bean:message key="label.adminselect" />
			</td>
		</tr>
		<tr height="140">
			<td>
				<div style="height:110px;">
					<table id="email-content1" border="0" cellpadding="0" cellspacing="0">
						<c:set var="invalidEmail" value=""/>
						<c:forEach var="shippingDealer" items="${data.shippingDealer}" varStatus="counter">
							<c:if test="${not empty shippingDealer}">
								<c:set var="emailId" value="${dpal:getEmailByWSLId(shippingDealer)}"/>
								<%--c:set var="emailStyle" value=""/--%>
								<c:choose>
									<c:when test="${dpal:isValidEmail(emailId)}">
										<tr><td>
											<input type="text" name="emailId" value="<c:out value="${emailId}"/>" readonly/>
											<input type="hidden" name="shippingDealerWslId" value="<c:out value="${shippingDealer}"/>"/>
											<c:if test="${requestScope.isDealer}">
											<img src="images/minus-icon.gif" height="10" width="10" onclick="deleteRow(this.parentNode.parentNode)" title="Remove this email id" style="cursor:pointer"/>
											</c:if>
										</td></tr>
									</c:when>
									<c:otherwise>
										<c:choose>
											<c:when test="${empty invalidEmail}">
												<c:set var="invalidEmail" value="${shippingDealer}"/>
											</c:when>
											<c:otherwise>
												<c:set var="invalidEmail" value="${invalidEmail}, ${shippingDealer}"/>
											</c:otherwise>
										</c:choose>
										<%--c:set var="invalidEmail" value="${invalidEmail}${error-email}"/--%>
									</c:otherwise>
								</c:choose>
							</c:if>
						</c:forEach>						
					</table>
				</div>
				<c:if test="${requestScope.isDealer}">
					<a href="javascript:void(0)" onclick="javascript:popupDealerPage('email-content1')" alt="Opens a page with a list of email ids to choose from">
						<bean:message key="admin.click" />
					</a>
					<c:if test="${not empty invalidEmail}">
						<br/>
							<div class="error-email">Logon Id(s) <b>[<c:out value="${invalidEmail}"/>]</b> have invalid email addresse(s) in the system. These email id(s) will be removed.</div>
					</c:if>
				</c:if>
			</td>
		</tr>
	
		<tr>
			<td align="left" valign="middle" style="background-color:#EEE;height:25px;"><bean:message key="label.adminuser"/></td>
		</tr>
		<tr height="140">
			<td>
				<div style="height:110px;">
					<table id="email-content2" border="0" cellpadding="0" cellspacing="0">
						<c:set var="invalidEmail" value=""/>
						<c:forEach var="buyingDealer" items="${data.buyingDealer}" varStatus="counter">
							<c:if test="${not empty buyingDealer}">
								<c:set var="emailId" value="${dpal:getEmailByWSLId(buyingDealer)}"/>
								<%--c:set var="emailStyle" value=""/--%>
								<c:choose>
									<c:when test="${dpal:isValidEmail(emailId)}">
										<tr><td>
											<input type="text" name="emailId" value="<c:out value="${emailId}"/>" readonly/>
											<input type="hidden" name="buyingDealerWslId" value="<c:out value="${buyingDealer}"/>"/>
											<c:if test="${requestScope.isDealer}">
											<img src="images/minus-icon.gif" height="10" width="10" onclick="deleteRow(this.parentNode.parentNode)" title="Remove this email id" style="cursor:pointer"/>
											</c:if>
										</td></tr>
									</c:when>
									<c:otherwise>
										<c:choose>
											<c:when test="${empty invalidEmail}">
												<c:set var="invalidEmail" value="${buyingDealer}"/>
											</c:when>
											<c:otherwise>
												<c:set var="invalidEmail" value="${invalidEmail}, ${buyingDealer}"/>
											</c:otherwise>
										</c:choose>
									</c:otherwise>
								</c:choose>
							</c:if>
						</c:forEach>
					</table>
				</div>
				<c:if test="${requestScope.isDealer}">
					<a href="javascript:void(0)" onclick="javascript: popupDealerPage('email-content2')" alt="Opens a page with a list of email ids to choose from"><bean:message key="admin.click" /></a>
					<c:if test="${not empty invalidEmail}">
						<br/>
							<div class="error-email">Logon Id(s) <b>[<c:out value="${invalidEmail}"/>]</b> have invalid email addresse(s) in the system. These email id(s) will be removed.</div>
					</c:if>
				</c:if>
			</td>
		</tr>
	
		<tr>
			<td align="left" valign="middle" style="background-color:#EEE;height:25px;"><bean:message key="admin.head"/></td>
			
		</tr>
		<tr>
			<td>
				<table>
					<tr>
						<td valign="middle" align="right"><bean:message key="admin.contactname" />:</td>
						<td><input type="text" name="contactName" style="width: 280px;" value="<c:out value="${data.contactName}"/>"
							<c:if test="${!requestScope.isDealer}">disabled</c:if>/></td>
					</tr>
					<tr>
						<td valign="center" align="right"><bean:message key="admin.contactnumber" />:</td>
						<td><input type="text" name="contactNumber" style="width: 280px;" value="<c:out value="${data.contactNumber}"/>"
							<c:if test="${!requestScope.isDealer}">disabled</c:if>/></td>
					</tr>
				</table>
			</td>
		</tr>
		<c:if test="${requestScope.isDealer}">
		<tr>
			<td align="center">
				<input type="button" value="<bean:message key="button.save" />" onclick = "submitcontact()" >
				<input type="button" value="Reset" onclick="resetAdminPage()" >
			</td>
		</tr>
		</c:if>
	
		<!--tr>
			<td colspan="2" align="center"></td>
		</tr-->
	</table>
	<br>
	<br>
	<div style="" align="left"><a href="${dpal:getLinkURL('admin.url.tnc')}" target="_blank"><bean:message key="admin.tnc" /></a>&nbsp;&nbsp;<a href="${dpal:getLinkURL('admin.url.faq')}" target="_blank"><bean:message key="admin.faq" /></a></div>
	<c:if test="${requestScope.isDealer}">
	<input type="hidden" name="aAction">
	</c:if>
	</form>
</body>
</html>

