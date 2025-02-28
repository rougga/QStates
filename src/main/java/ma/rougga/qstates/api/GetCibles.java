package ma.rougga.qstates.api;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ma.rougga.qstates.Stats;
import ma.rougga.qstates.controller.CibleController;
import ma.rougga.qstates.modal.Cible;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class GetCibles extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONObject result = new JSONObject();
        JSONObject cibleJSON = new JSONObject();
        JSONArray ciblesJSON = new JSONArray();
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        CibleController cc = new CibleController();
        List<Cible> cibles = cc.getAllCibles();
        for (Cible cible : cibles) {
            cibleJSON.put("service_id", cible.getServiceId());
            cibleJSON.put("service_name", cible.getServiceName());
            cibleJSON.put("cible_a", cible.getCibleA());
            cibleJSON.put("cible_t", cible.getCibleT());
            cibleJSON.put("cible_per", cible.getCiblePer());
            ciblesJSON.add(cibleJSON);
        }
        result.put("result", ciblesJSON);
        out.print(result);

    }

}
