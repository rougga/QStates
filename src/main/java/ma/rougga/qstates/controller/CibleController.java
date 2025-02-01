package ma.rougga.qstates.controller;

import ma.rougga.qstates.modal.Cible;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import ma.rougga.qstates.CPConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CibleController {

    private static final Logger logger = LoggerFactory.getLogger(CibleController.class);

    public CibleController() {
    }

    // Create a new Cible
    public boolean addCible(Cible cible) {
        String sql = "INSERT INTO rougga_cibles (service_id, service_name, cible_a, cible_t, cible_per) VALUES (?, ?, ?, ?, ?)";

        try {
            Connection con = new CPConnection().getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql);

            pstmt.setString(1, cible.getServiceId());
            pstmt.setString(2, cible.getServiceName());
            pstmt.setLong(3, cible.getCibleA());
            pstmt.setLong(4, cible.getCibleT());
            pstmt.setDouble(5, cible.getCiblePer());

            boolean result = pstmt.executeUpdate() > 0;
            pstmt.close();
            con.close();
            return result;

        } catch (SQLException e) {
            logger.error(e.getMessage());
            return false;
        }
    }

    // Get all Cibles
    public List<Cible> getAllCibles() {
        List<Cible> cibles = new ArrayList<>();
        String sql = "SELECT * FROM rougga_cibles";

        try {
            Connection con = new CPConnection().getConnection();
            ResultSet rs = con.createStatement().executeQuery(sql);

            while (rs.next()) {
                cibles.add(new Cible(
                        rs.getString("service_id"),
                        rs.getString("service_name"),
                        rs.getLong("cible_a"),
                        rs.getLong("cible_t"),
                        rs.getDouble("cible_per")
                ));
            }
            rs.close();
            con.close();

        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return cibles;
    }

    // Get Cible by ID
    public Cible getCibleById(String serviceId) {
        String sql = "SELECT * FROM rougga_cibles WHERE service_id = ?";

        try {
            Connection con = new CPConnection().getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, serviceId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Cible cible = new Cible(
                        rs.getString("service_id"),
                        rs.getString("service_name"),
                        rs.getLong("cible_a"),
                        rs.getLong("cible_t"),
                        rs.getDouble("cible_per")
                );
                rs.close();
                pstmt.close();
                con.close();
                return cible;
            }
            rs.close();
            pstmt.close();
            con.close();

        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    // Update Cible
    public boolean updateCible(Cible updatedCible) {
        String sql = "UPDATE rougga_cibles SET service_name = ?, cible_a = ?, cible_t = ?, cible_per = ? WHERE service_id = ?";

        try {
            Connection con = new CPConnection().getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql);

            pstmt.setString(1, updatedCible.getServiceName());
            pstmt.setLong(2, updatedCible.getCibleA());
            pstmt.setLong(3, updatedCible.getCibleT());
            pstmt.setDouble(4, updatedCible.getCiblePer());
            pstmt.setString(5, updatedCible.getServiceId());

            boolean result = pstmt.executeUpdate() > 0;
            pstmt.close();
            con.close();
            return result;

        } catch (SQLException e) {
            logger.error(e.getMessage());
            return false;
        }
    }

    // Delete Cible
    public boolean deleteCible(String serviceId) {
        String sql = "DELETE FROM rougga_cibles WHERE service_id = ?";

        try {
            Connection con = new CPConnection().getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, serviceId);

            boolean result = pstmt.executeUpdate() > 0;
            pstmt.close();
            con.close();
            return result;

        } catch (SQLException e) {
            logger.error(e.getMessage());
            return false;
        }
    }

}
