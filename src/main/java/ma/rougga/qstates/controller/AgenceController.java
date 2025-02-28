package ma.rougga.qstates.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import ma.rougga.qstates.CPConnection;
import org.slf4j.LoggerFactory;

public  class AgenceController {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(AgenceController.class);

    public AgenceController() {
    }
    

    // Method to insert a new row
    public static void insertParameter(String name, String value) {
        String sql = "INSERT INTO rougga_pars (name, value) VALUES (?, ?)";

        try {
            Connection con = new CPConnection().getConnection();
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setString(2, value);
            stmt.executeUpdate();
            con.close();
            System.out.println("Inserted successfully: " + name + " = " + value);
            
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
    }

    // Method to get a value by name
    public static String getParameter(String name) {
        String sql = "SELECT value FROM rougga_pars WHERE name = ?";
        String value = null;

         try {
            Connection con = new CPConnection().getConnection();
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                value = rs.getString("value");
            }
            con.close();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return value;
    }

    // Method to update an existing row
    public static void updateParameter(String name, String newValue) {
        String sql = "UPDATE rougga_pars SET value = ? WHERE name = ?";

        try {
            Connection con = new CPConnection().getConnection();
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, newValue);
            stmt.setString(2, name);
            int rowsAffected = stmt.executeUpdate();
            con.close();
            if (rowsAffected > 0) {
                System.out.println("Updated successfully: " + name + " = " + newValue);
            } else {
                System.out.println("No record found with name: " + name);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
    }
    
    public static String getMaxAtt() {
        return getParameter("max_a");
    }
    public static String getGoalTr() {
        return getParameter("goal_t");
    }
    
    public String getBranchName() {
        String BRANCH_NAME = "BORNE ?";
        try {
            Connection con = new CPConnection().getConnection();
            ResultSet r = con.createStatement().executeQuery("SELECT value FROM t_basic_par where name='BRANCH_NAME';");
            if (r.next()) {
                BRANCH_NAME = r.getString("value");
            }
            con.close();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return BRANCH_NAME;
    }

}
