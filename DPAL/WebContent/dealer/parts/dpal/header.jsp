<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/util.tld" prefix="dpal"%>

<%@ page import="com.mazdausa.dealer.parts.dpal.applications.util.WebUtils"%>
<%--table width=100% border="0" align="center" cellpadding="0" cellspacing="0" class="bottom">
	<tr>
		<td class="mazda-logo">&nbsp;</td>
		<td width=60% valign="center" id="MainTitle" class="font1"><bean:message key="header.main" /></td>
		<td width=30% align="right" class="font2" id="MainTitle1">
			<c:choose>
				<c:when test="${param.menu != 'home'}">
					<a href="<%=WebUtils.getAppUrl(request, "home.do")%>"><bean:message	key="header.home" /></a> 
				</c:when>
			</c:choose>
			<a href="#"><bean:message key="header.help" /></a>
			<a href="#" onClick="window.print()"><bean:message key="header.print" /></a>
			<a href="javascript:window.close()"><bean:message key="header.close" /></a>
		</td>
	</tr>
</table--%>
<div class="mazda-logo" style="position:absolute;top:5px;left:4px;"></div>
<div align="center" class="bottom font1" id="MainTitle" style="height:30px;"><bean:message key="header.main" /></div>
<div style="position:absolute;top:17px;right:9px;">
	<c:choose>
		<c:when test="${param.menu != 'home'}">
			<a href="home.do?aAction=menu"><bean:message	key="header.home" /></a> 
		</c:when>
	</c:choose>
	<a href="${dpal:getLinkURL('pages.url.help')}" target="_blank"><bean:message key="header.help" /></a>
	<a href="#" onClick="window.print()"><bean:message key="header.print" /></a>
	<a href="javascript:top.window.close()"><bean:message key="header.close" /></a>
</div>

<center>
<c:if test="${param.pageType != 'error'}">
	<jsp:include page="dealerDropDown.jsp"/>
</c:if>
<div style="margin-top:6px;width:100%;font-family:arial;font-weight:bold;font-size:18px;margin-bottom:15px;text-align:center"><bean:message key="${pageKey}" />
</div>
</center>
<BR>
