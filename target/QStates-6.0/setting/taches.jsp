
<%@page import="java.sql.Statement"%>
<%@page import="ma.rougga.nst.controller.ServiceController"%>
<%@page import="ma.rougga.nst.modal.Service"%>
<%@page import="ma.rougga.qstates.PgConnection"%>
<%@page import="java.util.ArrayList"%>
<%@page import="ma.rougga.nst.controller.TaskController"%>
<%@page import="ma.rougga.nst.modal.Task"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>QStates</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="icon" type="image/png" href="/QStates/img/favicon-32x32.png">
        <script src="/QStates/js/jquery.js"></script>
        <link href="/QStates/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
        <script src="/QStates/js/bootstrap.min.js"></script>
        <link href="/QStates/css/navbar.css" rel="stylesheet" type="text/css"/> 
        <link href="/QStates/css/body.css" rel="stylesheet" type="text/css"/>
        <script src="/QStates/js/settings.js"></script>
        <style>
            table{
                white-space:nowrap; 
            }
        </style>
    </head>
    <body>
        <div class="container-lg p-0">
            <div class="head">
                <%@include file="../addon/navbar.jsp" %>
                <script>
                    $("#home").removeClass("active");
                </script>
            </div>
            <div class="body p-3">
                <%                     if (request.getParameter("err") != "" && request.getParameter("err") != null) {

                %>
                <%= "<div class='alert alert-danger alert-dismissible fade show' role='alert'><b>"
                        + request.getParameter("err")
                        + "</b><button type='button' class='close' data-dismiss='alert' aria-label='Close'><span aria-hidden='true'>&times;</span></button></div>"%>
                <%
                    }
                %>
                <div class="" id="tacheTbl">
                    <h1>
                        Les taches : 
                    </h1>
                    <div class="  float-right m-2">
                        <a class="btn btn-success" id="tacheAdd" data-toggle="modal" data-target="#tacheModal"><img src="/QStates/img/icon/plus.png"> Ajouter</a>
                    </div>
                    <table class="table table-bordered table-light table-bordered table-striped border-dark" id="tacheTable">
                        <thead class="appColor ">
                            <tr>
                                <th scope="col">Service</th>
                                <th scope="col">Tache</th>
                                <th></th>
                            </tr>
                        </thead>
                        <tbody class=" border-dark">
                            <%
                                Statement stm = new PgConnection().getStatement();
                                ArrayList<Task> taches = new TaskController(stm).getTasks();
                                
                                for(Task tache : taches){
                            %>

                            <tr>
                                <th class="font-weight-bold db text-center align-middle border-dark" data-id="<%=tache.getId_service()%>"><%= new ServiceController(stm).getById(tache.getId_service()).getName() %></th>
                                <td class="font-weight-bold border-dark"><%= tache.getName() %></td>
                                <td class=" border-dark">
                                    <a class="btn btn-info disabled" id="tacheEdit" href="#" title="Editer"><img src="/QStates/img/icon/pencil.png"></a>
                                    <a class="btn btn-danger" id="tacheDlt" href="/QStates/DeleteTask?id=<%=tache.getId().toString()%>" title="Supprimer"><img src="/QStates/img/icon/trash.png"></a>
                                </td>
                            </tr>

                            <%
                                    
                                }
                            %>
                        </tbody>
                    </table>
                </div>
                <div class="modal fade text-dark" id="tacheModal" tabindex="-1" aria-labelledby="tacheModalaria" aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <form id="tacheForm" action="../addTask" method="POST">
                                <div class="modal-header">
                                    <h5 class="modal-title "  id="exampleModalLabel">Ajouter tache :</h5>
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                        <span aria-hidden="true">&times;</span>
                                    </button>
                                </div>
                                <div class="modal-body">

                                    <div class="form-group">
                                        <label for="serviceName">Service:</label>
                                        <input class="form-control" type="hidden" id="servicePlace">
                                        <select class="form-control" id="serviceName" name="serviceId" required>
                                            <option selected disabled value="0">Sélectionner service:</option>
                                            <%   
                                                stm = new PgConnection().getStatement();
                                                for(Service service : new ServiceController(stm).getAll()){
                                            %>
                                            <option value="<%=service.getId()%>"><%=service.getName()%></option>
                                            <%
                                                }
                                            %>
                                        </select>
                                    </div>
                                    <div class="form-group">
                                        <label for="taskName">Nom de Tache:</label><br>
                                        <input type="text" class="form-control" id="taskName" name="taskName" required>
                                    </div>

                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-danger" data-dismiss="modal">Fermer</button>
                                    <button type="submit" class="btn btn-success  " id="tacheSubmit" >Enregistrer</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
    <script>
        var rowSpan = function() {
        var elements = $('.db');
        var ids = [];
        for (var i = 0; i < elements.length; i++) {
            if (ids.indexOf($(elements[i]).attr("data-id")) === -1) {
                ids.push($(elements[i]).attr("data-id"));
            } else {
                $(elements[i]).hide();
            }
        }
        for (var i = 0; i < ids.length; i++) {
            $('.db[data-id=' + ids[i] + ']:first').attr("rowspan", $('.db[data-id=' + ids[i] + ']').length);
        }
        };
        rowSpan();
    </script>
</html>
