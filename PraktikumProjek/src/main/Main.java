package main;

import java.sql.SQLException;
import view.LoginRegisterFrame;

public class Main {
    public static void main(String[] args) throws SQLException {
        new LoginRegisterFrame().setVisible(true);
    }
}
