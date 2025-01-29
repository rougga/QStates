package ma.rougga.qstates.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import ma.rougga.qstates.modal.Task;

public class TaskController {
    private Connection con;
    private Statement stm;

    public TaskController(Statement stm) {
        this.stm = stm;
    }

    public ArrayList<Task> getTasks() {
        ArrayList<Task> taches = new ArrayList<>();
        String SQL = "SELECT id,name,id_service FROM rougga_task order by id_service;";
        try {
            ResultSet r = stm.executeQuery(SQL);
            while (r.next()) {
                taches.add(new Task(UUID.fromString(r.getString("id")), r.getString("name"), r.getString("id_service")));
            }
            return taches;
        } catch (SQLException ex) {
            Logger.getLogger(TaskController.class.getName()).log(Level.SEVERE, null, ex);
            return taches;
        }

    }

    public boolean add(Task task) {
        try {
            String SQL = "INSERT INTO rougga_task values (?,?,?);";
            PreparedStatement ps = stm.getConnection().prepareStatement(SQL);
            ps.setString(1, task.getId().toString());
            ps.setString(2, task.getName());
            ps.setString(3, task.getId_service());
            return ps.execute();
        } catch (SQLException ex) {
            Logger.getLogger(TaskController.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean deleteById(UUID id) {
        try {
            String SQL = "DELETE from rougga_task where id=?";
            PreparedStatement ps = stm.getConnection().prepareStatement(SQL);
            ps.setString(1, id.toString());
            ps.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(TaskController.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public ArrayList<Task> getTasksByService(String id_service) {
        ArrayList<Task> taches = new ArrayList<>();
        try {
            String SQL = "SELECT * from rougga_task where id_service=?";
            PreparedStatement ps = stm.getConnection().prepareStatement(SQL);
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
            return taches;
        } catch (SQLException ex) {
            Logger.getLogger(TaskController.class.getName()).log(Level.SEVERE, null, ex);
            return taches;
        }
    }

    public boolean setTaskToTicket(String id_task, String id_ticket, int qte) {
        try {
            String SQL ="INSERT INTO rougga_ticket_task values (?,?,?)";
            PreparedStatement ps = stm.getConnection().prepareCall(SQL);
            ps.setString(1, id_ticket);
            ps.setString(2, id_task);
            ps.setInt(3, qte);
            ps.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(TaskController.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
}
