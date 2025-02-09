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
import org.json.simple.JSONObject;

public class GetWaitingTickets extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            request.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            JSONObject result = new JSONObject();
            Stats stats = new Stats();
            List services = stats.getWaitingTicketsByService(null, null, response);
            long totatlWaitingTickets = 0;
            for (Object service : services) {
                ArrayList ser = (ArrayList) service;
                totatlWaitingTickets += (long) ser.get(1);
            }
            result.put("waitingTickets", totatlWaitingTickets);
            out.print(result);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }

    }

}
