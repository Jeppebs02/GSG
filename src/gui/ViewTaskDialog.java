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
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * The {@code ViewTaskDialog} class provides a dialog for viewing all the
 * details of a specified {@link Task}. It displays information such as the
 * task's description, location, associated user ID, date, and all the
 * {@link Shift} objects associated with that task.
 * 
 * <p>
 * All fields are set to non-editable, so that the user can only view the
 * details.
 * </p>
 */
public class ViewTaskDialog extends JDialog {
	private JTextField txtDescription;
	private JTextField txtLocation;
	private JTextField txtUser;
	private JTextField txtDate;
	private JTable shiftsTable;
	private List<Shift> allShiftsOnTask;

	/**
	 * Constructs a {@code ViewTaskDialog} instance, initializing the UI components
	 * and populating them with details from the given {@link Task} object.
	 * 
	 * @param task the {@link Task} whose information is to be displayed in this
	 *             dialog.
	 * @throws SQLException if a database access error occurs while retrieving shift
	 *                      details.
	 */
	public ViewTaskDialog(Task task) throws SQLException {
		allShiftsOnTask = new ArrayList<>();
		ShiftDB sdb = new ShiftDB();
		allShiftsOnTask = sdb.findAllShiftsByTaskIDFromDB(task.getTaskID());

		setTitle("Show Task");
		setBounds(100, 100, 600, 600);
		setResizable(false);
		getContentPane().setLayout(null);

		JLabel lblDescription = new JLabel("Discription:");
		lblDescription.setBounds(20, 20, 100, 25);
		getContentPane().add(lblDescription);

		txtDescription = new JTextField(task.getDescription());
		txtDescription.setBounds(130, 20, 400, 25);
		txtDescription.setEditable(false); // Read-only
		getContentPane().add(txtDescription);

		JLabel lblLocation = new JLabel("Location:");
		lblLocation.setBounds(20, 60, 100, 25);
		getContentPane().add(lblLocation);

		txtLocation = new JTextField(task.getLocation());
		txtLocation.setBounds(130, 60, 400, 25);
		txtLocation.setEditable(false); // Read-only
		getContentPane().add(txtLocation);

		JLabel lblUser = new JLabel("CustomerID:");
		lblUser.setBounds(20, 100, 100, 25);
		getContentPane().add(lblUser);

		txtUser = new JTextField(String.valueOf(task.getUser().getUserID()));
		txtUser.setBounds(130, 100, 400, 25);
		txtUser.setEditable(false); // Read-only
		getContentPane().add(txtUser);

		JLabel lblDate = new JLabel("Date:");
		lblDate.setBounds(20, 180, 100, 25);
		getContentPane().add(lblDate);

		txtDate = new JTextField(task.getDate().toString());
		txtDate.setBounds(130, 180, 400, 25);
		txtDate.setEditable(false); // Read-only
		getContentPane().add(txtDate);

		JLabel lblShifts = new JLabel("Shifts:");
		lblShifts.setBounds(20, 220, 100, 25);
		getContentPane().add(lblShifts);

		shiftsTable = new JTable(new DefaultTableModel(new Object[] { "Starttime", "Endtime", "Employee" }, 0));
		DefaultTableModel model = (DefaultTableModel) shiftsTable.getModel();

		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
		allShiftsOnTask.forEach(shift -> {
			model.addRow(
					new Object[] { shift.getStartTime().format(timeFormatter), shift.getEndTime().format(timeFormatter),
							shift.getEmployee().getFirstName() + " " + shift.getEmployee().getLastName() });
		});

		JScrollPane scrollPane = new JScrollPane(shiftsTable);
		scrollPane.setBounds(130, 220, 400, 277);
		getContentPane().add(scrollPane);

		JButton btnOK = new JButton("OK");
		btnOK.setBounds(380, 508, 150, 30);
		btnOK.addActionListener(e -> dispose());
		getContentPane().add(btnOK);

		JButton btnViewReport = new JButton("View report");
		btnViewReport.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					viewReport(task);
				} catch (Exception e1) {
					e1.printStackTrace();
				}

			}
		});
		btnViewReport.setBounds(130, 508, 150, 30);
		getContentPane().add(btnViewReport);
	}

	/**
	 * Opens the {@link ShiftReportView} dialog to view and possibly update the
	 * report associated with the given {@link Task}.
	 * 
	 * @param task the {@link Task} for which the report is to be viewed.
	 * @throws Exception if a database or other error occurs while initializing the
	 *                   report view.
	 */
	private void viewReport(Task task) throws Exception {
		ShiftReportView view = new ShiftReportView(task);
		view.setVisible(true);
	}
}
