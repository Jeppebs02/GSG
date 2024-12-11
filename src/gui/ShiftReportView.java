package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import ctrl.ReportCtrl;
import ctrl.TaskCtrl;
import dal.ShiftDB;
import model.Alarm;
import model.Classification;
import model.Report;
import model.Shift;
import model.Task;

import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.awt.Component;
import javax.swing.table.TableModel;

/**
 * The AddTaskDialog class provides a GUI dialog for creating a new task, adding
 * shifts to it, and saving it along with associated details (such as user and
 * location).
 * 
 * <p>
 * This dialog allows the user to:
 * <ul>
 * <li>Enter task information (description, location, user ID).</li>
 * <li>Specify the date of the task.</li>
 * <li>Add one or more shifts to the task using the {@link AddShiftDialog}.</li>
 * <li>Save the task and its shifts to a provided data structure and the
 * database via {@link TaskCtrl}.</li>
 * </ul>
 */
public class ShiftReportView extends JDialog {
	private JTextField txtDescription;
	private JTextField txtDate;
	private JTable shiftsTable;
	private List<Shift> shifts;
	private List<Alarm> alarms;
	private JTextField textFieldAge;
	private JTextField textFieldOther;
	private JTextField textFieldAttitude;
	private JTextField textFieldRedAlarm;
	private JTextField textFieldGreenAlarm;
	private JTextField textFieldTotalAlarm;
	private JTextField textFieldEmpSign;
	private JTextField textFieldManaSign;
	private JTextField textFieldOtherComments;
	private ReportCtrl rc;
	private JTable alarmTable;

	/**
	 * Constructs an AddTaskDialog instance, initializing the GUI components,
	 * setting default values, and preparing the dialog for user input.
	 * 
	 * @param date    the default date to use for the task, typically passed from a
	 *                calendar or scheduler view.
	 * @param taskMap a HashMap where the key is a LocalDate and the value is a list
	 *                of task details. This dialog adds the newly created task to
	 *                this map.
	 * @throws Exception
	 */
	public ShiftReportView(Task task) throws Exception {

		rc = new ReportCtrl();
		shifts = new ArrayList<>();
		alarms = new ArrayList<>();
		ShiftDB sdb = new ShiftDB();
		shifts = sdb.findAllShiftsByTaskIDFromDB(task.getTaskID());
		Report r = rc.findReportByTaskID(task.getTaskID());

		alarms = r.getAlarms();

		setModal(true);

		setTitle("Add task");
		setBounds(100, 100, 600, 600);
		getContentPane().setLayout(null);

		JLabel lblDescription = new JLabel("Task Report");
		lblDescription.setBounds(20, 20, 72, 25);
		getContentPane().add(lblDescription);

		txtDescription = new JTextField(task.getDescription());
		txtDescription.setEditable(false);
		txtDescription.setBounds(94, 20, 262, 25);
		getContentPane().add(txtDescription);

		JLabel lblDate = new JLabel("Date:");
		lblDate.setBounds(380, 20, 44, 25);
		getContentPane().add(lblDate);

		txtDate = new JTextField(task.getDate().toString());
		txtDate.setBounds(420, 20, 110, 25);
		getContentPane().add(txtDate);
		txtDate.setEditable(false);

		JLabel lblShifts = new JLabel("Shifts:");
		lblShifts.setBounds(20, 57, 100, 25);
		getContentPane().add(lblShifts);

		shiftsTable = new JTable(new DefaultTableModel(new Object[] { "Starttime", "Endtime", "Employee" }, 0));
		DefaultTableModel model = (DefaultTableModel) shiftsTable.getModel();

		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
		shifts.forEach(shift -> {

			model.addRow(
					new Object[] { shift.getStartTime().format(timeFormatter), shift.getEndTime().format(timeFormatter),
							shift.getEmployee().getFirstName() + " " + shift.getEmployee().getLastName() });
		});

		JScrollPane scrollPane = new JScrollPane(shiftsTable);
		scrollPane.setBounds(94, 55, 436, 114);
		getContentPane().add(scrollPane);

		JButton btnPDF = new JButton("PDF");
		btnPDF.setBounds(405, 508, 64, 30);
		getContentPane().add(btnPDF);

		JLabel lblAge = new JLabel("Age:");
		lblAge.setBounds(363, 210, 64, 25);
		getContentPane().add(lblAge);

		JLabel lblAttitude = new JLabel("Attitude:");
		lblAttitude.setBounds(363, 246, 64, 25);
		getContentPane().add(lblAttitude);

		JLabel lblOther = new JLabel("Other:");
		lblOther.setBounds(363, 282, 64, 25);
		getContentPane().add(lblOther);

		textFieldAge = new JTextField();
		textFieldAge.setBounds(437, 212, 93, 25);
		getContentPane().add(textFieldAge);

		textFieldOther = new JTextField();
		textFieldOther.setBounds(437, 282, 93, 25);
		getContentPane().add(textFieldOther);

		textFieldAttitude = new JTextField();
		textFieldAttitude.setBounds(437, 246, 93, 25);
		getContentPane().add(textFieldAttitude);

		JLabel lblRejection = new JLabel("Number of rejections");
		lblRejection.setBounds(363, 180, 167, 25);
		getContentPane().add(lblRejection);

		JLabel lblGreen = new JLabel("Green:");
		lblGreen.setBounds(20, 211, 66, 25);
		getContentPane().add(lblGreen);

		JLabel lblRed = new JLabel("Red:");
		lblRed.setBounds(20, 247, 64, 25);
		getContentPane().add(lblRed);

		textFieldRedAlarm = new JTextField();
		textFieldRedAlarm.setBounds(94, 249, 93, 25);
		getContentPane().add(textFieldRedAlarm);

		textFieldGreenAlarm = new JTextField();
		textFieldGreenAlarm.setBounds(94, 215, 93, 25);
		getContentPane().add(textFieldGreenAlarm);

		textFieldTotalAlarm = new JTextField();
		textFieldTotalAlarm.setBounds(94, 285, 93, 25);
		getContentPane().add(textFieldTotalAlarm);

		JLabel lblTotal_1 = new JLabel("Total:");
		lblTotal_1.setBounds(20, 283, 64, 25);
		getContentPane().add(lblTotal_1);

		JLabel lblAlarms = new JLabel("Number of alarms");
		lblAlarms.setBounds(20, 180, 100, 25);
		getContentPane().add(lblAlarms);

		JScrollPane scrollPane_1 = new JScrollPane((Component) null);
		scrollPane_1.setBounds(94, 354, 203, 104);
		getContentPane().add(scrollPane_1);

		alarmTable = new JTable(new DefaultTableModel(new Object[] { "Time", "Classification", "Description" }, 0));
		DefaultTableModel model2 = (DefaultTableModel) alarmTable.getModel();

		alarms.forEach(alarm -> {

			model2.addRow(new Object[] { alarm.getTime().format(timeFormatter), alarm.getClassificationValue(),
					alarm.getDescription() });
		});
		scrollPane_1.setViewportView(alarmTable);

		JLabel lblAlarms_1 = new JLabel("Alarms");
		lblAlarms_1.setBounds(20, 342, 66, 25);
		getContentPane().add(lblAlarms_1);

		JButton btnAddAlarm = new JButton("Add Alarm");
		btnAddAlarm.setBounds(0, 378, 93, 30);
		btnAddAlarm.addActionListener((ActionEvent e) -> {
			try {
				addAlarmView(task);
			} catch (Exception e1) {

				e1.printStackTrace();
			}
		});
		getContentPane().add(btnAddAlarm);

		JLabel lblSignatureEmployee = new JLabel("Signature employee");
		lblSignatureEmployee.setBounds(109, 481, 100, 25);
		getContentPane().add(lblSignatureEmployee);

		JLabel lblSignatureManager = new JLabel("Signature manager");
		lblSignatureManager.setBounds(256, 481, 100, 25);
		getContentPane().add(lblSignatureManager);

		textFieldEmpSign = new JTextField();
		textFieldEmpSign.setBounds(109, 511, 93, 25);
		getContentPane().add(textFieldEmpSign);

		textFieldManaSign = new JTextField();
		textFieldManaSign.setBounds(256, 513, 93, 25);
		getContentPane().add(textFieldManaSign);

		JLabel lblOther_1 = new JLabel("Other:");
		lblOther_1.setBounds(363, 394, 64, 25);
		getContentPane().add(lblOther_1);

		textFieldOtherComments = new JTextField();
		textFieldOtherComments.setBounds(405, 354, 150, 104);
		getContentPane().add(textFieldOtherComments);

		JButton btnOK = new JButton("OK");
		btnOK.setBounds(491, 508, 64, 30);
		btnOK.addActionListener((ActionEvent e) -> {
			try {
				saveReport(task);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});
		getContentPane().add(btnOK);

		numberOfAlarms(task);
	}

	private void addAlarmView(Task task) throws Exception {
		AddAlarmView view = new AddAlarmView(task);
		view.setVisible(true);
		numberOfAlarms(task);
		refreshAlarmTable(task);
		
	}

	private void numberOfAlarms(Task task) throws Exception {
		ArrayList<Alarm> listOfAlarms = new ArrayList<>();
		listOfAlarms = (ArrayList<Alarm>) rc.findReportByTaskID(task.getTaskID()).getAlarms();

		int numberOfGreen = (int) listOfAlarms.stream().filter(a -> a.getClassification() == Classification.GREEN)
				.count();
		int numberOfRed = (int) listOfAlarms.stream().filter(a -> a.getClassification() == Classification.RED).count();
		int totalNumber = listOfAlarms.size();

		textFieldTotalAlarm.setText(String.valueOf(totalNumber));
		textFieldTotalAlarm.setEditable(false);
		textFieldGreenAlarm.setText(String.valueOf(numberOfGreen));
		textFieldGreenAlarm.setEditable(false);
		textFieldRedAlarm.setText(String.valueOf(numberOfRed));
		textFieldRedAlarm.setEditable(false);

	}

	private void saveReport(Task task) throws Exception {
		int age = 0;
		int attitude = 0;
		int other = 0;

		String inputAge = textFieldAge.getText().trim();
		if (inputAge.matches("\\d+")) {
			age = Integer.parseInt(inputAge);
		} else if (textFieldAge.getText().isBlank()) {
			age = 0;
		} else {
			JOptionPane.showMessageDialog(this, "Please enter a valid integer for the age.", "Invalid Input",
					JOptionPane.ERROR_MESSAGE);
		}

		String inputAttitude = textFieldAttitude.getText().trim();
		if (inputAttitude.matches("\\d+")) {
			attitude = Integer.parseInt(inputAttitude);
		} else if (textFieldAttitude.getText().isBlank()) {
			attitude = 0;
		} else {
			JOptionPane.showMessageDialog(this, "Please enter a valid integer for the age.", "Invalid Input",
					JOptionPane.ERROR_MESSAGE);
		}

		String inputOther = textFieldOther.getText().trim();
		if (inputOther.matches("\\d+")) {
			other = Integer.parseInt(inputOther);
		} else if (textFieldOther.getText().isBlank()) {
			other = 0;
		} else {
			JOptionPane.showMessageDialog(this, "Please enter a valid integer for the age.", "Invalid Input",
					JOptionPane.ERROR_MESSAGE);
		}
		//TODO UPDATE REPORT INSTED
		rc.updateReportByTaskID(task, age, attitude, other, textFieldOtherComments.getText(), textFieldEmpSign.getText(),
				textFieldManaSign.getText());
		this.dispose();
	}
	
	private void refreshAlarmTable(Task task) throws Exception {
	    // Fetch the updated list of alarms from the database
	    ArrayList<Alarm> updatedAlarms = (ArrayList<Alarm>) rc.findReportByTaskID(task.getTaskID()).getAlarms();

	    // Clear the existing rows
	    DefaultTableModel model2 = (DefaultTableModel) alarmTable.getModel();
	    model2.setRowCount(0);

	    // Re-populate the table
	    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
	    updatedAlarms.forEach(alarm -> {
	        model2.addRow(new Object[] {
	            alarm.getTime().format(timeFormatter),
	            alarm.getClassificationValue(),
	            alarm.getDescription()
	        });
	    });
	}

}
