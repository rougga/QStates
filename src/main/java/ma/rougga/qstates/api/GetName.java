package ma.rougga.qstates.api;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ma.rougga.qstates.controller.UtilisateurController;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GetName extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(GetName.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            request.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            JSONObject result = new JSONObject();
            result.put("name", new UtilisateurController().getBranchName());
            out.print(result);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }

    }

}
