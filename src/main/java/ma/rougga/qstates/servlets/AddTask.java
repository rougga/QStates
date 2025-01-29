package ma.rougga.qstates.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ma.rougga.qstates.CfgHandler;
import ma.rougga.qstates.PgConnection;
import ma.rougga.qstates.controller.TaskController;
import ma.rougga.qstates.modal.Task;
import org.apache.commons.lang3.StringUtils;

public class AddTask extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        //loging test
        if (Objects.equals(request.getSession().getAttribute("user"), null)) {
            response.sendRedirect(CfgHandler.PAGE_HOME);
        } else {
            // admin permition test
            if (Objects.equals(request.getSession().getAttribute("grade"), "adm")) {
                String taskName = request.getParameter("taskName");
                String serviceId = request.getParameter("serviceId");
                //empty check
                if (StringUtils.isNoneBlank(taskName, serviceId)) {
                    System.err.println("Name:" + taskName + " Id: " + serviceId);
                    try {
                        new TaskController(new PgConnection().getStatement()).add(
                                new Task(taskName, serviceId)
                        );
                        response.sendRedirect(CfgHandler.PAGE_TASK + "?err=Tache%20et%20ajouter");
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
