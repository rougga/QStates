<%@page import="ma.rougga.qstates.controller.AgenceController"%>
<%@page import="java.util.Map"%>
<%@page import="javax.swing.text.TabExpander"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="ma.rougga.qstates.Stats"%>
<%@page import="ma.rougga.qstates.TableGenerator"%>
<%@page import="java.util.Date"%>
<%@page import="ma.rougga.qstates.CfgHandler"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="ma.rougga.qstates.PgConnection"%>
<%@page import="java.sql.Connection"%>
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
            </div>
            <%                    
                String err = "", data , lable ;
                float waitPer = 100;
                float goalPer = 0;
                String waitCss = "bg-success";
                String goalCss = "bg-danger";
                String waitClass = "is-valid";
                String goalClass = "is-invalid";
                Stats stat= new Stats();
                long cWait = stat.getWaitingTicket(null,null);
                long cGoal = stat.getDealTicket(null, null);
                long oWait = Long.parseLong(AgenceController.getMaxAtt());
                long oGoal = Long.parseLong(AgenceController.getGoalTr());
                if(oWait!=0){
                    waitPer = (((float)cWait/oWait)*100) ;
                }
                if(waitPer>85){
                     waitCss = "bg-danger";
                     waitClass = "is-invalid";
                }else if(waitPer>60){
                    waitCss = "bg-warning";
                     waitClass = "is-valid";
                }
                if(cGoal!=0){
                    goalPer = (((float)cGoal/oGoal)*100);
                }
                if(goalPer>85){
                     waitCss = "bg-success";
                     waitClass = "is-valid";
                }else if(goalPer>60){
                    waitCss = "bg-warning";
                     waitClass = "is-invalid";
                }
                Map d = stat.getDealTicketByServiceChart(null, null, response);
                lable=d.get("lable").toString();
                data=d.get("data").toString();

            %>
            <div class="d-flex justify-content-center  justify-content-md-between flex-md-row flex-column align-items-start">
                <div class="col-12 col-md-6">
                    <div class="mt-4  mb-md-5">
                        <h3 class="text-white">Ticket en attente <span class="badge badge-pill appColor"><%= cWait + " / " + oWait%></span>:
                        <div class='spinner-grow text-white' role='status'>
                                <span class='sr-only'>Chargement...</span>
                            </div>
                        </h3>
                        <div class="progress" style="height: 40px;">
                            <div class="progress-bar <%= waitCss%> font-weight-bold progress-bar-striped progress-bar-animated " role="progressbar" style="width: <%= waitPer%>%" aria-valuenow="25" aria-valuemin="0" aria-valuemax="100"><%= cWait + " / " + oWait%></div>
                        </div>
                    </div>
                    <div class="border border-white rounded p-2">
                        <h4 class="text-white mx-1 text-center">Pourcentage de ticket traité par service:</h4>
                        <canvas id="myChart"  height="270"></canvas>
                    </div>

                    <script>
                        var ctx = document.getElementById('myChart').getContext('2d');
                        var myChart = new Chart(ctx, {
                            type: 'doughnut',
                            data: {
                                labels: <%= lable%>,
                                datasets: [{
                                        label: '# of Votes',
                                        data: <%= data %>,
                                        backgroundColor: [
                                            'rgba(255, 99, 132, 0.2)',
                                            'rgba(54, 162, 235, 0.2)',
                                            'rgba(255, 206, 86, 0.2)',
                                            'rgba(75, 192, 192, 0.2)',
                                            'rgba(153, 102, 255, 0.2)',
                                            'rgba(255, 159, 64, 0.2)'
                                        ],
                                        borderColor: [
                                            'rgba(255, 99, 132, 1)',
                                            'rgba(54, 162, 235, 1)',
                                            'rgba(255, 206, 86, 1)',
                                            'rgba(75, 192, 192, 1)',
                                            'rgba(153, 102, 255, 1)',
                                            'rgba(255, 159, 64, 1)'
                                        ],
                                        borderWidth: 1
                                    }]
                            }
                        });
                    </script>

                </div>
                <div class="col-12 col-md-6 ">

                    <div class="mt-4 mb-md-5">
                        <h3 class="text-white">Totale de ticket Traité <span class="badge badge-pill appColor"><%= cGoal + " / " + oGoal%></span>:
                            <div class='spinner-grow text-white' role='status'>
                                <span class='sr-only'>Chargement...</span>
                            </div>
                        </h3>
                        <div class="progress " style="height: 40px;">
                            <div class="progress-bar <%= goalCss%> font-weight-bold  progress-bar-striped progress-bar-animated"" role="progressbar" style="width: <%= goalPer%>%" aria-valuenow="25" aria-valuemin="0" aria-valuemax="100"> <%= cGoal + " / " + oGoal%></div>
                        </div>
                    </div>
                    <form class="text-white border border-white rounded" >

                        <h4 class="text-center"><img src="./img/agence.png" class="img-fluid mx-auto my-3"> <%= session.getAttribute("db")%></h4>

                        <div class="form-group d-flex justify-content-center align-items-center flex-md-row flex-column mx-auto">
                            <label for="validationDefaultUsername" class="col-12 col-md-5 text-md-right">Tickets édités:</label>
                            <div class="input-group col-12 col-md-6">
                                <div class="input-group-prepend ">
                                    <span class="input-group-text bg-dark" id="inputGroupPrepend2"><img src="img/icon/ticket.png"/></span>
                                </div>
                                <input type="text" class="form-control bg-dark text-white text-md-right" id="validationDefaultUsername" aria-describedby="inputGroupPrepend2" disabled value="<%= stat.getTotalTicket(null, null)%>">
                            </div>
                        </div>
                        <div class="form-group d-flex justify-content-center align-items-center flex-md-row flex-column mx-auto">
                            <label for="validationDefaultUsername" class="col-md-5  text-md-right">Tickets traités:</label>
                            <div class="input-group  col-md-6">
                                <div class="input-group-prepend">
                                    <span class="input-group-text bg-dark" id="inputGroupPrepend2"><img src="img/icon/done.png"/></span>
                                </div>
                                <input type="text" class="form-control bg-dark text-white text-md-right <%= goalClass %>" id="validationDefaultUsername" aria-describedby="inputGroupPrepend2" disabled value="<%= stat.getDealTicket(null, null)%>">
                            </div>
                        </div>
                        <div class="form-group d-flex justify-content-center align-items-center flex-md-row flex-column">
                            <label for="validationDefaultUsername" class="col-md-5 text-md-right ">Tickets absents:</label>
                            <div class="input-group  col-md-6">
                                <div class="input-group-prepend ">
                                    <span class="input-group-text bg-dark" id="inputGroupPrepend2"><img src="img/icon/x.png"/></span>
                                </div>
                                <input type="text" class="form-control bg-dark text-white text-md-right" id="validationDefaultUsername" aria-describedby="inputGroupPrepend2" disabled value="<%= stat.getAbsentTicket(null, null)%>">
                            </div>
                        </div>
                        <div class="form-group d-flex justify-content-center align-items-center flex-md-row flex-column">
                            <label for="validationDefaultUsername" class="col-md-5 text-md-right ">Tickets en attente:</label>
                            <div class="input-group  col-md-6">
                                <div class="input-group-prepend ">
                                    <span class="input-group-text bg-dark" id="inputGroupPrepend2"><img src="img/icon/ticket.png"/></span>
                                </div>
                                <input type="text" class="form-control bg-dark text-white text-md-right <%= waitClass %>" id="validationDefaultUsername" aria-describedby="inputGroupPrepend2" disabled value="<%= stat.getWaitingTicket(null, null)%>">
                            </div>
                        </div>
                        <div class="form-group  d-flex justify-content-center align-items-center flex-md-row flex-column">
                            <label for="validationDefaultUsername" class="col-md-5 text-md-right ">Moyenne d'attente:</label>
                            <div class="input-group col-md-6 ">
                                <div class="input-group-prepend ">
                                    <span class="input-group-text bg-dark " id="inputGroupPrepend2"><img src="img/icon/wait.png"/></span>
                                </div>
                                <input type="text" class="form-control bg-dark text-white text-md-right" id="validationDefaultUsername" aria-describedby="inputGroupPrepend2" disabled value="<%= stat.getAvgWaitTime(null, null)%>">
                            </div>
                        </div>
                        <div class="form-group d-flex justify-content-center align-items-center flex-md-row flex-column">
                            <label for="validationDefaultUsername" class="col-md-5 text-md-right ">Moyenne de traitement:</label>
                            <div class="input-group  col-md-6">
                                <div class="input-group-prepend ">
                                    <span class="input-group-text bg-dark" id="inputGroupPrepend2"><img src="img/icon/done.png"/></span>
                                </div>
                                <input type="text" class="form-control bg-dark text-white text-md-right" id="validationDefaultUsername" aria-describedby="inputGroupPrepend2" disabled value="<%= stat.getAvgDealTime(null, null)%>">
                            </div>
                        </div>
                        <div class="form-group d-flex justify-content-center align-items-center flex-md-row flex-column">
                            <label for="validationDefaultUsername" class="col-md-5 text-md-right ">Max. Attente:</label>
                            <div class="input-group  col-md-6">
                                <div class="input-group-prepend ">
                                    <span class="input-group-text bg-dark" id="inputGroupPrepend2"><img src="img/icon/max.png"/></span>
                                </div>
                                <input type="text" class="form-control bg-dark text-white text-md-right" id="validationDefaultUsername" aria-describedby="inputGroupPrepend2" disabled value="<%= stat.getMaxWaitTime(null, null)%>">
                            </div>
                        </div>
                        <div class="form-group d-flex justify-content-center align-items-center flex-md-row flex-column">
                            <label for="validationDefaultUsername" class="col-md-5 text-md-right ">Max. Traitement:</label>
                            <div class="input-group  col-md-6 ">
                                <div class="input-group-prepend ">
                                    <span class="input-group-text bg-dark" id="inputGroupPrepend2"><img src="img/icon/max.png"/></span>
                                </div>
                                <input type="text" class="form-control bg-dark text-white text-md-right" id="validationDefaultUsername" aria-describedby="inputGroupPrepend2" disabled value="<%= stat.getMaxDealTime(null, null)%>">
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <script>
            var timer = setInterval(function () {window.location = window.location.href;}, 60000);
            $(document).ready(function () {
                var d = <%= waitPer %>;
                if (d >=80){
                    alert("le pourcentage d'attente est supérieur à 80% !!!");
                }
                updateLinks();
            });
        </script>
    </body>
</html>
