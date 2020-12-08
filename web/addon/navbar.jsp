<%@page import="java.util.Objects"%>
<%
    if (Objects.equals(session.getAttribute("user"), null)) {
        response.sendRedirect("./index.jsp");
    }
%>
<nav class="navbar navbar-expand-lg navbar-dark " style="background-color: #b83dba;">
    <a class="navbar-brand" href="#">QStates</a>

    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#collapsibleNavbar">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="collapsibleNavbar">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item active" id="home">
                <a class="nav-link font-weight-bold" href="./home.jsp">
                    <span class="fas fa-home font-weight-bold"></span> Accueil
                </a>
            </li>
            <li class="nav-item dropdown" id="report">
                <a class="nav-link dropdown-toggle font-weight-bold gbl emp empser gch gchserv" href="#" id="navbarDropdownR" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    <span class=""></span> Rapport
                </a>
                <div class="dropdown-menu" aria-labelledby="navbarDropdownR" >
                    <a class="dropdown-item font-weight-bold navHover" href="./report.jsp?type=gbl">RAPPORT GLOBALE</a>
                    <a class="dropdown-item font-weight-bold navHover" href="./report.jsp?type=emp">RAPPORT EMPLOYE</a>
                    <a class="dropdown-item font-weight-bold navHover" href="./report.jsp?type=empser">RAPPORT EMPLOYE (service)</a>
                    <a class="dropdown-item font-weight-bold navHover" href="./report.jsp?type=gch">RAPPORT GUICHET</a>
                    <a class="dropdown-item font-weight-bold navHover" href="./report.jsp?type=gchserv">RAPPORT GUICHET (service)</a>
                    <div class="dropdown-divider"></div>
                    <a class="dropdown-item font-weight-bold navHover" href="./report.jsp?type=gla">Grille attente</a>
                    <a class="dropdown-item font-weight-bold navHover" href="./report.jsp?type=glt">Grille traitement</a>
                    <div class="dropdown-divider"></div>
                    <a class="dropdown-item font-weight-bold navHover" href="./report.jsp?type=apl">D�tail des appels</a>
                </div>
            </li> 
            <li class="nav-item dropdown" id="tranche">
                <a class="nav-link dropdown-toggle font-weight-bold ndt ndtt ndta ndtsa" href="#" id="navbarDropdownRe" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    <span class=""></span> Tranche horaire
                </a>
                <div class="dropdown-menu" aria-labelledby="navbarDropdownRe" >
                    <a class="dropdown-item font-weight-bold navHover" href="./report.jsp?type=ndt">Nombre de tickets edit�s</a>
                    <a class="dropdown-item font-weight-bold navHover" href="./report.jsp?type=ndtt">Nombre de tickets trait�s</a>
                    <a class="dropdown-item font-weight-bold navHover" href="./report.jsp?type=ndta">Nombre de tickets absents</a>
                    <a class="dropdown-item font-weight-bold navHover" href="./report.jsp?type=ndtsa">Nombre de tickets sans affectation</a>
                </div>
            </li>
            <li class="nav-item dropdown" id="topics">
                <a class="nav-link font-weight-bold dropdown-toggle cnx remp"  href="#" id="rend" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    <span class="far fa-file-alt"></span> Rendement
                </a>
                <div class="dropdown-menu" aria-labelledby="rend" >
                    <a class="dropdown-item  font-weight-bold navHover" href="./report.jsp?type=cnx">Connexions</a>
                    <a class="dropdown-item  font-weight-bold navHover" href="./report.jsp?type=gbl">Pauses</a>
                    <a class="dropdown-item  font-weight-bold navHover" href="./report.jsp?type=remp">Employ�s</a>
                </div>
            </li> 
            <li class="nav-item dropdown" id="topics">
                <a class="nav-link font-weight-bold dropdown-toggle ser sgch fj" href="#" id="superV" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    <span class="far"></span> Supervision
                </a>
                <div class="dropdown-menu" aria-labelledby="superV" >
                    <a class="dropdown-item  font-weight-bold navHover" href="./fj.jsp">Flash journ�e</a>
                    <a class="dropdown-item  font-weight-bold navHover" href="./report.jsp?type=sgch">Guichets - Employ�s</a>
                    <a class="dropdown-item  font-weight-bold navHover" href="./report.jsp?type=ser">Services</a>
                </div>
            </li>  
            <li class="nav-item" id="topics">
                <a class="nav-link font-weight-bold" href="">
                    <span class="far fa-file-alt"></span> Aide
                </a>
            </li> 
        </ul>


        <ul class="navbar-nav">
            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle active" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    <b><%= session.getAttribute("user")%></b>
                    <img src="./img/icon/database.png">
                    <small><%= session.getAttribute("db")%></small>
                </a>
                <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                    <a class="dropdown-item font-weight-bold navHover" href="./settings.jsp" >Param�tres</a>
                    <div class="dropdown-divider"></div>
                    <a class="dropdown-item font-weight-bold navHover" href="./Logoff">
                        D�connexion</a>
                </div>
            </li>

        </ul>
    </div> 
</nav>
