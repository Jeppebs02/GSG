package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import ctrl.TaskCtrl;

import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.sql.SQLException;

/**
 * The AddTaskDialog class provides a GUI dialog for creating a new task, 
 * adding shifts to it, and saving it along with associated details (such as user and location).
 * 
 * <p>This dialog allows the user to:
 * <ul>
 *     <li>Enter task information (description, location, user ID).</li>
 *     <li>Specify the date of the task.</li>
 *     <li>Add one or more shifts to the task using the {@link AddShiftDialog}.</li>
 *     <li>Save the task and its shifts to a provided data structure and the database via {@link TaskCtrl}.</li>
 * </ul>
 */
public class AddTaskDialog extends JDialog {
    private JTextField txtDescription;
    private JTextField txtLocation;
    private JTextField txtUser;
    private JTextField txtDate;
    private JTable shiftsTable;
    private List<String> shifts;
    private TaskCtrl tc;

    /**
     * Constructs an AddTaskDialog instance, initializing the GUI components, setting default values,
     * and preparing the dialog for user input.
     * 
     * @param date     the default date to use for the task, typically passed from a calendar or scheduler view.
     * @param taskMap  a HashMap where the key is a LocalDate and the value is a list of task details. 
     *                 This dialog adds the newly created task to this map.
     * @throws SQLException if there's an error initializing the underlying TaskCtrl or database operations.
     */
    public AddTaskDialog(LocalDate date, HashMap<LocalDate, List<String>> taskMap) throws SQLException {
        tc = new TaskCtrl();
        setTitle("Tilføj Opgave");
        setBounds(100, 100, 600, 600);
        getContentPane().setLayout(null);
        shifts = new ArrayList<>();

        JLabel lblDescription = new JLabel("Beskrivelse:");
        lblDescription.setBounds(20, 20, 100, 25);
        getContentPane().add(lblDescription);

        txtDescription = new JTextField();
        txtDescription.setBounds(130, 20, 400, 25);
        getContentPane().add(txtDescription);

        JLabel lblLocation = new JLabel("Lokation:");
        lblLocation.setBounds(20, 60, 100, 25);
        getContentPane().add(lblLocation);

        txtLocation = new JTextField();
        txtLocation.setBounds(130, 60, 400, 25);
        getContentPane().add(txtLocation);

        JLabel lblUser = new JLabel("Bruger:");
        lblUser.setBounds(20, 100, 100, 25);
        getContentPane().add(lblUser);

        txtUser = new JTextField();
        txtUser.setBounds(130, 100, 400, 25);
        getContentPane().add(txtUser);

        JLabel lblDate = new JLabel("Dato:");
        lblDate.setBounds(20, 180, 100, 25);
        getContentPane().add(lblDate);

        txtDate = new JTextField(date.toString());
        txtDate.setEditable(false);
        txtDate.setBounds(130, 180, 400, 25);
        getContentPane().add(txtDate);

        JLabel lblShifts = new JLabel("Vagter:");
        lblShifts.setBounds(20, 220, 100, 25);
        getContentPane().add(lblShifts);

        shiftsTable = new JTable(new DefaultTableModel(new Object[]{"Starttid", "Sluttid", "Medarbejder"}, 0));
        JScrollPane scrollPane = new JScrollPane(shiftsTable);
        scrollPane.setBounds(130, 220, 400, 277);
        getContentPane().add(scrollPane);

        JButton btnAddShift = new JButton("Tilføj Vagt");
        btnAddShift.setBounds(130, 508, 150, 30);
        btnAddShift.addActionListener(e -> {
            try {
                addShiftDialog(date);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
        getContentPane().add(btnAddShift);

        JButton btnOK = new JButton("OK");
        btnOK.setBounds(380, 508, 150, 30);
        btnOK.addActionListener((ActionEvent e) -> dispose());
        getContentPane().add(btnOK);

        JButton btnSave = new JButton("Gem Opgave");
        btnSave.addActionListener((ActionEvent e) -> {
            try {
                saveTask(date, taskMap);
            } catch (NumberFormatException e1) {
                e1.printStackTrace();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
        btnSave.setBounds(380, 139, 150, 30);
        getContentPane().add(btnSave);
    }

    /**
     * Opens the {@link AddShiftDialog} to allow the user to add a shift to the current task.
     * Once the dialog is closed and if the shift is successfully saved, it will be reflected in the table.
     * 
     * @param date the date of the shift being added.
     * @throws Exception if an error occurs while showing the AddShiftDialog.
     */
    private void addShiftDialog(LocalDate date) throws Exception {
        AddShiftDialog dialog = new AddShiftDialog(shiftsTable, date, tc);
        dialog.setVisible(true);
    }

    /**
     * Saves the current task and all entered details (including shifts) to the given task map
     * and also persists the task in the database through the {@link TaskCtrl}.
     * 
     * <p>It retrieves the description, location, user ID, and any shifts from the table, 
     * then updates the provided HashMap with this information keyed by the given date.</p>
     * 
     * @param date    the date of the task, used as the key in the taskMap.
     * @param taskMap the map to store the task's details for later retrieval.
     * @throws NumberFormatException if the user ID is not a valid integer.
     * @throws Exception if an error occurs during task creation or saving.
     */
    private void saveTask(LocalDate date, HashMap<LocalDate, List<String>> taskMap) throws NumberFormatException, Exception {
        // Create and save the task via TaskCtrl
        tc.createTask(date, txtDescription.getText(), txtLocation.getText(), Integer.parseInt(txtUser.getText().trim()));

        String description = txtDescription.getText();
        String location = txtLocation.getText();
        String user = txtUser.getText();

        DefaultTableModel model = (DefaultTableModel) shiftsTable.getModel();
        List<String> taskDetails = new ArrayList<>();
        taskDetails.add("Beskrivelse: " + description);
        taskDetails.add("Lokation: " + location);
        taskDetails.add("Bruger: " + user);

        // Collect shift details from the table
        for (int i = 0; i < model.getRowCount(); i++) {
            String shift = "Vagt: Starttid " + model.getValueAt(i, 0) + ", Sluttid " + model.getValueAt(i, 1)
                    + ", Medarbejder " + model.getValueAt(i, 2);
            taskDetails.add(shift);
        }

        // Update the taskMap with the new task details
        taskMap.put(date, taskDetails);
        JOptionPane.showMessageDialog(this, "Opgave gemt for " + date);
    }
}
