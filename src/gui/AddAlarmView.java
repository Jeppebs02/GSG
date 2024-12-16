package gui;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import ctrl.AlarmCtrl;
import ctrl.ReportCtrl;
import model.Classification;
import model.Task;

import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import java.awt.Font;
import javax.swing.ButtonGroup;

/**
 * The {@code AddAlarmView} class provides a GUI dialog that allows the user to
 * add an alarm associated with a given {@link Task}. Users can specify the
 * time, classification (GREEN or RED), comments, and whether a notification
 * should be triggered.
 * 
 * <p>
 * This dialog validates user input such as time format and ensures that only
 * one classification option is selected at a time. Upon successful validation,
 * the alarm is created and saved to the database.
 * </p>
 */
public class AddAlarmView extends JDialog {
    private JTextArea txtCommentsArea;
    private JRadioButton rdbtnGREEN;
    private JRadioButton rdbtnRED;
    private JCheckBox chckbxNotify;
    private AlarmCtrl ac;
    private ReportCtrl rc;
    private JTextField textFieldTime;

    /**
     * Constructs an {@code AddAlarmView} dialog for adding an alarm to the given
     * task.
     * 
     * @param task the {@link Task} for which the alarm is to be created.
     * @throws Exception if there's an issue initializing the controllers or
     *                   fetching report data.
     */
    public AddAlarmView(Task task) throws Exception {
        ac = new AlarmCtrl();
        rc = new ReportCtrl();

        setTitle("Add Alarm");
        setResizable(false);
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
        lblcomments.setBounds(20, 131, 100, 25);
        getContentPane().add(lblcomments);

        txtCommentsArea = new JTextArea();
        txtCommentsArea.setLineWrap(true);
        txtCommentsArea.setWrapStyleWord(true);

        // Place the JTextArea into a JScrollPane for scrolling:
        JScrollPane scrollPaneComments = new JScrollPane(txtCommentsArea,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPaneComments.setBounds(130, 131, 200, 63);
        getContentPane().add(scrollPaneComments);

        JButton btnAdd = new JButton("Add");
        btnAdd.setBounds(276, 222, 100, 30);
        btnAdd.addActionListener(e -> {
            try {
                // Check that one of the radio buttons is selected
                if (getClassification() == null) {
                    JOptionPane.showMessageDialog(this, "Please select a classification (GREEN or RED).",
                            "Invalid Selection", JOptionPane.WARNING_MESSAGE);
                    return; 
                }
                saveAlarm(task);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
        getContentPane().add(btnAdd);

        rdbtnGREEN = new JRadioButton("GREEN");
        rdbtnGREEN.setFont(new Font("Tahoma", Font.PLAIN, 20));
        rdbtnGREEN.setBounds(126, 21, 99, 23);
        getContentPane().add(rdbtnGREEN);

        rdbtnRED = new JRadioButton("RED");
        rdbtnRED.setFont(new Font("Tahoma", Font.PLAIN, 20));
        rdbtnRED.setBounds(126, 52, 99, 23);
        getContentPane().add(rdbtnRED);

        // Button group to ensure only one can be selected
        ButtonGroup classificationGroup = new ButtonGroup();
        classificationGroup.add(rdbtnGREEN);
        classificationGroup.add(rdbtnRED);

        chckbxNotify = new JCheckBox("Notify");
        chckbxNotify.setBounds(130, 226, 99, 23);
        getContentPane().add(chckbxNotify);

        JLabel lblTimeOfAlarm = new JLabel("Time of alarm");
        lblTimeOfAlarm.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblTimeOfAlarm.setBounds(20, 95, 100, 25);
        getContentPane().add(lblTimeOfAlarm);

        textFieldTime = new JTextField();
        textFieldTime.setBounds(130, 93, 100, 25);
        getContentPane().add(textFieldTime);
    }

    /**
     * Determines the alarm classification based on which radio button is selected.
     * 
     * @return a {@link Classification} enum (GREEN or RED) depending on the
     *         selected radio button, or {@code null} if none is selected.
     */
    private Classification getClassification() {
        if (rdbtnGREEN.isSelected()) {
            return Classification.GREEN;
        } else if (rdbtnRED.isSelected()) {
            return Classification.RED;
        }
        return null;
    }

    /**
     * Validates and saves the alarm to the database. It checks the time format,
     * ensures a valid {@link Classification} is selected, and then calls the
     * {@link AlarmCtrl} to create and persist the alarm.
     * 
     * @param task the {@link Task} to which the alarm is associated.
     */
    private void saveAlarm(Task task) {
        boolean notify = chckbxNotify.isSelected();
        int reportID;
        try {
            reportID = rc.findReportByTaskID(task.getTaskID()).getReportNr();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Could not find report for this task: " + ex.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        LocalDateTime localTimeDate;
        String startTime = textFieldTime.getText().trim();

        // Validate and parse the start time
        String regex = "^([01]\\d|2[0-3]):[0-5]\\d$";
        Pattern pattern = Pattern.compile(regex);
        Matcher startMatcher = pattern.matcher(startTime);

        if (!startMatcher.matches()) {
            JOptionPane.showMessageDialog(this, "Invalid time format. Use HH:mm", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
            LocalTime localTime = LocalTime.parse(startTime, timeFormatter);
            localTimeDate = LocalDateTime.of(task.getDate(), localTime);
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this, "Invalid time format. Use HH:mm", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            ac.createAlarm(localTimeDate, getClassification(), txtCommentsArea.getText(), notify, reportID);
            this.dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error creating alarm: " + ex.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
