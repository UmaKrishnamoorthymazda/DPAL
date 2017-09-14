<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ page import="com.mazdausa.dealer.parts.dpal.applications.util.WebUtils"%>
<html>
	<head>
		<c:if test="${requestScope.isDealer}">
			<script src="<%=WebUtils.getJsFolder(/*request*/)%>/notification.js" type="text/javascript"></script>
			<script src="<%=WebUtils.getJsFolder(/*request*/)%>/glm-ajax.js" type="text/javascript"></script>
			<script>
				//NotificationEngine.init(5000, "mainView");
			</script>
		</c:if>
	</head>
</html>