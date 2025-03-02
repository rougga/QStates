
<%@page import="java.sql.ResultSet"%>
<%@page import="org.w3c.dom.Element"%>
<%@page import="org.w3c.dom.Node"%>
<%@page import="org.w3c.dom.NodeList"%>
<%@page import="org.w3c.dom.Document"%>
<%@page import="ma.rougga.qstates.CfgHandler"%>
<%@page import="java.util.Objects"%>

<%
    if (Objects.equals(session.getAttribute("user"), null)) {
        //response.sendRedirect("./index.jsp");
    }
%>


