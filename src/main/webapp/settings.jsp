<%@page import="ma.rougga.qstates.modal.Service"%>
<%@page import="ma.rougga.qstates.controller.ServiceController"%>
<%@page import="ma.rougga.qstates.controller.AgenceController"%>
<%@page import="ma.rougga.qstates.modal.Utilisateur"%>
<%@page import="ma.rougga.qstates.controller.UtilisateurController"%>
<%@page import="ma.rougga.qstates.modal.Cible"%>
<%@page import="java.util.List"%>
<%@page import="ma.rougga.qstates.controller.CibleController"%>
<%@page import="ma.rougga.qstates.CfgHandler"%>
<%@page import="org.w3c.dom.Element"%>
<%@page import="org.w3c.dom.NodeList"%>
<%@page import="org.w3c.dom.Node"%>
<%@page import="org.w3c.dom.Document"%>
<%@page import="java.sql.ResultSet"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    if (!Objects.equals(session.getAttribute("grade"), "adm")) {
        response.sendRedirect("./home.jsp");
        return;
    }
    CibleController cc = new CibleController();
    ServiceController sc = new ServiceController();
    UtilisateurController uc = new UtilisateurController();
    List<Service> services = sc.getAll();
    List<Cible> cibles = cc.getAllCibles();
    List<Utilisateur> users = uc.getAllUtilisateur();
%>
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
        <link href="css/navbar.css" rel="stylesheet" type="text/css"/> 
        <link href="./css/body.css" rel="stylesheet" type="text/css"/>
        <script src="./js/settings.js"></script>
        <style>
            table{
                white-space:nowrap;
            }
        </style>
    </head>
    <body>
        <div class="container-lg p-0">
            <div class="head">
                <%@include file="./addon/navbar.jsp" %>
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
                <div class="row">
                    <div class="btn-group btn-group-lg p-4 mx-auto" role="group" aria-label="Basic example">
                        <a class="btn btn-primary" id="cibleBtn">Cible</a>
                        <a class="btn btn-secondary" id="userBtn">Utilisateurs</a>
                        <a class="btn btn-secondary" id="extraBtn">Extra</a>
                        <a class="btn btn-secondary" id="goalBtn">Objectif</a>
                    </div>
                </div>
                <div class="" id="cibleTbl">
                    <h1>
                        Cible : 
                        <span class="  float-right">
                            <a class="btn btn-success" id="cibleAdd" data-toggle="modal" data-target="#cibleModal"><img src="./img/icon/plus.png"> Ajouter</a>
                            <a class="btn btn-info " id="cibleEdit" ><img src="./img/icon/pencil.png"> Editer</a>
                            <a class="btn btn-danger" id="cibleDlt" href="#"><img src="./img/icon/trash.png"> Supprimer</a>
                        </span>
                    </h1>
                    <table class="table table-bordered table-light   table-responsive-sm " id="cibleTable">
                        <thead class="appColor ">
                            <tr>
                                <th scope="col">Service</th>
                                <th scope="col">Cible d'attente</th>
                                <th scope="col">Cible Traitement</th>
                                <th scope="col">% dépassé Cible</th>
                            </tr>
                        </thead>
                        <tbody class="">
                            <c:forEach var="cible" items="<%=cibles%>" varStatus="status">
                                <tr class=' clickable-row '>
                                    <th scope='row'  data-id='<c:out value="${ cible.getServiceId() }"/>'>
                                        <c:out value="${cible.getServiceName()}"/>
                                    </th>
                                    <td >
                                        <c:out value="${CfgHandler.getFormatedTime(cible.getCibleA())}"/>
                                    </td>
                                    <td>
                                        <c:out value="${CfgHandler.getFormatedTime(cible.getCibleT())}"/>
                                    </td>
                                    <td>
                                        <c:out value="${cible.getCiblePer()}"/>%
                                    </td>
                                </tr>
                            </c:forEach>

                        </tbody>
                    </table>

                </div>
                <div class="" id="userTbl">
                    <h1>
                        Utilisateurs : 
                        <span class="  float-right">
                            <a class="btn btn-success" id="userAdd" data-toggle="modal" data-target="#userModal"><img src="./img/icon/plus.png"> Ajouter</a>
                            <!-- <a class="btn btn-info disabled" id="userEdit"><img src="./img/icon/pencil.png"> Editer</a>-->
                            <a class="btn btn-danger" id="userDlt" href="#"><img src="./img/icon/trash.png"> Supprimer</a>
                        </span>
                    </h1>
                    <table class="table table-bordered table-light   table-responsive-sm " id="userTable">
                        <thead class="appColor ">
                            <tr>
                                <th scope="col">Nom D'utilisateur</th>
                                <th scope="col">Nom Complet</th>
                                <th scope="col">Grade</th>
                            </tr>
                        </thead>

                        <tbody class="">
                            <c:forEach var="user" items="<%=users%>" varStatus="status">
                                <tr class=' clickable-row3 '>
                                    <th scope='row'  data-id='<c:out value="${ user.getId() }"/>'>
                                        <c:out value="${user.getUsername()}"/>
                                    </th>
                                    <td >
                                        <c:out value="${user.getLastName()}"/>
                                        <c:out value="${user.getFirstName()}"/>
                                    </td>
                                    <td data-grade='<c:out value="${user.getGrade()}"/>'>
                                        <c:out value="${CfgHandler.getGrade(user.getGrade())}"/>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>             
                <div class="w-100" id="extraTbl">
                    <h1>
                        Extra : 
                        <span class="  float-right">
                            <a class="btn btn-success" id="extraAdd" data-toggle="modal" data-target="#extraModal"><img src="./img/icon/plus.png"> Ajouter</a>
                            <!--<a class="btn btn-info disabled" id="extraEdit"><img src="./img/icon/pencil.png"> Editer</a>-->
                            <a class="btn btn-danger" id="extraDlt" href="#"><img src="./img/icon/trash.png"> Supprimer</a>
                        </span>
                    </h1>
                    <table class="table table-bordered table-light " id="extraTable">
                        <thead class="appColor ">
                            <tr>
                                <th scope="col">Service</th>
                                <th scope="col">Extra</th>
                            </tr>
                        </thead>

                        <tbody class="">
                            <%

                                String path = CfgHandler.getExtraFile(request);

                                Document doc = CfgHandler.getXml(path);
                                NodeList nList = doc.getElementsByTagName("service");
                                for (int i = 0; i < nList.getLength(); i++) {
                                    Node nNode2 = nList.item(i);
                                    if (nNode2.getNodeType() == Node.ELEMENT_NODE) {
                                        Element eElement2 = (Element) nNode2;

                            %><%="<tr class=' clickable-row2 '>"
                                    + "<th scope='row'  data-id='" + eElement2.getElementsByTagName("id").item(0).getTextContent() + "'>" + eElement2.getElementsByTagName("name").item(0).getTextContent() + "</th>"
                                    + "<td ><b>" + CfgHandler.getFormatedTime(Float.parseFloat(eElement2.getElementsByTagName("extra").item(0).getTextContent())) + "</b></td>"
                                    + "</tr>"%><%

                                            }
                                        }


                            %>

                        </tbody>
                    </table>
                </div>  
                <div class="w-100" id="goalTbl">
                    <h1>
                        Objectif : 
                        <span class="  float-right">
                            <a class="btn btn-info " id="goalEdit" data-toggle="modal" data-target="#goalModal"><img src="./img/icon/pencil.png"> Editer</a>
                        </span>
                    </h1>
                    <table class="table table-bordered table-light  " id="goalTable">
                        <thead class="appColor ">
                            <tr>
                                <th scope="col">Objectif</th>
                                <th scope="col">Valeur</th>
                            </tr>
                        </thead>

                        <tbody class="font-weight-bold">

                            <tr class="clickable-row4">
                                <td>Max Attente</td>
                                <td><%= AgenceController.getMaxAtt()%></td>
                            </tr>
                            <tr class="clickable-row4">
                                <td>But Traitement</td>
                                <td><%= AgenceController.getGoalTr()%></td>
                            </tr>

                        </tbody>
                    </table>
                    <script>

                    </script>
                </div>         

            </div>
            <div class="footer">
                <div class="modal fade text-dark" id="cibleModal" tabindex="-1" aria-labelledby="cibleModalaria" aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <form id="cibleForm" action="./Add" method="POST">
                                <div class="modal-header">
                                    <h5 class="modal-title "  id="exampleModalLabel">Modal title</h5>
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                        <span aria-hidden="true">&times;</span>
                                    </button>
                                </div>
                                <div class="modal-body">

                                    <div class="form-group">
                                        <label for="serviceName">Service:</label>
                                        <input class="form-control" type="hidden" id="servicePlace">
                                        <select class="form-control" id="serviceName" name="service" required>

                                            <option selected disabled value="0">Sélectionner service:</option>
                                            <c:forEach var="service" items="<%=services%>" varStatus="status">
                                                <option value='<c:out value="${service.getId()}"/>'>
                                                    <c:out value="${service.getName()}"/>
                                                </option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="form-group">
                                        <label for="cibleA">Cible d'attente:</label><br>
                                        h:
                                        <input type="number" class="form-control w-25 d-inline-block" id="cibleAH" name="cibleAH" placeholder="Hr" min="0" max="24" required>
                                        m:
                                        <input type="number" class="form-control w-25 d-inline-block" id="cibleAM" name="cibleAM" placeholder="Min" min="0" max="60" required>
                                        s:
                                        <input type="number" class="form-control w-25 d-inline-block" id="cibleAS" name="cibleAS" placeholder="Sec" min="0" max="60" required>
                                    </div>
                                    <div class="form-group">
                                        <label for="cibleT">Cible Traitement:</label><br>
                                        h:
                                        <input type="number" class="form-control w-25 d-inline-block" id="cibleTH" name="cibleTH" placeholder="Hr" min="0" max="24" required>
                                        m:
                                        <input type="number" class="form-control w-25 d-inline-block" id="cibleTM" name="cibleTM" placeholder="Min" min="0" max="60" required>
                                        s:
                                        <input type="number" class="form-control w-25 d-inline-block" id="cibleTS" name="cibleTS" placeholder="Sec" min="0" max="60" required>
                                    </div>
                                    <div class="form-group">
                                        <label for="cibleD">% dépassé Cible:</label>
                                        <input type="number" class="form-control" id="cibleD" name="cibleD" min="0" max="100" required>
                                    </div>
                                    <div class="form-group">
                                        <input type="hidden" class="form-control" name="type" value="cible">
                                    </div>

                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-danger" data-dismiss="modal">Fermer</button>
                                    <button type="submit" class="btn btn-success  " id="cibleSubmit" >Enregistrer</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>

                <div class="modal fade text-dark" id="userModal" tabindex="-1" aria-labelledby="userModalaria" aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <form id="userForm" action="./Add" method="POST">
                                <div class="modal-header">
                                    <h5 class="modal-title "  id="userModalLabel">Modal title</h5>
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                        <span aria-hidden="true">&times;</span>
                                    </button>
                                </div>
                                <div class="modal-body">

                                    <div class="form-group">
                                        <label for="username">Nom D'utilisateur:*</label>
                                        <input type="text" class="form-control" id="username" name="username" required>
                                    </div>
                                    <div class="form-group">
                                        <label for="password">Mot de passe:*</label>
                                        <input type="password" class="form-control" id="password" name="password" required>
                                    </div>
                                    <div class="form-group">
                                        <label for="password">Mot de passe:*</label>
                                        <input type="password" class="form-control" id="password2" name="password2" required>
                                    </div>

                                    <div class="form-group">
                                        <label for="grade">Grade:*</label>
                                        <select class="form-control" id="grade" name="grade" required>
                                            <option value="0" selected  disabled> Selectioner Grade:</option>
                                            <option value="adm">ADMINISTATEUR</option>
                                            <option value="user">UTILISATEUR</option>
                                        </select>
                                    </div>
                                    <div class="form-group">
                                        <label for="firstName">Nom:</label>
                                        <input type="text" class="form-control" id="firstName" name="firstName">
                                    </div>
                                    <div class="form-group">
                                        <label for="lastName">Prenom:</label>
                                        <input type="text" class="form-control" id="lastName" name="lastName">
                                    </div>
                                    <div class="form-group">
                                        <input type="hidden" class="form-control" id="type" name="type" value="user">
                                    </div>


                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-danger" data-dismiss="modal">Fermer</button>
                                    <button type="submit" class="btn btn-success" id="userSubmit">Ajouter</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>

                <div class="modal fade text-dark" id="extraModal" tabindex="-1" aria-labelledby="extraModalaria" aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <form id="extraForm" action="./Add" method="POST">
                                <div class="modal-header">
                                    <h5 class="modal-title "  id="exampleModalLabel">Modal title</h5>
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                        <span aria-hidden="true">&times;</span>
                                    </button>
                                </div>
                                <div class="modal-body">

                                    <div class="form-group">
                                        <label for="serviceName">Service:</label>
                                        <select class="form-control" id="serviceNameExtra" name="serviceNameExtra" required>>
                                            <option selected disabled value="0">Sélectionner service:</option>
                                            <c:forEach var="service" items="<%=services%>" varStatus="status">
                                                <option value='<c:out value="${service.getId()}"/>'>
                                                    <c:out value="${service.getName()}"/>
                                                </option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="form-group">
                                        <label for="extra">Extra:</label><br>
                                        <input type="number" class="form-control w-25 d-inline-block" id="extraH" name="extraH" placeholder="Hr" required max="23" min="0">
                                        <input type="number" class="form-control w-25 d-inline-block" id="extraM" name="extraM" placeholder="Min" required max="60" min="0">
                                        <input type="number" class="form-control w-25 d-inline-block" id="extraS" name="extraS" placeholder="Sec" required max="60" min="0">
                                    </div>
                                    <div class="form-group">

                                        <input type="hidden" class="form-control" id="type" name="type" value="extra">
                                    </div>

                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-danger" data-dismiss="modal">Fermer</button>
                                    <button type="submit" class="btn appBg text-white hover">Enregistrer</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>

                <div class="modal fade text-dark" id="goalModal" tabindex="-1" aria-labelledby="goalModalaria" aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <form id="goalForm" action="./Add" method="POST">
                                <div class="modal-header">
                                    <h5 class="modal-title "  id="exampleModalLabel">Modifier Objectif:</h5>
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                        <span aria-hidden="true">&times;</span>
                                    </button>
                                </div>
                                <div class="modal-body">

                                    <div class="form-group">
                                        <label for="maxA">Max Attente:</label><br>
                                        <input type="number" class="form-control" id="goalAH" name="maxA" placeholder="Nb." min="0" value="<%= AgenceController.getMaxAtt()%>" required>
                                    </div>
                                    <div class="form-group">
                                        <label for="goalT">But Traitement:</label><br>
                                        <input type="number" class="form-control" id="goalTH" name="goalT" placeholder="Nb." min="0" value="<%= AgenceController.getGoalTr()%>" required>
                                    </div>
                                    <div class="">
                                        <input type="hidden" class="form-control" name="type" value="goal">
                                    </div>

                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-danger" data-dismiss="modal">Fermer</button>
                                    <button type="submit" class="btn btn-success  " id="goalSubmit" >Enregistrer</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>


            </div>
        </div>
    </body>

    <script>
        history.replaceState({page: 1}, 'title', "?err=");
    </script>
</html>
