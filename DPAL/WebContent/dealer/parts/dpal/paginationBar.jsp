<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:if test="${totalPages != '0'}">
<table border="0" cellspacing="0" cellpadding="0" id="Pagination">
	<tr>
		<td height="3%"><img src="images/pagination-left.gif" width="16" height="30"/></td>
		<td height="3%" background="images/pagination-back.gif">
			<c:choose>
				<c:when test="${pageNumber=='1'}">
					<span class="pagination-disabled">&lt;&lt;</span>
					&nbsp;&nbsp;
					<span class="pagination-disabled">&lt;</span>
				</c:when>
				<c:otherwise>
					<a href="javascript:showPage('first');">&lt;&lt;</a>
					&nbsp;&nbsp;
					<a href="javascript:showPage('previous');">&lt;</a>
				</c:otherwise>
			</c:choose>
			&nbsp;
			Page <bean:write name="pageNumber"/> out of <bean:write name="totalPages"/>
			&nbsp;
			<c:choose>
				<c:when test="${pageNumber == totalPages}">
					<span class="pagination-disabled">&gt;</span>
					&nbsp; &nbsp;
					<span class="pagination-disabled">&gt;&gt;</span>
				</c:when>
				<c:otherwise>
					<a href="javascript:showPage('next');">&gt;</a>
					&nbsp; &nbsp;
					<a href="javascript:showPage('last');">&gt;&gt;</a>
				</c:otherwise>
			</c:choose>
		</td>
		<td height="30"><img src="images/pagination-right.gif" width="16" height="30"></td>
	</tr>
</table>
<input type="hidden" name="idList" value="<bean:write name="idList"/>"/>
<input type="hidden" name="errorIdList" value="<c:out value="${backOrderForm.errorIdList}"/>"/>
<%-- Added to fix Bug id: 11 --%>
<input type="hidden" name="pageAction" />
</c:if>