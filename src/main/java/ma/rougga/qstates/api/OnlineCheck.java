
package ma.rougga.qstates.api;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ma.rougga.qstates.CfgHandler;
import org.json.simple.JSONObject;

public class OnlineCheck extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       try {
            request.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            JSONObject result = new JSONObject();
            result.put("isOnline", "true");
            result.put("version", CfgHandler.VERSION);
            out.print(result);
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }

        
    }

   
}
