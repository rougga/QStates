package ma.rougga.qstates.controller.report;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import ma.rougga.qstates.CfgHandler;
import ma.rougga.qstates.PgConnection;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class EmpSerTableController {

    public EmpSerTableController() {
    }

    public JSONObject generateSimpleEmpServiceTable(HttpServletRequest request, String d1, String d2) throws ClassNotFoundException, SQLException {
        d1 = (d1 == null) ? CfgHandler.format.format(new Date()) : d1;
        d2 = (d2 == null) ? CfgHandler.format.format(new Date()) : d2;
        JSONObject all = new JSONObject();
        JSONArray table2 = new JSONArray();
        PgConnection con = new PgConnection();
        String dateCon = " and to_date(to_char(t2.ticket_time,'YYYY-MM-DD'),'YYYY-MM-DD')  BETWEEN TO_DATE('" + d1 + "','YYYY-MM-DD') AND TO_DATE('" + d2 + "','YYYY-MM-DD') ";

        String empSQL = "select g1.deal_user, g1.user_name,g1.user_id ,g1.service_name, g1.service_id, g1.nb_t, g1.nb_tt, g1.nb_a, g1.nb_tl1, g1.nb_sa, g1.avgsec_a, g1.avgsec_t,  CASE WHEN G1.NB_T::numeric = 0::numeric THEN 0::numeric  ELSE CAST((G1.nb_a::numeric / G1.NB_T::numeric) * 100::numeric AS DECIMAL(10,2)) END AS PERAPT, CASE WHEN G1.NB_T::numeric = 0::numeric THEN 0::numeric                   ELSE CAST((G1.nb_tl1::numeric / G1.NB_T::numeric) * 100::numeric AS DECIMAL(10,2)) END AS PERTL1PT, CASE WHEN G1.NB_T::numeric = 0::numeric THEN 0::numeric ELSE CAST((G1.nb_sa::numeric / G1.NB_T::numeric) * 100::numeric AS DECIMAL(10,2))  END AS PERSAPT from "
                + " (select t1.deal_user,  u.name as user_name,u.id as user_id, t1.biz_type_id as service_id, b.name as service_name ,"
                + "(select count(*) from t_ticket t2 where t2.deal_user=t1.deal_user and t2.biz_type_id=t1.biz_type_id " + dateCon + ") as nb_t, "
                + "(select count(*) from t_ticket t2 where t2.deal_user=t1.deal_user and t2.biz_type_id=t1.biz_type_id and t2.status=4    " + dateCon + "  ) as nb_tt, "
                + "(select count(*) from t_ticket t2 where t2.deal_user=t1.deal_user and t2.biz_type_id=t1.biz_type_id and t2.status=2   " + dateCon + "   ) as nb_a, "
                + "(select count(*) from t_ticket t2 where t2.deal_user=t1.deal_user and t2.biz_type_id=t1.biz_type_id and DATE_PART('epoch'::text, T2.FINISH_TIME - T2.START_TIME)::numeric / 60::numeric <= 1 and t2.status=4   " + dateCon + "  ) as nb_tl1,"
                + "(select count(*) from t_ticket t2 where t2.deal_user=t1.deal_user and t2.biz_type_id=t1.biz_type_id and t2.status=0   " + dateCon + "   ) as nb_sa,"
                + "(SELECT AVG(DATE_PART('epoch'::text, T2.CALL_TIME - T2.TICKET_TIME)::numeric) FROM T_TICKET T2 WHERE t2.deal_user=t1.deal_user and t2.biz_type_id=t1.biz_type_id and T2.call_time is not null    " + dateCon + "   ) AS AVGSEC_A,"
                + "(SELECT AVG(DATE_PART('epoch'::text, T2.FINISH_TIME - T2.START_TIME)::numeric) FROM T_TICKET T2 WHERE t2.deal_user=t1.deal_user and t2.biz_type_id=t1.biz_type_id and T2.STATUS = 4   " + dateCon + " ) AS AVGSEC_T "
                + "  from t_ticket t1 , t_biz_type b,t_user u "
                + "where t1.deal_user is not null and t1.biz_type_id=b.id and t1.deal_user=u.id and to_date(to_char(t1.ticket_time,'YYYY-MM-DD'),'YYYY-MM-DD')  BETWEEN TO_DATE('" + d1 + "','YYYY-MM-DD') AND TO_DATE('" + d2 + "','YYYY-MM-DD') "
                + "group by t1.deal_user,u.name,u.id,t1.biz_type_id,b.name order by user_name , service_name) g1;";

        ResultSet r = con.getStatement().executeQuery(empSQL);
        while (r.next()) {
            JSONObject empser = new JSONObject();
            empser.put("user_id", r.getString("user_id"));
            empser.put("user_name", r.getString("user_name"));
            empser.put("service_id", r.getString("service_id"));
            empser.put("service_name", r.getString("service_name"));
            empser.put("nb_t", r.getLong("nb_t"));
            empser.put("nb_tt", r.getLong("nb_tt"));
            empser.put("nb_a", r.getLong("nb_a"));
            empser.put("nb_tl1", r.getLong("nb_tl1"));
            empser.put("nb_sa", r.getLong("nb_sa"));
            empser.put("perApT", r.getFloat("perApT"));
            empser.put("PERTL1pt", r.getFloat("PERTL1pt"));
            empser.put("perSApT", r.getFloat("perSApT"));
            empser.put("avgSec_A", r.getFloat("avgSec_A"));
            empser.put("nb_ca", 0);
            empser.put("percapt", 0.00);
            empser.put("avgSec_T", r.getFloat("avgSec_T"));
            empser.put("nb_ct", 0);
            empser.put("perctpt", 0.00);
            table2.add(empser);
        }
        con.closeConnection();
        all.put("result", table2);
        return all;
    }

}
