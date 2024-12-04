
package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import ctrl.TaskCtrl;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.awt.event.ActionListener;

public class AddTaskDialog extends JDialog {
    private JTextField txtDescription;
    private JTextField txtLocation;
    private JTextField txtUser;
    private JTextField txtReport;
    private JTextField txtDate;
    private JTable shiftsTable;
    private List<String> shifts;
    private TaskCtrl tc;

    public AddTaskDialog(LocalDate date, HashMap<LocalDate, List<String>> taskMap) {
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

        txtDate = new JTextField(date.toString()); // Auto-fill with selected date
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

    private void addShiftDialog(LocalDate date) throws NumberFormatException, Exception {
    	
    	
    	AddShiftDialog dialog = new AddShiftDialog(shiftsTable, date, tc);
        dialog.setVisible(true);
    	

    }

    private void saveTask(LocalDate date, HashMap<LocalDate, List<String>> taskMap) throws NumberFormatException, Exception {
    	
    	tc.createTask(date, txtDescription.getText(), txtLocation.getText(), Integer.parseInt(txtUser.getText().trim()));
    	
    	String description = txtDescription.getText();
        String location = txtLocation.getText();
        String user = txtUser.getText();
        //String report = txtReport.getText();

        DefaultTableModel model = (DefaultTableModel) shiftsTable.getModel();
        List<String> taskDetails = new ArrayList<>();
        taskDetails.add("Beskrivelse: " + description);
        taskDetails.add("Lokation: " + location);
        taskDetails.add("Bruger: " + user);
        //taskDetails.add("Rapport: " + report);

        for (int i = 0; i < model.getRowCount(); i++) {
            String shift = "Vagt: Starttid " + model.getValueAt(i, 0) + ", Sluttid " + model.getValueAt(i, 1)
                    + ", Medarbejder " + model.getValueAt(i, 2);
            taskDetails.add(shift);
        }

        taskMap.put(date, taskDetails);
        JOptionPane.showMessageDialog(this, "Opgave gemt for " + date);
        
    }
}
