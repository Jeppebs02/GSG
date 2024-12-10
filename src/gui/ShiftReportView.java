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
import java.awt.Component;
import javax.swing.table.TableModel;

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
public class ShiftReportView extends JDialog {
    private JTextField txtDescription;
    private JTextField txtDate;
    private JTable shiftsTable;
    private List<String> shifts;
    private JTextField textField;
    private JTextField textField_1;
    private JTextField textField_2;
    private JTextField textField_3;
    private JTextField textField_4;
    private JTextField textField_5;
    private JTextField textField_6;
    private JTextField textField_7;
    private JTextField textField_8;
    private JTextField textField_9;

    /**
     * Constructs an AddTaskDialog instance, initializing the GUI components, setting default values,
     * and preparing the dialog for user input.
     * 
     * @param date     the default date to use for the task, typically passed from a calendar or scheduler view.
     * @param taskMap  a HashMap where the key is a LocalDate and the value is a list of task details. 
     *                 This dialog adds the newly created task to this map.
     * @throws SQLException if there's an error initializing the underlying TaskCtrl or database operations.
     */
    public ShiftReportView(LocalDate date, HashMap<LocalDate, List<String>> taskMap) throws SQLException {
        
        setTitle("Add task");
        setBounds(100, 100, 600, 600);
        getContentPane().setLayout(null);
        shifts = new ArrayList<>();

        JLabel lblDescription = new JLabel("Task Report");
        lblDescription.setBounds(20, 20, 72, 25);
        getContentPane().add(lblDescription);

        txtDescription = new JTextField();
        txtDescription.setBounds(94, 20, 116, 25);
        getContentPane().add(txtDescription);

        JLabel lblDate = new JLabel("Date:");
        lblDate.setBounds(266, 20, 44, 25);
        getContentPane().add(lblDate);

        txtDate = new JTextField(date.toString());
        txtDate.setEditable(false);
        txtDate.setBounds(306, 20, 110, 25);
        getContentPane().add(txtDate);

        JLabel lblShifts = new JLabel("Shifts:");
        lblShifts.setBounds(20, 57, 100, 25);
        getContentPane().add(lblShifts);

        shiftsTable = new JTable(new DefaultTableModel(new Object[]{"Starttime", "Endtime", "Employee"}, 0));
        JScrollPane scrollPane = new JScrollPane(shiftsTable);
        scrollPane.setBounds(94, 55, 436, 114);
        getContentPane().add(scrollPane);

        JButton btnOK = new JButton("PDF");
        btnOK.setBounds(405, 508, 64, 30);
        btnOK.addActionListener((ActionEvent e) -> dispose());
        getContentPane().add(btnOK);
        
        JLabel lblAge = new JLabel("Age:");
        lblAge.setBounds(363, 210, 64, 25);
        getContentPane().add(lblAge);
        
        JLabel lblAttitude = new JLabel("Attitude:");
        lblAttitude.setBounds(363, 246, 64, 25);
        getContentPane().add(lblAttitude);
        
        JLabel lblOther = new JLabel("Other:");
        lblOther.setBounds(363, 282, 64, 25);
        getContentPane().add(lblOther);
        
        textField = new JTextField();
        textField.setBounds(437, 212, 93, 25);
        getContentPane().add(textField);
        
        textField_1 = new JTextField();
        textField_1.setBounds(437, 282, 93, 25);
        getContentPane().add(textField_1);
        
        textField_2 = new JTextField();
        textField_2.setBounds(437, 246, 93, 25);
        getContentPane().add(textField_2);
        
        JLabel lblTotal = new JLabel("Total:");
        lblTotal.setBounds(363, 318, 64, 25);
        getContentPane().add(lblTotal);
        
        textField_3 = new JTextField();
        textField_3.setBounds(437, 318, 93, 25);
        getContentPane().add(textField_3);
        
        JLabel lblRejection = new JLabel("Number of rejections");
        lblRejection.setBounds(363, 180, 167, 25);
        getContentPane().add(lblRejection);
        
        JLabel lblGreen = new JLabel("Green:");
        lblGreen.setBounds(20, 211, 66, 25);
        getContentPane().add(lblGreen);
        
        JLabel lblRed = new JLabel("Red:");
        lblRed.setBounds(20, 247, 64, 25);
        getContentPane().add(lblRed);
        
        textField_4 = new JTextField();
        textField_4.setBounds(94, 249, 93, 25);
        getContentPane().add(textField_4);
        
        textField_5 = new JTextField();
        textField_5.setBounds(94, 215, 93, 25);
        getContentPane().add(textField_5);
        
        textField_6 = new JTextField();
        textField_6.setBounds(94, 285, 93, 25);
        getContentPane().add(textField_6);
        
        JLabel lblTotal_1 = new JLabel("Total:");
        lblTotal_1.setBounds(20, 283, 64, 25);
        getContentPane().add(lblTotal_1);
        
        JLabel lblAlarms = new JLabel("Number of alarms");
        lblAlarms.setBounds(20, 180, 100, 25);
        getContentPane().add(lblAlarms);
        
        JScrollPane scrollPane_1 = new JScrollPane((Component) null);
        scrollPane_1.setBounds(94, 354, 203, 104);
        getContentPane().add(scrollPane_1);
        
        JLabel lblAlarms_1 = new JLabel("Alarms");
        lblAlarms_1.setBounds(20, 342, 66, 25);
        getContentPane().add(lblAlarms_1);
        
        JButton btnAddAlarm = new JButton("Add Alarm");
        btnAddAlarm.setBounds(0, 378, 93, 30);
        getContentPane().add(btnAddAlarm);
        
        JLabel lblSignatureEmployee = new JLabel("Signature employee");
        lblSignatureEmployee.setBounds(109, 481, 100, 25);
        getContentPane().add(lblSignatureEmployee);
        
        JLabel lblSignatureManager = new JLabel("Signature manager");
        lblSignatureManager.setBounds(256, 481, 100, 25);
        getContentPane().add(lblSignatureManager);
        
        textField_7 = new JTextField();
        textField_7.setBounds(109, 511, 93, 25);
        getContentPane().add(textField_7);
        
        textField_8 = new JTextField();
        textField_8.setBounds(256, 513, 93, 25);
        getContentPane().add(textField_8);
        
        JLabel lblOther_1 = new JLabel("Other:");
        lblOther_1.setBounds(363, 394, 64, 25);
        getContentPane().add(lblOther_1);
        
        textField_9 = new JTextField();
        textField_9.setBounds(405, 354, 150, 104);
        getContentPane().add(textField_9);
        
        JButton btnOK_1 = new JButton("OK");
        btnOK_1.setBounds(491, 508, 64, 30);
        getContentPane().add(btnOK_1);
    }

    

    
}
