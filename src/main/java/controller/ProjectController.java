package controller;

import model.Project;
import util.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProjectController {
    public void save(Project project) throws SQLException {
        String sql = "INSERT INTO projects (" +
                "name, " +
                "description, " +
                "created_at, " +
                "updated_at) VALUES (?, ?, ?, ?)";

        Connection conn = null;
        PreparedStatement statement = null;

        try {
            conn = ConnectionFactory.getConnection();

            statement = conn.prepareStatement(sql);
            statement.setString(1, project.getName());
            statement.setString(2, project.getDescription());
            statement.setDate(3, new Date(project.getCreatedAt().getTime()));
            statement.setDate(4, new Date(project.getUpdatedAt().getTime()));

            statement.execute();
        } catch (SQLException ex) {
            throw new SQLException("Error while saving new project: ", ex);
        } finally {
            ConnectionFactory.closeConnection(conn, statement);
        }
    }

    public List<Project> getAll() throws SQLException {
        String sql = "SELECT * FROM projects";

        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet result = null;

        List<Project> projects = new ArrayList<>();

        try {
            conn = ConnectionFactory.getConnection();

            statement = conn.prepareStatement(sql);

            result = statement.executeQuery();

            while (result.next()) {
                Project project = new Project();

                project.setId(result.getInt("id"));
                project.setName(result.getString("name"));
                project.setCreatedAt(result.getDate("created_at"));
                project.setUpdatedAt(result.getDate("updated_at"));

                projects.add(project);
            }
        } catch (SQLException ex) {
            throw new SQLException("Error while retrieving projects: ", ex);
        } finally {
            ConnectionFactory.closeConnection(conn, statement, result);
        }

        return projects;
    }

    public void update(Project project) throws SQLException {
        String sql = "UPDATE projects SET " +
                "name = ?, " +
                "description = ?, " +
                "created_at = ?, " +
                "updated_at = ? " +
                "WHERE id = ?";

        Connection conn = null;
        PreparedStatement statement = null;

        try {
            conn = ConnectionFactory.getConnection();

            statement = conn.prepareStatement(sql);
            statement.setString(1, project.getName());
            statement.setString(2, project.getDescription());
            statement.setDate(3, new Date(project.getCreatedAt().getTime()));
            statement.setDate(4, new Date(project.getUpdatedAt().getTime()));
            statement.setInt(5, project.getId());

            statement.execute();
        } catch (SQLException ex) {
            throw new SQLException("Error while updating the project: ", ex);
        } finally {
            ConnectionFactory.closeConnection(conn, statement);
        }
    }

    public void removeById(int projectId) throws SQLException {
        String sql = "DELETE FROM projects WHERE id = ?";

        Connection conn = null;
        PreparedStatement statement = null;

        try {
            conn = ConnectionFactory.getConnection();

            statement = conn.prepareStatement(sql);
            statement.setInt(1, projectId);
            statement.execute();
        } catch (SQLException ex) {
            throw new SQLException("Error while removing project: ", ex);
        } finally {
            ConnectionFactory.closeConnection(conn, statement);
        }
    }
}
