package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import dal.ShiftDB;

import java.awt.*;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import model.Task;
import model.Shift;


/**
 * ViewTaskDialog is a dialog used to display the information of a specific task.
 * All fields are set to be non-editable so that users can only view the details.
 */
public class ViewTaskDialog extends JDialog {
    private JTextField txtDescription;
    private JTextField txtLocation;
    private JTextField txtUser;
    private JTextField txtDate;
    private JTable shiftsTable;
    private List<Shift> allShiftsOnTask;

    /**
     * Constructs a ViewTaskDialog instance, initializes components, and sets values.
     *
     * @param task the task whose information is to be displayed.
     * @throws SQLException 
     */
    public ViewTaskDialog(Task task) throws SQLException {
    	
    	allShiftsOnTask = new ArrayList<>();
    	ShiftDB sdb = new ShiftDB();
    	allShiftsOnTask = sdb.findAllShiftsByTaskIDFromDB(task.getTaskID());
    	
    	
        setTitle("Vis Opgave");
        setBounds(100, 100, 600, 600);
        getContentPane().setLayout(null);
        setModal(true);  // Makes the dialog modal

        JLabel lblDescription = new JLabel("Beskrivelse:");
        lblDescription.setBounds(20, 20, 100, 25);
        getContentPane().add(lblDescription);

        txtDescription = new JTextField(task.getDescription());
        txtDescription.setBounds(130, 20, 400, 25);
        txtDescription.setEditable(false);  // Read-only
        getContentPane().add(txtDescription);

        JLabel lblLocation = new JLabel("Lokation:");
        lblLocation.setBounds(20, 60, 100, 25);
        getContentPane().add(lblLocation);

        txtLocation = new JTextField(task.getLocation());
        txtLocation.setBounds(130, 60, 400, 25);
        txtLocation.setEditable(false);  // Read-only
        getContentPane().add(txtLocation);

        JLabel lblUser = new JLabel("Bruger:");
        lblUser.setBounds(20, 100, 100, 25);
        getContentPane().add(lblUser);

        txtUser = new JTextField(String.valueOf(task.getUser().getUserID()));
        txtUser.setBounds(130, 100, 400, 25);
        txtUser.setEditable(false);  // Read-only
        getContentPane().add(txtUser);

        JLabel lblDate = new JLabel("Dato:");
        lblDate.setBounds(20, 180, 100, 25);
        getContentPane().add(lblDate);

        txtDate = new JTextField(task.getDate().toString());
        txtDate.setBounds(130, 180, 400, 25);
        txtDate.setEditable(false);  // Read-only
        getContentPane().add(txtDate);

        JLabel lblShifts = new JLabel("Vagter:");
        lblShifts.setBounds(20, 220, 100, 25);
        getContentPane().add(lblShifts);

        shiftsTable = new JTable(new DefaultTableModel(new Object[]{"Starttid", "Sluttid", "Medarbejder"}, 0));
        DefaultTableModel model = (DefaultTableModel) shiftsTable.getModel();
        
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        allShiftsOnTask.forEach(shift -> {
        	
            model.addRow(new Object[]{shift.getStartTime().format(timeFormatter), shift.getEndTime().format(timeFormatter) , shift.getEmployee().getFirstName() + " " + shift.getEmployee().getLastName()});
        });

        JScrollPane scrollPane = new JScrollPane(shiftsTable);
        scrollPane.setBounds(130, 220, 400, 277);
        getContentPane().add(scrollPane);

        JButton btnOK = new JButton("OK");
        btnOK.setBounds(380, 508, 150, 30);
        btnOK.addActionListener(e -> dispose());
        getContentPane().add(btnOK);
    }
    
    	
}
