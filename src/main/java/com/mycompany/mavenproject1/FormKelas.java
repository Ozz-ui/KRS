package com.mycompany.mavenproject1;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class FormKelas extends JFrame {

    JTextField txtIdKelas, txtKodeMK, txtIdDosen, txtTahunAjaran;
    JTextField txtJamMulai, txtJamSelesai, txtRuang;
    JComboBox<String> cbSemester, cbHari;
    JTable tabel;
    DefaultTableModel modelTabel;
    JButton btnTambah, btnUpdate, btnHapus, btnBersih;

    public FormKelas() {
        setTitle("Form Kelas");
        setSize(850, 530);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel lblHeader = new JLabel("SISTEM KRS", SwingConstants.CENTER);
        lblHeader.setBounds(0, 0, 850, 40);
        add(lblHeader);

        // ── Form Kelas ──
        JLabel lblKelas = new JLabel("-- Data Kelas --");
        lblKelas.setBounds(20, 50, 200, 20);
        add(lblKelas);

        JLabel lblIdKelas = new JLabel("ID Kelas :");
        lblIdKelas.setBounds(20, 75, 80, 25);
        add(lblIdKelas);

        txtIdKelas = new JTextField();
        txtIdKelas.setBounds(105, 75, 130, 25);
        txtIdKelas.setEditable(false);
        add(txtIdKelas);

        JLabel lblKodeMK = new JLabel("Kode MK :");
        lblKodeMK.setBounds(20, 108, 80, 25);
        add(lblKodeMK);

        txtKodeMK = new JTextField();
        txtKodeMK.setBounds(105, 108, 130, 25);
        add(txtKodeMK);

        JLabel lblIdDosen = new JLabel("ID Dosen :");
        lblIdDosen.setBounds(20, 141, 80, 25);
        add(lblIdDosen);

        txtIdDosen = new JTextField();
        txtIdDosen.setBounds(105, 141, 130, 25);
        add(txtIdDosen);

        JLabel lblTahun = new JLabel("Thn Ajaran :");
        lblTahun.setBounds(20, 174, 80, 25);
        add(lblTahun);

        txtTahunAjaran = new JTextField();
        txtTahunAjaran.setBounds(105, 174, 130, 25);
        add(txtTahunAjaran);

        JLabel lblSemester = new JLabel("Semester :");
        lblSemester.setBounds(20, 207, 80, 25);
        add(lblSemester);

        cbSemester = new JComboBox<>(new String[]{"Ganjil", "Genap"});
        cbSemester.setBounds(105, 207, 130, 25);
        add(cbSemester);

        // ── Form Jadwal ──
        JLabel lblJadwal = new JLabel("-- Data Jadwal --");
        lblJadwal.setBounds(20, 245, 200, 20);
        add(lblJadwal);

        JLabel lblHari = new JLabel("Hari :");
        lblHari.setBounds(20, 270, 80, 25);
        add(lblHari);

        cbHari = new JComboBox<>(new String[]{"Senin","Selasa","Rabu","Kamis","Jumat","Sabtu"});
        cbHari.setBounds(105, 270, 130, 25);
        add(cbHari);

        JLabel lblJamMulai = new JLabel("Jam Mulai :");
        lblJamMulai.setBounds(20, 303, 80, 25);
        add(lblJamMulai);

        txtJamMulai = new JTextField("08:00:00");
        txtJamMulai.setBounds(105, 303, 130, 25);
        add(txtJamMulai);

        JLabel lblJamSelesai = new JLabel("Jam Selesai:");
        lblJamSelesai.setBounds(20, 336, 80, 25);
        add(lblJamSelesai);

        txtJamSelesai = new JTextField("09:40:00");
        txtJamSelesai.setBounds(105, 336, 130, 25);
        add(txtJamSelesai);

        JLabel lblRuang = new JLabel("Ruang :");
        lblRuang.setBounds(20, 369, 80, 25);
        add(lblRuang);

        txtRuang = new JTextField();
        txtRuang.setBounds(105, 369, 130, 25);
        add(txtRuang);

        // ── Tombol ──
        btnTambah = new JButton("Tambah");
        btnTambah.setBounds(20, 415, 80, 25);
        add(btnTambah);

        btnUpdate = new JButton("Update");
        btnUpdate.setBounds(108, 415, 80, 25);
        add(btnUpdate);

        btnHapus = new JButton("Hapus");
        btnHapus.setBounds(20, 448, 80, 25);
        add(btnHapus);

        btnBersih = new JButton("Bersih");
        btnBersih.setBounds(108, 448, 80, 25);
        add(btnBersih);

        // ── Tabel ──
        String[] kolom = {"ID Kelas","Kode MK","ID Dosen","Thn Ajaran","Semester","Hari","Jam Mulai","Jam Selesai","Ruang"};
        modelTabel = new DefaultTableModel(kolom, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };

        tabel = new JTable(modelTabel);
        JScrollPane scroll = new JScrollPane(tabel);
        scroll.setBounds(260, 55, 565, 420);
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
        String sql = "SELECT k.id_kelas, k.kode_mk, k.id_dosen, k.tahun_ajaran, k.semester, " +
                     "j.hari, j.jam_mulai, j.jam_selesai, j.ruang " +
                     "FROM kelas k LEFT JOIN jadwal j ON k.id_kelas = j.id_kelas";
        try {
            Connection c = getConn();
            Statement s = c.createStatement();
            ResultSet r = s.executeQuery(sql);
            while (r.next()) {
                modelTabel.addRow(new Object[]{
                    r.getInt("id_kelas"),
                    r.getString("kode_mk"),
                    r.getInt("id_dosen"),
                    r.getString("tahun_ajaran"),
                    r.getString("semester"),
                    r.getString("hari"),
                    r.getString("jam_mulai"),
                    r.getString("jam_selesai"),
                    r.getString("ruang")
                });
            }
            c.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    void tambahData() {
        Connection c = null;
        try {
            c = getConn();
            c.setAutoCommit(false);

            PreparedStatement psK = c.prepareStatement(
                "INSERT INTO kelas (kode_mk, id_dosen, tahun_ajaran, semester) VALUES (?,?,?,?)",
                Statement.RETURN_GENERATED_KEYS);
            psK.setString(1, txtKodeMK.getText());
            psK.setInt(2, Integer.parseInt(txtIdDosen.getText()));
            psK.setString(3, txtTahunAjaran.getText());
            psK.setString(4, cbSemester.getSelectedItem().toString());
            psK.executeUpdate();

            ResultSet gk = psK.getGeneratedKeys();
            int idBaru = 0;
            if (gk.next()) idBaru = gk.getInt(1);

            PreparedStatement psJ = c.prepareStatement(
                "INSERT INTO jadwal (id_kelas, hari, jam_mulai, jam_selesai, ruang) VALUES (?,?,?,?,?)");
            psJ.setInt(1, idBaru);
            psJ.setString(2, cbHari.getSelectedItem().toString());
            psJ.setString(3, txtJamMulai.getText());
            psJ.setString(4, txtJamSelesai.getText());
            psJ.setString(5, txtRuang.getText());
            psJ.executeUpdate();

            c.commit();
            JOptionPane.showMessageDialog(this, "Data berhasil ditambahkan!");
            bersihForm();
            loadData();
            c.close();
        } catch (SQLException ex) {
            try { if (c != null) c.rollback(); } catch (SQLException ignored) {}
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    void updateData() {
        if (txtIdKelas.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Pilih data di tabel dulu!");
            return;
        }
        Connection c = null;
        try {
            c = getConn();
            c.setAutoCommit(false);
            int idK = Integer.parseInt(txtIdKelas.getText());

            PreparedStatement psK = c.prepareStatement(
                "UPDATE kelas SET kode_mk=?, id_dosen=?, tahun_ajaran=?, semester=? WHERE id_kelas=?");
            psK.setString(1, txtKodeMK.getText());
            psK.setInt(2, Integer.parseInt(txtIdDosen.getText()));
            psK.setString(3, txtTahunAjaran.getText());
            psK.setString(4, cbSemester.getSelectedItem().toString());
            psK.setInt(5, idK);
            psK.executeUpdate();

            PreparedStatement psJ = c.prepareStatement(
                "UPDATE jadwal SET hari=?, jam_mulai=?, jam_selesai=?, ruang=? WHERE id_kelas=?");
            psJ.setString(1, cbHari.getSelectedItem().toString());
            psJ.setString(2, txtJamMulai.getText());
            psJ.setString(3, txtJamSelesai.getText());
            psJ.setString(4, txtRuang.getText());
            psJ.setInt(5, idK);
            psJ.executeUpdate();

            c.commit();
            JOptionPane.showMessageDialog(this, "Data berhasil diupdate!");
            bersihForm();
            loadData();
            c.close();
        } catch (SQLException ex) {
            try { if (c != null) c.rollback(); } catch (SQLException ignored) {}
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    void hapusData() {
        if (txtIdKelas.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Pilih data di tabel dulu!");
            return;
        }
        int pilih = JOptionPane.showConfirmDialog(this, "Yakin hapus data ini?");
        if (pilih == JOptionPane.YES_OPTION) {
            Connection c = null;
            try {
                c = getConn();
                c.setAutoCommit(false);
                int idK = Integer.parseInt(txtIdKelas.getText());

                PreparedStatement psJ = c.prepareStatement("DELETE FROM jadwal WHERE id_kelas=?");
                psJ.setInt(1, idK);
                psJ.executeUpdate();

                PreparedStatement psK = c.prepareStatement("DELETE FROM kelas WHERE id_kelas=?");
                psK.setInt(1, idK);
                psK.executeUpdate();

                c.commit();
                JOptionPane.showMessageDialog(this, "Data berhasil dihapus!");
                bersihForm();
                loadData();
                c.close();
            } catch (SQLException ex) {
                try { if (c != null) c.rollback(); } catch (SQLException ignored) {}
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        }
    }

    void isiFormDariTabel() {
        int row = tabel.getSelectedRow();
        if (row >= 0) {
            txtIdKelas.setText(modelTabel.getValueAt(row, 0).toString());
            txtKodeMK.setText(modelTabel.getValueAt(row, 1).toString());
            txtIdDosen.setText(modelTabel.getValueAt(row, 2).toString());
            txtTahunAjaran.setText(modelTabel.getValueAt(row, 3).toString());
            cbSemester.setSelectedItem(modelTabel.getValueAt(row, 4));
            Object hari = modelTabel.getValueAt(row, 5);
            if (hari != null) cbHari.setSelectedItem(hari.toString());
            Object jm = modelTabel.getValueAt(row, 6);
            txtJamMulai.setText(jm != null ? jm.toString() : "");
            Object js = modelTabel.getValueAt(row, 7);
            txtJamSelesai.setText(js != null ? js.toString() : "");
            Object ruang = modelTabel.getValueAt(row, 8);
            txtRuang.setText(ruang != null ? ruang.toString() : "");
        }
    }

    void bersihForm() {
        txtIdKelas.setText("");
        txtKodeMK.setText("");
        txtIdDosen.setText("");
        txtTahunAjaran.setText("");
        cbSemester.setSelectedIndex(0);
        cbHari.setSelectedIndex(0);
        txtJamMulai.setText("08:00:00");
        txtJamSelesai.setText("09:40:00");
        txtRuang.setText("");
        tabel.clearSelection();
    }

    public static void main(String[] args) {
        new FormKelas();
    }
}
