/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.view.scene;

import client.RunClient;
import client.view.helper.Validation;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class Login extends javax.swing.JFrame {

    /**
     * Creates new form LoginF
     */
    public Login() {
        try {
            URL url = new URL("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQLw-jhsZE2DjYrRMcg7lXaRnhiYpjwKkRJ2w&s");
            Image logo = ImageIO.read(url);
            setIconImage(logo);
        } catch (IOException e) {
            System.out.println("1");
        }
        initComponents();
        this.setLocationRelativeTo(null);
        getContentPane().setBackground(new java.awt.Color(240, 240, 240)); // Màu nền xám nhạt
        pgbLoading.setVisible(false); // Ẩn thanh tiến trình mặc định
        lbHeaderText.setFont(new java.awt.Font("Segoe UI", 1, 30)); // Font Segoe UI đậm cỡ 30
        lbHeaderText.setOpaque(true);
        btnLogin.setBackground(new java.awt.Color(135, 206, 250)); // Nền xanh nhạt
        btnLogin.setForeground(new java.awt.Color(255, 255, 255)); // Chữ trắng
        btnLogin.setFont(new java.awt.Font("Segoe UI", 1, 20));    // Font đậm cỡ 20
        btnLogin.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        btnSignup.setBackground(new java.awt.Color(240, 240, 240)); // Nền xám nhạt
        btnSignup.setForeground(new java.awt.Color(0, 0, 0));      // Chữ đen
        btnSignup.setFont(new java.awt.Font("Segoe UI", 0, 18));   // Font thường cỡ 18
        btnSignup.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        // Thay đổi màu của các nút khi di chuột
        btnLogin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnLogin.setBackground(new java.awt.Color(255, 255, 255));
                btnLogin.setForeground(new java.awt.Color(135, 206, 250)); // Chuyển màu khi di chuột
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnLogin.setBackground(new java.awt.Color(135, 206, 250)); // Màu nền xanh nhạt
                btnLogin.setForeground(new java.awt.Color(255, 255, 255)); // Chữ trắng
            }
        });

        btnSignup.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnSignup.setBackground(new java.awt.Color(255, 255, 255)); // Nền trắng khi di chuột
                btnSignup.setForeground(new java.awt.Color(135, 206, 250)); // Chữ xanh nhạt khi di chuột
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnSignup.setBackground(new java.awt.Color(240, 240, 240)); // Nền xám nhạt
                btnSignup.setForeground(new java.awt.Color(0, 0, 0)); // Chữ đen
            }
        });
        txtUser.setBackground(new java.awt.Color(255, 255, 255));  // Màu nền trắng cho ô nhập tài khoản
        txtUser.setForeground(new java.awt.Color(51, 51, 51));     // Màu chữ đen
        txtUser.setFont(new java.awt.Font("Segoe UI", 0, 18));     // Font Segoe UI cỡ 18

        txPassword.setBackground(new java.awt.Color(255, 255, 255)); // Màu nền trắng cho ô nhập mật khẩu
        txPassword.setForeground(new java.awt.Color(51, 51, 51));    // Màu chữ đen
        txPassword.setFont(new java.awt.Font("Segoe UI", 0, 18));    // Font Segoe UI cỡ 18

        // Label "Tài khoản" và "Mật khẩu"
        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 20));       // Font chữ Segoe UI cỡ 20
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));          // Màu chữ đen

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 20));       // Font chữ Segoe UI cỡ 20
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jPanel1.setBackground(new java.awt.Color(240, 240, 240));
    }

    public void setLoading(boolean isLoading) {
        btnSignup.setEnabled(!isLoading);
        btnLogin.setEnabled(!isLoading);
        pgbLoading.setVisible(isLoading);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lbHeaderText = new javax.swing.JLabel();
        btnLogin = new javax.swing.JButton();
        btnSignup = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        lbUserName = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txtUser = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        lbPassword = new javax.swing.JLabel();
        txPassword = new javax.swing.JPasswordField();
        pgbLoading = new javax.swing.JProgressBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Đăng Nhập");
        setResizable(false);

        lbHeaderText.setBackground(new java.awt.Color(135, 206, 250));
        lbHeaderText.setFont(new java.awt.Font("Tahoma", 1, 25)); // NOI18N
        lbHeaderText.setForeground(new java.awt.Color(240, 240, 240));
        lbHeaderText.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbHeaderText.setText("CHÀO MỪNG TRỞ LẠI");

        btnLogin.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        btnLogin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/client/view/asset/icons8_login_32px.png"))); // NOI18N
        btnLogin.setText("ĐĂNG NHẬP");
        btnLogin.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });

        btnSignup.setText("Đăng ký?");
        btnSignup.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSignup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSignupActionPerformed(evt);
            }
        });

        lbUserName.setIcon(new javax.swing.ImageIcon(getClass().getResource("/client/view/asset/icons8_email_48px.png"))); // NOI18N

        jLabel1.setText("Tài khoản");

        txtUser.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        txtUser.setToolTipText("Email");
        txtUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUserActionPerformed(evt);
            }
        });

        jLabel2.setText("Mật khẩu");

        lbPassword.setIcon(new javax.swing.ImageIcon(getClass().getResource("/client/view/asset/icons8_password_48px.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(lbUserName)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(txtUser, javax.swing.GroupLayout.PREFERRED_SIZE, 437, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(lbPassword)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(txPassword))))
                .addContainerGap(33, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbUserName)
                    .addComponent(txtUser, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(44, 44, 44)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbPassword)
                    .addComponent(txPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(86, Short.MAX_VALUE))
        );

        pgbLoading.setIndeterminate(true);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbHeaderText, javax.swing.GroupLayout.PREFERRED_SIZE, 672, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 307, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSignup, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pgbLoading, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(lbHeaderText, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                .addComponent(pgbLoading, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSignup, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginActionPerformed
        String username = txtUser.getText();
        String password = new String(txPassword.getPassword());

        if (!Validation.checkPassword(password)) {
            JOptionPane.showMessageDialog(this, "Mật khẩu phải từ 6-30 ký tự", "Lỗi", JOptionPane.ERROR_MESSAGE);
            txPassword.requestFocus();
            return;
        }

        RunClient.socketHandler.login(username, password);
        setLoading(true);
    }//GEN-LAST:event_btnLoginActionPerformed

    private void btnSignupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSignupActionPerformed
        this.dispose();
        RunClient.openScene(RunClient.SceneName.SIGNUP);
    }//GEN-LAST:event_btnSignupActionPerformed

    private void txtUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUserActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUserActionPerformed

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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLogin;
    private javax.swing.JButton btnSignup;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lbHeaderText;
    private javax.swing.JLabel lbPassword;
    private javax.swing.JLabel lbUserName;
    private javax.swing.JProgressBar pgbLoading;
    private javax.swing.JPasswordField txPassword;
    private javax.swing.JTextField txtUser;
    // End of variables declaration//GEN-END:variables
}
