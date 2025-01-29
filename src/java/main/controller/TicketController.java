package main.controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.CfgHandler;
import main.Modal.Ticket;
import main.PgConnection;

public class TicketController {

    public TicketController() {

    }

    public Ticket getOldestTicket() {
        Ticket ticket = new Ticket();
        
        try {
            PgConnection con = new PgConnection();
            String sqlQuery = "SELECT id, biz_type_id, ticket_id, evaluation_id, ticket_type, status, " +
                  "deal_win, transfer_win, deal_user, id_card_info_id, ticket_time, call_time, " +
                  "start_time, finish_time, call_type, branch_id, id_card_name " +
                  "FROM t_ticket " +
                  "WHERE ticket_time = (SELECT MIN(ticket_time) FROM t_ticket) " +
                  "LIMIT 1;";
            ResultSet resultSet = con.getStatement().executeQuery(sqlQuery);
            if(resultSet.next()){
                ticket.setId(resultSet.getString("id"));
                ticket.setBiz_type_id(resultSet.getString("biz_type_id"));
                ticket.setTicket_id(resultSet.getString("ticket_id"));
                ticket.setEvaluation_id(resultSet.getString("evaluation_id"));
                ticket.setTicket_type(resultSet.getString("ticket_type"));
                ticket.setStatus(resultSet.getInt("status"));
                ticket.setDeal_win(resultSet.getString("deal_win"));
                ticket.setTransfer_win(resultSet.getString("transfer_win"));
                ticket.setDeal_user(resultSet.getString("deal_user"));
                ticket.setId_card_info_id(resultSet.getString("id_card_info_id"));
                ticket.setTicket_time(CfgHandler.getFormatedDateAsDate(resultSet.getString("ticket_time")));
                ticket.setCall_time(CfgHandler.getFormatedDateAsDate(resultSet.getString("call_time")));
                ticket.setStart_time(CfgHandler.getFormatedDateAsDate(resultSet.getString("start_time")));
                ticket.setFinish_time(CfgHandler.getFormatedDateAsDate(resultSet.getString("finish_time")));
                ticket.setCall_type(resultSet.getString("call_type"));
                ticket.setBranch_id(resultSet.getString("branch_id"));
                ticket.setId_card_name(resultSet.getString("id_card_name"));
            }
            con.closeConnection();
            return ticket;
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        return null;
    }

}
