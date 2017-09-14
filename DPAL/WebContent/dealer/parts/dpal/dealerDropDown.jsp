<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/util.tld" prefix="dpal"%>

<%--@ page import="com.mazdausa.dealer.parts.dpal.applications.util.WebUtils"%>
<%@ page import="com.mazdausa.common.application.actions.UserContext"--%>

<%
	/*WebUtils.setRequest(request);*/
	/*UserContext userContext = (UserContext)request.getAttribute("userContext");*/
	/*WebUtils.setUserContext(userContext);*/
%>
<div class="font2" style="position:absolute;top:50px;left:7px;width:250px;text-align:left"><B><bean:message key="menu.dealer" />:</B>
	<c:choose>
		<c:when test="${isShowDropDown}">
			<c:choose>
				<c:when test="${empty UserInfoBean.domainDealers}">
					<c:choose>
						<c:when test="${empty UserInfoBean.dealer.code}">
							---
						</c:when>
						<c:otherwise>
			               ${UserInfoBean.dealer.code} status=${UserInfoBean.dealer.status}
			        	</c:otherwise>
					</c:choose>
				</c:when>
				<c:otherwise>
					<select name="selAllDealer" onchange="submitDealerSelect(this)">
						<option value="choose">Choose</option>
						<c:forEach items="${UserInfoBean.domainDealers}" var="aDealer" varStatus="dealerIndex">
							<option value="<c:out value="${aDealer.code}"/>" <c:if test="${requestScope.selectedDealer == aDealer.code}">selected</c:if>>
								<c:out value="${aDealer.code}" />
							</option>
						</c:forEach>
					</select>
					<input type="hidden" name="setDealer"/>
					<%--c:if test="${selectDealer}">
						<br/><div id="dealerSelectMsg">Please select a dealer from this list before proceeding</div>
					</c:if--%>
				</c:otherwise>
			</c:choose>
		</c:when>
		<c:otherwise>
			<%--= WebUtils.getDealerCode(request, userContext) --%>
			<c:out value="${sessionScope.dealerCode}"/>
		</c:otherwise>
	</c:choose>
</div>