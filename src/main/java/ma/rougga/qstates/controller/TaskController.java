package ma.rougga.qstates.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.UUID;
import ma.rougga.qstates.CPConnection;
import ma.rougga.qstates.modal.Task;
import org.slf4j.LoggerFactory;

public class TaskController {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(TaskController.class);

    public TaskController() {
    }

    public ArrayList<Task> getTasks() {
        ArrayList<Task> taches = new ArrayList<>();
        String SQL = "SELECT id,name,id_service FROM rougga_tasks order by id_service;";
        try (Connection con = new CPConnection().getConnection()) {
            ResultSet r = con.createStatement().executeQuery(SQL);
            while (r.next()) {
                taches.add(
                        new Task(
                                UUID.fromString(r.getString("id")),
                                r.getString("name"),
                                r.getString("id_service")
                        )
                );
            }
            con.close();
            return taches;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return taches;
        }

    }

    public boolean add(Task task) {
        try (Connection con = new CPConnection().getConnection()) {
            String SQL = "INSERT INTO rougga_tasks values (?,?,?);";
            PreparedStatement ps = con.prepareStatement(SQL);
            ps.setString(1, task.getId().toString());
            ps.setString(2, task.getName());
            ps.setString(3, task.getId_service());
            boolean result = ps.execute();
            con.close();
            return result;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
    }

    public boolean deleteById(UUID id) {
        try (Connection con = new CPConnection().getConnection()) {
            String SQL = "DELETE from rougga_tasks where id=?";
            PreparedStatement ps = con.prepareStatement(SQL);
            ps.setString(1, id.toString());
            boolean result = ps.execute();
            con.close();
            return result;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
    }

    public ArrayList<Task> getTasksByService(String id_service) {
        ArrayList<Task> taches = new ArrayList<>();
        try (Connection con = new CPConnection().getConnection()) {
            String SQL = "SELECT * from rougga_tasks where id_service=?";
            PreparedStatement ps = con.prepareStatement(SQL);
            ps.setString(1, id_service);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                taches.add(
                        new Task(
                                UUID.fromString(rs.getString("id")),
                                rs.getString("name"),
                                rs.getString("id_service")
                        )
                );
            }
            con.close();
            return taches;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return taches;
        }
    }

    public boolean setTaskToTicket(String id_task, String id_ticket, int qte) {
        try (Connection con = new CPConnection().getConnection()) {
            String SQL = "INSERT INTO rougga_ticket_task values (?,?,?)";
            PreparedStatement ps = con.prepareStatement(SQL);
            ps.setString(1, id_ticket);
            ps.setString(2, id_task);
            ps.setInt(3, qte);
            boolean result = ps.execute();
            con.close();
            return result;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
    }
}
