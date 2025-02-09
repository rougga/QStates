
package ma.rougga.qstates.controller.report;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import ma.rougga.qstates.CPConnection;
import ma.rougga.qstates.CfgHandler;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


public class ThTTTableController {

    public ThTTTableController() {
    }

    
     public JSONObject generateNdttTable(String d1, String d2) throws SQLException {
        d1 = (d1 == null) ? CfgHandler.format.format(new Date()) : d1;
        d2 = (d2 == null) ? CfgHandler.format.format(new Date()) : d2;
        JSONObject all = new JSONObject();
        JSONArray result = new JSONArray();
        JSONArray table2 = new JSONArray();
        String dateCon = " and to_date(to_char(t2.ticket_time,'YYYY-MM-DD'),'YYYY-MM-DD')  BETWEEN TO_DATE('" + d1 + "','YYYY-MM-DD') AND TO_DATE('" + d2 + "','YYYY-MM-DD') and t2.biz_type_id=b.id ";

         String SQL = "Select b.name as service_name, b.id as service_id,"
                 + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('00:00:00','HH24:MI:SS')::time and to_timestamp('00:59:59','HH24:MI:SS')::time " + dateCon + " and t2.status=4) as h0,"
                 + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('01:00:00','HH24:MI:SS')::time and to_timestamp('01:59:59','HH24:MI:SS')::time " + dateCon + " and t2.status=4) as h1,"
                 + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('02:00:00','HH24:MI:SS')::time and to_timestamp('02:59:59','HH24:MI:SS')::time " + dateCon + " and t2.status=4) as h2,"
                 + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('03:00:00','HH24:MI:SS')::time and to_timestamp('03:59:59','HH24:MI:SS')::time " + dateCon + " and t2.status=4) as h3,"
                 + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('04:00:00','HH24:MI:SS')::time and to_timestamp('04:59:59','HH24:MI:SS')::time " + dateCon + " and t2.status=4) as h4,"
                 + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('05:00:00','HH24:MI:SS')::time and to_timestamp('05:59:59','HH24:MI:SS')::time " + dateCon + " and t2.status=4) as h5,"
                 + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('06:00:00','HH24:MI:SS')::time and to_timestamp('06:59:59','HH24:MI:SS')::time " + dateCon + " and t2.status=4) as h6,"
                 + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('07:00:00','HH24:MI:SS')::time and to_timestamp('07:59:59','HH24:MI:SS')::time " + dateCon + " and t2.status=4) as h7,"
                 + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('08:00:00','HH24:MI:SS')::time and to_timestamp('08:59:59','HH24:MI:SS')::time " + dateCon + " and t2.status=4) as h8,"
                 + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('09:00:00','HH24:MI:SS')::time and to_timestamp('09:59:59','HH24:MI:SS')::time " + dateCon + " and t2.status=4) as h9,"
                 + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('10:00:00','HH24:MI:SS')::time and to_timestamp('10:59:59','HH24:MI:SS')::time " + dateCon + " and t2.status=4) as h10,"
                 + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('11:00:00','HH24:MI:SS')::time and to_timestamp('11:59:59','HH24:MI:SS')::time " + dateCon + " and t2.status=4) as h11,"
                 + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('12:00:00','HH24:MI:SS')::time and to_timestamp('12:59:59','HH24:MI:SS')::time " + dateCon + " and t2.status=4) as h12,"
                 + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('13:00:00','HH24:MI:SS')::time and to_timestamp('13:59:59','HH24:MI:SS')::time " + dateCon + " and t2.status=4) as h13,"
                 + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('14:00:00','HH24:MI:SS')::time and to_timestamp('14:59:59','HH24:MI:SS')::time " + dateCon + " and t2.status=4) as h14,"
                 + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('15:00:00','HH24:MI:SS')::time and to_timestamp('15:59:59','HH24:MI:SS')::time " + dateCon + " and t2.status=4) as h15,"
                 + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('16:00:00','HH24:MI:SS')::time and to_timestamp('16:59:59','HH24:MI:SS')::time " + dateCon + " and t2.status=4) as h16,"
                 + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('17:00:00','HH24:MI:SS')::time and to_timestamp('17:59:59','HH24:MI:SS')::time " + dateCon + " and t2.status=4) as h17,"
                 + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('18:00:00','HH24:MI:SS')::time and to_timestamp('18:59:59','HH24:MI:SS')::time " + dateCon + " and t2.status=4) as h18,"
                 + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('19:00:00','HH24:MI:SS')::time and to_timestamp('19:59:59','HH24:MI:SS')::time " + dateCon + " and t2.status=4) as h19,"
                 + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('20:00:00','HH24:MI:SS')::time and to_timestamp('20:59:59','HH24:MI:SS')::time " + dateCon + " and t2.status=4) as h20,"
                 + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('21:00:00','HH24:MI:SS')::time and to_timestamp('21:59:59','HH24:MI:SS')::time " + dateCon + " and t2.status=4) as h21,"
                 + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('22:00:00','HH24:MI:SS')::time and to_timestamp('22:59:59','HH24:MI:SS')::time " + dateCon + " and t2.status=4) as h22,"
                 + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('23:00:00','HH24:MI:SS')::time and to_timestamp('23:59:59','HH24:MI:SS')::time " + dateCon + " and t2.status=4) as h23"
                 + " from t_biz_type b;";
        Connection con = new CPConnection().getConnection();
        ResultSet r = con.createStatement().executeQuery(SQL);
        while (r.next()) {
            JSONObject service = new JSONObject();
            service.put("service_id", r.getString("service_id"));
            service.put("service_name", r.getString("service_name"));
            service.put("h0", r.getString("h0"));
            service.put("h1", r.getString("h1"));
            service.put("h2", r.getString("h2"));
            service.put("h3", r.getString("h3"));
            service.put("h4", r.getString("h4"));
            service.put("h5", r.getString("h5"));
            service.put("h6", r.getString("h6"));
            service.put("h7", r.getString("h7"));
            service.put("h8", r.getString("h8"));
            service.put("h9", r.getString("h9"));
            service.put("h10", r.getString("h10"));
            service.put("h11", r.getString("h11"));
            service.put("h12", r.getString("h12"));
            service.put("h13", r.getString("h13"));
            service.put("h14", r.getString("h14"));
            service.put("h15", r.getString("h15"));
            service.put("h16", r.getString("h16"));
            service.put("h17", r.getString("h17"));
            service.put("h18", r.getString("h18"));
            service.put("h19", r.getString("h19"));
            service.put("h20", r.getString("h20"));
            service.put("h21", r.getString("h21"));
            service.put("h22", r.getString("h22"));
            service.put("h23", r.getString("h23"));
            table2.add(service);
        }

        con.close();
        all.put("result", table2);
        return all;
    }

}
