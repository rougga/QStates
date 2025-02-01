<%@page import="ma.rougga.qstates.controller.TitleController"%>
<%@page import="ma.rougga.qstates.modal.Title"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
        <%
            List<Title> t = new TitleController().getAllTitles();
            for (Title tt : t) {
        %>
        <span><%= tt.getName() %></span>
        <%
        }
        %>
    </body>
</html>
