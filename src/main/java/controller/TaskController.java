package controller;

import model.Task;
import util.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaskController {
    public void save(Task task) throws SQLException {
        String sql = "INSERT INTO tasks (" +
                "name, " +
                "description, " +
                "completed, " +
                "notes, " +
                "deadline, " +
                "created_at, " +
                "updated_at, " +
                "project_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        Connection conn = null;
        PreparedStatement statement = null;

        try {
            conn = ConnectionFactory.getConnection();

            statement = conn.prepareStatement(sql);
            statement.setString(1, task.getName());
            statement.setString(2, task.getDescription());
            statement.setBoolean(3, task.isCompleted());
            statement.setString(4, task.getNotes());
            statement.setDate(5, new Date(task.getDeadline().getTime()));
            statement.setDate(6, new Date(task.getCreatedAt().getTime()));
            statement.setDate(7, new Date(task.getUpdatedAt().getTime()));
            statement.setInt(8, task.getProjectId());

            statement.execute();
        } catch (SQLException ex) {
            throw new SQLException("Error while saving new task: ", ex);
        } finally {
            ConnectionFactory.closeConnection(conn, statement);
        }
    }

    public List<Task> getAll(int projectId) throws SQLException {
        String sql = "SELECT * FROM tasks WHERE project_id = ?";

        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet result = null;

        List<Task> tasks = new ArrayList<>();

        try {
            conn = ConnectionFactory.getConnection();

            statement = conn.prepareStatement(sql);
            statement.setInt(1, projectId);

            result = statement.executeQuery();

            while (result.next()) {
                Task task = new Task();

                task.setId(result.getInt("id"));
                task.setName(result.getString("name"));
                task.setDescription(result.getString("description"));
                task.setIsCompleted(result.getBoolean("completed"));
                task.setNotes(result.getString("notes"));
                task.setDeadline(result.getDate("deadline"));
                task.setDeadline(result.getDate("created_at"));
                task.setDeadline(result.getDate("updated_at"));
                task.setProjectId(result.getInt("project_id"));

                tasks.add(task);
            }
        } catch (SQLException ex) {
            throw new SQLException("Error while retrieving tasks: ", ex);
        } finally {
            ConnectionFactory.closeConnection(conn, statement, result);
        }

        return tasks;
    }

    public void update(Task task) throws SQLException {
        String sql = "UPDATE tasks SET " +
                "name = ?, " +
                "description = ?, " +
                "completed = ?, " +
                "notes = ?, " +
                "deadline = ?," +
                "created_at = ?," +
                "updated_at = ?," +
                "project_id = ? " +
                "WHERE id = ?";

        Connection conn = null;
        PreparedStatement statement = null;

        try {
            conn = ConnectionFactory.getConnection();

            statement = conn.prepareStatement(sql);
            statement.setString(1, task.getName());
            statement.setString(2, task.getDescription());
            statement.setBoolean(3, task.isCompleted());
            statement.setString(4, task.getNotes());
            statement.setDate(5, new Date(task.getDeadline().getTime()));
            statement.setDate(6, new Date(task.getCreatedAt().getTime()));
            statement.setDate(7, new Date(task.getUpdatedAt().getTime()));
            statement.setInt(8, task.getProjectId());
            statement.setInt(9, task.getId());

            statement.execute();
        } catch (SQLException ex) {
            throw new SQLException("Error while updating the task: ", ex);
        } finally {
            ConnectionFactory.closeConnection(conn, statement);
        }
    }

    public void removeById(int taskId) throws SQLException {
        String sql = "DELETE FROM tasks WHERE id = ?";

        Connection conn = null;
        PreparedStatement statement = null;

        try {
            conn = ConnectionFactory.getConnection();

            statement = conn.prepareStatement(sql);
            statement.setInt(1, taskId);
            statement.execute();
        } catch (SQLException ex) {
            throw new SQLException("Error while removing task: ", ex);
        } finally {
            ConnectionFactory.closeConnection(conn, statement);
        }
    }
}
