package util;

import model.Task;
import javax.swing.table.AbstractTableModel;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class TaskTableModel extends AbstractTableModel {
    private List<Task> taskList;
    private String[] columns;

    public TaskTableModel() throws SQLException {
        createTable();
    }

    public void createTable() throws SQLException {
        taskList = new ArrayList<>();
        columns = new String[]{"Name", "Description", "Deadline", "Completed", "Edit", "Remove"};
    }

    @Override
    public int getRowCount() {
        return taskList.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return columns[columnIndex];
    }

    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 3;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (taskList.isEmpty()) {
            return Object.class;
        }

        return this.getValueAt(0, columnIndex).getClass();
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        taskList.get(rowIndex).setIsCompleted((Boolean) aValue);
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return taskList.get(rowIndex).getName();
            case 1:
                return taskList.get(rowIndex).getDescription();
            case 2:
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                return dateFormat.format(taskList.get(rowIndex).getDeadline());
            case 3:
                return taskList.get(rowIndex).isCompleted();
            case 4:
                return "";
            case 5:
                return "";
            default:
                return "Not found.";
        }
    }

    public List<Task> getTaskList() {
        return taskList;
    }

    public String[] getColumns() {
        return columns;
    }

    public void setTaskList(List<Task> taskList) {
        this.taskList = taskList;
    }
}
