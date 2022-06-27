package view;

import controller.ProjectController;
import model.Project;

import javax.swing.*;
import java.awt.event.*;

public class ProjectDialogScreen extends JDialog {
    private JPanel projectDialogPanel;
    private JButton submitButton;
    private JPanel buttonPanel;
    private JPanel titleBar;
    private JLabel title;
    private JLabel name;
    private JLabel description;
    private JPanel formPanel;
    private JTextArea descriptionTextArea;
    private JTextField nameTextField;
    private JButton buttonCancel;
    private ProjectController controller;
    private Project project;

    public ProjectDialogScreen() {
        setContentPane(projectDialogPanel);
        setModal(true);
        getRootPane().setDefaultButton(submitButton);
        saveProject();
        cancelProject();
    }

    public void saveProject() {
        controller = new ProjectController();
        project = new Project();

        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    if (!nameTextField.getText().equals("")) {
                        // If the 'Name' field is not empty, get user input and save the new project
                        project.setName(nameTextField.getText());
                        project.setDescription(descriptionTextArea.getText());
                        controller.save(project);

                        JOptionPane.showMessageDialog(rootPane, "Project saved successfully.");
                        onOK();
                    } else {
                        JOptionPane.showMessageDialog(rootPane, "Project not saved. 'Name' field cannot be empty.");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(rootPane, ex.getMessage());
                }
            }
        });
    }

    public void cancelProject() {
        // call onCancel() on ESCAPE
        projectDialogPanel.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        // add your code here
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        ProjectDialogScreen dialog = new ProjectDialogScreen();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }

}
