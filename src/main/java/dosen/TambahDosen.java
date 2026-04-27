package dosen;

import com.mycompany.mavenproject1.database;
import java.sql.*;

public class TambahDosen extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(TambahDosen.class.getName());

    public TambahDosen() {
        initComponents();
        loadProdi();
    }

    private void loadProdi() {
        jComboBox1.removeAllItems();
        database db = new database();
        try (Connection con = db.koneksi();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT id_prodi, nama_prodi FROM prodi ORDER BY id_prodi")) {
            while (rs.next()) {
                jComboBox1.addItem(rs.getInt("id_prodi") + " - " + rs.getString("nama_prodi"));
            }
        } catch (SQLException e) {
            javax.swing.JOptionPane.showMessageDialog(this,
                "Error: " + e.getMessage(), "Error",
                javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        Back = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        labelNama = new javax.swing.JLabel();
        fieldNama = new javax.swing.JTextField();
        labelProdi = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        btnTambah = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        Back.setText("Back");
        Back.addActionListener(evt -> {
            new FormDosen().setVisible(true);
            this.dispose();
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24));
        jLabel1.setText("Form Tambah Dosen");

        labelNama.setFont(new java.awt.Font("Segoe UI", 1, 12));
        labelNama.setText("Nama Dosen");

        labelProdi.setFont(new java.awt.Font("Segoe UI", 1, 12));
        labelProdi.setText("Prodi");

        btnTambah.setBackground(new java.awt.Color(26, 86, 160));
        btnTambah.setFont(new java.awt.Font("Segoe UI", 1, 24));
        btnTambah.setForeground(new java.awt.Color(255, 255, 255));
        btnTambah.setText("Tambah Data");
        btnTambah.addActionListener(evt -> {
            String nama = fieldNama.getText().trim();
            if (nama.isEmpty()) {
                javax.swing.JOptionPane.showMessageDialog(this,
                    "Nama dosen tidak boleh kosong!",
                    "Peringatan", javax.swing.JOptionPane.WARNING_MESSAGE);
                return;
            }
            String selectedProdi = jComboBox1.getSelectedItem().toString();
            int idProdi = Integer.parseInt(selectedProdi.split(" - ")[0]);

            database db = new database();
            try (Connection con = db.koneksi();
                 PreparedStatement ps = con.prepareStatement(
                     "INSERT INTO dosen (nama_dosen, id_prodi) VALUES (?, ?)")) {
                ps.setString(1, nama);
                ps.setInt(2, idProdi);
                ps.executeUpdate();
                javax.swing.JOptionPane.showMessageDialog(this,
                    "Dosen berhasil ditambahkan!", "Sukses",
                    javax.swing.JOptionPane.INFORMATION_MESSAGE);
                new FormDosen().setVisible(true);
                this.dispose();
            } catch (SQLException e) {
                javax.swing.JOptionPane.showMessageDialog(this,
                    "Error: " + e.getMessage(), "Error",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(fieldNama)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Back)
                            .addComponent(labelNama)
                            .addComponent(labelProdi)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 152, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(35, 35, 35))
            .addGroup(layout.createSequentialGroup()
                .addGap(89, 89, 89)
                .addComponent(btnTambah)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Back)
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(labelNama)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(fieldNama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(labelProdi)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(50, Short.MAX_VALUE))
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
        java.awt.EventQueue.invokeLater(() -> new TambahDosen().setVisible(true));
    }

    private javax.swing.JButton Back;
    private javax.swing.JButton btnTambah;
    private javax.swing.JTextField fieldNama;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel labelNama;
    private javax.swing.JLabel labelProdi;
}
