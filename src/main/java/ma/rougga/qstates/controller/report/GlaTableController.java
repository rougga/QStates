package ma.rougga.qstates.controller.report;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import ma.rougga.qstates.CPConnection;
import ma.rougga.qstates.CfgHandler;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GlaTableController {

    private static final Logger logger = LoggerFactory.getLogger(GlaTableController.class);

    public GlaTableController() {
    }
    
    public JSONObject generateGlaTable(HttpServletRequest request, String d1, String d2) {
        d1 = (d1 == null) ? CfgHandler.format.format(new Date()) : d1;
        d2 = (d2 == null) ? CfgHandler.format.format(new Date()) : d2;
        JSONObject all = new JSONObject();
        JSONArray table2 = new JSONArray();
        try (Connection con = new CPConnection().getConnection()) {
            String dateCon = "and to_date(to_char(t.ticket_time,'YYYY-MM-DD'),'YYYY-MM-DD')  BETWEEN TO_DATE('" + d1 + "','YYYY-MM-DD') AND TO_DATE('" + d2 + "','YYYY-MM-DD')  ";
            String SQL = "SELECT  "
                    + "b.name,b.id , "
                    + "(select count(*) from t_ticket t where t.biz_type_id=b.id and DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric<=300 and t.call_time is not null " + dateCon + ") as m0_5, "
                    + "(select count(*) from t_ticket t where t.biz_type_id=b.id and DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric>300 and DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric<=600  and t.call_time is not null  " + dateCon + ") as m5_10, "
                    + "(select count(*) from t_ticket t where t.biz_type_id=b.id and DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric>600 and DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric<=1200 and t.call_time is not null  " + dateCon + ") as m10_20, "
                    + "(select count(*) from t_ticket t where t.biz_type_id=b.id and DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric>1200 and DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric<=1800  and t.call_time is not null  " + dateCon + ") as m20_30, "
                    + "(select count(*) from t_ticket t where t.biz_type_id=b.id and DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric>1800 and DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric<=2700  and t.call_time is not null  " + dateCon + ") as m30_45, "
                    + "(select count(*) from t_ticket t where t.biz_type_id=b.id and DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric>2700 and DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric<=3000  and t.call_time is not null  " + dateCon + ") as m45_50, "
                    + "(select count(*) from t_ticket t where t.biz_type_id=b.id and DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric>3000  and t.call_time is not null  " + dateCon + ") as m50, "
                    + " "
                    + "(select count(*) from t_ticket t where t.biz_type_id=b.id and DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric<=15 and t.call_time is not null  " + dateCon + ") as s0_15, "
                    + "(select count(*) from t_ticket t where t.biz_type_id=b.id and DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric>15 and DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric<=30  and t.call_time is not null  " + dateCon + ") as s15_30, "
                    + "(select count(*) from t_ticket t where t.biz_type_id=b.id and DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric>30 and DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric<=60 and t.call_time is not null  " + dateCon + ") as s30_60, "
                    + "(select count(*) from t_ticket t where t.biz_type_id=b.id and DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric>60 and DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric<=90  and t.call_time is not null  " + dateCon + ") as s60_90, "
                    + "(select count(*) from t_ticket t where t.biz_type_id=b.id and DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric>90 and DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric<=120 and t.call_time is not null  " + dateCon + ") as s90_120, "
                    + "(select count(*) from t_ticket t where t.biz_type_id=b.id and DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric>120 and DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric<=300  and t.call_time is not null  " + dateCon + ") as s120, "
                    + " "
                    + "(select count(*) from t_ticket t where t.biz_type_id=b.id and t.call_time is not null  " + dateCon + ") as total "
                    + "FROM t_biz_type b "
                    + ";";
            ResultSet r = con.createStatement().executeQuery(SQL);
            while (r.next()) {
                JSONObject emp = new JSONObject();
                emp.put("service_id", r.getString("id"));
                emp.put("service_name", r.getString("name"));

                //data
                emp.put("s0_15", r.getLong("s0_15"));
                emp.put("s15_30", r.getLong("s15_30"));
                emp.put("s30_60", r.getLong("s30_60"));
                emp.put("s60_90", r.getLong("s60_90"));
                emp.put("s90_120", r.getLong("s90_120"));
                emp.put("s120", r.getLong("s120"));
                
                emp.put("m0_5", r.getLong("m0_5"));
                emp.put("m5_10", r.getLong("m5_10"));
                emp.put("m10_20", r.getLong("m10_20"));
                emp.put("m20_30", r.getLong("m20_30"));
                emp.put("m30_45", r.getLong("m30_45"));
                emp.put("m45_50", r.getLong("m45_50"));
                emp.put("m50", r.getLong("m50"));
                emp.put("total", r.getLong("total"));
                
                table2.add(emp);
            }
            con.close();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        all.put("result", table2);
        return all;
    }
    
}
