
package gui;

import javax.swing.*;

public class AddShiftDialog extends JDialog {
    private JTextField txtStartTime;
    private JTextField txtEndTime;

    public AddShiftDialog() {
        setTitle("Tilføj Vagt");
        setBounds(100, 100, 300, 200);
        getContentPane().setLayout(null);

        JLabel lblStartTime = new JLabel("Starttid:");
        lblStartTime.setBounds(20, 20, 100, 25);
        getContentPane().add(lblStartTime);

        txtStartTime = new JTextField();
        txtStartTime.setBounds(130, 20, 100, 25);
        getContentPane().add(txtStartTime);

        JLabel lblEndTime = new JLabel("Sluttid:");
        lblEndTime.setBounds(20, 60, 100, 25);
        getContentPane().add(lblEndTime);

        txtEndTime = new JTextField();
        txtEndTime.setBounds(130, 60, 100, 25);
        getContentPane().add(txtEndTime);

        JButton btnSaveShift = new JButton("Gem vagt");
        btnSaveShift.setBounds(80, 120, 150, 30);
        btnSaveShift.addActionListener(e -> saveShift());
        getContentPane().add(btnSaveShift);
    }

    private void saveShift() {
        String startTime = txtStartTime.getText();
        String endTime = txtEndTime.getText();
        JOptionPane.showMessageDialog(this, "Vagt gemt: " + startTime + " - " + endTime);
        dispose();
    }
}
