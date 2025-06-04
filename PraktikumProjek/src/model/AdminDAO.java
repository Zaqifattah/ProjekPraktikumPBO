package model;

import config.Koneksi;
import java.sql.*;

public class AdminDAO {
    private final Connection conn;

    public AdminDAO() throws SQLException {
        conn = Koneksi.getConnection();
    }

    public boolean login(String username, String password) {
        try {
            String sql = "SELECT * FROM admin WHERE username = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            return rs.next(); // true jika login berhasil
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean register(Admin admin) {
        try {
            String sql = "INSERT INTO admin (username, password) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, admin.getUsername());
            stmt.setString(2, admin.getPassword());
            return stmt.executeUpdate() > 0; // true jika insert berhasil
        } catch (SQLException e) {
            return false;
        }
    }
}
