package main.api;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import main.JsonGenerator;
import main.PgConnection;
import org.json.simple.JSONObject;

public class GetGlobaleTable extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            request.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            String date1 = request.getParameter("date1");
            String date2 = request.getParameter("date2");
            JSONObject result = null;
            try {
                result = new JsonGenerator().generateSimpleGblTable(date1, date2, request);
            } catch (Exception e) {
                out.print(e.getMessage());
            }
            out.print(result);

        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }

        
    }

}
