package ma.rougga.qstates.controller.report;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import javax.xml.parsers.ParserConfigurationException;

import ma.rougga.qstates.CPConnection;
import ma.rougga.qstates.CfgHandler;
import ma.rougga.qstates.controller.CibleController;
import ma.rougga.qstates.modal.Cible;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

public class GblTableController {

    private static final Logger logger = LoggerFactory.getLogger(GblTableController.class);

    public GblTableController() {
    }

    public JSONObject generateSimpleGblTable(String d1, String d2) {
        d1 = (d1 == null) ? CfgHandler.format.format(new Date()) : d1;
        d2 = (d2 == null) ? CfgHandler.format.format(new Date()) : d2;
        JSONObject all = new JSONObject();
        JSONArray table2 = new JSONArray();
        try (Connection con = new CPConnection().getConnection()) {
            String dateCon = " and to_date(to_char(t2.ticket_time,'YYYY-MM-DD'),'YYYY-MM-DD')  BETWEEN TO_DATE('" + d1 + "','YYYY-MM-DD') AND TO_DATE('" + d2 + "','YYYY-MM-DD') ";

            String gblSQL2 = "SELECT "
                    + " g1.BIZ_TYPE_ID,"
                    + " G1.NAME,  "
                    + " G1.NB_T,"
                    + " G1.NB_TT, "
                    + "G1.NB_A,"
                    + " G1.NB_TL1,"
                    + " G1.NB_SA,"
                    + " CASE "
                    + "WHEN G1.NB_T::numeric = 0::numeric THEN 0::numeric "
                    + "ELSE CAST((G1.NB_A::numeric / G1.NB_T::numeric) * 100::numeric AS DECIMAL(10,2)) "
                    + "END AS PERAPT,"
                    + " CASE"
                    + " WHEN G1.NB_T::numeric = 0::numeric THEN 0::numeric"
                    + " ELSE CAST((G1.NB_TL1::numeric / G1.NB_T::numeric) * 100::numeric AS DECIMAL(10,2)) "
                    + "END AS PERTL1pt,"
                    + " CASE WHEN G1.NB_T::numeric = 0::numeric THEN 0::numeric "
                    + "ELSE CAST((G1.NB_SA::numeric / G1.NB_T::numeric) * 100::numeric AS DECIMAL(10,2)) "
                    + "END AS PERSAPT , "
                    + "G1.AVGSEC_A, G1.avgsec_T "
                    + "from "
                    + "( select "
                    + "t1.biz_type_id,"
                    + " b.name, "
                    + "(SELECT COUNT(*) FROM T_TICKET T2 WHERE T2.BIZ_TYPE_ID = T1.BIZ_TYPE_ID " + dateCon + " ) AS NB_T,"
                    + " (SELECT COUNT(*) FROM T_TICKET T2 WHERE T2.BIZ_TYPE_ID = T1.BIZ_TYPE_ID AND T2.STATUS = 4 " + dateCon + " ) AS NB_TT,"
                    + " (SELECT COUNT(*) FROM T_TICKET T2 WHERE T2.BIZ_TYPE_ID = T1.BIZ_TYPE_ID AND T2.STATUS = 2 " + dateCon + " ) AS NB_A,"
                    + " (SELECT COUNT(*) FROM T_TICKET T2 WHERE T2.BIZ_TYPE_ID = T1.BIZ_TYPE_ID AND DATE_PART('epoch'::text, T2.FINISH_TIME - T2.START_TIME)::numeric / 60::numeric <= 1 AND T2.STATUS = 4 " + dateCon + " ) AS NB_TL1,"
                    + " (SELECT COUNT(*) FROM T_TICKET T2 WHERE T2.BIZ_TYPE_ID = T1.BIZ_TYPE_ID AND T2.STATUS = 0 " + dateCon + " ) AS NB_SA,"
                    + " (SELECT AVG(DATE_PART('epoch'::text, T2.CALL_TIME - T2.TICKET_TIME)::numeric) FROM T_TICKET T2 WHERE T2.BIZ_TYPE_ID = T1.BIZ_TYPE_ID and T2.call_time is not null  " + dateCon + " ) AS AVGSEC_A, "
                    + " (SELECT AVG(DATE_PART('epoch'::text, T2.FINISH_TIME - T2.START_TIME)::numeric) FROM T_TICKET T2 WHERE T2.BIZ_TYPE_ID = T1.BIZ_TYPE_ID AND T2.STATUS = 4 " + dateCon + " ) AS AVGSEC_T FROM T_TICKET T1, T_BIZ_TYPE B WHERE T1.BIZ_TYPE_ID = B.ID AND TO_DATE(TO_CHAR(T1.TICKET_TIME,'YYYY-MM-DD'),'YYYY-MM-DD') BETWEEN TO_DATE('" + d1 + "','YYYY-MM-DD') AND TO_DATE('" + d2 + "','YYYY-MM-DD') GROUP BY T1.BIZ_TYPE_ID, B.NAME ) G1 ;";


            ResultSet r = con.createStatement().executeQuery(gblSQL2);
            while (r.next()) {
                JSONObject service = new JSONObject();
                String id = r.getString("biz_type_id");
                service.put("service_id", id);
                service.put("service_name", r.getString("name"));
                service.put("nb_t", r.getLong("nb_t"));
                service.put("nb_tt", r.getLong("nb_tt"));
                service.put("nb_a", r.getLong("nb_a"));
                service.put("nb_tl1", r.getLong("nb_tl1"));
                service.put("nb_sa", r.getLong("nb_sa"));
                service.put("perApT", r.getFloat("perApT"));
                service.put("PERTL1pt", r.getFloat("PERTL1pt"));
                service.put("perSApT", r.getFloat("perSApT"));
                service.put("avgSec_A", r.getFloat("avgSec_A"));

                long cibleA = 0;
                long cibleT = 0;
                CibleController cc = new CibleController();
                Cible cible = cc.getCibleById(id);
                if (cible != null) {
                    cibleA = cible.getCibleA();
                    cibleT = cible.getCibleT();
                }
                String cibleSQL = "SELECT G1.BIZ_TYPE_ID, "
                        + "G1.NAME, "
                        + "G1.NB_TT, "
                        + "G1.NB_CA, "
                        + "CASE "
                        + "WHEN G1.NB_TT::numeric = 0 "
                        + "OR G1.NB_CA::numeric = 0 THEN 0 "
                        + "ELSE CAST((G1.NB_CA::numeric / G1.NB_TT::numeric) * 100::numeric AS DECIMAL(10,2)) "
                        + "END AS PERCAPT, "
                        + "G1.NB_CT, "
                        + "CASE "
                        + "WHEN G1.NB_TT::numeric = 0 "
                        + "OR G1.NB_CT::numeric = 0 THEN 0 "
                        + "ELSE CAST((G1.NB_CT::numeric / G1.NB_TT::numeric) * 100::numeric AS DECIMAL(10,2)) "
                        + "END AS PERCTPT "
                        + "FROM "
                        + "(SELECT B.NAME, "
                        + "T1.BIZ_TYPE_ID, "
                        + "(SELECT COUNT(*) "
                        + "FROM T_TICKET T2 "
                        + "WHERE T2.BIZ_TYPE_ID = T1.BIZ_TYPE_ID "
                        + "AND T2.STATUS = 4  " + dateCon + " ) AS NB_TT, "
                        + " "
                        + "(SELECT COUNT(*) "
                        + "FROM T_TICKET T2 "
                        + "WHERE T2.BIZ_TYPE_ID = T1.BIZ_TYPE_ID "
                        + "AND DATE_PART('epoch'::text, T2.CALL_TIME - T2.TICKET_TIME)::numeric >  " + cibleA + " "
                        + "AND T2.STATUS = 4  " + dateCon + " ) AS NB_CA, "
                        + " "
                        + "(SELECT COUNT(*) "
                        + "FROM T_TICKET T2 "
                        + "WHERE T2.BIZ_TYPE_ID = T1.BIZ_TYPE_ID "
                        + "AND DATE_PART('epoch'::text, T2.FINISH_TIME - T2.START_TIME)::numeric >  " + cibleT + " "
                        + "AND T2.STATUS = 4  " + dateCon + ") AS NB_CT "
                        + "FROM T_TICKET T1, "
                        + "T_BIZ_TYPE B "
                        + "WHERE T1.BIZ_TYPE_ID = B.ID "
                        + " AND TO_DATE(TO_CHAR(T1.TICKET_TIME,'YYYY-MM-DD'),'YYYY-MM-DD') BETWEEN TO_DATE('" + d1 + "','YYYY-MM-DD') AND TO_DATE('" + d2 + "','YYYY-MM-DD') "
                        + "AND T1.BIZ_TYPE_ID = '" + id + "' "
                        + "GROUP BY T1.BIZ_TYPE_ID, "
                        + "B.NAME) G1 ; "
                        + "";
                ResultSet cib = con.createStatement().executeQuery(cibleSQL);
                if (cib.next()) {
                    service.put("nb_ca", cib.getLong("nb_ca"));
                    service.put("percapt", cib.getFloat("percapt"));
                    service.put("avgSec_T", r.getFloat("avgSec_T"));
                    service.put("nb_ct", cib.getLong("nb_ct"));
                    service.put("perctpt", cib.getFloat("perctpt"));
                } else {
                    service.put("nb_ca", 0);
                    service.put("percapt", 0.00);
                    service.put("avgSec_T", r.getFloat("avgSec_T"));
                    service.put("nb_ct", 0);
                    service.put("perctpt", 0.00);
                }
                table2.add(service);
            }

            con.close();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        all.put("result", table2);
        return all;
    }

}
