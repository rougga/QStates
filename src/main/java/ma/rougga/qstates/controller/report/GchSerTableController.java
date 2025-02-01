
package ma.rougga.qstates.controller.report;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import ma.rougga.qstates.CfgHandler;
import ma.rougga.qstates.PgConnection;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


public class GchSerTableController {

    public GchSerTableController() {
    }

    public JSONObject generateGchServiceTable(HttpServletRequest request, String d1, String d2) throws ClassNotFoundException, SQLException {
        d1 = (d1 == null) ? CfgHandler.format.format(new Date()) : d1;
        d2 = (d2 == null) ? CfgHandler.format.format(new Date()) : d2;
        JSONObject all = new JSONObject();
        JSONArray table2 = new JSONArray();
        PgConnection con = new PgConnection();
        String dateCon = " and to_date(to_char(t2.ticket_time,'YYYY-MM-DD'),'YYYY-MM-DD')  BETWEEN TO_DATE('" + d1 + "','YYYY-MM-DD') AND TO_DATE('" + d2 + "','YYYY-MM-DD') ";

        String SQL = "SELECT G1.DEAL_WIN, "
                + "G1.GUICHET,"
                + "G1.biz_type_id, "
                + "g1.service, "
                + "G1.NB_T, "
                + "G1.NB_TT, "
                + "G1.nb_a, "
                + "G1.nb_tl1, "
                + "G1.nb_sa, "
                + "G1.AVGSEC_A, "
                + "G1.AVGSEC_T, "
                + "CASE "
                + "WHEN G1.NB_T::numeric = 0::numeric THEN 0::numeric "
                + "ELSE CAST((G1.nb_a::numeric / G1.NB_T::numeric) * 100::numeric AS DECIMAL(10,2)) "
                + "END AS PERAPT, "
                + "CASE "
                + "WHEN G1.NB_T::numeric = 0::numeric THEN 0::numeric "
                + "ELSE CAST((G1.nb_tl1::numeric / G1.NB_T::numeric) * 100::numeric AS DECIMAL(10,2)) "
                + "END AS PERTL1PT, "
                + "CASE "
                + "WHEN G1.NB_T::numeric = 0::numeric THEN 0::numeric "
                + "ELSE CAST((G1.nb_sa::numeric / G1.NB_T::numeric) * 100::numeric AS DECIMAL(10,2)) "
                + "END AS PERSAPT "
                + "FROM "
                + "(SELECT T1.DEAL_WIN, "
                + " t1.biz_type_id, "
                + "W.NAME AS GUICHET, "
                + " b.name as service, "
                + " "
                + "(SELECT COUNT(*) "
                + "FROM T_TICKET T2 "
                + "WHERE T2.DEAL_WIN = T1.DEAL_WIN and T2.biz_type_id=t1.biz_type_id " + dateCon + ") AS NB_T, "
                + " "
                + "(SELECT COUNT(*) "
                + "FROM T_TICKET T2 "
                + "WHERE T2.DEAL_WIN = T1.DEAL_WIN "
                + "AND T2.STATUS = 4 and T2.biz_type_id=t1.biz_type_id  " + dateCon + ") AS NB_TT, "
                + " "
                + "(SELECT COUNT(*) "
                + "FROM T_TICKET T2 "
                + "WHERE T2.DEAL_WIN = T1.DEAL_WIN and T2.biz_type_id=t1.biz_type_id "
                + "AND T2.STATUS = 2  " + dateCon + ") AS nb_a, "
                + " "
                + "(SELECT COUNT(*) "
                + "FROM T_TICKET T2 "
                + "WHERE T2.DEAL_WIN = T1.DEAL_WIN "
                + "AND DATE_PART('epoch'::text, "
                + " "
                + "T2.FINISH_TIME - T2.START_TIME)::numeric / 60::numeric <= 1 "
                + "AND T2.STATUS = 4 and T2.biz_type_id=t1.biz_type_id " + dateCon + ") AS nb_tl1, "
                + " "
                + "(SELECT COUNT(*) "
                + "FROM T_TICKET T2 "
                + "WHERE T2.DEAL_WIN = T1.DEAL_WIN "
                + "AND T2.STATUS = 0 and T2.biz_type_id=t1.biz_type_id " + dateCon + ") AS nb_sa, "
                + " "
                + "(SELECT AVG(DATE_PART('epoch'::text, "
                + " "
                + "T2.CALL_TIME - T2.TICKET_TIME)::numeric) "
                + "FROM T_TICKET T2 "
                + "WHERE T2.DEAL_WIN = T1.DEAL_WIN "
                + "AND T2.CALL_TIME IS NOT NULL and T2.biz_type_id=t1.biz_type_id " + dateCon + ") AS AVGSEC_A, "
                + " "
                + "(SELECT AVG(DATE_PART('epoch'::text,T2.FINISH_TIME - T2.START_TIME)::numeric) "
                + "FROM T_TICKET T2 "
                + "WHERE T2.DEAL_WIN = T1.DEAL_WIN "
                + "AND T2.STATUS = 4 and T2.biz_type_id=t1.biz_type_id " + dateCon + ") AS AVGSEC_T "
                + "FROM T_TICKET T1, "
                + "T_WINDOW W , t_biz_type b "
                + "WHERE T1.DEAL_WIN = W.ID and T1.biz_type_id = b.id  and to_date(to_char(t1.ticket_time,'YYYY-MM-DD'),'YYYY-MM-DD')  BETWEEN TO_DATE('" + d1 + "','YYYY-MM-DD') AND TO_DATE('" + d2 + "','YYYY-MM-DD')"
                + "GROUP BY T1.DEAL_WIN, "
                + "W.NAME,b.name,t1.biz_type_id) G1;";
        ResultSet r = con.getStatement().executeQuery(SQL);
        
        while (r.next()) {
            JSONObject emp = new JSONObject();
            JSONObject data = new JSONObject();
            emp.put("service_id", r.getString("biz_type_id"));
            emp.put("service_name", r.getString("service"));
            emp.put("guichet_id", r.getString("DEAL_WIN"));
            emp.put("guichet_name", r.getString("GUICHET"));
            data.put("nb_t", r.getLong("nb_t"));
            data.put("nb_tt", r.getLong("nb_tt"));
            data.put("nb_a", r.getLong("nb_a"));
            data.put("nb_tl1", r.getLong("nb_tl1"));
            data.put("nb_sa", r.getLong("nb_sa"));
            data.put("perApT", r.getFloat("perApT"));
            data.put("PERTL1pt", r.getFloat("PERTL1pt"));
            data.put("perSApT", r.getFloat("perSApT"));
            data.put("avgSec_A", r.getFloat("avgSec_A"));
            data.put("nb_ca", 0);
            data.put("percapt", 0.00);
            data.put("avgSec_T", r.getFloat("avgSec_T"));
            data.put("nb_ct", 0);
            data.put("perctpt", 0.00);
            emp.put("data", data);
            table2.add(emp);
        }
        con.closeConnection();
        all.put("result", table2);
        return all;
    }

}
