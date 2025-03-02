package ma.rougga.qstates.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import ma.rougga.qstates.CPConnection;
import ma.rougga.qstates.CfgHandler;
import ma.rougga.qstates.modal.Utilisateur;
import org.slf4j.LoggerFactory;

public class UtilisateurController {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(UtilisateurController.class);

    public UtilisateurController() {
    }

    public List<Utilisateur> getAllUtilisateur() {
        try (Connection con = new CPConnection().getConnection()) {
            List<Utilisateur> utilisateurs = new ArrayList();
            ResultSet r = con.createStatement().executeQuery("select * from rougga_users order by date;");
            while (r.next()) {
                utilisateurs.add(
                        new Utilisateur(
                                UUID.fromString(r.getString("id")),
                                r.getString("username"),
                                r.getString("password"),
                                r.getString("grade"),
                                r.getString("first_name"),
                                r.getString("last_name"),
                                CfgHandler.getFormatedDateAsDate(r.getString("date")),
                                r.getString("sponsor"))
                );
            }
            con.close();
            return utilisateurs;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }

    }

    public Utilisateur getUtilisateurById(UUID id) {
        Utilisateur u = null;
        try (Connection con = new CPConnection().getConnection()) {
            PreparedStatement ps = con.prepareStatement("select * from rougga_users where id=?;");
            ps.setString(1, id.toString());
            ResultSet r = ps.executeQuery();
            if (r.next()) {
                u = new Utilisateur(
                        UUID.fromString(r.getString("id")),
                        r.getString("username"),
                        r.getString("password"),
                        r.getString("grade"),
                        r.getString("first_name"),
                        r.getString("last_name"),
                        CfgHandler.getFormatedDateAsDate(r.getString("date")),
                        r.getString("sponsor"));
            }
            con.close();
            return u;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    public Utilisateur getUtilisateurByUsername(String username) {
        Utilisateur u = null;
        try (Connection con = new CPConnection().getConnection()) {
            PreparedStatement ps = con.prepareStatement("select id from rougga_users where username=?;");
            ps.setString(1, username);
            ResultSet r = ps.executeQuery();
            if (r.next()) {
                u = getUtilisateurById(UUID.fromString(r.getString("id")));
            }
            con.close();
            return u;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    public boolean AddUtilisateur(Utilisateur u) {
        try (Connection con = new CPConnection().getConnection()) {
            PreparedStatement p = con.prepareStatement("insert into rougga_users (id,username,password,grade,first_name,last_name,sponsor,date) values(?,?,?,?,?,?,?,?);");
            p.setString(1, u.getId().toString());
            p.setString(2, u.getUsername());
            p.setString(3, u.getPassword());
            p.setString(4, u.getGrade());
            p.setString(5, u.getFirstName());
            p.setString(6, u.getLastName());
            p.setString(7, u.getSponsor());
            p.setString(8, CfgHandler.getFormatedDateAsString(u.getDate()));
            p.execute();
            con.close();
            return true;

        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
    }

    public boolean deleteUtilisateurById(UUID id) {
        try (Connection con = new CPConnection().getConnection()) {
            PreparedStatement p = con.prepareStatement("delete from rougga_users where id=?;");
            p.setString(1, id.toString());
            p.execute();
            con.close();
            return true;

        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
    }

    public String getBranchName() {
        String BRANCH_NAME = "BORNE";
        try (Connection con = new CPConnection().getConnection()) {
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
