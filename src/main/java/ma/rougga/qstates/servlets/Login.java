package ma.rougga.qstates.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ma.rougga.qstates.PasswordAuthentication;
import ma.rougga.qstates.controller.UtilisateurController;
import ma.rougga.qstates.modal.Utilisateur;

/**
 *
 * @author bouga
 */
public class Login extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String err = "";
        boolean isFound = false;
        boolean pwFalse = true;
        if (!(username == null) && !(password == null)) {
            String hashedPass;
            username = username.trim().toLowerCase();
            UtilisateurController uc = new UtilisateurController();
            Utilisateur user = uc.getUtilisateurByUsername(username);

            if (user != null) {
                isFound = true;
                PasswordAuthentication pa = new PasswordAuthentication();
                if (pa.authenticate(password.toCharArray(), user.getPassword())) {
                    request.getSession().setAttribute("user", username);
                    request.getSession().setAttribute("grade", user.getGrade());
                    request.getSession().setAttribute("db", uc.getBranchName());
                    response.sendRedirect("./home.jsp");

                } else {
                    pwFalse = true;
                    err = "2";
                    response.sendRedirect("./index.jsp?err=" + err);
                }
            } else {
                isFound = false;
                err = "1";
                response.sendRedirect("./index.jsp?err=" + err);
            }

        }
    }

}
