
package ma.rougga.qstates.controller.report;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import ma.rougga.qstates.CfgHandler;
import ma.rougga.qstates.PgConnection;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


public class GchTableController {

    public GchTableController() {
    }
    
    public JSONObject generateGchTable(HttpServletRequest request, String d1, String d2) throws ClassNotFoundException, SQLException {
        d1 = (d1 == null) ? CfgHandler.format.format(new Date()) : d1;
        d2 = (d2 == null) ? CfgHandler.format.format(new Date()) : d2;
        JSONObject all = new JSONObject();
        JSONArray table2 = new JSONArray();
        String dateCon = " and to_date(to_char(t2.ticket_time,'YYYY-MM-DD'),'YYYY-MM-DD')  BETWEEN TO_DATE('" + d1 + "','YYYY-MM-DD') AND TO_DATE('" + d2 + "','YYYY-MM-DD') ";

        String SQL = "select "
                + "g1.deal_win, "
                + "g1.guichet, "
                + "g1.nb_t, "
                + "g1.nb_tt, "
                + "g1.nb_a, "
                + "g1.nb_tl1, "
                + "g1.nb_sa, "
                + "g1.avgsec_a, "
                + "g1.avgsec_t, "
                + "CASE "
                + "WHEN G1.NB_T::numeric=0::numeric then 0::numeric "
                + " else  CAST((G1.nb_a::numeric/G1.NB_T::numeric)*100::numeric AS DECIMAL(10,2)) "
                + "END AS PERAPT, "
                + "CASE "
                + "WHEN G1.NB_T::numeric=0::numeric then 0::numeric "
                + " else  CAST((G1.nb_tl1::numeric/G1.NB_T::numeric)*100::numeric AS DECIMAL(10,2)) "
                + "END AS PERTL1PT, "
                + "CASE "
                + "WHEN G1.NB_T::numeric=0::numeric then 0::numeric "
                + " else  CAST((G1.nb_sa::numeric/G1.NB_T::numeric)*100::numeric AS DECIMAL(10,2)) "
                + "END AS PERSAPT "
                + "from "
                + "(SELECT "
                + "t1.deal_win, "
                + "w.name as guichet, "
                + "( select count(*)from t_ticket t2 where t2.deal_win=t1.deal_win " + dateCon + ") as nb_t, "
                + "( select count(*)from t_ticket t2 where t2.deal_win=t1.deal_win and t2.status=4 " + dateCon + ") as  nb_tt, "
                + "( select count(*)from t_ticket t2 where t2.deal_win=t1.deal_win and t2.status=2 " + dateCon + ") as  nb_a, "
                + "( select count(*)from t_ticket t2 where t2.deal_win=t1.deal_win and DATE_PART('epoch'::text,T2.FINISH_TIME-T2.START_TIME)::numeric/60::numeric<=1 and t2.status=4 " + dateCon + ") as nb_tl1, "
                + "( select count(*)from t_ticket t2 where t2.deal_win=t1.deal_win and t2.status=0 " + dateCon + ") as  nb_sa, "
                + "(SELECT AVG(DATE_PART('epoch'::text,T2.CALL_TIME-T2.TICKET_TIME)::numeric) from T_TICKET T2 WHERE t2.deal_win=t1.deal_win and T2.call_time is not null " + dateCon + ") AS AVGSEC_A, "
                + "(SELECT AVG(DATE_PART('epoch'::text,T2.FINISH_TIME-T2.START_TIME)::numeric) from T_TICKET T2 WHERE t2.deal_win=t1.deal_win and T2.STATUS=4 " + dateCon + ") AS AVGSEC_T "
                + "from "
                + "t_ticket t1, "
                + "t_window w "
                + "where t1.deal_win=w.id and to_date(to_char(t1.ticket_time,'YYYY-MM-DD'),'YYYY-MM-DD')  BETWEEN TO_DATE('" + d1 + "','YYYY-MM-DD') AND TO_DATE('" + d2 + "','YYYY-MM-DD')"
                + "group by t1.deal_win,w.name)g1 "
                + ";";
        PgConnection con = new PgConnection();
        ResultSet r = con.getStatement().executeQuery(SQL);
        while (r.next()) {
            JSONObject emp = new JSONObject();
            String id = r.getString("deal_win");
            emp.put("guichet_id", id);
            emp.put("guichet_name", r.getString("guichet"));
            emp.put("nb_t", r.getLong("nb_t"));
            emp.put("nb_tt", r.getLong("nb_tt"));
            emp.put("nb_a", r.getLong("nb_a"));
            emp.put("nb_tl1", r.getLong("nb_tl1"));
            emp.put("nb_sa", r.getLong("nb_sa"));
            emp.put("perApT", r.getFloat("perApT"));
            emp.put("PERTL1pt", r.getFloat("PERTL1pt"));
            emp.put("perSApT", r.getFloat("perSApT"));
            emp.put("avgSec_A", r.getFloat("avgSec_A"));
            emp.put("nb_ca", 0);
            emp.put("percapt", 0.00);
            emp.put("avgSec_T", r.getFloat("avgSec_T"));
            emp.put("nb_ct", 0);
            emp.put("perctpt", 0.00);
            table2.add(emp);
        }
        con.closeConnection();
        all.put("result", table2);
        return all;
        
    }

}
