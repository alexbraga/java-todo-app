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
    private TaskDialogScreen taskDialog;
    private ProjectController projectController;
    private TaskController taskController;
    private DefaultListModel<Project> projectModel;
    private TaskTableModel taskTableModel;

    public MainScreen() throws SQLException {
        initDataController();
        initComponentsModel();
        addProject();
        addTask();
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

        if (!projectModel.isEmpty()) {
            projectList.setSelectedIndex(0);
            int projectIndex = projectList.getSelectedIndex();
            Project project = projectModel.get(projectIndex);
            loadTasks(project.getId());
        }

        jTableClickedEvent();
        jListClickedEvent();
    }

    public void jTableClickedEvent() {
        taskTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int rowIndex = taskTable.rowAtPoint(e.getPoint());
                int columnIndex = taskTable.columnAtPoint(e.getPoint());
                Task task = taskTableModel.getTaskList().get(rowIndex);

                switch (columnIndex) {
                    case 3:
                        try {
                            taskController.update(task);
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }

                        break;
                    case 4:
                    case 5:
                    default:
                }
            }
        });
    }

    public void jListClickedEvent() {
        projectList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
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

    public void getProjectDialog() {
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
                getProjectDialog();
            }
        });
    }

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

    public void getTaskDialog() {
        taskDialog = new TaskDialogScreen();

        int projectIndex = projectList.getSelectedIndex();
        Project project = projectModel.get(projectIndex);
        taskDialog.setProject(project);

        taskDialog.setMinimumSize(new Dimension(500, 750));
        taskDialog.setResizable(false);
        taskDialog.setLocationRelativeTo(null);
        taskDialog.setVisible(true);

        refreshTaskList();
    }

    public void addTask() {
        addTaskButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                getTaskDialog();
            }
        });
    }

    public void refreshTaskList() {
        taskDialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                super.windowClosed(e);
                try {
                    int projectIndex = projectList.getSelectedIndex();
                    Project project = projectModel.get(projectIndex);
                    loadTasks(project.getId());
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(root, "Cannot refresh project list. Please, try again or restart the application.");
                }
            }
        });
    }

    public void showJTableTasks(boolean hasTasks) {
        if (hasTasks) {
            if (emptyTaskList.isVisible()) {
                emptyTaskList.setVisible(false);
//                taskListPanel.remove(emptyTaskList);
            }

//            taskListPanel.add(taskScrollPane);
            taskScrollPane.setVisible(true);
            taskScrollPane.setSize(taskListPanel.getWidth(), taskListPanel.getHeight());
        } else {
            if (taskScrollPane.isVisible()) {
                taskScrollPane.setVisible(false);
//                taskListPanel.remove(taskScrollPane);
            }

//            taskListPanel.add(emptyTaskList);
            emptyTaskList.setVisible(true);
            emptyTaskList.setSize(taskListPanel.getWidth(), taskListPanel.getHeight());
        }
    }

    public void formatTableStyle() {
        taskTable.getTableHeader().setFont(new Font("Noto Sans", Font.BOLD, 14));
        taskTable.getTableHeader().setBackground(new Color(0, 153, 102));
        taskTable.getTableHeader().setForeground(new Color(255, 255, 255));
        taskTable.setAutoCreateRowSorter(true);
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
