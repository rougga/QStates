
package ma.rougga.qstates.controller.report;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.ParserConfigurationException;
import ma.rougga.qstates.CfgHandler;
import ma.rougga.qstates.PgConnection;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.xml.sax.SAXException;


public class GltTableController {

    public GltTableController() {
    }
    
    public JSONObject generateGltTable(HttpServletRequest request, String d1, String d2) throws ClassNotFoundException, SQLException {
        d1 = (d1 == null) ? CfgHandler.format.format(new Date()) : d1;
        d2 = (d2 == null) ? CfgHandler.format.format(new Date()) : d2;
        JSONObject all = new JSONObject();
        JSONArray table2 = new JSONArray();
        PgConnection con = new PgConnection();
        String dateCon = "and to_date(to_char(t.ticket_time,'YYYY-MM-DD'),'YYYY-MM-DD')  BETWEEN TO_DATE('" + d1 + "','YYYY-MM-DD') AND TO_DATE('" + d2 + "','YYYY-MM-DD')  ";
        String SQL = "SELECT  "
                + "b.name,b.id ,"
                + "(select count(*) from t_ticket t where t.biz_type_id=b.id and DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric>=300 and t.status=4 " + dateCon + ") as m0_5, "
                + "(select count(*) from t_ticket t where t.biz_type_id=b.id and DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric>300 and DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric<=600  and t.status=4  " + dateCon + ") as m5_10, "
                + "(select count(*) from t_ticket t where t.biz_type_id=b.id and DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric>600 and DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric<=1200 and t.status=4  " + dateCon + ") as m10_20, "
                + "(select count(*) from t_ticket t where t.biz_type_id=b.id and DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric>1200 and DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric<=1800  and t.status=4  " + dateCon + ") as m20_30, "
                + "(select count(*) from t_ticket t where t.biz_type_id=b.id and DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric>1800 and DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric<=2700  and t.status=4  " + dateCon + ") as m30_45, "
                + "(select count(*) from t_ticket t where t.biz_type_id=b.id and DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric>2700 and DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric<=3000  and t.status=4  " + dateCon + ") as m45_50, "
                + "(select count(*) from t_ticket t where t.biz_type_id=b.id and DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric>3000  and t.status=4  " + dateCon + ") as m50, "
                + " "
                + "(select count(*) from t_ticket t where t.biz_type_id=b.id and DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric<=15 and t.status=4  " + dateCon + ") as s0_15, "
                + "(select count(*) from t_ticket t where t.biz_type_id=b.id and DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric>15 and DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric<=30  and t.status=4  " + dateCon + ") as s15_30, "
                + "(select count(*) from t_ticket t where t.biz_type_id=b.id and DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric>30 and DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric<=60 and t.status=4  " + dateCon + ") as s30_60, "
                + "(select count(*) from t_ticket t where t.biz_type_id=b.id and DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric>60 and DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric<=90  and t.status=4  " + dateCon + ") as s60_90, "
                + "(select count(*) from t_ticket t where t.biz_type_id=b.id and DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric>90 and DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric<=120 and t.status=4  " + dateCon + ") as s90_120, "
                + "(select count(*) from t_ticket t where t.biz_type_id=b.id and DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric>120 and DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric<=300  and t.status=4  " + dateCon + ") as s120, "
                + " "
                + "(select count(*) from t_ticket t where t.biz_type_id=b.id and t.status=4  " + dateCon + ") as total "
                + "FROM t_biz_type b "
                + ";";
        ResultSet r = con.getStatement().executeQuery(SQL);
        while (r.next()) {
            JSONObject emp = new JSONObject();
            JSONObject data = new JSONObject();
            emp.put("service_id", r.getString("id"));
            emp.put("service_name", r.getString("name"));
            
            //data 
            data.put("s0_15", r.getLong("s0_15"));
            data.put("s15_30", r.getLong("s15_30"));
            data.put("s30_60", r.getLong("s30_60"));
            data.put("s60_90", r.getLong("s60_90"));
            data.put("s90_120", r.getLong("s90_120"));
            data.put("s120", r.getLong("s120"));
            
            data.put("m0_5", r.getLong("m0_5"));
            data.put("m5_10", r.getLong("m5_10"));
            data.put("m10_20", r.getLong("m10_20"));
            data.put("m20_30", r.getLong("m20_30"));
            data.put("m30_45", r.getLong("m30_45"));
            data.put("m45_50", r.getLong("m45_50"));
            data.put("m50", r.getLong("m50"));
            data.put("total", r.getLong("total"));
            
            emp.put("data", data);
            table2.add(emp);
        }
        con.closeConnection();
        all.put("result", table2);
        return all;
    }

}
