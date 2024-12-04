
package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AddTaskDialog extends JDialog {
    private JTextField txtDescription;
    private JTextField txtLocation;
    private JTextField txtUser;
    private JTextField txtReport;
    private JTextField txtDate;
    private JTable shiftsTable;
    private List<String> shifts;

    public AddTaskDialog(LocalDate date, HashMap<LocalDate, List<String>> taskMap) {
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

//        JLabel lblReport = new JLabel("Rapport:");
//        lblReport.setBounds(20, 140, 100, 25);
//        getContentPane().add(lblReport);
//
//        txtReport = new JTextField();
//        txtReport.setBounds(130, 140, 400, 25);
//        getContentPane().add(txtReport);

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
        scrollPane.setBounds(130, 220, 400, 150);
        getContentPane().add(scrollPane);

        JButton btnAddShift = new JButton("Tilføj Vagt");
        btnAddShift.setBounds(20, 380, 150, 30);
        btnAddShift.addActionListener(e -> addShiftDialog());
        getContentPane().add(btnAddShift);

        JButton btnSave = new JButton("Gem Opgave");
        btnSave.setBounds(300, 500, 150, 30);
        btnSave.addActionListener((ActionEvent e) -> saveTask(date, taskMap));
        getContentPane().add(btnSave);
    }

    private void addShiftDialog() {
        JDialog shiftDialog = new JDialog(this, "Tilføj Vagt", true);
        shiftDialog.setBounds(100, 100, 400, 300);
        shiftDialog.setLayout(null);

        JLabel lblStartTime = new JLabel("Starttid:");
        lblStartTime.setBounds(20, 20, 100, 25);
        shiftDialog.add(lblStartTime);

        JTextField txtStartTime = new JTextField();
        txtStartTime.setBounds(130, 20, 200, 25);
        shiftDialog.add(txtStartTime);

        JLabel lblEndTime = new JLabel("Sluttid:");
        lblEndTime.setBounds(20, 60, 100, 25);
        shiftDialog.add(lblEndTime);

        JTextField txtEndTime = new JTextField();
        txtEndTime.setBounds(130, 60, 200, 25);
        shiftDialog.add(txtEndTime);

        JLabel lblEmployee = new JLabel("Medarbejder:");
        lblEmployee.setBounds(20, 100, 100, 25);
        shiftDialog.add(lblEmployee);

        JTextField txtEmployee = new JTextField();
        txtEmployee.setBounds(130, 100, 200, 25);
        shiftDialog.add(txtEmployee);

        JButton btnAdd = new JButton("Tilføj");
        btnAdd.setBounds(130, 150, 100, 30);
        btnAdd.addActionListener(e -> {
            String startTime = txtStartTime.getText();
            String endTime = txtEndTime.getText();
            String employee = txtEmployee.getText();
            DefaultTableModel model = (DefaultTableModel) shiftsTable.getModel();
            model.addRow(new Object[]{startTime, endTime, employee});
            shiftDialog.dispose();
        });
        shiftDialog.add(btnAdd);

        shiftDialog.setVisible(true);
    }

    private void saveTask(LocalDate date, HashMap<LocalDate, List<String>> taskMap) {
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
        dispose();
    }
}
