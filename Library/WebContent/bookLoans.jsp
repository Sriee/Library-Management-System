<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="f"  uri="http://java.sun.com/jsf/core"%>
<%@ taglib prefix="h"  uri="http://java.sun.com/jsf/html"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Book Loans</title>
</head>
<body>
<f:view>
<h:form>
<h:panelGrid columns="1">
<h:commandLink action ="checkIn.jsp" value="Check In" disabled="false" style='line-height: normal; color: 0x660000; font-style: normal; font-size: 16px; font-family: "Comic Sans MS", Sans-Serif'></h:commandLink>
<h:commandLink action ="checkout.jsp" value="Check Out" disabled="false" style='line-height: normal; color: 0x660000; font-style: normal; font-size: 16px; font-family: "Comic Sans MS", Sans-Serif'></h:commandLink>
</h:panelGrid>
</h:form>
</f:view>
</body>
</html>