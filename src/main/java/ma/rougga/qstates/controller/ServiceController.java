package ma.rougga.qstates.controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import ma.rougga.qstates.modal.Service;

public class ServiceController {

    private Statement stm;

    public ServiceController(Statement stm) {
        this.stm = stm;
    }

    public ArrayList<Service> getAll() {
        try {
            ArrayList<Service> services = new ArrayList();

            ResultSet r = stm.executeQuery("select * from t_biz_type;");
            while (r.next()) {
                services.add(
                        new Service(
                                r.getString("id"),
                                r.getString("name"),
                                r.getString("biz_prefix"),
                                r.getInt("status"),
                                r.getString("start_num"),
                                r.getInt("sort"),
                                r.getInt("call_delay"),
                                r.getString("biz_class_id"),
                                r.getInt("deal_time_warning"),
                                r.getInt("hidden"),
                                null)
                );
            }
            //stm.getConnection().close();
            return services;
        } catch (Exception ex) {
            Logger.getLogger(ServiceController.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public Service getById(String id) {
        try {

            PreparedStatement p = stm.getConnection().prepareStatement("select * from t_biz_type where id=? ;");
            p.setString(1, id);
            ResultSet r = p.executeQuery();
            if (r.next()) {
                Service s = (new Service(
                        r.getString("id"),
                        r.getString("name"),
                        r.getString("biz_prefix"),
                        r.getInt("status"),
                        r.getString("start_num"),
                        r.getInt("sort"),
                        r.getInt("call_delay"),
                        r.getString("biz_class_id"),
                        r.getInt("deal_time_warning"),
                        r.getInt("hidden"),
                        null));
                
                //stm.getConnection().close();
                return s;
            } else {
                //stm.getConnection().close();
                return null;
            }
        } catch (Exception ex) {
            Logger.getLogger(ServiceController.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    
}
