package view;

import java.sql.SQLException;
import model.Admin;
import model.AdminDAO;

import javax.swing.*;

public class LoginRegisterFrame extends JFrame {
    private JTextField tfUsername;
    private JPasswordField pfPassword;
    private JButton btnLogin, btnRegister;

    public LoginRegisterFrame() throws SQLException {
        setTitle("Login / Register Admin");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        tfUsername = new JTextField(15);
        pfPassword = new JPasswordField(15);
        btnLogin = new JButton("Login");
        btnRegister = new JButton("Register");

        JPanel panel = new JPanel();
        panel.add(new JLabel("Username:"));
        panel.add(tfUsername);
        panel.add(new JLabel("Password:"));
        panel.add(pfPassword);
        panel.add(btnLogin);
        panel.add(btnRegister);

        add(panel);

        AdminDAO adminDAO = new AdminDAO();

        btnLogin.addActionListener(e -> {
            String username = tfUsername.getText();
            String password = String.valueOf(pfPassword.getPassword());

            if (adminDAO.login(username, password)) {
                JOptionPane.showMessageDialog(this, "Login berhasil!");
                dispose();
                new MainFrame().setVisible(true); // tampilkan halaman utama
            } else {
                JOptionPane.showMessageDialog(this, "Login gagal!");
            }
        });

        btnRegister.addActionListener(e -> {
            String username = tfUsername.getText();
            String password = String.valueOf(pfPassword.getPassword());

            Admin admin = new Admin(username, password);
            if (adminDAO.register(admin)) {
                JOptionPane.showMessageDialog(this, "Registrasi berhasil!");
            } else {
                JOptionPane.showMessageDialog(this, "Registrasi gagal!");
            }
        });
    }
}
