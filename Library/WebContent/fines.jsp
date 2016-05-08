<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="f"  uri="http://java.sun.com/jsf/core"%>
<%@ taglib prefix="h"  uri="http://java.sun.com/jsf/html"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Fines</title>
</head>
<body>
<f:view>
<h3 align = "center"><font>Please enter fine details to search</font></h3>
<font color="0x660000">
<h:form>
<h:panelGrid columns="2" border="1" styleClass="panelGridCenter" >
<h:outputLabel value="Card No: "></h:outputLabel>
<h:inputText value="#{finesMB.cardNo}"></h:inputText>
<h:outputLabel value="Fname: "></h:outputLabel>
<h:inputText value="#{finesMB.fname}"></h:inputText>
<h:outputLabel value="Lname: "></h:outputLabel>
<h:inputText value="#{finesMB.lname}"></h:inputText>
<h:commandButton value="Submit" action="#{finesMB.searchFines}"></h:commandButton>
<h:commandButton value="Goto Home Page" action="#{finesMB.goBack}"></h:commandButton>
<h:commandButton value="Refresh Fines" action="#{finesMB.refreshFines}"></h:commandButton>
</h:panelGrid>
<h:outputText value="#{finesMB.message}" style="color: Red; "  styleClass="panelGridCenter"></h:outputText>
<c:if test="${finesMB.fineList!=null}">
<h:dataTable value="#{finesMB.fineList}" var="fineList" border="1"  styleClass="panelGridCenter">
		 <h:column>    				
         <f:facet name="header">
         <h:outputText value="Loan ID: "></h:outputText>
         </f:facet>
         <h:outputText value="#{fineList.loanId}"></h:outputText>    				
         </h:column>
		 <h:column>    				
         <f:facet name="header">
         <h:outputText value="Card No: "></h:outputText>
         </f:facet>    				
         <h:outputText value="#{fineList.cardNo}"></h:outputText> 
         </h:column>
         <h:column>
         <f:facet name="header">
         <h:outputText value="First Name"></h:outputText>
         </f:facet>    				
         <h:outputText value="#{fineList.fname}"></h:outputText>
         </h:column>
         <h:column>
         <f:facet name="header">
         <h:outputText value="Last Name"></h:outputText>
         </f:facet>    				
         <h:outputText value="#{fineList.lname}"></h:outputText>
         </h:column>
         <h:column>
         <f:facet name="header">
         <h:outputText value="Book ID"></h:outputText>
         </f:facet>    				
         <h:outputText value="#{fineList.bookId}"></h:outputText>
         </h:column>
         <h:column>
         <f:facet name="header">
         <h:outputText value="Branch ID"></h:outputText>
         </f:facet>    				
         <h:outputText value="#{fineList.branchId}"></h:outputText>
         </h:column>
         <h:column>
         <f:facet name="header">
         <h:outputText value="Fine Amount"></h:outputText>
         </f:facet>    				
         <h:outputText value="#{fineList.fineAmountToBePaid}"></h:outputText>
         </h:column>
    	</h:dataTable>
    	<h:outputText value = "Do you wish to pay?"></h:outputText>
    	<h:selectOneRadio required="true" value="#{finesMB.wishToPay}" valueChangeListener="#{finesMB.radioButtonCheck}" onclick="submit()">
            <f:selectItem itemValue="true" itemLabel="Yes" />
            <f:selectItem itemValue="false" itemLabel="No" />
        </h:selectOneRadio>
        <c:if test="${finesMB.wishToPay!=null and finesMB.wishToPay == true}">
        <h:panelGrid columns="2" styleClass="panelGridCenter">
        <h:outputLabel value="Loan ID: "></h:outputLabel>
        <h:inputText value="#{finesMB.loanID}"></h:inputText>
        <h:outputLabel value="Payment: "></h:outputLabel>
        <h:inputText value="#{finesMB.payment}"></h:inputText>
        <h:commandButton value="Pay Fine" action = "#{finesMB.payFine}"></h:commandButton>
        </h:panelGrid>
        </c:if>
</c:if>
</h:form>
</font>
</f:view>
</body>
</html>