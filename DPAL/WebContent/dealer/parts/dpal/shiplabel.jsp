<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ page import="com.mazdausa.dealer.parts.dpal.applications.util.WebUtils"%>


<html>
<head>
<title>View/Print Label</title>
<script language="JavaScript" src="<%=WebUtils.getJsFolder(/*request*/)%>/custom.js" type="text/javascript"></script>
<style>
    .small_text {font-size: 80%;}
    .large_text {font-size: 115%;}
</style>
</head>

<body bgcolor="#FFFFFF">
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

<table border="0" cellpadding="0" cellspacing="0" width="650" ><tr>
<td align="left" valign="top">
<IMG SRC="<embed src="http://localhost:21001/parts/dpal/label.do" content="image/gif">" height="392" width="651">
</td>
</tr></table>
</form>
</body>
</html>
