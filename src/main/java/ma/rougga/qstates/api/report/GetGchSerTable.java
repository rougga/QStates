package ma.rougga.qstates.api.report;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ma.rougga.qstates.controller.report.EmpTableController;
import ma.rougga.qstates.controller.report.GchSerTableController;
import org.json.simple.JSONObject;
import org.slf4j.LoggerFactory;

public class GetGchSerTable extends HttpServlet {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(GetGchSerTable.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            request.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            String date1 = request.getParameter("date1");
            String date2 = request.getParameter("date2");
            JSONObject result = new GchSerTableController().generateGchServiceTable(request, date1, date2);
            out.print(result);

        } catch (IOException e) {
            logger.error(e.getMessage());
        }

    }
}
