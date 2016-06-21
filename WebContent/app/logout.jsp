<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<META http-equiv="refresh" content="4;URL=http://localhost:7887/ShiroApp/app/signin.jsp">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Logout</title>
</head>
<body>
<%@ page import="org.apache.shiro.SecurityUtils" %>
<% SecurityUtils.getSubject().logout();%>
<p>You have successfully logged out. 
</body>
</html>