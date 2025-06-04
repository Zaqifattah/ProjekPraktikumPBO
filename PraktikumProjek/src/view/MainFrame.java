package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import config.Koneksi;

public class MainFrame extends JFrame {
    private JTable table;
    private DefaultTableModel model;
    private JTextField tfNama, tfUkuran, tfHarga;
    private JButton btnTambah, btnEdit, btnHapus, btnReset;

    public MainFrame() {
        setTitle("Aplikasi Toko Baju");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createTitledBorder("Form Baju"));

        tfNama = new JTextField();
        tfUkuran = new JTextField();
        tfHarga = new JTextField();

        formPanel.add(new JLabel("Nama:"));
        formPanel.add(tfNama);
        formPanel.add(new JLabel("Ukuran:"));
        formPanel.add(tfUkuran);
        formPanel.add(new JLabel("Harga:"));
        formPanel.add(tfHarga);

        btnTambah = new JButton("Tambah");
        btnEdit = new JButton("Edit");
        btnHapus = new JButton("Hapus");
        btnReset = new JButton("Reset");

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnPanel.add(btnTambah);
        btnPanel.add(btnEdit);
        btnPanel.add(btnHapus);
        btnPanel.add(btnReset);

        model = new DefaultTableModel(new String[]{"ID", "Nama", "Ukuran", "Harga"}, 0);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(formPanel, BorderLayout.CENTER);
        topPanel.add(btnPanel, BorderLayout.SOUTH);

        getContentPane().setLayout(new BorderLayout(10, 10));
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        btnTambah.addActionListener(e -> tambahData());
        btnEdit.addActionListener(e -> editData());
        btnHapus.addActionListener(e -> hapusData());
        btnReset.addActionListener(e -> resetForm());

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                tfNama.setText(model.getValueAt(row, 1).toString());
                tfUkuran.setText(model.getValueAt(row, 2).toString());
                tfHarga.setText(model.getValueAt(row, 3).toString());
            }
        });

        loadData();
        setVisible(true);
    }

    private void loadData() {
        model.setRowCount(0);
        try (Connection conn = Koneksi.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM baju")) {
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("nama"),
                        rs.getString("ukuran"),
                        rs.getDouble("harga")
                });
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Gagal load data: " + ex.getMessage());
        }
    }

    private void tambahData() {
        String nama = tfNama.getText();
        String ukuran = tfUkuran.getText();
        double harga = Double.parseDouble(tfHarga.getText());

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement("INSERT INTO baju (nama, ukuran, harga) VALUES (?, ?, ?)")) {
            ps.setString(1, nama);
            ps.setString(2, ukuran);
            ps.setDouble(3, harga);
            ps.executeUpdate();
            loadData();
            resetForm();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Gagal tambah data: " + ex.getMessage());
        }
    }

    private void editData() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow < 0) return;
        int id = (int) model.getValueAt(selectedRow, 0);

        String nama = tfNama.getText();
        String ukuran = tfUkuran.getText();
        double harga = Double.parseDouble(tfHarga.getText());

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement("UPDATE baju SET nama=?, ukuran=?, harga=? WHERE id=?")) {
            ps.setString(1, nama);
            ps.setString(2, ukuran);
            ps.setDouble(3, harga);
            ps.setInt(4, id);
            ps.executeUpdate();
            loadData();
            resetForm();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Gagal edit data: " + ex.getMessage());
        }
    }

    private void hapusData() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow < 0) return;
        int id = (int) model.getValueAt(selectedRow, 0);

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement("DELETE FROM baju WHERE id=?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
            loadData();
            resetForm();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Gagal hapus data: " + ex.getMessage());
        }
    }
private void resetForm() {
    tfNama.setText("");
    tfUkuran.setText("");
    tfHarga.setText("");
    table.clearSelection(); // diperbaiki: hapus argumen ""
}}
