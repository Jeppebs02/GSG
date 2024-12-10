package gui;

import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;

import ctrl.TaskCtrl;
import javax.swing.JCheckBox;
import java.awt.Font;

public class AddAlarmView extends JDialog{
    private JTextField txtComments;
    


    public AddAlarmView() {
    
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
            
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    });
    getContentPane().add(btnAdd);
    
    JCheckBox chckbxGREEN = new JCheckBox("GREEN");
    chckbxGREEN.setFont(new Font("Tahoma", Font.PLAIN, 20));
    chckbxGREEN.setBounds(126, 21, 99, 23);
    getContentPane().add(chckbxGREEN);
    
    JCheckBox chckbxRed = new JCheckBox("RED");
    chckbxRed.setFont(new Font("Tahoma", Font.PLAIN, 20));
    chckbxRed.setBounds(126, 52, 99, 23);
    getContentPane().add(chckbxRed);
    
    JCheckBox chckbxNotify = new JCheckBox("Notify");
    chckbxNotify.setBounds(130, 226, 99, 23);
    getContentPane().add(chckbxNotify);
    }
}


