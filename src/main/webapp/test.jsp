<%@page import="com.aleksm.simpleclicktracker.config.SCTConfiguration"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ page import="java.util.List" %>
<%@ page import="java.util.Set" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
    <link type="text/css" rel="stylesheet" href="/stylesheets/main.css"/>
</head>

<body>





<p>
	Props values:
	
<%
    SCTConfiguration conf = SCTConfiguration.getInstance();
	Set<Object> keys = conf.getAppSettings().keySet();
	String key;
	String value;
	for(Object k:keys){
    	key = (String)k;
    	value = conf.getAppSettings().getProperty(key);
    
%>
	<p>Key: <%=key %>, value: <%=value %></p>
<%     
	}
%>
	

		 
</p>





</body>
</html>