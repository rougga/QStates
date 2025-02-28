package ma.rougga.qstates.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ma.rougga.qstates.CPConnection;
import ma.rougga.qstates.modal.Title;
import org.slf4j.LoggerFactory;

public class TitleController {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(TitleController.class);

    public TitleController() {
    }

    public boolean addTitle(Title title) {
        String sql = "INSERT INTO rougga_titles (type, title) VALUES (?, ?)";

        try {
            Connection con = new CPConnection().getConnection();
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, title.getName());
            stmt.setString(2, title.getValue());
            stmt.executeUpdate();
            con.close();
            return true;
        } catch (SQLException e) {
            logger.error(e.getMessage());
            return false;
        }
    }

    // Method to fetch all titles from the table
    public List<Title> getAllTitles() {
        List<Title> titles = new ArrayList<>();
        String sql = "SELECT type, title FROM rougga_titles";

        try {
            Connection con = new CPConnection().getConnection();
            ResultSet rs = con.createStatement().executeQuery(sql);
            while (rs.next()) {
                String type = rs.getString("type");
                String title = rs.getString("title");
                titles.add(new Title(type, title));
            }
            con.close();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return titles;
    }

    // Method to fetch a title by type
    public Title getTitleByType(String type) {
        String sql = "SELECT title FROM rougga_titles WHERE type = ?";
        Title title = null;

        try {
            Connection con = new CPConnection().getConnection();
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, type);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String titleValue = rs.getString("title");
                    title = new Title(type, titleValue);
                }
            }
            con.close();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return title;
    }

    // Method to update a title
    public boolean updateTitle(Title title) {
        String sql = "UPDATE rougga_titles SET title = ? WHERE type = ?";

        try {
            Connection con = new CPConnection().getConnection();
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, title.getValue());
            stmt.setString(2, title.getName());
            int rowsUpdated = stmt.executeUpdate();

            if (rowsUpdated > 0) {
                con.close();
                return true;
            } else {
                con.close();
                return false;
            }

        } catch (SQLException e) {
            logger.error(e.getMessage());
            return false;
        }
    }

    // Method to delete a title by type
    public boolean deleteTitle(String type) {
        String sql = "DELETE FROM rougga_titles WHERE type = ?";

        try {
            Connection con = new CPConnection().getConnection();
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, type);
            int rowsDeleted = stmt.executeUpdate();

            if (rowsDeleted > 0) {
                con.close();
                return true;
            } else {
                con.close();
                return false;
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
            return false;
        }
    }

    public String getGblTitle() {
        String title = "Rapport Globale";
        Title t = this.getTitleByType("gbl");
        if (t != null) {
            title = t.getValue();
        }
        return title;
    }

    public String getEmpTitle() {
        String title = "Rapport Employé";
        Title t = this.getTitleByType("emp");
        if (t != null) {
            title = t.getValue();
        }
        return title;
    }

    public String getEmpSerTitle() {
        String title = "Rapport Employé par service";
        Title t = this.getTitleByType("empser");
        if (t != null) {
            title = t.getValue();
        }
        return title;
    }

    public String getGchTitle() {
        String title = "Rapport Guichet";
        Title t = this.getTitleByType("gch");
        if (t != null) {
            title = t.getValue();
        }
        return title;
    }

    public String getGchServTitle() {
        String title = "Rapport Guichet par service";
        Title t = this.getTitleByType("gchserv");
        if (t != null) {
            title = t.getValue();
        }
        return title;
    }

    public String getGlaTitle() {
        String title = "Grille d attente";
        Title t = this.getTitleByType("gla");
        if (t != null) {
            title = t.getValue();
        }
        return title;
    }

    public String getGltTitle() {
        String title = "Grille de traitement";
        Title t = this.getTitleByType("glt");
        if (t != null) {
            title = t.getValue();
        }
        return title;
    }

    public String getNdtTitle() {
        String title = "Nombre de tickets edités";
        Title t = this.getTitleByType("ndt");
        if (t != null) {
            title = t.getValue();
        }
        return title;
    }

    public String getNdttTitle() {
        String title = "Nombre de tickets traités";
        Title t = this.getTitleByType("ndtt");
        if (t != null) {
            title = t.getValue();
        }
        return title;
    }

    public String getNdtaTitle() {
        String title = "Nombre de tickets absents";
        Title t = this.getTitleByType("ndta");
        if (t != null) {
            title = t.getValue();
        }
        return title;
    }

    public String getNdtsaTitle() {
        String title = "Nombre de tickets sans affectation";
        Title t = this.getTitleByType("ndtsa");
        if (t != null) {
            title = t.getValue();
        }
        return title;
    }

    public String getCnxTitle() {
        String title = "Connexions";
        Title t = this.getTitleByType("cnx");
        if (t != null) {
            title = t.getValue();
        }
        return title;
    }

    public String getRempTitle() {
        String title = "Rendement Employés";
        Title t = this.getTitleByType("remp");
        if (t != null) {
            title = t.getValue();
        }
        return title;
    }

    public String getSerTitle() {
        String title = "Supervision: Service";
        Title t = this.getTitleByType("ser");
        if (t != null) {
            title = t.getValue();
        }
        return title;
    }

    public String getSgchTitle() {
        String title = "Supervision: Guichet";
        Title t = this.getTitleByType("sgch");
        if (t != null) {
            title = t.getValue();
        }
        return title;
    }

    public String getAplTitle() {
        String title = "Détail des appels";
        Title t = this.getTitleByType("apl");
        if (t != null) {
            title = t.getValue();
        }
        return title;
    }

    public String getTaskTitle() {
        String title = "Rapport des Taches";
        Title t = this.getTitleByType("tch");
        if (t != null) {
            title = t.getValue();
        }
        return title;
    }
}
