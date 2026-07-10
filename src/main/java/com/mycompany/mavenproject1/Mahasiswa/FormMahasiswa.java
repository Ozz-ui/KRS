/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.mavenproject1.Mahasiswa;
import javax.swing.JOptionPane;
/**
 *
 * @author nicos
 */
public class FormMahasiswa extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(FormMahasiswa.class.getName());

    /**
     * Creates new form FormMahasiswa
     */
    public FormMahasiswa() {
        initComponents();
        tbMahasiswa.setDefaultEditor(Object.class, null);
        loadProdi();
        loadData();
        tbMahasiswa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pilihBaris();
            }
        });
    }
    
    private void loadProdi() {
        comboProdi.removeAllItems();
        com.mycompany.mavenproject1.database db = new com.mycompany.mavenproject1.database();
        try (java.sql.Connection con = db.koneksi();
             java.sql.Statement st = con.createStatement();
             java.sql.ResultSet rs = st.executeQuery("SELECT id_prodi, nama_prodi FROM prodi")) {
            while (rs.next()) {
                comboProdi.addItem(rs.getInt("id_prodi") + " - " + rs.getString("nama_prodi"));
            }
        } catch (java.sql.SQLException e) {
            javax.swing.JOptionPane.showMessageDialog(this,
                "Gagal load prodi: " + e.getMessage(), "Error",
                javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadData() {
        javax.swing.table.DefaultTableModel model = new javax.swing.table.DefaultTableModel(
            new String[]{"NIM", "Nama", "Angkatan", "Semester", "Prodi"}, 0
        ) {
            public boolean isCellEditable(int row, int col) { return false; }
        };

        com.mycompany.mavenproject1.database db = new com.mycompany.mavenproject1.database();
        try (java.sql.Connection con = db.koneksi();
             java.sql.Statement st = con.createStatement();
             java.sql.ResultSet rs = st.executeQuery(
                 "SELECT m.nim, m.nama, m.angkatan, m.semester, p.nama_prodi " +
                 "FROM mahasiswa m LEFT JOIN prodi p ON m.id_prodi = p.id_prodi")) {
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("nim"),
                    rs.getString("nama"),
                    rs.getString("angkatan"),
                    rs.getInt("semester"),
                    rs.getString("nama_prodi")
                });
            }
        } catch (java.sql.SQLException e) {
            javax.swing.JOptionPane.showMessageDialog(this,
                "Error: " + e.getMessage(), "Error",
                javax.swing.JOptionPane.ERROR_MESSAGE);
        }
        tbMahasiswa.setModel(model);
    }

    private void pilihBaris() {
        int baris = tbMahasiswa.getSelectedRow();
        if (baris == -1) return;

        String nim = tbMahasiswa.getValueAt(baris, 0).toString();

        com.mycompany.mavenproject1.database db = new com.mycompany.mavenproject1.database();
        try (java.sql.Connection con = db.koneksi();
             java.sql.PreparedStatement ps = con.prepareStatement(
                 "SELECT m.*, p.nama_prodi FROM mahasiswa m " +
                 "LEFT JOIN prodi p ON m.id_prodi = p.id_prodi WHERE m.nim = ?")) {
            ps.setString(1, nim);
            java.sql.ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                fieldNim.setText(rs.getString("nim"));
                fieldNama.setText(rs.getString("nama"));
                fieldPassword.setText(rs.getString("password"));
                fieldAngkatan.setText(rs.getString("angkatan"));
                fieldSemester.setText(String.valueOf(rs.getInt("semester")));

                int idProdi = rs.getInt("id_prodi");
                for (int i = 0; i < comboProdi.getItemCount(); i++) {
                    if (comboProdi.getItemAt(i).startsWith(idProdi + " -")) {
                        comboProdi.setSelectedIndex(i);
                        break;
                    }
                }
            }
        } catch (java.sql.SQLException e) {
            javax.swing.JOptionPane.showMessageDialog(this,
                "Error: " + e.getMessage(), "Error",
                javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }

    private void bersihForm() {
        fieldNim.setText("");
        fieldNama.setText("");
        fieldPassword.setText("");
        fieldAngkatan.setText("");
        fieldSemester.setText("");
        if (comboProdi.getItemCount() > 0) comboProdi.setSelectedIndex(0);
        tbMahasiswa.clearSelection();
        fieldNim.setEditable(true);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Back = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        labelNim = new javax.swing.JLabel();
        fieldNim = new javax.swing.JTextField();
        labelNama = new javax.swing.JLabel();
        fieldNama = new javax.swing.JTextField();
        labelPassword = new javax.swing.JLabel();
        fieldPassword = new javax.swing.JPasswordField();
        labelAngkatan = new javax.swing.JLabel();
        fieldAngkatan = new javax.swing.JTextField();
        labelSemester = new javax.swing.JLabel();
        fieldSemester = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        comboProdi = new javax.swing.JComboBox<>();
        btnTambah = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();
        btnBersih = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbMahasiswa = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        Back.setText("Back");
        Back.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BackActionPerformed(evt);
            }
        });

        jLabel1.setText("FORM MAHASISWA");

        labelNim.setText("NIM :");

        labelNama.setText("Nama :");

        labelPassword.setText("Password :");

        labelAngkatan.setText("Angkatan :");

        labelSemester.setText("Semester :");

        jLabel2.setText("Prodi :");

        comboProdi.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btnTambah.setText("Tambah");
        btnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahActionPerformed(evt);
            }
        });

        btnUpdate.setText("Update");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnHapus.setText("Hapus");
        btnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusActionPerformed(evt);
            }
        });

        btnBersih.setText("Bersih");
        btnBersih.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBersihActionPerformed(evt);
            }
        });

        jScrollPane1.setViewportView(tbMahasiswa);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(Back)
                        .addGap(181, 181, 181)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(labelSemester)
                                    .addComponent(jLabel2))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(fieldSemester)
                                    .addComponent(comboProdi, 0, 112, Short.MAX_VALUE)))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addComponent(labelAngkatan)
                                    .addGap(18, 18, 18)
                                    .addComponent(fieldAngkatan))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addComponent(labelPassword)
                                    .addGap(18, 18, 18)
                                    .addComponent(fieldPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(labelNama)
                                    .addComponent(labelNim))
                                .addGap(36, 36, 36)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(fieldNama, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(fieldNim, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 525, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnHapus)
                        .addGap(18, 18, 18)
                        .addComponent(btnBersih))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnTambah)
                        .addGap(18, 18, 18)
                        .addComponent(btnUpdate)))
                .addContainerGap(25, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Back)
                    .addComponent(jLabel1))
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(labelNim)
                            .addComponent(fieldNim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 21, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(labelNama)
                            .addComponent(fieldNama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(labelPassword)
                            .addComponent(fieldPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(labelAngkatan)
                            .addComponent(fieldAngkatan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(labelSemester)
                            .addComponent(fieldSemester, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(comboProdi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTambah)
                    .addComponent(btnUpdate))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnHapus)
                    .addComponent(btnBersih))
                .addGap(8, 8, 8))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BackActionPerformed
        new com.mycompany.mavenproject1.FormAdmin().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_BackActionPerformed

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
        String nim      = fieldNim.getText().trim();
        String nama     = fieldNama.getText().trim();
        String password = new String(fieldPassword.getPassword()).trim();
        String angkatan = fieldAngkatan.getText().trim();
        String semester = fieldSemester.getText().trim();
        
        // Validasi format angkatan (contoh: 2024/2025)
        if (!angkatan.matches("\\d{4}/\\d{4}")) {
        javax.swing.JOptionPane.showMessageDialog(this,
            "Format angkatan harus seperti 2024/2025",
            "Peringatan",
            JOptionPane.WARNING_MESSAGE);
        fieldAngkatan.requestFocus();
        return;
}

        if (nim.isEmpty() || nama.isEmpty() || password.isEmpty() || angkatan.isEmpty() || semester.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this,
                "Semua field harus diisi!", "Peringatan",
                javax.swing.JOptionPane.WARNING_MESSAGE);
            return;
        }

        String prodiItem = (String) comboProdi.getSelectedItem();
        int idProdi = Integer.parseInt(prodiItem.split(" - ")[0]);

        com.mycompany.mavenproject1.database db = new com.mycompany.mavenproject1.database();
        try (java.sql.Connection con = db.koneksi()) {

            // Cek NIM duplikat
            java.sql.PreparedStatement cek = con.prepareStatement(
                "SELECT COUNT(*) FROM mahasiswa WHERE nim = ?");
            cek.setString(1, nim);
            java.sql.ResultSet rs = cek.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                javax.swing.JOptionPane.showMessageDialog(this,
                    "NIM sudah terdaftar!", "Peringatan",
                    javax.swing.JOptionPane.WARNING_MESSAGE);
                return;
            }

            java.sql.PreparedStatement ps = con.prepareStatement(
                "INSERT INTO mahasiswa (nim, nama, password, angkatan, id_prodi, semester) " +
                "VALUES (?, ?, ?, ?, ?, ?)");
            ps.setString(1, nim);
            ps.setString(2, nama);
            ps.setString(3, password);
            ps.setString(4, angkatan);
            ps.setInt(5, idProdi);
            ps.setInt(6, Integer.parseInt(semester));
            ps.executeUpdate();

            javax.swing.JOptionPane.showMessageDialog(this,
                "Mahasiswa berhasil ditambahkan!", "Sukses",
                javax.swing.JOptionPane.INFORMATION_MESSAGE);
            bersihForm();
            loadData();

        } catch (java.sql.SQLException e) {
            javax.swing.JOptionPane.showMessageDialog(this,
                "Error: " + e.getMessage(), "Error",
                javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnTambahActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        String nim      = fieldNim.getText().trim();
        String nama     = fieldNama.getText().trim();
        String password = new String(fieldPassword.getPassword()).trim();
        String angkatan = fieldAngkatan.getText().trim();
        String semester = fieldSemester.getText().trim();
        
        // Validasi format angkatan
        if (!angkatan.matches("\\d{4}/\\d{4}")) {
         javax.swing.JOptionPane.showMessageDialog(this,
            "Format angkatan harus seperti 2024/2025",
            "Peringatan",
            JOptionPane.WARNING_MESSAGE);
        fieldAngkatan.requestFocus();
        return;
}

        if (nim.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this,
                "Pilih data dari tabel terlebih dahulu!", "Peringatan",
                javax.swing.JOptionPane.WARNING_MESSAGE);
            return;
        }

        String prodiItem = (String) comboProdi.getSelectedItem();
        int idProdi = Integer.parseInt(prodiItem.split(" - ")[0]);

        com.mycompany.mavenproject1.database db = new com.mycompany.mavenproject1.database();
        try (java.sql.Connection con = db.koneksi()) {

            java.sql.PreparedStatement ps;
            if (password.isEmpty()) {
                ps = con.prepareStatement(
                    "UPDATE mahasiswa SET nama=?, angkatan=?, id_prodi=?, semester=? WHERE nim=?");
                ps.setString(1, nama);
                ps.setString(2, angkatan);
                ps.setInt(3, idProdi);
                ps.setInt(4, Integer.parseInt(semester));
                ps.setString(5, nim);
            } else {
                ps = con.prepareStatement(
                    "UPDATE mahasiswa SET nama=?, password=?, angkatan=?, id_prodi=?, semester=? WHERE nim=?");
                ps.setString(1, nama);
                ps.setString(2, password);
                ps.setString(3, angkatan);
                ps.setInt(4, idProdi);
                ps.setInt(5, Integer.parseInt(semester));
                ps.setString(6, nim);
            }
            ps.executeUpdate();

            javax.swing.JOptionPane.showMessageDialog(this,
                "Data berhasil diupdate!", "Sukses",
                javax.swing.JOptionPane.INFORMATION_MESSAGE);
            bersihForm();
            loadData();

        } catch (java.sql.SQLException e) {
            javax.swing.JOptionPane.showMessageDialog(this,
                "Error: " + e.getMessage(), "Error",
                javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
        String nim = fieldNim.getText().trim();
        if (nim.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this,
                "Pilih data dari tabel terlebih dahulu!", "Peringatan",
                javax.swing.JOptionPane.WARNING_MESSAGE);
            return;
        }

        int konfirmasi = javax.swing.JOptionPane.showConfirmDialog(this,
            "Yakin ingin menghapus mahasiswa dengan NIM " + nim + "?",
            "Konfirmasi Hapus", javax.swing.JOptionPane.YES_NO_OPTION);
        if (konfirmasi != javax.swing.JOptionPane.YES_OPTION) return;

        com.mycompany.mavenproject1.database db = new com.mycompany.mavenproject1.database();
        try (java.sql.Connection con = db.koneksi();
             java.sql.PreparedStatement ps = con.prepareStatement(
                 "DELETE FROM mahasiswa WHERE nim = ?")) {
            ps.setString(1, nim);
            ps.executeUpdate();

            javax.swing.JOptionPane.showMessageDialog(this,
                "Data berhasil dihapus!", "Sukses",
                javax.swing.JOptionPane.INFORMATION_MESSAGE);
            bersihForm();
            loadData();

        } catch (java.sql.SQLException e) {
            javax.swing.JOptionPane.showMessageDialog(this,
                "Error: " + e.getMessage(), "Error",
                javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnHapusActionPerformed

    private void btnBersihActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBersihActionPerformed
        bersihForm();
    }//GEN-LAST:event_btnBersihActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new FormMahasiswa().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Back;
    private javax.swing.JButton btnBersih;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnTambah;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JComboBox<String> comboProdi;
    private javax.swing.JTextField fieldAngkatan;
    private javax.swing.JTextField fieldNama;
    private javax.swing.JTextField fieldNim;
    private javax.swing.JPasswordField fieldPassword;
    private javax.swing.JTextField fieldSemester;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labelAngkatan;
    private javax.swing.JLabel labelNama;
    private javax.swing.JLabel labelNim;
    private javax.swing.JLabel labelPassword;
    private javax.swing.JLabel labelSemester;
    private javax.swing.JTable tbMahasiswa;
    // End of variables declaration//GEN-END:variables
}
