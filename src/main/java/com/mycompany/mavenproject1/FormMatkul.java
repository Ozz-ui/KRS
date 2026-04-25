package com.mycompany.mavenproject1;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class FormMatkul extends JFrame {

    JTextField txtKodeMK, txtNamaMK, txtSKS, txtIdProdi;
    JTable tabel;
    DefaultTableModel modelTabel;
    JButton btnTambah, btnUpdate, btnHapus, btnBersih;

    public FormMatkul() {
        setTitle("Form Mata Kuliah");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel lblHeader = new JLabel("SISTEM KRS", SwingConstants.CENTER);
        lblHeader.setBounds(0, 0, 700, 40);
        add(lblHeader);

        JLabel lblKodeMK = new JLabel("Kode MK :");
        lblKodeMK.setBounds(20, 55, 80, 25);
        add(lblKodeMK);

        txtKodeMK = new JTextField();
        txtKodeMK.setBounds(105, 55, 150, 25);
        add(txtKodeMK);

        JLabel lblNamaMK = new JLabel("Nama MK :");
        lblNamaMK.setBounds(20, 90, 80, 25);
        add(lblNamaMK);

        txtNamaMK = new JTextField();
        txtNamaMK.setBounds(105, 90, 150, 25);
        add(txtNamaMK);

        JLabel lblSKS = new JLabel("SKS :");
        lblSKS.setBounds(20, 125, 80, 25);
        add(lblSKS);

        txtSKS = new JTextField();
        txtSKS.setBounds(105, 125, 150, 25);
        add(txtSKS);

        JLabel lblIdProdi = new JLabel("ID Prodi :");
        lblIdProdi.setBounds(20, 160, 80, 25);
        add(lblIdProdi);

        txtIdProdi = new JTextField();
        txtIdProdi.setBounds(105, 160, 150, 25);
        add(txtIdProdi);

        btnTambah = new JButton("Tambah");
        btnTambah.setBounds(20, 205, 85, 25);
        add(btnTambah);

        btnUpdate = new JButton("Update");
        btnUpdate.setBounds(115, 205, 85, 25);
        add(btnUpdate);

        btnHapus = new JButton("Hapus");
        btnHapus.setBounds(20, 240, 85, 25);
        add(btnHapus);

        btnBersih = new JButton("Bersih");
        btnBersih.setBounds(115, 240, 85, 25);
        add(btnBersih);

        String[] kolom = {"Kode MK", "Nama MK", "SKS", "ID Prodi"};
        modelTabel = new DefaultTableModel(kolom, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };

        tabel = new JTable(modelTabel);
        JScrollPane scroll = new JScrollPane(tabel);
        scroll.setBounds(280, 55, 390, 380);
        add(scroll);

        btnTambah.addActionListener(e -> tambahData());
        btnUpdate.addActionListener(e -> updateData());
        btnHapus.addActionListener(e -> hapusData());
        btnBersih.addActionListener(e -> bersihForm());

        tabel.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) isiFormDariTabel();
        });

        loadData();
        setVisible(true);
    }

    Connection getConn() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/krs", "root", "");
    }

    void loadData() {
        modelTabel.setRowCount(0);
        try {
            Connection c = getConn();
            Statement s = c.createStatement();
            ResultSet r = s.executeQuery("SELECT * FROM mata_kuliah");
            while (r.next()) {
                modelTabel.addRow(new Object[]{
                    r.getString("kode_mk"),
                    r.getString("nama_mk"),
                    r.getInt("sks"),
                    r.getInt("id_prodi")
                });
            }
            c.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    void tambahData() {
        try {
            Connection c = getConn();
            PreparedStatement ps = c.prepareStatement(
                "INSERT INTO mata_kuliah (kode_mk, nama_mk, sks, id_prodi) VALUES (?,?,?,?)");
            ps.setString(1, txtKodeMK.getText());
            ps.setString(2, txtNamaMK.getText());
            ps.setInt(3, Integer.parseInt(txtSKS.getText()));
            ps.setInt(4, Integer.parseInt(txtIdProdi.getText()));
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Data berhasil ditambahkan!");
            bersihForm();
            loadData();
            c.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    void updateData() {
        try {
            Connection c = getConn();
            PreparedStatement ps = c.prepareStatement(
                "UPDATE mata_kuliah SET nama_mk=?, sks=?, id_prodi=? WHERE kode_mk=?");
            ps.setString(1, txtNamaMK.getText());
            ps.setInt(2, Integer.parseInt(txtSKS.getText()));
            ps.setInt(3, Integer.parseInt(txtIdProdi.getText()));
            ps.setString(4, txtKodeMK.getText());
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Data berhasil diupdate!");
            bersihForm();
            loadData();
            c.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    void hapusData() {
        int pilih = JOptionPane.showConfirmDialog(this, "Yakin hapus data ini?");
        if (pilih == JOptionPane.YES_OPTION) {
            try {
                Connection c = getConn();
                PreparedStatement ps = c.prepareStatement(
                    "DELETE FROM mata_kuliah WHERE kode_mk=?");
                ps.setString(1, txtKodeMK.getText());
                ps.executeUpdate();
                JOptionPane.showMessageDialog(this, "Data berhasil dihapus!");
                bersihForm();
                loadData();
                c.close();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        }
    }

    void isiFormDariTabel() {
        int row = tabel.getSelectedRow();
        if (row >= 0) {
            txtKodeMK.setText(modelTabel.getValueAt(row, 0).toString());
            txtNamaMK.setText(modelTabel.getValueAt(row, 1).toString());
            txtSKS.setText(modelTabel.getValueAt(row, 2).toString());
            txtIdProdi.setText(modelTabel.getValueAt(row, 3).toString());
        }
    }

    void bersihForm() {
        txtKodeMK.setText("");
        txtNamaMK.setText("");
        txtSKS.setText("");
        txtIdProdi.setText("");
        tabel.clearSelection();
    }

    public static void main(String[] args) {
        new FormMatkul();
    }
}
