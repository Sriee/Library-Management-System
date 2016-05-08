<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="f"  uri="http://java.sun.com/jsf/core"%>
<%@ taglib prefix="h"  uri="http://java.sun.com/jsf/html"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Check In</title>
</head>
<body>
<f:view>
<font color="Cyan">
<h:form>
<h5 align="center"><font color="yellow">Please enter the check in details</font></h5>
<h:panelGrid columns="2" border="1" styleClass="panelGridCenter" >
<h:outputLabel value="Borrower Name: "></h:outputLabel>
<h:inputText value="#{checkInMB.borrowerName}"></h:inputText>
<h:outputLabel value="Book ID: "></h:outputLabel>
<h:inputText value="#{checkInMB.bookID}"></h:inputText>
<h:outputLabel value="Card No: "></h:outputLabel>
<h:inputText value="#{checkInMB.cardNo}"></h:inputText>
<h:commandButton value="Submit" action="#{checkInMB.checkInSearch}"></h:commandButton>
<h:commandButton value = "Go to home Page" action="#{checkInMB.goBack}"></h:commandButton>
</h:panelGrid>
<h:outputText value="#{checkInMB.message}" style="color: Red; " styleClass="panelGridCenter"></h:outputText>
<h:commandLink styleClass="panelGridCenter" action ="fines.jsp" value="Fines" disabled="false" style='line-height: normal; color: Cyan; font-style: normal; font-size: 16px; font-family: "Comic Sans MS", Sans-Serif'></h:commandLink>
</h:form>
<h:form>
<c:if test="${checkInMB.loanList!=null}">
<h:outputText value="Please select the options to check in and click submit" styleClass="panelGridCenter"></h:outputText>
<h:dataTable value="#{checkInMB.loanList}" var="loanList" border="1" styleClass="panelGridCenter">
		 <h:column>    				
         <f:facet name="header">
         <h:outputText value="Loan Id"></h:outputText>
         </f:facet>
         <h:outputText value="#{loanList.loanId}"></h:outputText>    				
         </h:column>
		 <h:column>    				
         <f:facet name="header">
         <h:outputText value="Card Number"></h:outputText>
         </f:facet>    				
         <h:outputText value="#{loanList.cardNo}"></h:outputText> 
         </h:column>
         <h:column>
         <f:facet name="header">
         <h:outputText value="Book Id"></h:outputText>
         </f:facet>    				
         <h:outputText value="#{loanList.bookId}"></h:outputText>
         </h:column>
         <h:column>
         <f:facet name="header">
         <h:outputText value="Branch ID"></h:outputText>
         </f:facet>    				
         <h:outputText value="#{loanList.branchId}"></h:outputText>
         </h:column>
         <h:column>
         <f:facet name="header">
         <h:outputText value="Check"></h:outputText>
         </f:facet>
        <h:selectBooleanCheckbox value="#{checkInMB.checkStatus[loanList.loanId]}" />
    </h:column>
</h:dataTable>
<h:commandButton value= "Submit" action ="#{checkInMB.checkInCompletion}" styleClass="panelGridCenter"></h:commandButton>
</c:if>

</h:form>
</font>
</f:view>
</body>
</html>