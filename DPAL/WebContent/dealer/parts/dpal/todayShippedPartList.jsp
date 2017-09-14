<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="/WEB-INF/util.tld" prefix="interra"%>
<%@ page import="com.mazdausa.dealer.parts.dpal.applications.form.ProcessedBackOrderForm" %>

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
<bean:define id="pageKey" toScope="request" type="java.lang.String" value="menu.shippedlist"></bean:define>
<jsp:include page="header.jsp">
	<jsp:param name="todayshippedpart" value="todayshippedpart" />
</jsp:include>

<!-- Main Form Table. "FormTable" is the class that is being used for this table with the id of "FormTable".
Keep the id on the table so no class or span tags need to be defined within the table. DO NOT edit the table.-->
<table width=100% border="1" cellpadding="0" cellspacing="0" id="FormTable1">
	<thead>
	<tr class="column-header">
		<th rowspan=2 class="date" valign="center">
							<c:set var="jsFunction" value="sortColumn('OD')"></c:set>
							<c:set var="className" value="${interra:getDSortIndicatorImage()}"/>
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
		<th colspan="3"><bean:message key="table.partinfo" /></th>
		<th colspan="6"><bean:message key="table.reqdealerinfo" /></th>
		<th rowspan="2" width="8%"><bean:message key="table.tracking" /></th>
	</tr>
	<tr class="column-header">
		
		<th class="number"><c:if test="${param.sortType != 'ID' && param.sortType != 'IA'}">
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
		<th class="quantity"><bean:message key="table.shipquan" /></th>
		<th class="code"><c:if test="${param.sortType != 'BD' && param.sortType != 'BA'}">
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
	<c:if test="${not empty processedBackOrderForm.processedBackOrderDTO}">
		<logic:iterate name="processedBackOrderForm" property="processedBackOrderDTO.processedBackOrderDataBeanList" id="data">
			<tr class="table-font">
	
				<td><bean:write name="data" property="backOrderDate" /></td>
				<td><bean:write name="data" property="itemNumber" /></td>
				<td align="left"><bean:write name="data" property="itemDescription" /></td>
				<td><bean:write name="data" property="shippedQuantity" /></td>
				<td><bean:write name="data" property="buyingDealer.code" /></td>
				<td align="left"><span style="margin-left:2px;"><bean:write name="data" property="buyingDealer.name" /></span></td>
				<td class="table-content-left">&nbsp;<bean:write name="data" property="buyingDealer.city" /></td>
				<td><bean:write name="data" property="buyingDealer.state" /></td>
				<td><bean:write name="data" property="buyingDealer.zip" /></td>
				<td><a href="javascript:void(0)" rel="dealerDetailBalloon" myonclick="getDealerInfo('<bean:write name="data" property="buyingDealer.code"/>')"><bean:message key="table.click"/></a></td>			
				<td><a href="${trackingnumber}<bean:write name="data" property="trackingNumber"/>" target="_blank"><bean:write name="data" property="trackingNumber" /></a></td>
			</tr>
		</logic:iterate>
	</c:if>
					<c:if test="${totalPages == '0'}">
						<tr><td align="center" valign="middle" colspan="12" height="70">Sorry, no data was found for this page.Please go to the <a href="backorderlist.do" style="font-family:arial;font-size:14px;">Current BackOrder List</a> page to commit orders.</td></tr>
					</c:if>
				</tbody>
			</table>
			<br>
			<table width="100%">
			
			<tr width="100%">
			<td width="70%"></td>
			<td width="100%" align="right">
			<c:choose>
			<c:when test="${totalPages != '0'}">
			<jsp:include page="paginationBar.jsp"></jsp:include>
			<input type="hidden" name="totalCount" value="<bean:write name="processedBackOrderForm" property="processedBackOrderDTO.totalRecordsCount"/>"/>	
			<input type="hidden" name="pageNumber" value="<bean:write name="pageNumber"/>"/>
			</c:when>
			<c:otherwise>
				<br/><br/>
				</c:otherwise>
			</c:choose>
			</td>
			</tr>
			<input type="hidden" name="sortType" value="<bean:write name="processedBackOrderForm" property="sortType"/>"/>
		<input type="hidden" name="aAction">
		</table>
		</form>
		</body>
</html>

