
<%@page import="ma.rougga.qstates.Stats"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8"/>
        <title>QStates</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="icon" type="image/png" href="./img/favicon-32x32.png">
        <script src="./js/jquery.js"></script>
        <link href="./css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
        <script src="./js/bootstrap.min.js"></script>
        <script src="./js/Chart.min.js"></script>
        <link href="./css/Chart.min.css" rel="stylesheet" type="text/css"/>
        <link href="./css/body.css" rel="stylesheet" type="text/css"/>
        <link href="./css/navbar.css" rel="stylesheet" type="text/css"/>
        <script src="./js/rememberDate.js"></script>
    </head>
    <body>
        <div class="container-lg p-0">
            <div>
                <%@include file="./addon/navbar.jsp" %>
                <script>
                    $("#home").removeClass("active");
                    $(".fj").addClass("active");
                </script>
            </div>

            <div class="w-100">
                <h1 class="text-white text-center m-4"><%= session.getAttribute("db")%></h1>
                <div class="d-flex justify-content-center  justify-content-lg-between flex-lg-row flex-column align-items-start">
                    <form class="text-white border border-white rounded mx-auto mt-2 col-12  col-lg-6 col-xl-5" style="min-height: 232px" >
                        <h4 class="text-center p-2 ">Nb. Tickets en attente: 
                            <div class='spinner-grow text-white' role='status'>
                                <span class='sr-only'>Chargement...</span>
                            </div>
                        </h4>
                        <%
                            Stats stat = new Stats();
                            List<ArrayList> table = stat.getWaitingTicketsByService(null, null,response);
                            
                            for(int i =0;i<table.size();i++){
                        %>
                        <div class="form-group d-flex justify-content-center align-items-center flex-md-row flex-column ">
                            <label for="validationDefaultUsername" class="col-12 col-md-5 text-md-right font-weight-bold "><%=  table.get(i).get(0) %>:</label>
                            <div class="input-group col-12 col-md-6 ">
                                <input type="text" class="form-control bg-dark text-white text-md-right  font-weight-bold "   disabled value="<%=  table.get(i).get(1) %>">
                            </div>
                        </div>

                        <%
                    }
                            
                        %>
                    </form>
                    <form class="text-white border border-white rounded  mx-auto mt-2 col-12 col-lg-6 col-xl-5" style="min-height: 232px" >
                        <h4 class="text-center p-2">Nb. Tickets traité:
                            <div class='spinner-grow text-white' role='status'>
                                <span class='sr-only'>Chargement...</span>
                            </div>
                        </h4>
                        <%
                             table = stat.getDealTicketsByService(null, null,response);
                            
                            for(int i =0;i<table.size();i++){
                        %>
                        <div class="form-group d-flex justify-content-center align-items-center flex-md-row flex-column ">
                            <label for="validationDefaultUsername" class="col-12 col-md-5 text-md-right font-weight-bold "><%=  table.get(i).get(0) %>:</label>
                            <div class="input-group col-12 col-md-6 ">
                                <input type="text" class="form-control bg-dark text-white text-md-right  font-weight-bold "   disabled value="<%=  table.get(i).get(1) %>">
                            </div>
                        </div>

                        <%
                    }
                            
                        %>
                    </form>
                </div>
            </div>
        </div>
        <script>
            var timer = setInterval(function () {window.location = window.location.href;}, 60000);
            $(document).ready(function () {
                updateLinks();
            });
        </script>
    </body>
</html>
