package gui;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import ctrl.TaskCtrl;
import model.Shift;

/**
 * The AddShiftDialog class provides a GUI dialog for adding a new shift to a task.
 * 
 * <p>This dialog allows the user to input a start time, end time, and employee ID, and then
 * saves the shift to the database through the {@link TaskCtrl} class.</p>
 */
public class AddShiftDialog extends JDialog {
    private JTextField txtStartTime;
    private JTextField txtEndTime;
    private JTextField txtEmployee;
    private JTable shiftsTable;
    private TaskCtrl tc;

    /**
     * Constructs an AddShiftDialog with references to a JTable for displaying shifts, 
     * a specified date for the shift, and a {@link TaskCtrl} instance for managing tasks.
     * 
     * @param shiftsTable the JTable where the shift information will be displayed
     * @param date the LocalDate representing the date of the shift
     * @param tc the TaskCtrl instance used to manage and persist the shift data
     */
    public AddShiftDialog(JTable shiftsTable, LocalDate date, TaskCtrl tc) {
        this.tc = tc;
        this.shiftsTable = shiftsTable;
        setTitle("Add shift");
        setBounds(100, 100, 400, 300);
        setLayout(null);
        setModal(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JLabel lblStartTime = new JLabel("Starttime:");
        lblStartTime.setBounds(20, 20, 100, 25);
        add(lblStartTime);

        txtStartTime = new JTextField();
        txtStartTime.setBounds(130, 20, 200, 25);
        add(txtStartTime);

        JLabel lblEndTime = new JLabel("Endtime:");
        lblEndTime.setBounds(20, 60, 100, 25);
        add(lblEndTime);

        txtEndTime = new JTextField();
        txtEndTime.setBounds(130, 60, 200, 25);
        add(txtEndTime);

        JLabel lblEmployee = new JLabel("Employee:");
        lblEmployee.setBounds(20, 100, 100, 25);
        add(lblEmployee);

        txtEmployee = new JTextField();
        txtEmployee.setBounds(130, 100, 200, 25);
        add(txtEmployee);

        JButton btnAdd = new JButton("Add");
        btnAdd.setBounds(130, 150, 100, 30);
        btnAdd.addActionListener(e -> {
            try {
                saveShift(date);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
        add(btnAdd);
    }

    /**
     * Validates user input, parses the start and end times, parses the employee ID, 
     * and then uses the {@link TaskCtrl} to add and save the shift to the database.
     * 
     * <p>If validation or saving fails, appropriate error messages are shown to the user. 
     * If successful, the new shift is added to the JTable's model and the dialog is closed.</p>
     * 
     * @param date the LocalDate representing the date for the shift
     */
    private void saveShift(LocalDate date) {
        try {
            // Prepare the time format and regex for validation
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
            String regex = "^([01]\\d|2[0-3]):[0-5]\\d$";
            Pattern pattern = Pattern.compile(regex);

            // Validate and parse start time
            String startTime = txtStartTime.getText();
            Matcher startMatcher = pattern.matcher(startTime);
            if (!startMatcher.matches()) {
                throw new IllegalArgumentException("Invalid time format. Use HH:mm");
            }
            LocalTime localTime = LocalTime.parse(startTime, timeFormatter);
            LocalDateTime localTimeDate = LocalDateTime.of(date, localTime);

            // Validate and parse end time
            String endTime = txtEndTime.getText();
            Matcher endMatcher = pattern.matcher(endTime);
            if (!endMatcher.matches()) {
                throw new IllegalArgumentException("Invalid time format. Use HH:mm");
            } 
            LocalTime localTimeEnd = LocalTime.parse(endTime, timeFormatter);
            LocalDateTime localTimeDateEnd = LocalDateTime.of(date, localTimeEnd);

            // Check that end time is not before start time
            if (localTimeDateEnd.isBefore(localTimeDate)) {
                throw new IllegalArgumentException("Endtime is before starttime, try again");
            }

            // Create and add the shift to the current task
            tc.addShift(localTimeDate, localTimeDateEnd);

            // Parse the employee ID
            String employee = txtEmployee.getText();
            int employeeId = Integer.parseInt(employee);

            // Add employee to the shift and save
            tc.addEmployeeToShift(employeeId);

            // If all is successful, update the table and close the dialog
            DefaultTableModel model = (DefaultTableModel) shiftsTable.getModel();
            model.addRow(new Object[] { startTime, endTime, employee });
            dispose();
            JOptionPane.showMessageDialog(this, "Shift saved: " + startTime + " - " + endTime);

        } catch (DateTimeParseException e) {
            // Handle invalid time format parsing
            JOptionPane.showMessageDialog(this, "Invalid time format. Use HH:mm", "Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException e) {
            // Handle invalid employee ID format
            JOptionPane.showMessageDialog(this, "Employee_ID has to be valid", "Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException e) {
            // Handle validation errors
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            // Handle any other errors, including database issues
            JOptionPane.showMessageDialog(this, "A Error has occurred when trying to save employee", "Error",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

}
