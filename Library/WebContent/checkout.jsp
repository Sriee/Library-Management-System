<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="f"  uri="http://java.sun.com/jsf/core"%>
<%@ taglib prefix="h"  uri="http://java.sun.com/jsf/html"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Check Out</title>
</head>
<body>
<f:view>
<h:form>
	<h5><font color="yellow">Please enter the check out details</font></h5>
	<h5><font color = "red">(*All values are compulsory)</font></h5>
<h:panelGrid columns="2" border="1"  styleClass="panelGridCenter">
<h:outputLabel value="*Book ID: "></h:outputLabel>
<h:inputText value="#{checkOutMB.bookID}"></h:inputText>
<h:outputLabel value="*Branch ID: "></h:outputLabel>
<h:inputText value="#{checkOutMB.branchID}"></h:inputText>
<h:outputLabel value="*Card No: " ></h:outputLabel>
<h:inputText value="#{checkOutMB.cardNo}"></h:inputText>
<h:commandButton value="Submit" action="#{checkOutMB.checkOut}"></h:commandButton>
<h:commandButton value="Goto Home Page" action="#{checkOutMB.goBack}"></h:commandButton>
</h:panelGrid>
</h:form>
<h:outputText value="#{checkOutMB.message}" style="color: Red; " styleClass="panelGridCenter"></h:outputText>

</f:view>
</body>
</html>