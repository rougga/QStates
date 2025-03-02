package ma.rougga.qstates.servlets;

import java.io.IOException;
import java.util.Objects;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ma.rougga.qstates.CfgHandler;
import ma.rougga.qstates.controller.TaskController;
import ma.rougga.qstates.modal.Task;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;

public class AddTask extends HttpServlet {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(AddTask.class);

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
                    TaskController tc = new TaskController();
                    if (tc.add(new Task(taskName, serviceId))) {
                        response.sendRedirect(CfgHandler.PAGE_TASK + "?err=Tache%20et%20ajouter");
                    }else{
                        response.sendRedirect(CfgHandler.PAGE_TASK + "?err=Tache%20net%20pas%20ajouter");
                    }
                }
            } else {
                response.sendRedirect(CfgHandler.PAGE_HOME);
            }
        }
    }
}
