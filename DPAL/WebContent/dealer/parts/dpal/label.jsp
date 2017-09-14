<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/util.tld" prefix="dpal"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html><head><title>
View/Print Label</title></head><style>
    .small_text {font-size: 80%;}
    .large_text {font-size: 115%;}
	.error {
		background-color: #FEE;
		background-image: url('/parts/dpal/images/erroricon.gif');
		background-position: 1px 2px;
		background-repeat: no-repeat;
		padding: 2px 2px 2px 35px;
		font-family: arial;
		font-size: 12px;
		border: 1px solid #F66;
		/*margin-top: 10px;*/
		width: 500px;
	}
</style>
</head>
<c:choose>
	<c:when test="${rollbackSuccess == '1'}">
		<div class="error">
			<%--bean:define id="pageKey" toScope="request" type="java.lang.String" value="menu.shippingconfirm"></bean:define--%>
			<b>Error: </b><bean:message key="rollback.success" /> 
		</div>
	</c:when>
	<c:when test="${rollbackSuccess == '0'}">
		<div class="error">
			<%--bean:define id="pageKey" toScope="request" type="java.lang.String" value="menu.shippingconfirm"></bean:define--%>
			<b>Error: </b><bean:message key="rollback.failure" /> 
		</div>
	</c:when>	
<c:otherwise>
<body bgcolor="#FFFFFF" onload="setTimeout('window.opener.updateTrackingNumber(\'<c:out value="${trackingNumber}"/>\');window.print()', 200)">

<form>
<table border="0" cellpadding="0" cellspacing="0" width="600"><tr>
<td height="410" align="left" valign="top">
<B class="large_text">View/Print Label</B>
&nbsp;<br>
&nbsp;<br>
<ol class="small_text"> <li><b>Print the label:</b> &nbsp;
Select Print from the File menu in this browser window to print the label below.<br><br><li><b>
Fold the printed label at the dotted line.</b> &nbsp;
Place the label in a UPS Shipping Pouch. If you do not have a pouch, affix the folded label using clear plastic shipping tape over the entire label.<br><br><li><b>GETTING YOUR SHIPMENT TO UPS<br>
Customers without a Daily Pickup</b><ul><li>Take this package to any location of The UPS Store®, UPS Drop Box, UPS Customer Center, UPS Alliances (Office Depot® or Staples®) or Authorized Shipping Outlet near you or visit <a href="http://www.ups.com/content/us/en/index.jsx">www.ups.com/content/us/en/index.jsx</a> and select Drop Off.<li>
Air shipments (including Worldwide Express and Expedited) can be picked up or dropped off. To schedule a pickup, or to find a drop-off location, select the Pickup or Drop-off icon from the UPS tool bar.  </ul> <br> 
<b>Customers with a Daily Pickup</b><ul><li>
Your driver will pickup your shipment(s) as usual. </ul>
</ol></td></tr></table><table border="0" cellpadding="0" cellspacing="0" width="600">
<tr>
<td class="small_text" align="left" valign="top">
&nbsp;&nbsp;&nbsp;
<a name="foldHere">FOLD HERE</a></td>
</tr>
<tr>
<td align="left" valign="top"><hr>
</td>
</tr>
</table>

<table>
<tr>
<td height="10">&nbsp;
</td>
</tr>
</table>

<table border="0" cellpadding="0" cellspacing="0" width="651" ><tr>
<td align="left" valign="top">
<IMG SRC="awaitingconfirmation.do?aAction=shippingLabel" height="392" width="651">
</td>
</tr></table>
</form>
</body>
</c:otherwise>
</c:choose>
</html>
