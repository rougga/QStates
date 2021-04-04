package main.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ma.rougga.nst.controller.TaskController;
import main.CfgHandler;
import main.PgConnection;
import org.apache.commons.lang3.StringUtils;

public class DeleteTask extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        //loging test
        if (Objects.equals(request.getSession().getAttribute("user"), null)) {
            response.sendRedirect(CfgHandler.PAGE_HOME);
        } else {
            // admin permition test
            if (Objects.equals(request.getSession().getAttribute("grade"), "adm")) {
                String id = request.getParameter("id");
                System.err.println(id);
                //empty check
                if (StringUtils.isNotBlank(id)) {
                    try {
                        //supprission d'une tache
                        if (new TaskController(new PgConnection().getStatement()).deleteById(UUID.fromString(id))) {
                            response.sendRedirect(CfgHandler.PAGE_TASK + "?err=Tache%20et%20supprime");
                        }else{
                            response.sendRedirect(CfgHandler.PAGE_TASK + "?err=Erreur");
                        }
                    } catch (ClassNotFoundException | SQLException ex) {
                        response.sendRedirect(CfgHandler.PAGE_TASK + "?err=" + ex.getMessage());
                    }
                }
            } else {
                response.sendRedirect(CfgHandler.PAGE_HOME);
            }
        }

    }

}
