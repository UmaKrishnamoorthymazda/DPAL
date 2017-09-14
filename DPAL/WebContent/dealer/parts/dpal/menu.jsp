<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ page
	import="com.mazdausa.dealer.parts.dpal.applications.util.WebUtils"%>
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

<!-- IMPORTANT: All areas that are enclosed withing comment tags such as these can be edited. DO NOT edit any are that is not defined as editable!
  Only areas between the "START" and "END" comment tags can edited.-->


<body>
	<form>
	<bean:define id="pageKey" toScope="request" type="java.lang.String" value="dpal.mainmenu"></bean:define>
	<jsp:include page="header.jsp">
		<jsp:param name="menu" value="home" />
	</jsp:include>
	
	
	<!-- Main Form Table. "FormTable" is the class that is being used for this table with the id of "FormTable".
	  Keep the id on the table so no class or span tags need to be defined within the table. DO NOT edit the table.-->
	<div align="center">
		<div id="dpal-menu-container">
			<a href="backorderlist.do" class="dpal-menu-item-anchor">
				<div class="dpal-menu-item" onmouseover="this.className='dpal-menu-item-hover';" onmouseout="this.className='dpal-menu-item';" style="height:50px">
					<div class="dpal-menu-item-container">
						<div class="dpal-menu-item-name"><bean:message key="menu.bolist" /></div>
						<div class="dpal-menu-item-desc"><bean:message key="menu.bolistcontent" /></div>
					</div>
				</div>
			</a>
		
			<a href="awaitingconfirmation.do" class="dpal-menu-item-anchor">
				<div class="dpal-menu-item" onmouseover="this.className='dpal-menu-item-hover';" onmouseout="this.className='dpal-menu-item';">
					<div class="dpal-menu-item-container">
						<div class="dpal-menu-item-name"><bean:message key="menu.awaitconfirm" /></div>
						<div class="dpal-menu-item-desc"><bean:message key="menu.awaitconfirmcontent" /></div>
					</div>
				</div>
			</a> 
			
			<a href="todayshippedpartlist.do" class="dpal-menu-item-anchor">
				<div class="dpal-menu-item" onmouseover="this.className='dpal-menu-item-hover';" onmouseout="this.className='dpal-menu-item';">
					<div class="dpal-menu-item-container">
						<div class="dpal-menu-item-name"><bean:message key="menu.shippedlist" /></div>
						<div class="dpal-menu-item-desc"><bean:message key="menu.shippedlistcontent" /></div>
					</div>
				</div>
			</a>
		
			<a href="dpaladmin.do" class="dpal-menu-item-anchor">
				<div class="dpal-menu-item" onmouseover="this.className='dpal-menu-item-hover';" onmouseout="this.className='dpal-menu-item';">
					<div class="dpal-menu-item-container">
						<div class="dpal-menu-item-name formtext"><bean:message key="menu.admin" /></div>
						<div class="dpal-menu-item-desc"><bean:message key="menu.admincontent" /></div>
					</div>
				</div>
			</a>
		</div>
	</div>
	</form>
</body>
</html>

