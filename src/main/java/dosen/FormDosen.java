package dosen;

import com.mycompany.mavenproject1.FormAdmin;
import com.mycompany.mavenproject1.database;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class FormDosen extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(FormDosen.class.getName());

    public FormDosen() {
        initComponents();
        jTable1.setDefaultEditor(Object.class, null);
        loadData();
    }

    private void loadData() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID Dosen");
        model.addColumn("Nama Dosen");
        model.addColumn("Prodi");

        database db = new database();
        try (Connection con = db.koneksi();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(
                 "SELECT d.id_dosen, d.nama_dosen, p.nama_prodi " +
                 "FROM dosen d " +
                 "LEFT JOIN prodi p ON d.id_prodi = p.id_prodi " +
                 "ORDER BY d.id_dosen")) {

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("id_dosen"),
                    rs.getString("nama_dosen"),
                    rs.getString("nama_prodi")
                });
            }

        } catch (SQLException e) {
            javax.swing.JOptionPane.showMessageDialog(this,
                "Error: " + e.getMessage(), "Error",
                javax.swing.JOptionPane.ERROR_MESSAGE);
        }

        jTable1.setModel(model);
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        Back = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        Tambah = new javax.swing.JButton();
        Edit = new javax.swing.JButton();
        Hapus = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        Back.setText("Back");
        Back.addActionListener(evt -> {
            new FormAdmin().setVisible(true);
            this.dispose();
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24));
        jLabel1.setText("Form Dosen");

        Tambah.setText("Tambah");
        Tambah.addActionListener(evt -> {
            new TambahDosen().setVisible(true);
            this.dispose();
        });

        Edit.setText("Edit");
        Edit.addActionListener(evt -> {
            int baris = jTable1.getSelectedRow();
            if (baris == -1) {
                javax.swing.JOptionPane.showMessageDialog(this,
                    "Pilih dosen yang ingin diedit!",
                    "Peringatan", javax.swing.JOptionPane.WARNING_MESSAGE);
                return;
            }
            int idDosen      = (int) jTable1.getValueAt(baris, 0);
            String namaDosen = (String) jTable1.getValueAt(baris, 1);
            String prodi     = (String) jTable1.getValueAt(baris, 2);

            new EditDosen(idDosen, namaDosen, prodi).setVisible(true);
            this.dispose();
        });

        Hapus.setText("Hapus");
        Hapus.addActionListener(evt -> {
            int baris = jTable1.getSelectedRow();
            if (baris == -1) {
                javax.swing.JOptionPane.showMessageDialog(this,
                    "Pilih dosen yang ingin dihapus!",
                    "Peringatan", javax.swing.JOptionPane.WARNING_MESSAGE);
                return;
            }

            int konfirmasi = javax.swing.JOptionPane.showConfirmDialog(this,
                "Yakin ingin menghapus dosen ini?",
                "Konfirmasi Hapus", javax.swing.JOptionPane.YES_NO_OPTION);
            if (konfirmasi != javax.swing.JOptionPane.YES_OPTION) return;

            int idDosen = (int) jTable1.getValueAt(baris, 0);

            database db = new database();
            try (Connection con = db.koneksi();
                 PreparedStatement ps = con.prepareStatement(
                     "DELETE FROM dosen WHERE id_dosen=?")) {
                ps.setInt(1, idDosen);
                ps.executeUpdate();
                javax.swing.JOptionPane.showMessageDialog(this,
                    "Dosen berhasil dihapus!", "Sukses",
                    javax.swing.JOptionPane.INFORMATION_MESSAGE);
                loadData();
            } catch (SQLException e) {
                javax.swing.JOptionPane.showMessageDialog(this,
                    "Error: " + e.getMessage(), "Error",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object[][] {{}, {}, {}, {}},
            new String[]{}
        ));
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(Back))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(102, 102, 102)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(Tambah)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(Edit)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(Hapus)))
                .addContainerGap(108, Short.MAX_VALUE))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Back)
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Tambah)
                    .addComponent(Edit)
                    .addComponent(Hapus))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 281, Short.MAX_VALUE))
        );

        pack();
    }

    public static void main(String args[]) {
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
        java.awt.EventQueue.invokeLater(() -> new FormDosen().setVisible(true));
    }

    private javax.swing.JButton Back;
    private javax.swing.JButton Edit;
    private javax.swing.JButton Hapus;
    private javax.swing.JButton Tambah;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
}
