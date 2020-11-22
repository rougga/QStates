<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!doctype html>
<html>
    <head>
        <meta charset="utf-8"/>
        <title>QStates</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="icon" type="image/png" href="./img/favicon-32x32.png">
        <script src="js/jquery.js"></script>
        <link href="css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
        <script src="js/bootstrap.min.js"></script>
        <link href="css/body.css" rel="stylesheet" type="text/css"/> 
        <link href="css/login.css" rel="stylesheet" type="text/css"/> 
        <link href="css/loginSS.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
        <div class="wrapper">
            <%
                if (request.getParameter("err") != "" && request.getParameter("err") != null) {
                    String msg = "";
                    switch (request.getParameter("err")) {
                        case "1":
                            msg = "Utilisateur non trouvé.";
                            break;
                        case "2":
                            msg = "Mot de passe incorrect.";
                            break;
                        case "3":
                            msg = "Déconnecté.";
                            break;
                        default:
                            msg = request.getParameter("err");
                    }
            %>
            <%= "<div class='alert alert-danger alert-dismissible fade show' role='alert'><b>"
                    + msg
                    + "</b><button type='button' class='close' data-dismiss='alert' aria-label='Close'><span aria-hidden='true'>&times;</span></button></div>"%>
            <%
                }
            %>


            <form class="login" method="POST" action="./Login">
                <img src="img/banner.png"   class="logo center-block img-fluid" alt=""/>
                <p class="title">Connexion:</p>
                <input type="text" placeholder="Nom D'utilisateur" name="username" autofocus required/>
                <input type="password" name="password" placeholder="Mot de passe" required/>
                <a href="#">Mot de passe oublié ?</a>
                <button type="submit">
                    <span class="state">Connexion</span>
                </button>
            </form>
            <footer><a target="_blank" href="#">CopyRight &COPY; 2020 NST-Maroc</a></footer>

        </div>
    </body>
</html>