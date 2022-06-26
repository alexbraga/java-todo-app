package view;

import controller.ProjectController;
import controller.TaskController;
import model.Project;
import model.Task;
import util.TaskTableModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.List;

public class MainScreen {
    private JList<Project> projectList;
    private JPanel titleBar;
    private JLabel title;
    private JLabel subheading;
    private JPanel addProjectPanel;
    private JLabel projectsHeader;
    private JLabel addProjectButton;
    private JPanel addTaskPanel;
    private JLabel tasksHeader;
    private JLabel addTaskButton;
    private JPanel projectListPanel;
    private JPanel taskListPanel;
    private JPanel emptyTaskList;
    private JLabel emptyListIcon;
    private JTable taskTable;
    private JPanel root;
    private JScrollPane taskScrollPane;
    private ProjectDialogScreen projectDialog;
    private ProjectController projectController;
    private TaskController taskController;
    private DefaultListModel<Project> projectModel;
    private TaskTableModel taskTableModel;

    public MainScreen() throws SQLException {
        initDataController();
        initComponentsModel();
        formatTableStyle();
    }

    public void initDataController() {
        projectController = new ProjectController();
        taskController = new TaskController();
    }

    public void initComponentsModel() throws SQLException {
        projectModel = new DefaultListModel<>();
        loadProjects();

        taskTableModel = new TaskTableModel();
        taskTable.setModel(taskTableModel);

        // Auto-select first project on the list at launch and load its tasks
        if (!projectModel.isEmpty()) {
            projectList.setSelectedIndex(0);
            int projectIndex = projectList.getSelectedIndex();
            Project project = projectModel.get(projectIndex);
            loadTasks(project.getId());
        }

        jListClickedEvent();
        jTableClickedEvent();
        addProject();
        addTask();
    }

    public void jTableClickedEvent() {
        taskTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                // Detect mouse click at table cell and identify its row and column
                int rowIndex = taskTable.rowAtPoint(e.getPoint());
                int columnIndex = taskTable.columnAtPoint(e.getPoint());

                // Get task object from task list based on clicked row
                Task task = taskTableModel.getTaskList().get(rowIndex);

                switch (columnIndex) {
                    case 3:
                        // Toggle task "completed" status
                        try {
                            taskController.update(task);
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }

                        break;
                    case 4:
                        break;
                    case 5:
                        try {
                            taskController.removeById(task.getId());
                            JOptionPane.showMessageDialog(root, "Task deleted successfully.");
                            refreshTaskList();
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }

                        break;
                }
            }
        });
    }

    public void jListClickedEvent() {
        projectList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                // Load tasks based on selected project
                int projectIndex = projectList.getSelectedIndex();
                Project project = projectModel.get(projectIndex);
                try {
                    loadTasks(project.getId());
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    public void loadProjects() throws SQLException {
        List<Project> projects = projectController.getAll();
        projectModel.clear();

        for (Project project : projects) {
            projectModel.addElement(project);
        }

        projectList.setModel(projectModel);
    }

    public void loadTasks(int projectId) throws SQLException {
        List<Task> tasks = taskController.getAll(projectId);
        taskTableModel.setTaskList(tasks);
        showJTableTasks(!tasks.isEmpty());
    }

    // Open "add new project" dialog window
    public void showProjectDialog() {
        projectDialog = new ProjectDialogScreen();
        projectDialog.setMinimumSize(new Dimension(400, 415));
        projectDialog.setResizable(false);
        projectDialog.setLocationRelativeTo(null);
        projectDialog.setVisible(true);
        refreshProjectList();
    }

    public void addProject() {
        addProjectButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                showProjectDialog();
            }
        });
    }

    // Refresh project list after project dialog window is closed to include new saved projects, if any
    public void refreshProjectList() {
        projectDialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                super.windowClosed(e);
                try {
                    loadProjects();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(root, "Cannot refresh project list. Please, try again or restart the application.");
                }
            }
        });
    }

    // Open "add new task" dialog window
    public void showTaskDialog() {
        TaskDialogScreen taskDialog = new TaskDialogScreen();

        // Set project for new task based on selection
        int projectIndex = projectList.getSelectedIndex();
        Project project = projectModel.get(projectIndex);
        taskDialog.setProject(project);

        taskDialog.setMinimumSize(new Dimension(500, 750));
        taskDialog.setResizable(false);
        taskDialog.setLocationRelativeTo(null);
        taskDialog.setVisible(true);

        taskDialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                super.windowClosed(e);
                refreshTaskList();
            }
        });
    }

    public void addTask() {
        addTaskButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                showTaskDialog();
            }
        });
    }

    public void refreshTaskList() {
        try {
            int projectIndex = projectList.getSelectedIndex();
            Project project = projectModel.get(projectIndex);
            loadTasks(project.getId());
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(root, "Cannot refresh project list. Please, try again or restart the application.");
        }
    }

    public void showJTableTasks(boolean hasTasks) {
        if (hasTasks) {
            // If the selected project is not empty, hide and remove the panel containing "You have no tasks here" message
            if (emptyTaskList.isVisible()) {
                emptyTaskList.setVisible(false);
                taskListPanel.remove(emptyTaskList);
            }

            // Add and un-hide the table containing project's tasks
            taskListPanel.add(taskScrollPane);
            taskScrollPane.setVisible(true);
            taskScrollPane.setSize(taskListPanel.getWidth(), taskListPanel.getHeight());
        } else {
            // If the selected project is empty, hide and remove the table containing project's tasks
            if (taskScrollPane.isVisible()) {
                taskScrollPane.setVisible(false);
                taskListPanel.remove(taskScrollPane);
            }

            // Add and un-hide the panel containing "You have no tasks here" message
            taskListPanel.add(emptyTaskList);
            emptyTaskList.setVisible(true);
            emptyTaskList.setSize(taskListPanel.getWidth(), taskListPanel.getHeight());
        }
    }

    public void formatTableStyle() {
        taskTable.getTableHeader().setFont(new Font("Noto Sans", Font.BOLD, 14));
        taskTable.getTableHeader().setBackground(new Color(0, 153, 102));
        taskTable.getTableHeader().setForeground(new Color(255, 255, 255));
        taskTable.setAutoCreateRowSorter(false);
        taskTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        taskTable.getColumnModel().getColumn(0).setPreferredWidth(150);
        taskTable.getColumnModel().getColumn(1).setPreferredWidth(200);
        taskTable.getColumnModel().getColumn(2).setPreferredWidth(90);
        taskTable.getColumnModel().getColumn(3).setPreferredWidth(90);
    }

    public static void main(String[] args) throws SQLException {
        JFrame frame = new JFrame("MainScreen");
        frame.setContentPane(new MainScreen().root);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(900, 900));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
