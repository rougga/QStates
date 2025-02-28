package ma.rougga.qstates;

import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Stats {

    private static final Logger logger = LoggerFactory.getLogger(Stats.class);
    private final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    private String date1;
    private String date2;

    public Stats() {
    }

    public String getFormatedTime(Float Sec) {
        int hours = (int) (Sec / 3600);
        int minutes = (int) ((Sec % 3600) / 60);
        int seconds = (int) (Sec % 60);
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    public long getTotalTicket(String d1, String d2) {
        try {
            setDate1(d1);
            setDate2(d2);
            Connection con = new CPConnection().getConnection();
            String SQL = "Select count(*) from t_ticket where to_date(to_char(ticket_time,'YYYY-MM-DD'),'YYYY-MM-DD')  BETWEEN TO_DATE(?,'YYYY-MM-DD') AND TO_DATE(?,'YYYY-MM-DD');";
            PreparedStatement s = con.prepareStatement(SQL);
            s.setString(1, getDate1());
            s.setString(2, getDate2());
            ResultSet r = s.executeQuery();
            long nb;
            if (r.next()) {
                nb = r.getLong(1);
                r.close();
                con.close();
                return nb;
            }
            r.close();
            con.close();

        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return 0;
    }

    public long getDealTicket(String d1, String d2) {
        try {
            setDate1(d1);
            setDate2(d2);
            Connection con = new CPConnection().getConnection();
            String SQL = "Select count(*) from t_ticket where to_date(to_char(ticket_time,'YYYY-MM-DD'),'YYYY-MM-DD')  BETWEEN TO_DATE(?,'YYYY-MM-DD') AND TO_DATE(?,'YYYY-MM-DD') and status=4;";
            PreparedStatement s = con.prepareStatement(SQL);
            s.setString(1, getDate1());
            s.setString(2, getDate2());
            ResultSet r = s.executeQuery();
            if (r.next()) {
                long nb = r.getLong(1);
                r.close();
                con.close();
                return nb;
            }
            r.close();
            con.close();

        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return 0;
    }

    public long getAbsentTicket(String d1, String d2) {
        try {
            setDate1(d1);
            setDate2(d2);
            Connection con = new CPConnection().getConnection();
            String SQL = "Select count(*) from t_ticket where to_date(to_char(ticket_time,'YYYY-MM-DD'),'YYYY-MM-DD')  BETWEEN TO_DATE(?,'YYYY-MM-DD') AND TO_DATE(?,'YYYY-MM-DD') and status=2;";
            PreparedStatement s = con.prepareStatement(SQL);
            s.setString(1, getDate1());
            s.setString(2, getDate2());
            ResultSet r = s.executeQuery();
            if (r.next()) {
                long nb = r.getLong(1);
                r.close();
                con.close();
                return nb;
            }
            r.close();
            con.close();

        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return 0;
    }

    public long getWaitingTicket(String d1, String d2) {
        try {
            setDate1(d1);
            setDate2(d2);
            Connection con = new CPConnection().getConnection();
            String SQL = "Select count(*) from t_ticket where to_date(to_char(ticket_time,'YYYY-MM-DD'),'YYYY-MM-DD')  BETWEEN TO_DATE(?,'YYYY-MM-DD') AND TO_DATE(?,'YYYY-MM-DD') and status=0;";
            PreparedStatement s = con.prepareStatement(SQL);
            s.setString(1, getDate1());
            s.setString(2, getDate2());
            ResultSet r = s.executeQuery();
            if (r.next()) {
                long nb = r.getLong(1);
                r.close();
                con.close();
                return nb;
            }
            r.close();
            con.close();

        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return 0;
    }

    public String getMaxWaitTime(String d1, String d2) {
        try {
            setDate1(d1);
            setDate2(d2);
            Connection con = new CPConnection().getConnection();
            String SQL = "Select max(DATE_PART('epoch'::text, CALL_TIME - TICKET_TIME)::numeric) from t_ticket where to_date(to_char(ticket_time,'YYYY-MM-DD'),'YYYY-MM-DD')  BETWEEN TO_DATE(?,'YYYY-MM-DD') AND TO_DATE(?,'YYYY-MM-DD') and call_time is not null;";
            PreparedStatement s = con.prepareStatement(SQL);
            s.setString(1, getDate1());
            s.setString(2, getDate2());
            ResultSet r = s.executeQuery();
            if (r.next()) {
                String nb = getFormatedTime(r.getFloat(1));
                r.close();
                con.close();
                return nb;
            }
            r.close();
            con.close();

        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return "00:00:00";
    }

    public String getAvgWaitTime(String d1, String d2) {
        try {
            setDate1(d1);
            setDate2(d2);
            Connection con = new CPConnection().getConnection();
            String SQL = "Select avg(DATE_PART('epoch'::text, CALL_TIME - TICKET_TIME)::numeric) from t_ticket where to_date(to_char(ticket_time,'YYYY-MM-DD'),'YYYY-MM-DD')  BETWEEN TO_DATE(?,'YYYY-MM-DD') AND TO_DATE(?,'YYYY-MM-DD') and call_time is not null;";
            PreparedStatement s = con.prepareStatement(SQL);
            s.setString(1, getDate1());
            s.setString(2, getDate2());
            ResultSet r = s.executeQuery();
            if (r.next()) {
                String nb = getFormatedTime(r.getFloat(1));
                r.close();
                con.close();
                return nb;
            }
            r.close();
            con.close();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return "00:00:00";
    }

    public String getMaxDealTime(String d1, String d2) {
        try {
            setDate1(d1);
            setDate2(d2);
            Connection con = new CPConnection().getConnection();
            String SQL = "Select max(DATE_PART('epoch'::text, finish_time - start_time)::numeric) from t_ticket where to_date(to_char(ticket_time,'YYYY-MM-DD'),'YYYY-MM-DD')  BETWEEN TO_DATE(?,'YYYY-MM-DD') AND TO_DATE(?,'YYYY-MM-DD') and status=4;";
            PreparedStatement s = con.prepareStatement(SQL);
            s.setString(1, getDate1());
            s.setString(2, getDate2());
            ResultSet r = s.executeQuery();
            String nb;
            if (r.next()) {
                nb = getFormatedTime(r.getFloat(1));
                r.close();
                con.close();
                return nb;
            }
            r.close();
            con.close();

        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return "00:00:00";
    }

    public String getAvgDealTime(String d1, String d2) {
        try {
            setDate1(d1);
            setDate2(d2);
            Connection con = new CPConnection().getConnection();
            String SQL = "Select avg(DATE_PART('epoch'::text, finish_time - start_time)::numeric) from t_ticket where to_date(to_char(ticket_time,'YYYY-MM-DD'),'YYYY-MM-DD')  BETWEEN TO_DATE(?,'YYYY-MM-DD') AND TO_DATE(?,'YYYY-MM-DD') and status=4;";
            PreparedStatement s = con.prepareStatement(SQL);
            s.setString(1, getDate1());
            s.setString(2, getDate2());
            ResultSet r = s.executeQuery();
            String nb;
            if (r.next()) {
                nb = getFormatedTime(r.getFloat(1));
                r.close();
                con.close();
                return nb;
            }
            r.close();
            con.close();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return "00:00:00";
    }

    public List getWaitingTicketsByService(String d1, String d2, HttpServletResponse res) throws IOException {
        List<ArrayList> table = new ArrayList<>();
        try {
            setDate1(d1);
            setDate2(d2);
            Connection con = new CPConnection().getConnection();
            String SQL = "select "
                    + "b.name,"
                    + "b.id,"
                    + "(select count(*) from t_ticket t where t.biz_type_id=b.id and t.status=0 and to_date(to_char(t.ticket_time,'YYYY-MM-DD'),'YYYY-MM-DD')  BETWEEN TO_DATE(?,'YYYY-MM-DD') AND TO_DATE(?,'YYYY-MM-DD') )as nb_t"
                    + " from "
                    + " t_biz_type b;";
            PreparedStatement s = con.prepareStatement(SQL);
            s.setString(1, getDate1());
            s.setString(2, getDate2());
            ResultSet r = s.executeQuery();
            while (r.next()) {
                ArrayList row = new ArrayList();
                row.add(r.getString("name"));
                row.add(r.getLong("nb_t"));
                table.add(row);
            }
            con.close();
        } catch (Exception e) {
            logger.error(e.getMessage());
            res.sendRedirect("./home.jsp?err=" + URLEncoder.encode(e.getMessage(), "UTF-8"));
            return table;
        }
        return table;
    }

    public List getDealTicketsByService(String d1, String d2, HttpServletResponse res) throws IOException {
        List<ArrayList> table = new ArrayList<>();
        try {
            setDate1(d1);
            setDate2(d2);
            Connection con = new CPConnection().getConnection();
            String SQL = "select "
                    + "b.name,"
                    + "(select count(*) from t_ticket t where t.biz_type_id=b.id and t.status=4 and to_date(to_char(t.ticket_time,'YYYY-MM-DD'),'YYYY-MM-DD')  BETWEEN TO_DATE(?,'YYYY-MM-DD') AND TO_DATE(?,'YYYY-MM-DD') )as nb_t"
                    + " from "
                    + " t_biz_type b;";
            PreparedStatement s = con.prepareStatement(SQL);
            s.setString(1, getDate1());
            s.setString(2, getDate2());
            ResultSet r = s.executeQuery();
            while (r.next()) {
                ArrayList row = new ArrayList();
                row.add(r.getString("name"));
                row.add(r.getLong("nb_t"));
                table.add(row);
            }
            con.close();
        } catch (Exception e) {
            logger.error(e.getMessage());
            res.sendRedirect("./home.jsp?err=" + URLEncoder.encode(e.getMessage(), "UTF-8"));
        }
        return table;
    }

    public Map getDealTicketByServiceChart(String d1, String d2, HttpServletResponse res) throws IOException {
        Map map = new HashMap();
        List<ArrayList> table = getDealTicketsByService(d1, d2, res);
        String lable = "[", data = "[";
        for (int i = 0; i < table.size(); i++) {
            lable += "'" + table.get(i).get(0) + "',";
            data += table.get(i).get(1) + ",";
        }
        if (lable.lastIndexOf(",") >= 0) {
            lable = lable.substring(0, lable.lastIndexOf(","));
        }
        if (data.lastIndexOf(",") >= 0) {
            data = data.substring(0, data.lastIndexOf(","));
        }
        lable += "]";
        data += "]";
        map.put("lable", lable);
        map.put("data", data);
        return map;
    }

    public void setDate1(String date1) {
        if (Objects.equals(date1, null)) {
            this.date1 = format.format(new Date());
        } else {
            this.date1 = date1;
        }

    }

    public void setDate2(String date2) {
        if (Objects.equals(date2, null)) {
            this.date2 = format.format(new Date());
        } else {
            this.date2 = date2;
        }

    }

    public SimpleDateFormat getFormat() {
        return format;
    }

    public String getDate1() {
        return date1;
    }

    public String getDate2() {
        return date2;
    }

}
