<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="f"  uri="http://java.sun.com/jsf/core"%>
<%@ taglib prefix="h"  uri="http://java.sun.com/jsf/html"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Book Search</title>
</head>
<body>
<f:view>
<h3 align="center"><font color="forestgreen">Book Search</font></h3>
<font color="0x009999">
<h:form>
<h3 align="center">Please enter the book details</h3>
<h:panelGrid columns="2" styleClass="panelGridCenter">
<h:outputLabel value="Book ID"></h:outputLabel>
<h:inputText value="#{startPageMB.bookId}"></h:inputText>
<h:outputLabel value="Author Name: "></h:outputLabel>
<h:inputText value="#{startPageMB.bookAuthor}"></h:inputText>
<h:outputLabel value="Title of the Book"></h:outputLabel>
<h:inputText value="#{startPageMB.bookTitle}"></h:inputText>
<h:commandButton value="Submit search" action="#{startPageMB.bookSearch}"></h:commandButton>
<h:commandButton value = "Go to home Page" action="#{startPageMB.goBack}"></h:commandButton>
</h:panelGrid>
<h:outputText value="#{startPageMB.message}" style="color: Red; " styleClass="panelGridCenter"></h:outputText>
<c:if test="${startPageMB.bookList!=null}">
<h:dataTable value="#{startPageMB.bookList}" var="bookList" border="1" styleClass="panelGridCenter">
		 <h:column>    				
         <f:facet name="header">
         <h:outputText value="Book Name"></h:outputText>
         </f:facet>
         <h:outputText value="#{bookList.bookName}"></h:outputText>    				
         </h:column>
		 <h:column>    				
         <f:facet name="header">
         <h:outputText value="Book Id"></h:outputText>
         </f:facet>    				
         <h:outputText value="#{bookList.bookId}"></h:outputText> 
         </h:column>
         <h:column>
         <f:facet name="header">
         <h:outputText value="Author Name"></h:outputText>
         </f:facet>    				
         <h:outputText value="#{bookList.authorName}"></h:outputText>
         </h:column>
         <h:column>
         <f:facet name="header">
         <h:outputText value="Branch ID"></h:outputText>
         </f:facet>    				
         <h:outputText value="#{bookList.branchID}"></h:outputText>
         </h:column>
         <h:column>
         <f:facet name="header">
         <h:outputText value="Branch Name"></h:outputText>
         </f:facet>    				
         <h:outputText value="#{bookList.branchName}"></h:outputText>
         </h:column>
         <h:column>
         <f:facet name="header">
         <h:outputText value="No of Copies Available"></h:outputText>
         </f:facet>    				
         <h:outputText value="#{bookList.noOfCopiesAvailable}"></h:outputText>
         </h:column>
         <h:column>
         <f:facet name="header">
         <h:outputText value="No of Inventored Copies"></h:outputText>
         </f:facet>    				
         <h:outputText value="#{bookList.noOfCopiesInventored}"></h:outputText>
         </h:column>
</h:dataTable>
</c:if>
</h:form>
</font>
</f:view>
</body>
</html>