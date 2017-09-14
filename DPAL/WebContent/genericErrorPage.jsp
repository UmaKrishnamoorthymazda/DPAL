<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<HTML>
<HEAD>
<TITLE>Standard Error Page</TITLE>
<style type="text/css">
<!--
.note {  font-family: Arial, Helvetica, sans-serif; font-size: 10pt; color: #3399FF}
-->
</style>

</HEAD>

<BODY bgcolor="white">
<html:form action="/localloginaction"  method="post">
    <div align="center">
    <h3>
  <font color="#000066">UNEXPECTED ERROR</font></h3>
  </div> 

  <table align="center" border="0" width="229">
    <tr>
      <td align="right">
	  <font size="3" color="#000066"><html:errors/></font> <a href="http://">Click Here</a> 
      </td>
    </tr>
    	      
  </table>
  </html:form>
</BODY>

</HTML>
