package main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.ParserConfigurationException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.xml.sax.SAXException;

public class JsonGenerator {

    private String date1;
    private String date2;

    private final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    public String getFormatedTime(Float Sec) {
        int hours = (int) (Sec / 3600);
        int minutes = (int) ((Sec % 3600) / 60);
        int seconds = (int) (Sec % 60);
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    public JSONObject generateSimpleGblTable(String d1, String d2, HttpServletRequest request) throws IOException, ClassNotFoundException, SQLException, FileNotFoundException, ParserConfigurationException, SAXException {
        this.date1 = (d1 == null) ? format.format(new Date()) : d1;
        this.date2 = (d2 == null) ? format.format(new Date()) : d2;
        JSONObject all = new JSONObject();
        JSONArray result = new JSONArray();
        CfgHandler cfg = new CfgHandler(request);
        JSONArray table2 = new JSONArray();
        String dateCon = " and to_date(to_char(t2.ticket_time,'YYYY-MM-DD'),'YYYY-MM-DD')  BETWEEN TO_DATE('" + date1 + "','YYYY-MM-DD') AND TO_DATE('" + date2 + "','YYYY-MM-DD') ";

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
                + " (SELECT AVG(DATE_PART('epoch'::text, T2.FINISH_TIME - T2.START_TIME)::numeric) FROM T_TICKET T2 WHERE T2.BIZ_TYPE_ID = T1.BIZ_TYPE_ID AND T2.STATUS = 4 " + dateCon + " ) AS AVGSEC_T FROM T_TICKET T1, T_BIZ_TYPE B WHERE T1.BIZ_TYPE_ID = B.ID AND TO_DATE(TO_CHAR(T1.TICKET_TIME,'YYYY-MM-DD'),'YYYY-MM-DD') BETWEEN TO_DATE('" + date1 + "','YYYY-MM-DD') AND TO_DATE('" + date2 + "','YYYY-MM-DD') GROUP BY T1.BIZ_TYPE_ID, B.NAME ) G1 ;";

        String subTotalSQL = "SELECT G1.NB_T, "
                + "G1.NB_TT, "
                + "G1.NB_A, "
                + "G1.NB_TL1, "
                + "G1.NB_SA, "
                + "CASE "
                + "WHEN G1.NB_T::numeric = 0::numeric THEN 0::numeric "
                + "ELSE CAST((G1.NB_A::numeric / G1.NB_T::numeric) * 100::numeric AS DECIMAL(10,2)) "
                + "END AS PERAPT, "
                + "CASE "
                + "WHEN G1.NB_T::numeric = 0::numeric THEN 0::numeric "
                + "ELSE CAST((G1.NB_TL1::numeric / G1.NB_T::numeric) * 100::numeric AS DECIMAL(10,2)) "
                + "END AS PERTL1PT, "
                + "CASE "
                + "WHEN G1.NB_T::numeric = 0::numeric THEN 0::numeric "
                + "ELSE CAST((G1.NB_SA::numeric / G1.NB_T::numeric) * 100::numeric AS DECIMAL(10,2)) "
                + "END AS PERSAPT, "
                + "G1.AVGSEC_A, "
                + "G1.AVGSEC_T "
                + "FROM "
                + "(SELECT "
                + "(SELECT COUNT(*) "
                + "FROM T_TICKET T2 "
                + "WHERE 1 = 1 "
                + " " + dateCon + " ) AS NB_T, "
                + " "
                + "(SELECT COUNT(*) "
                + "FROM T_TICKET T2 "
                + "WHERE T2.STATUS = 4 "
                + " " + dateCon + " ) AS NB_TT, "
                + " "
                + "(SELECT COUNT(*) "
                + "FROM T_TICKET T2 "
                + "WHERE T2.STATUS = 2 "
                + " " + dateCon + " ) AS NB_A, "
                + " "
                + "(SELECT COUNT(*) "
                + "FROM T_TICKET T2 "
                + "WHERE DATE_PART('epoch'::text, T2.FINISH_TIME - T2.START_TIME)::numeric / 60::numeric <= 1 "
                + "AND T2.STATUS = 4 "
                + " " + dateCon + " ) AS NB_TL1, "
                + " "
                + "(SELECT COUNT(*) "
                + "FROM T_TICKET T2 "
                + "WHERE T2.STATUS = 0 "
                + " " + dateCon + " ) AS NB_SA, "
                + " "
                + "(SELECT AVG(DATE_PART('epoch'::text, T2.CALL_TIME - T2.TICKET_TIME)::numeric) "
                + "FROM T_TICKET T2 "
                + "WHERE T2.call_time is not null "
                + " " + dateCon + " ) AS AVGSEC_A, "
                + " "
                + "(SELECT AVG(DATE_PART('epoch'::text, T2.FINISH_TIME - T2.START_TIME)::numeric) "
                + "FROM T_TICKET T2 "
                + "WHERE T2.STATUS = 4 "
                + " " + dateCon + " ) AS AVGSEC_T "
                + "FROM T_TICKET T1 "
                + "WHERE TO_DATE(TO_CHAR(T1.TICKET_TIME,'YYYY-MM-DD'),'YYYY-MM-DD') BETWEEN TO_DATE('" + date1 + "','YYYY-MM-DD') AND TO_DATE('" + date2 + "','YYYY-MM-DD') limit 1 ) G1 ;";
        PgConnection con = new PgConnection();
        ResultSet r = con.getStatement().executeQuery(gblSQL2);
        while (r.next()) {
            JSONObject service = new JSONObject();
            JSONObject data = new JSONObject();
            String id = r.getString("biz_type_id");
            service.put("id", id);
            service.put("name", r.getString("name"));
            data.put("nb_t", (Object) r.getLong("nb_t"));
            data.put("nb_tt", r.getLong("nb_tt"));
            data.put("nb_a", r.getLong("nb_a"));
            data.put("nb_tl1", r.getLong("nb_tl1"));
            data.put("nb_sa", r.getLong("nb_sa"));
            data.put("perApT", r.getFloat("perApT"));
            data.put("PERTL1pt", r.getFloat("PERTL1pt"));
            data.put("perSApT", r.getFloat("perSApT"));
            data.put("avgSec_A", getFormatedTime(r.getFloat("avgSec_A")));

            int cibleA = cfg.getCibleA(id);
            int cibleT = cfg.getCibleT(id);

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
                    + " AND TO_DATE(TO_CHAR(T1.TICKET_TIME,'YYYY-MM-DD'),'YYYY-MM-DD') BETWEEN TO_DATE('" + date1 + "','YYYY-MM-DD') AND TO_DATE('" + date2 + "','YYYY-MM-DD') "
                    + "AND T1.BIZ_TYPE_ID = '" + id + "' "
                    + "GROUP BY T1.BIZ_TYPE_ID, "
                    + "B.NAME) G1 ; "
                    + "";
            ResultSet cib = con.getStatement().executeQuery(cibleSQL);
            if (cib.next()) {
                data.put("nb_ca", cib.getLong("nb_ca"));
                data.put("percapt", cib.getFloat("percapt") + "%");
                data.put("avgSec_T", getFormatedTime(r.getFloat("avgSec_T")));
                data.put("nb_ct", cib.getLong("nb_ct"));
                data.put("perctpt", cib.getFloat("perctpt") + "%");
            } else {
                data.put("nb_ca", "--");
                data.put("percapt", "--%");
                data.put("avgSec_T", getFormatedTime(r.getFloat("avgSec_T")));
                data.put("nb_ct", "--");
                data.put("perctpt", "-%");
            }
            service.put("data", data);
            table2.add(service);
        }

        con.closeConnection();
        all.put("result", table2);
        return all;
    }

}
