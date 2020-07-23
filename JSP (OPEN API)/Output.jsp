<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<%@ page import="Project.INFO_FOOD" %>
<%@ page import="org.json.simple.JSONArray" %>
<%
	String food = request.getParameter("foodname");
	if(food != null){ 
		food = new String(food.getBytes("8859_1"), "UTF-8");
	}
	INFO_FOOD jf = new INFO_FOOD();
	JSONArray str = jf.get_info(food);
	out.println(str);
%>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
</body>
</html>