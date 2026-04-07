package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginPage extends JFrame implements ActionListener {

    JLabel title, userLabel;
    JTextField username;
    JButton login;
    JPanel panel;

    LoginPage() {

        setTitle("Mini IRCTC Login");

        
        panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(230,240,255));

       
        title = new JLabel("MINI IRCTC SYSTEM");
        title.setFont(new Font("Arial", Font.BOLD, 26));
        title.setBounds(120,40,300,40);

        
        userLabel = new JLabel("Username:");
        userLabel.setFont(new Font("Arial", Font.PLAIN,16));
        userLabel.setBounds(100,140,100,30);

        
        username = new JTextField();
        username.setBounds(200,140,170,30);

        
        login = new JButton("Login");
        login.setBounds(200,200,120,40);
        login.setFont(new Font("Arial",Font.BOLD,16));
        login.setBackground(new Color(0,102,204));
        login.setForeground(Color.WHITE);

        login.addActionListener(this);

        panel.add(title);
        panel.add(userLabel);
        panel.add(username);
        panel.add(login);

        add(panel);

        setSize(500,350);
        setLocationRelativeTo(null); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {

        String name = username.getText();

        if(name.equals("")) {
            JOptionPane.showMessageDialog(this,"Please enter username");
        }
        else {
    new Dashboard(name);
    dispose();
}
    }

    public static void main(String args[]) {
        new LoginPage();
    }
}