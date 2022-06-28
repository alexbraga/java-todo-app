package view;

import controller.TaskController;
import model.Project;
import model.Task;

import javax.swing.*;
import javax.swing.text.DateFormatter;
import javax.swing.text.DefaultFormatterFactory;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class TaskDialogScreen extends JDialog {
    private JPanel taskDialogPanel;
    private JButton submitButton;
    private JPanel titleBar;
    private JLabel description;
    private JLabel name;
    private JPanel formPanel;
    private JTextArea notesTextArea;
    private JFormattedTextField deadlineTextField;
    private JLabel deadline;
    private JLabel notes;
    private JTextField nameTextField;
    private JScrollPane notesScroll;
    private JScrollPane descriptionScroll;
    private JTextArea descriptionTextArea;
    private TaskController controller;
    private Task task;
    private Project project;

    public TaskDialogScreen() {
        this(new Task());
    }

    public TaskDialogScreen(Task task) {
        // Set window properties
        setContentPane(taskDialogPanel);
        setModal(true);
        getRootPane().setDefaultButton(submitButton);
        formatDeadlineField();
        cancelTask();

        this.task = task;

        if (task.getName() != null && task.getDeadline() != null) {
            // If the Task object passed as a parameter already has defined values, fill in the fields
            nameTextField.setText(task.getName());
            descriptionTextArea.setText(task.getDescription());
            notesTextArea.setText(task.getNotes());

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String deadline = dateFormat.format(task.getDeadline());
            deadlineTextField.setText(deadline);

            // The submit button will update the database
            updateTask();
        } else {
            // If the parent method receives a new Task object as a parameter, the submit button will save the new task to the database
            saveTask();
        }
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public void saveTask() {
        controller = new TaskController();

        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    if (!nameTextField.getText().isEmpty() && !deadlineTextField.getText().isEmpty()) {
                        // If the 'Name' and 'Deadline' fields are not empty, get input from all fields and save the new task
                        task.setName(nameTextField.getText());
                        task.setDescription(descriptionTextArea.getText());
                        task.setIsCompleted(false);
                        task.setNotes(notesTextArea.getText());
                        task.setProjectId(project.getId());

                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        Date deadline = dateFormat.parse(deadlineTextField.getText());
                        task.setDeadline(deadline);

                        controller.save(task);

                        JOptionPane.showMessageDialog(rootPane, "Task saved successfully.");
                        onOK();
                    } else {
                        JOptionPane.showMessageDialog(rootPane, "Task not saved. 'Name' and 'Deadline' cannot be empty.");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(rootPane, ex);
                }
            }
        });
    }

    public void updateTask() {
        controller = new TaskController();

        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    if (!nameTextField.getText().isEmpty() && !deadlineTextField.getText().isEmpty()) {
                        // If the 'Name' and 'Deadline' fields are not empty, get input from all fields and update the new task
                        task.setName(nameTextField.getText());
                        task.setDescription(descriptionTextArea.getText());
                        task.setNotes(notesTextArea.getText());

                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        Date deadline = dateFormat.parse(deadlineTextField.getText());
                        task.setDeadline(deadline);

                        controller.update(task);

                        JOptionPane.showMessageDialog(rootPane, "Task updated successfully.");
                        onOK();
                    } else {
                        JOptionPane.showMessageDialog(rootPane, "Task not updated. 'Name' and 'Deadline' cannot be empty.");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(rootPane, ex);
                }
            }
        });
    }

    public void cancelTask() {
        // call onCancel() on ESCAPE
        taskDialogPanel.registerKeyboardAction(new ActionListener() {
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

    public void formatDeadlineField() {
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        DateFormatter dateFormatter = new DateFormatter(format);
        deadlineTextField.setFormatterFactory(new DefaultFormatterFactory(dateFormatter));
    }

    public static void main(String[] args) {
        TaskDialogScreen dialog = new TaskDialogScreen();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
