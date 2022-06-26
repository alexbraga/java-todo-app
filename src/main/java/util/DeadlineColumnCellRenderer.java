package util;

import model.Task;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.Date;

public class DeadlineColumnCellRenderer extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
        JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
        label.setHorizontalAlignment(CENTER);

        TaskTableModel taskTableModel = (TaskTableModel) table.getModel();
        Task task = taskTableModel.getTaskList().get(row);

        if (task.getDeadline().after(new Date())) {
            // If the deadline has not expired
            label.setBackground(Color.GREEN);
        } else {
            // If the deadline has expired
            label.setBackground(Color.RED);
        }

        return label;
    }
}
