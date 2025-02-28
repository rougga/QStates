package ma.rougga.qstates.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ma.rougga.qstates.CfgHandler;
import ma.rougga.qstates.Stats;
import ma.rougga.qstates.controller.AgenceController;
import ma.rougga.qstates.controller.ServiceController;
import ma.rougga.qstates.modal.Service;

public class AgenceAPI extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        AgenceController ac = new AgenceController();
        ServiceController sc = new ServiceController();
        Stats st = new Stats();
        Map<String,Object> agence = new HashMap<>();
        agence.put("name", ac.getBranchName());
        agence.put("version", CfgHandler.VERSION);
        agence.put("isOnline", "true");
        agence.put("waiting_tickets", st.getWaitingTicket(null, null));
        agence.put("dealt_tickets", st.getDealTicket(null, null));
        agence.put("total_tickets", st.getTotalTicket(null, null));
        agence.put("absent_tickets", st.getAbsentTicket(null, null));
        agence.put("max_wait_time", st.getMaxWaitTime(null, null));
        agence.put("avg_wait_time", st.getAvgWaitTime(null, null));
        agence.put("max_deal_time", st.getMaxDealTime(null, null));
        agence.put("avg_deal_time", st.getAvgDealTime(null, null));
        List<Service> services = sc.getAll();
        agence.put("services", services);
        String json = objectMapper.writeValueAsString(agence);
        resp.getWriter().println(json);
    }

}
