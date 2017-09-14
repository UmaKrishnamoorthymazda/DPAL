<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="/WEB-INF/util.tld" prefix="dpal"%>

<%@ page import="com.mazdausa.dealer.parts.dpal.applications.form.BackOrderForm" %>

<%@ page import="com.mazdausa.dealer.parts.dpal.applications.util.WebUtils"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
		<title>Dealer Parts Availability Locator</title>
		<link href="<%=WebUtils.getCssFolder(/*request*/)%>/mazdaMaster.css" rel="stylesheet" type="text/css">
		<link href="<%=WebUtils.getCssFolder(/*request*/)%>/custom.css" rel="stylesheet" type="text/css">
		<link href="<%=WebUtils.getCssFolder(/*request*/)%>/balloontip.css" rel="stylesheet" type="text/css">
		<script language="JavaScript" src="<%=WebUtils.getJsFolder(/*request*/)%>/scripts.js" type="text/javascript"></script>
		<script language="JavaScript" src="<%=WebUtils.getJsFolder(/*request*/)%>/custom.js" type="text/javascript"></script>
		<script language="JavaScript" src="<%=WebUtils.getJsFolder(/*request*/)%>/glm-ajax.js" type="text/javascript"></script>
		<script language="JavaScript" src="<%=WebUtils.getJsFolder(/*request*/)%>/balloontip.js" type="text/javascript"></script>
	</head>
	<body>
		<div id="dealerDetailBalloon" class="balloonstyle" style="width:290px;"><img src="<%=WebUtils.getImagesFolder(/*request*/)%>/loading.gif"/></div>
		<form method="post">
		<%-- WebUtils.setRequest(request); --%>
		<bean:define id="pageKey" toScope="request" type="java.lang.String" value="menu.awaitconfirm"></bean:define>
		<jsp:include page="header.jsp">
			<jsp:param name="awaitingconfirmation" value="awaitingconfirmation" />
		</jsp:include>
		<c:if test="${backOrderForm.showUpdateStatus}">
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
			<!-- bean:write name="backOrderForm" property="idArrayList"/-->
			<table border="1" cellpadding="0" cellspacing="0" id="FormTable1">
				<thead>
					<tr class="column-header">
						<c:if test="${requestScope.isDealer}">
							<th rowspan=2 class="commit"><bean:message key="table.checktoship" /></th>
						</c:if>
						<th rowspan=2 class="date" valign="middle">
							<c:set var="jsFunction" value="sortColumn('OD')"></c:set>
							<c:set var="className" value="${dpal:getDSortIndicatorImage()}"/>
							<c:if test="${param.sortType != 'OD' && param.sortType != 'OA'}">
								<c:set var="jsFunction" value="sortColumn('OD')" />
								<c:set var="className" value="sortable-column" />
							</c:if>
							<c:if test="${empty param.sortType}">
								<c:set var="jsFunction" value="sortColumn('OA')"></c:set>
								<c:set var="className" value="sortable-column-dsc"/>
							</c:if>
							<c:if test="${param.sortType == 'OA'}">
								<c:set var="jsFunction" value="sortColumn('OD')"></c:set>
								<c:set var="className" value="sortable-column-asc"/>
							</c:if>
							<c:if test="${param.sortType == 'OD'}">
								<c:set var="jsFunction" value="sortColumn('OA')"></c:set>
								<c:set var="className" value="sortable-column-dsc"/>
							</c:if>
							<span class="<c:out value="${className}"/>" onclick="<c:out value="${jsFunction}"/>">
								<bean:message key="table.bodate" />
							</span>
						</th>
						<th colspan=4><bean:message key="table.partinfo" /></th>
						<th colspan=6><bean:message key="table.reqdealerinfo" /></th>
					</tr>
					<tr class="column-header">
						<th class="number">
							<c:if test="${param.sortType != 'ID' && param.sortType != 'IA'}">
								<c:set var="jsFunction" value="sortColumn('IA')" />
								<c:set var="className" value="sortable-column" />
							</c:if>
							<c:if test="${param.sortType == 'IA'}">
								<c:set var="jsFunction" value="sortColumn('ID')"></c:set>
								<c:set var="className" value="sortable-column-asc"/>
							</c:if>
							<c:if test="${param.sortType == 'ID'}">
								<c:set var="jsFunction" value="sortColumn('IA')"></c:set>
								<c:set var="className" value="sortable-column-dsc"/>
							</c:if>
							<span class="<c:out value="${className}"/>" onclick="<c:out value="${jsFunction}"/>">
								<bean:message key="table.number" />
							</span>
						</th>
						<th class="description"><bean:message key="table.description" /></th>
						<th class="quantity"><bean:message key="table.orderedquan" /></th>
						<th class="inventory"><bean:message key="table.inventory" /></th>
						<th class="code">
							<c:if test="${param.sortType != 'BD' && param.sortType != 'BA'}">
								<c:set var="jsFunction" value="sortColumn('BA')" />
								<c:set var="className" value="sortable-column" />
							</c:if>
							<c:if test="${param.sortType == 'BA'}">
								<c:set var="jsFunction" value="sortColumn('BD')"></c:set>
								<c:set var="className" value="sortable-column-asc"/>
							</c:if>
							<c:if test="${param.sortType == 'BD'}">
								<c:set var="jsFunction" value="sortColumn('BA')"></c:set>
								<c:set var="className" value="sortable-column-dsc"/>
							</c:if>
							<span class="<c:out value="${className}"/>" onclick="<c:out value="${jsFunction}"/>">
								<bean:message key="table.code" />
							</span>
						</th>
						<th class="name"><bean:message key="table.name" /></th>
						<th class="city"><bean:message key="table.city" /></th>
						<th class="state"><bean:message key="table.state" /></th>
						<th class="zip"><bean:message key="table.zip" /></th>
						<th class="contact"><bean:message key="table.contacts" /></th>
					</tr>
				</thead>
				<tbody>
					<%
						java.util.ArrayList list = ((BackOrderForm)request.getAttribute("backOrderForm")).getIdArrayList();
					%> 
					<c:choose>
					<c:when test="${not empty backOrderForm.backOrderDTO}">
					<logic:iterate name="backOrderForm" property="backOrderDTO.backOrderDataBeanList" id="data">
						<bean:define id="idValue1" value="${data.salesOrder}|${data.lineNumber}" type="java.lang.String"/>
						<bean:define id="styleClass" value="" type="java.lang.String" scope="request"/>
						<% 	if(list.contains(idValue1)) styleClass="table-font row-selected"; else styleClass="table-font"; %>
						<tr class="<%=styleClass %>" 
							onmouseover="highlightRow(this, true)" onmouseout="highlightRow(this, false)">
							<c:set var="idValue" value="${data.salesOrder}|${data.lineNumber}" scope="request"/>
							<c:if test="${requestScope.isDealer}">
							<td><input name="id" 
										type="checkbox" 
										value="<bean:write name="idValue"/>" 
										onclick="registerSelection(this)"
									<% if(((BackOrderForm)request.getAttribute("backOrderForm")).getIdArrayList().contains(idValue1)){%>
										checked
										<%} %>								/>
							<c:set var="buyingDealerCode" value="${buyingDealer.code} }" scope="request"></c:set></td>	</c:if>									
							<td><bean:write name="data" property="backOrderDate"/>
							<td><bean:write name="data" property="parts.number"/>
							<td align="left"><span style="margin-left:2px;"><bean:write name="data" property="parts.description"/></span></td>
							<td><bean:write name="data" property="parts.orderedQuantity"/></td>
							<td><bean:write name="data" property="parts.myInventory"/></td>
							<td><bean:write name="data" property="buyingDealer.code"/></td>
							<td align="left"><span style="margin-left:2px;"><bean:write name="data" property="buyingDealer.name"/></span></td>
							<td><bean:write name="data" property="buyingDealer.city"/></td>
							<td><bean:write name="data" property="buyingDealer.state"/></td>
							<td><bean:write name="data" property="buyingDealer.zip"/></td>
							<td><a href="javascript:void(0)" rel="dealerDetailBalloon" myonclick="getDealerInfo('<bean:write name="data" property="buyingDealer.code"/>')"><bean:message key="table.click"/></a></td>
						</tr>
					</logic:iterate>
					</c:when>
					<c:otherwise>
						<%--c:if test="${totalPages == '0'}" --%>
							<tr><td align="center" valign="middle" colspan="12" height="70">Sorry, no data was found for this page. Please go to the <a href="backorderlist.do" style="font-family:arial;font-size:14px;">Current BackOrder List</a> page to commit orders.</td></tr>
						<%--/c:if--%>
						</c:otherwise>
					</c:choose>
				</tbody>
			</table>
			<c:if test="${totalPages != '0'}">
			<span style="padding-left: 1%"><font color="red" size=2><bean:message key="message.note"/></font></span>
			<br/><br/>
			<table width="100%" border="0" align="center">
				<tr>
					<c:if test="${requestScope.isDealer}">
					<td width="300" align="left" valign="middle"><div class="select-count">Currently selected <span id="selectCount"><%=((com.mazdausa.dealer.parts.dpal.applications.form.BackOrderForm)request.getAttribute("backOrderForm")).getIdArrayList().size() %></span> out of <bean:write name="backOrderForm" property="backOrderDTO.totalRecordsCount"/> records in all.</div></td>
					<td width="200" align="right" valign="middle"><input type="button" style="width: 100px" value="<bean:message key="button.ship"/>" onclick="ship(document.forms[0], 'idList')"/><input type="hidden" name="aAction"></td>
					<td width="150" align="right"  valign="middle">
					<input type="button" name="Operation" style="width: 110px" value='<bean:message key="button.remove" />' onClick="remove(document.forms[0], 'idList')">	
					</td>
					</c:if>
					<td width="400" align="right"  valign="middle">
						<jsp:include page="paginationBar.jsp"></jsp:include>
					</td>
				</tr>
			</table>
			<input type="hidden" name="totalCount" value="<bean:write name="backOrderForm" property="backOrderDTO.totalRecordsCount"/>"/>
			<input type="hidden" name="pageNumber" value="<bean:write name="pageNumber"/>"/>
			</c:if>
			<%-- Added to fix Bug id: 7, 8, 9 --%>
			<input type="hidden" name="sortType" value="<bean:write name="backOrderForm" property="sortType"/>"/>
		</form>
	</body>
</html>