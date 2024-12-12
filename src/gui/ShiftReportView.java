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
 * The {@code ShiftReportView} class provides a GUI dialog for viewing and
 * updating a task's report details, including shifts, alarms, and rejection
 * counts.
 * 
 * <p>
 * In particular, this dialog allows the user to:
 * <ul>
 * <li>View the associated {@link Task} description and date.</li>
 * <li>View all {@link Shift} objects related to the task.</li>
 * <li>View and add {@link Alarm} entries associated with the task's
 * report.</li>
 * <li>Specify the number of rejections for certain categories (age, attitude,
 * other).</li>
 * <li>Include extra comments and employee/manager signatures.</li>
 * </ul>
 * 
 * <p>
 * When saving, the updated report information is persisted to the database via
 * the {@link ReportCtrl} class. This dialog interacts with {@link ShiftDB} to
 * fetch shifts and with the {@link ReportCtrl} to fetch and update report
 * details.
 * </p>
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
	 * Constructs a {@code ShiftReportView} dialog, initializing the GUI components
	 * with data from the given {@link Task}, fetching associated {@link Shift} and
	 * {@link Alarm} details, and preparing the user interface for viewing/updating
	 * the report.
	 * 
	 * @param task the {@link Task} for which the report is being viewed or updated.
	 * @throws Exception if an error occurs while retrieving data from the database.
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
		txtDate.setEditable(false);
		getContentPane().add(txtDate);

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
		textFieldAge.setText(String.valueOf(r.getRejectionsAge()));
		getContentPane().add(textFieldAge);

		textFieldOther = new JTextField();
		textFieldOther.setBounds(437, 282, 93, 25);
		textFieldOther.setText(String.valueOf(r.getRejectionsAlternative()));
		getContentPane().add(textFieldOther);

		textFieldAttitude = new JTextField();
		textFieldAttitude.setBounds(437, 246, 93, 25);
		textFieldAttitude.setText(String.valueOf(r.getRejectionsAttitude()));
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
		scrollPane_1.setBounds(94, 354, 255, 104);
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
		lblSignatureEmployee.setBounds(117, 481, 129, 25);
		getContentPane().add(lblSignatureEmployee);

		JLabel lblSignatureManager = new JLabel("Signature manager");
		lblSignatureManager.setBounds(256, 481, 123, 25);
		getContentPane().add(lblSignatureManager);

		textFieldEmpSign = new JTextField();
		textFieldEmpSign.setBounds(109, 511, 93, 25);
		if(r.getEmployeeSignature() == null) {
			textFieldEmpSign.setText("");
		} else {
			textFieldEmpSign.setText(r.getEmployeeSignature());
		}
		getContentPane().add(textFieldEmpSign);

		textFieldManaSign = new JTextField();
		textFieldManaSign.setBounds(256, 513, 93, 25);
		if(r.getCustomerSignature() == null) {
			textFieldManaSign.setText("");
		} else {
			textFieldManaSign.setText(r.getCustomerSignature());
		}
		getContentPane().add(textFieldManaSign);

		JLabel lblOther_1 = new JLabel("Other:");
		lblOther_1.setBounds(363, 394, 64, 25);
		getContentPane().add(lblOther_1);

		textFieldOtherComments = new JTextField();
		textFieldOtherComments.setBounds(405, 354, 150, 104);
		if(r.getAlternativeRemarks() == null) {
			textFieldOtherComments.setText("");
		} else {
			textFieldOtherComments.setText(r.getAlternativeRemarks());
		}
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

	/**
	 * Opens the {@link AddAlarmView} dialog to add a new {@link Alarm} to the
	 * task's report. After the alarm is added, the alarm list and counts are
	 * refreshed.
	 * 
	 * @param task the {@link Task} to which a new alarm will be added.
	 * @throws Exception if an error occurs while adding or refreshing alarms.
	 */
	private void addAlarmView(Task task) throws Exception {
		AddAlarmView view = new AddAlarmView(task);
		view.setVisible(true);
		numberOfAlarms(task);
		refreshAlarmTable(task);
	}

	/**
	 * Updates the counts of red, green, and total alarms for the given task and
	 * displays them in the respective text fields.
	 * 
	 * @param task the {@link Task} whose alarm counts are being updated.
	 * @throws Exception if an error occurs while fetching the report or alarms.
	 */
	private void numberOfAlarms(Task task) throws Exception {
		ArrayList<Alarm> listOfAlarms = (ArrayList<Alarm>) rc.findReportByTaskID(task.getTaskID()).getAlarms();

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

	/**
	 * Saves the updated report information, including rejections, comments, and
	 * signatures, back to the database via {@link ReportCtrl}.
	 * 
	 * <p>
	 * If the input for rejections is invalid (non-integer), an error dialog is
	 * shown.
	 * </p>
	 * 
	 * @param task the {@link Task} whose report is being updated.
	 * @throws Exception if an error occurs while updating the report.
	 */
	private void saveReport(Task task) throws Exception {
		int age = 0;
		int attitude = 0;
		int other = 0;

		// Validate age input
		String inputAge = textFieldAge.getText().trim();
		if (inputAge.matches("\\d+")) {
			age = Integer.parseInt(inputAge);
		} else if (textFieldAge.getText().isBlank()) {
			age = 0;
		} else {
			JOptionPane.showMessageDialog(this, "Please enter a valid number for the age rejections.", "Invalid Input",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		// Validate attitude input
		String inputAttitude = textFieldAttitude.getText().trim();
		if (inputAttitude.matches("\\d+")) {
			attitude = Integer.parseInt(inputAttitude);
		} else if (textFieldAttitude.getText().isBlank()) {
			attitude = 0;
		} else {
			JOptionPane.showMessageDialog(this, "Please enter a valid number for the attitude rejections.",
					"Invalid Input", JOptionPane.ERROR_MESSAGE);
			return;
		}

		// Validate other input
		String inputOther = textFieldOther.getText().trim();
		if (inputOther.matches("\\d+")) {
			other = Integer.parseInt(inputOther);
		} else if (textFieldOther.getText().isBlank()) {
			other = 0;
		} else {
			JOptionPane.showMessageDialog(this, "Please enter a valid number for the other rejections.",
					"Invalid Input", JOptionPane.ERROR_MESSAGE);
			return;
		}

		// Update the report with the validated data
		rc.updateReportByTaskID(age, attitude, other, textFieldOtherComments.getText(), textFieldEmpSign.getText(),
				textFieldManaSign.getText(), task);
		this.dispose();
	}

	/**
	 * Refreshes the alarm table after adding a new alarm. Fetches the updated
	 * alarms from the database and repopulates the alarm table.
	 * 
	 * @param task the {@link Task} whose alarms are being refreshed.
	 * @throws Exception if an error occurs while fetching the updated alarms.
	 */
	private void refreshAlarmTable(Task task) throws Exception {
		ArrayList<Alarm> updatedAlarms = (ArrayList<Alarm>) rc.findReportByTaskID(task.getTaskID()).getAlarms();

		DefaultTableModel model2 = (DefaultTableModel) alarmTable.getModel();
		model2.setRowCount(0);

		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
		updatedAlarms.forEach(alarm -> {
			model2.addRow(new Object[] { alarm.getTime().format(timeFormatter), alarm.getClassificationValue(),
					alarm.getDescription() });
		});
	}
}
