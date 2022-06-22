package main;

import controller.ProjectController;
import model.Project;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {
        ProjectController projectController = new ProjectController();
        Project project = new Project();

//        // CREATE PROJECT
//        project.setName("Another Test");
//        project.setDescription("Another description");
//        projectController.save(project);

//        //  GET ALL PROJECTS
//        List<Project> projects = projectController.getAll();
//        System.out.println("Total number of projects: " + projects.size());

//        // UPDATE PROJECT
//        project.setId(2);
//        project.setName("Yet Another Test");
//        project.setDescription("Yet another description");
//        projectController.update(project);

//        // REMOVE PROJECT
//        projectController.removeById(2);
    }
}
