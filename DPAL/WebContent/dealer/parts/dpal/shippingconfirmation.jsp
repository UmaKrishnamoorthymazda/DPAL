<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/util.tld" prefix="dpal"%>

<%@ page import="com.mazdausa.dealer.parts.dpal.applications.util.WebUtils"%>
<%@ page import="com.mazdausa.dealer.parts.dpal.applications.action.AwaitingConfirmationAction"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
	<title>Dealer Parts Availability Locator</title>
	<link href="<%=WebUtils.getCssFolder(/*request*/)%>/mazdaMaster.css" rel="stylesheet" type="text/css">
	<link href="<%=WebUtils.getCssFolder(/*request*/)%>/custom.css" rel="stylesheet" type="text/css">
	<script language="JavaScript" src="<%=WebUtils.getJsFolder(/*request*/)%>/scripts.js" type="text/javascript"></script>
	<script language="JavaScript" src="<%=WebUtils.getJsFolder(/*request*/)%>/custom.js" type="text/javascript"></script>
	<script>
		function focusOnShipQuantity() {
			var shipTextBox = document.forms[0].elements["shipQuantity"];
			if(shipTextBox) {
				if(shipTextBox.length) {
					shipTextBox[0].focus();
				} else {
					shipTextBox.focus();
				}
			}
		}
	</script>
</head>
<body
	<c:choose> 
		<c:when test="${backOrderForm.stage == '2'}"> 
			onLoad="submitFormInNewWindow();" 
		</c:when> 
		<c:otherwise>
			onLoad="focusOnShipQuantity();"
		</c:otherwise>
	</c:choose>
	OnKeyPress="return disableEnterKey(event)">

	<c:if test="${backOrderForm.backOrderDTO != null && not empty backOrderForm.backOrderDTO.backOrderDataBeanList}">
		<script>
			window.onbeforeunload = function(){ return 'If you navigate away from this page you might lose the unprocessed data. If the data has already been processed, press the "Process More Shipments" button to successfully complete the shipment process.';}
		</script>
	</c:if>

	<bean:define id="pageKey" toScope="request" type="java.lang.String" value="menu.shippingconfirm"></bean:define>
	<jsp:include page="header.jsp">
		<%--jsp:param name="shippingconfirmation" value="shippingconfirmation" /--%>
	</jsp:include>
	
	<form method="post">
	<c:if test="${backOrderForm.showUpdateStatusShip}">
		<div id="status-container">
		<c:choose>
			<c:when test="${backOrderForm.updateStatus}">
				<div id="status" class="success">The information was updated successfully</div>
			</c:when>
			<c:otherwise>
				<div id="status" class="failure">The information was not updated successfully. Please try again later.</div>
			</c:otherwise>
		</c:choose>
	</div>
	</c:if>
		<table border="1" cellpadding="0" cellspacing="0" id="FormTable1" align="center">
			<tr class="column-header">
				<th rowspan=2 width=4%><span><bean:message key="table.bodate" /></span></th>
				<th colspan=6><bean:message key="table.partinfo" /></th>
			</tr>
			<tr class="column-header">
				<th width=5%><span><bean:message key="table.number" /></span></th>
				<th width=8%><bean:message key="table.description" /></th>
				<th width=3%><bean:message key="table.boquan" /></th>
				<th width=2%><bean:message key="table.inventory" /></th>
				<th width=4%><bean:message key="table.shipquan" /></th>
				<th width=4%><bean:message key="table.shipdate" /></th>
			</tr>
			<c:choose>
				<c:when test="${backOrderForm.backOrderDTO != null && not empty backOrderForm.backOrderDTO.backOrderDataBeanList}">
					<logic:iterate name="backOrderForm" property="backOrderDTO.backOrderDataBeanList" id="data">
						<c:set var="idCheck" value="${data.salesOrder}|${data.lineNumber}"></c:set>
						<c:set var="buyingDealerCode" value="${buyingDealer.code}" scope="request"></c:set>
						<c:set var="className" value=""/>
						<c:set var="disabled" value=""/>
						<c:set var="buttonDisabled" value=""/>
						<c:set var="buttonProcessShipmentDisabled" value="disabled"/>
						<c:set var="textReadOnly" value=""/>
						<%--
						idCheck = <c:out value="${idCheck}"/><br/>
						contains: <c:out value="${dpal:contains(backOrderForm.errorIdArrayList, idCheck)}"/><br/>
						--%>
						<c:choose>
							<c:when test="${backOrderForm.stage == '1'}">
								<c:set var="className" value="row-valid"/>
								<c:choose>
									<c:when test="${dpal:contains(backOrderForm.errorIdArrayList, idCheck)}">
										<%--
										1. <c:out value="${dpal:compareMapValueForKey(backOrderForm.errorType, idCheck, 'greater')}"/><br/>
										2. <c:out value="${dpal:compareMapValueForKey(backOrderForm.errorType, idCheck, 'zero')}"/><br/>
										3. <c:out value="${dpal:compareMapValueForKey(backOrderForm.errorType, idCheck, 'skip')}"/><br/>
										--%>
										<c:if test="${dpal:compareMapValueForKey(backOrderForm.errorType, idCheck, 'greater')}">
											<c:set var="className" value="row-greater"/>
										</c:if>
										<c:if test="${dpal:compareMapValueForKey(backOrderForm.errorType, idCheck, 'zero')}">
											<c:set var="className" value="row-zero"/>
											<c:set var="textReadOnly" value="readonly"/>
												<c:set var="buttonDisabled" value="disabled"/>
									<c:set var="buttonProcessShipmentDisabled" value="enabled"/>
										</c:if>
										<c:if test="${dpal:compareMapValueForKey(backOrderForm.errorType, idCheck, 'skip')}">
											<c:set var="className" value="row-skip"/>
											<c:set var="textReadOnly" value="readonly"/>
											<c:set var="buttonDisabled" value="disabled"/>
											<c:set var="buttonProcessShipmentDisabled" value="enabled"/>
										</c:if>
										<%--c:choose>
											<c:when test="${dpal:compareMapValueForKey(backOrderForm.errorType, idCheck, 'greater')}">
												<c:set var="className" value="row-greater"/>
											</c:when>
											<c:otherwise>
												<c:choose>
													<c:when test="${dpal:compareMapValueForKey(backOrderForm.errorType, idCheck, 'zero')}">
														<c:set var="className" value="row-zero"/>
														<c:set var="disabled" value="disabled"/>
													</c:when>
													<c:otherwise>
														<c:choose>
															<c:when test="${dpal:compareMapValueForKey(backOrderForm.errorType, idCheck, 'skip')}">
																<c:set var="className" value="row-skip"/>
																<c:set var="textReadOnly" value="readonly"/>
															</c:when>
															<c:otherwise>
																<c:set var="className" value="row-valid"/>
															</c:otherwise>
														</c:choose>														
													</c:otherwise>
												</c:choose>
												
											</c:otherwise>
										</c:choose--%>
									</c:when>
								</c:choose>
							</c:when>
							<c:otherwise>
							<c:choose>
								<c:when test="${dpal:compareMapValueForKey(backOrderForm.errorType, idCheck, 'skip')}">
											<c:set var="className" value="row-skip"/>
											<c:set var="textReadOnly" value="readonly"/>
												<c:set var="buttonDisabled" value="disabled"/>
									<c:set var="buttonProcessShipmentDisabled" value="enabled"/>
										</c:when>
								<c:when test="${dpal:compareMapValueForKey(backOrderForm.errorType, idCheck, 'include')}">
									<c:set var="className" value="row-valid"/>
									<c:set var="buttonDisabled" value="disabled"/>
									<c:set var="buttonProcessShipmentDisabled" value="enabled"/>
									<c:set var="textReadOnly" value="readonly"/>
								</c:when>
								<c:when test="${dpal:compareMapValueForKey(backOrderForm.errorType, idCheck, 'zero')}">
											<c:set var="className" value="row-zero"/>
											<c:set var="textReadOnly" value="readonly"/>
												<c:set var="buttonDisabled" value="disabled"/>
									<c:set var="buttonProcessShipmentDisabled" value="enabled"/>
										</c:when>
								</c:choose>
							</c:otherwise>
						</c:choose>
						
						<tr class="table-font <c:out value="${className}"/>" >
							<td>
								<input type="hidden" name="id" value="<bean:write name="data" property="salesOrder"/>|<bean:write name="data" property="lineNumber"/>" />
								<bean:write name="data" property="backOrderDate"/>
							</td>
							<td>
								<bean:write name="data" property="parts.number"/>
								<input type="hidden" name="partNumber" value="<bean:write name="data" property="parts.number"/>" />
							</td>
							<td align="left"><span style="margin-left:2px;"><bean:write name="data" property="parts.description"/></span></td>
							<td>
								<bean:write name="data" property="parts.orderedQuantity"/>
								<input type="hidden" name="boQuantity" 
									<c:choose>
										<c:when test="${dpal:compareMapValueForKey(backOrderForm.errorType, idCheck, 'zero')}">
											value="0"
										</c:when>
										<c:otherwise>
											value="<bean:write name="data" property="parts.orderedQuantity"/>"
										</c:otherwise>
									</c:choose>
								/>
							</td>
							<td>
								<bean:write name="data" property="parts.myInventory"/>
								<input type="hidden" name="inventory" value="<bean:write name="data" property="parts.myInventory"/>" />
							</td>
							<td align="center">
								<input 
									type="text" 
									size="1" name="shipQuantity"
									<c:choose>
										<c:when test="${not empty backOrderForm.idQuantityMap}">
										value="<c:out value="${dpal:getMapValueForKey(backOrderForm.idQuantityMap, idCheck)}"/>"
										</c:when>
									</c:choose>
										<c:if test="${dpal:compareMapValueForKey(backOrderForm.errorType, idCheck, 'skip')}">
											style="background-color: #DDD; border: 1px solid BLACK;"
										</c:if>
									<c:out value="${disabled}"/>
									<c:out value="${textReadOnly}"/>
								>
							</td>
							<td><c:out value="${dpal:getTodaysDate()}"/></td>
						</tr>
					</logic:iterate>
		</table>				
					<BR>
					<table width="100%" align="center" cellpadding="0" cellspacing="0">
						<tr>
							<td valign="top" align="left">
								<table style="border:1px solid #AAA;width:350px;" border="1" cellpadding="1" cellspacing="0">
									<THEAD>
										<TR class="column-header"><td colspan="2" align="center">Ship To Dealer</td></TR>
									</THEAD>
									<TBODY>
										<tr class="table-font">
											<td class="table-head-right"><bean:message key="table.code" /></td>
											<td align="left">
												<bean:write name="backOrderForm" property="dealerDetailDTO.dealerDataBean.code" />
												<input type="hidden" name="buyingDealerCode" value="<bean:write name="backOrderForm" property="dealerDetailDTO.dealerDataBean.code"/>" />
											</td>
										</tr>
										<tr class="table-font">
											<td class="table-head-right"><bean:message key="table.name" /></td>
											<td align="left"><bean:write name="backOrderForm" property="dealerDetailDTO.dealerDataBean.name" /></td>
										</tr>
										<tr class="table-font">
											<td class="table-head-right"><bean:message key="shipping.address" /></td>
											<td class="table-content-left"><bean:write name="backOrderForm" property="dealerDetailDTO.dealerDataBean.addressLines"/></td>
										</tr>
										<tr class="table-font">
											<td class="table-head-right"><bean:message key="table.city" />,<bean:message key="table.state" />,<bean:message key="table.zip" /></td>
											<td class="table-content-left">
												<bean:write name="backOrderForm" property="dealerDetailDTO.dealerDataBean.city"/>, <bean:write name="backOrderForm" property="dealerDetailDTO.dealerDataBean.state"/>, <bean:write name="backOrderForm" property="dealerDetailDTO.dealerDataBean.zip"/>
											</td>
										</tr>
										<tr class="table-font">
											<td class="table-head-right"><bean:message key="admin.contactname" /></td>
											<td class="table-content-left"><bean:write name="backOrderForm" property="dealerDetailDTO.dealerDataBean.contactName"/></td>
										</tr>
										<tr class="table-font">
											<td class="table-head-right"><bean:message key="admin.contactnumber" /></td>
											<td class="table-content-left"><bean:write name="backOrderForm" property="dealerDetailDTO.dealerDataBean.contactNumber"/></td>
										</tr>
									</TBODY>
								</table>
							</td>
							<td valign="top" align="right">
								<table style="border:1px solid #AAA;width:385px;" border="1" cellpadding="1" cellspacing="0">
									<THEAD>
										<TR class="column-header"><td colspan="2" align="center">Shipment Details</td></TR>
									</THEAD>
									<TBODY>
										<tr class="table-font">
											<TD class="table-head-right"><bean:message key="shipping.carrier" /></TD>
											<TD class="table-content-left">UPS</TD>
										</tr>
										<tr class="table-font">
											<TD class="table-head-right"><bean:message key="shipping.trackingnum" /></TD>
											<TD class="table-content-left"><span id="trackingNo">[will appear here]</span></TD>
										</tr>
									</TBODY>
								</table>
							</td>
						</tr>
					</table>
					
					<c:if test="${not empty backOrderForm.stage}">
						<div align="right" style="margin-top:30px;margin-right:10px;">
							<table style="border:1px solid #CCC">
								<tr><td align="center" style="font-family:arial;font-size:14px;font-weight:bold;background-color:#EEE" colspan="2">Legend</td></tr>
								<tr>
									<td style="height:20px;width:20px;float:left;border:1px solid #AFA" class="row-valid"></td>
									<td style="font-family:arial;font-size:11px;text-align:left;">Record validation successful</td>
								</tr>
								<tr>
									<td style="height:20px;width:20px;float:left;border:1px solid #FBB" class="row-greater"></td>
									<td style="font-family:arial;font-size:11px;text-align:left;">BackOrder Quantity has changed. Re-enter appropriate value.</td>
								</tr>
								<tr>
									<td style="height:20px;width:20px;float:left;border:1px solid #AAA" class="row-zero"></td>
									<td style="font-family:arial;font-size:11px;text-align:left;">BackOrder Quantity no longer valid. Record excluded</td>
								</tr>
								<tr>
									<td style="height:20px;width:20px;float:left;border:1px solid #AAA" class="row-skip"></td>
									<td style="font-family:arial;font-size:11px;text-align:left;">Record skipped and returned to queue</td>
								</tr>
							</table>
						</div>
					</c:if>
					
					<br/>
					<br/>
					<div align="center">
						<input type="button" value="<bean:message key="button.confirm" />" 
							<c:if test="${backOrderForm.disableButtonConfirmAndCancel}">disabled</c:if>
							<%--c:out value="${buttonDisabled}"/--%> 
							onclick="submitShippingConfirmationForm()" > &nbsp;&nbsp;
						<input type="button" value="<bean:message key="button.processmoreshipment"/>" 
							<c:if test="${backOrderForm.disableButtonProcessMoreShipment}">disabled</c:if>
							<%-- c:out value="${buttonProcessShipmentDisabled}"/--%> 
							onclick="processMoreShipment()">&nbsp;&nbsp;&nbsp;
						<input type="button" value="<bean:message key="button.cancelshipment" />" 
							<c:if test="${backOrderForm.disableButtonConfirmAndCancel}">disabled</c:if>
							<%-- c:out value="${buttonDisabled}"/ --%>
							onclick="cancelShipment()">
					</div>		
					<input type="hidden" name="aAction"/>
				</c:when>
				<c:otherwise>
					<tr><td align="center" valign="middle" colspan="12" height="70">There are no more BackOrders pending shipment confirmation. To ship more orders, please view the <a href="backorderlist.do" style="font-family:arial;font-size:14px;">Current BackOrder List</a> or the <a href="awaitingconfirmation.do" style="font-family:arial;font-size:14px;">Committed Orders Awaiting Shipment</a> pages.</td></tr>
				</c:otherwise>
			</c:choose>
	</form>
		<c:choose>
				<c:when test="${rollbackSuccess == '1'}">
				<div class="error">
				&nbsp;&nbsp;&nbsp;<b>Error:</b><bean:message key="rollback.error.success" />
				</div>
				</c:when>
				<c:when test="${rollbackSuccess == '0'}">
				<div class="error">
				&nbsp;&nbsp;&nbsp;<b>Error:</b><bean:message key="rollback.error.failure" /> 
				</div>
				</c:when>
		</c:choose>
</body>
</html>