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

public class AddShiftDialog extends JDialog {
    private JTextField txtStartTime;
    private JTextField txtEndTime;
    private JTextField txtEmployee;
    private JTable shiftsTable;
    private TaskCtrl tc;
    
    public AddShiftDialog(JTable shiftsTable, LocalDate date, TaskCtrl tc) {
        this.tc = tc;
    	this.shiftsTable = shiftsTable;
    	setTitle("Tilføj Vagt");
        setBounds(100, 100, 400, 300);
        setLayout(null);
        setModal(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JLabel lblStartTime = new JLabel("Starttid:");
        lblStartTime.setBounds(20, 20, 100, 25);
        add(lblStartTime);

        txtStartTime = new JTextField();
        txtStartTime.setBounds(130, 20, 200, 25);
        add(txtStartTime);

        JLabel lblEndTime = new JLabel("Sluttid:");
        lblEndTime.setBounds(20, 60, 100, 25);
        add(lblEndTime);

        txtEndTime = new JTextField();
        txtEndTime.setBounds(130, 60, 200, 25);
        add(txtEndTime);

        JLabel lblEmployee = new JLabel("Medarbejder:");
        lblEmployee.setBounds(20, 100, 100, 25);
        add(lblEmployee);

        txtEmployee = new JTextField();
        txtEmployee.setBounds(130, 100, 200, 25);
        add(txtEmployee);

        JButton btnAdd = new JButton("Tilføj");
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
    

    private void saveShift(LocalDate date) {
        try {
            

            // Parsing start time
            String startTime = txtStartTime.getText();
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

            // Regular expression to validate time format (HH:mm)
            String regex = "^([01]\\d|2[0-3]):[0-5]\\d$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(startTime);

            if (!matcher.matches()) {
                throw new IllegalArgumentException("Ugyldigt starttidsformat. Brug formatet HH:mm");
            }

            LocalTime localTime = LocalTime.parse(startTime, timeFormatter);
            LocalDateTime localTimeDate = LocalDateTime.of(date, localTime);

            // Parsing end time
            String endTime = txtEndTime.getText();
            Matcher endMatcher = pattern.matcher(endTime);

            if (!endMatcher.matches()) {
                throw new IllegalArgumentException("Ugyldigt sluttidsformat. Brug formatet HH:mm");
             
            }

            LocalTime localTimeEnd = LocalTime.parse(endTime, timeFormatter);
            LocalDateTime localTimeDateEnd = LocalDateTime.of(date, localTimeEnd);

            if (localTimeDateEnd.isBefore(localTimeDate)) {
                throw new IllegalArgumentException("Sluttidspunktet er før starttidspunkt. Prøv igen");
             
            }
            
            // Creating the shift
            Shift s = tc.addShift(localTimeDate, localTimeDateEnd);

            // Parsing employee ID
            String employee = txtEmployee.getText();
            int employeeId = Integer.parseInt(employee);
            tc.addEmployeeToShift(s, employeeId);
            
            DefaultTableModel model = (DefaultTableModel) shiftsTable.getModel();
            model.addRow(new Object[]{startTime, endTime, employee});
            dispose();
            JOptionPane.showMessageDialog(this, "Vagt gemt: " + startTime + " - " + endTime);
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this, "Ugyldigt tidsformat. Brug venligst formatet HH:mm", "Fejl", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Medarbejder-ID skal være et gyldigt tal", "Fejl", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Fejl", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Der opstod en fejl ved gemning af vagten", "Fejl", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
          
        }
        
        
        
       
    }


//    private void saveShift(LocalDate date) throws Exception {
//        TaskCtrl tc = new TaskCtrl();
//    	
//        String startTime = txtStartTime.getText();
//    	DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
//    	LocalTime localTime = LocalTime.parse(startTime, timeFormatter);
//    	LocalDateTime localTimeDate = LocalDateTime.of(date, localTime);
//    	String regex = "^([01]\d|2[0-3]):[0-5]\d$";
//    	
//    	Pattern pattern = Pattern.compile(regex);
//    	Matcher matcher = pattern.matcher(startTime);
//        
//    	String endTime = txtEndTime.getText();
//    	LocalTime localTimeEnd = LocalTime.parse(endTime, timeFormatter);
//    	LocalDateTime localTimeDateEnd = LocalDateTime.of(date, localTime);
//        
//    	Shift s = tc.addShift(localTimeDate, localTimeDateEnd);
//        
//        String employee = txtEmployee.getText();
//        int n = Integer.parseInt(employee);
//        tc.addEmployeeToShift(s, n);
//        
//        JOptionPane.showMessageDialog(this, "Vagt gemt: " + startTime + " - " + endTime);
//    }
    
}
