package ma.rougga.qstates.api;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ma.rougga.qstates.CfgHandler;
import ma.rougga.qstates.modal.Ticket;
import ma.rougga.qstates.PgConnection;
import ma.rougga.qstates.controller.TicketController;
import org.json.simple.JSONObject;

public class GetOldestTicketDate extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            request.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            JSONObject result = new JSONObject();
            Ticket oldestTicket = new TicketController().getOldestTicket();
            if (oldestTicket != null) {
                Date oldestDate = oldestTicket.getTicket_time();
                result.put("oldestDate", CfgHandler.getFormatedDateAsString(oldestDate));
            } else {
                result.put("oldestDate", null);
            }
            System.err.println(oldestTicket.getId());
            out.print(result);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }

    }

}
