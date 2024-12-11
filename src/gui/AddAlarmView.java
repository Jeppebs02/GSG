package gui;

import java.time.LocalDateTime;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import ctrl.AlarmCtrl;
import ctrl.ReportCtrl;
import model.Classification;
import model.Task;

import javax.swing.JCheckBox;
import java.awt.Font;

public class AddAlarmView extends JDialog {
	private JTextField txtComments;
	private JCheckBox chckbxGREEN;
	private JCheckBox chckbxRed;
	private JCheckBox chckbxNotify;
	private AlarmCtrl ac;
	private ReportCtrl rc;

	public AddAlarmView(Task task) throws Exception {

		ac = new AlarmCtrl();
		rc = new ReportCtrl();

		setTitle("Add shift");
		setBounds(100, 100, 400, 300);
		getContentPane().setLayout(null);
		setModal(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		JLabel lblalarm = new JLabel("Alarm");
		lblalarm.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lblalarm.setBounds(20, 20, 100, 37);
		getContentPane().add(lblalarm);

		JLabel lblcomments = new JLabel("Comments");
		lblcomments.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblcomments.setBounds(20, 100, 100, 25);
		getContentPane().add(lblcomments);

		txtComments = new JTextField();
		txtComments.setBounds(130, 100, 200, 94);
		getContentPane().add(txtComments);

		JButton btnAdd = new JButton("Add");
		btnAdd.setBounds(276, 222, 100, 30);
		btnAdd.addActionListener(e -> {
			try {
				if (!isOneColorSelected(chckbxGREEN, chckbxRed)) {
					JOptionPane.showMessageDialog(this, "You can only select one of the GREEN or RED options.",
							"Invalid Selection", JOptionPane.WARNING_MESSAGE);
					return; // Stop execution here, don't proceed to add
				}
				saveAlarm(task);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});
		getContentPane().add(btnAdd);

		chckbxGREEN = new JCheckBox("GREEN");
		chckbxGREEN.setFont(new Font("Tahoma", Font.PLAIN, 20));
		chckbxGREEN.setBounds(126, 21, 99, 23);
		getContentPane().add(chckbxGREEN);

		chckbxRed = new JCheckBox("RED");
		chckbxRed.setFont(new Font("Tahoma", Font.PLAIN, 20));
		chckbxRed.setBounds(126, 52, 99, 23);
		getContentPane().add(chckbxRed);

		chckbxNotify = new JCheckBox("Notify");
		chckbxNotify.setBounds(130, 226, 99, 23);
		getContentPane().add(chckbxNotify);
	}

	// A utility method to check if exactly one checkbox is selected
	private boolean isOneColorSelected(JCheckBox chckbxGREEN, JCheckBox chckbxRED) {
		boolean greenSelected = chckbxGREEN.isSelected();
		boolean redSelected = chckbxRED.isSelected();

		// Check if exactly one is selected
		if ((greenSelected && !redSelected) || (!greenSelected && redSelected)) {
			return true;
		}
		return false;
	}

	private Classification getClassification() {
		Classification c = null;

		if (chckbxGREEN.isSelected()) {
			c = Classification.GREEN;
		} else if (chckbxRed.isSelected()) {
			c = Classification.RED;
		}

		return c;
	}

	private void saveAlarm(Task task) throws Exception {
		
		int reportID = rc.findReportByTaskID(task.getTaskID()).getReportNr();
		ac.createAlarm(LocalDateTime.now(), getClassification(), txtComments.getText(), reportID);

	}

}
