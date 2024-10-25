/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.view.scene;

import static shared.constant.Server.*;
import client.RunClient;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

/**
 *
 * @author Hoang Tran < hoang at 99.hoangtran@gmail.com >
 */
public class ConnectServer extends javax.swing.JFrame {

    public ConnectServer() {
        try {
            URL url = new URL("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQLw-jhsZE2DjYrRMcg7lXaRnhiYpjwKkRJ2w&s");
            Image logo = ImageIO.read(url);
            setIconImage(logo);
        } catch (IOException e) {
            System.out.println("1");
        }
        initComponents();
        this.setLocationRelativeTo(null);
        jLabel2.setOpaque(true);
        
        pgbLoading.setVisible(false);
        getContentPane().setBackground(new java.awt.Color(240, 240, 240));
        selectServer.setBackground(new java.awt.Color(255, 255, 255)); // Màu nền trắng
        selectServer.setForeground(new java.awt.Color(51, 51, 51));    // Màu chữ xám đậm
        selectServer.setFont(new java.awt.Font("Segoe UI", 0, 18));     // Font chữ "Segoe UI" cỡ 18
        selectServer.setPreferredSize(new java.awt.Dimension(500, 40)); // Chiều rộng và cao của combo box
        DefaultListCellRenderer listRenderer = new DefaultListCellRenderer();
        listRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        selectServer.setRenderer(listRenderer);

        btnConnect.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnConnect.setBackground(new java.awt.Color(255, 255, 255));
                btnConnect.setForeground(new java.awt.Color(135, 206, 250));
                // Chuyển màu khi di chuột
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnConnect.setBackground(new java.awt.Color(135, 206, 250));
                btnConnect.setForeground(new java.awt.Color(255, 255, 255));
            }
        });
    }

    public void setLoading(boolean isLoading, String btnText) {
        pgbLoading.setVisible(isLoading);
        btnConnect.setEnabled(!isLoading);
        btnConnect.setText(isLoading ? btnText : "Kết nối");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        btnConnect = new javax.swing.JButton();
        pgbLoading = new javax.swing.JProgressBar();
        selectServer = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Kết nối");
        setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setBackground(new java.awt.Color(135, 206, 250));
        jLabel2.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(240, 240, 240));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText(" CARO ĐẠI CHIẾN");

        btnConnect.setBackground(new java.awt.Color(135, 206, 250));
        btnConnect.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        btnConnect.setForeground(new java.awt.Color(255, 255, 255));
        btnConnect.setText("Kết nối");
        btnConnect.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnConnect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConnectActionPerformed(evt);
            }
        });

        pgbLoading.setIndeterminate(true);

        selectServer.setFont(new java.awt.Font("Segoe UI", 0, 22)); // NOI18N
        selectServer.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Trái Đất", "Sao Hỏa", "Mặt Trời", "Mặt Trăng" }));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabel1.setText("Chọn máy chủ");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(229, 229, 229)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(pgbLoading, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnConnect, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(86, 86, 86)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(selectServer, javax.swing.GroupLayout.PREFERRED_SIZE, 538, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 699, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(selectServer, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 219, Short.MAX_VALUE)
                .addComponent(pgbLoading, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnConnect, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnConnectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConnectActionPerformed
        String ip;
        int port;

        String selectedItem = (String) selectServer.getSelectedItem();
        System.out.println("Server đã chọn: " + selectedItem);

        switch (selectedItem) {
            case "Trái Đất":
                port = PORT_TRAI_DAT;
                ip = SERVER_TRAI_DAT;
                break;
            case "Sao Hỏa":
                port = PORT_SAO_HOA;
                ip = SERVER_SAO_HOA;
                break;
            case "Mặt Trời":
                port = PORT_MAT_TROI;
                ip = SERVER_MAT_TROI;
                break;
            case "Mặt Trăng":
                port = PORT_MAT_TRANG;
                ip = SERVER_MAT_TRANG;
                break;
            default:
                port = PORT_TRAI_DAT;
                ip = SERVER_TRAI_DAT;
                break;
        }
        System.out.println(port);

        connect(ip, port);
    }//GEN-LAST:event_btnConnectActionPerformed

    private void connect(String ip, int port) {
        // show loading
        setLoading(true, "Đang kết nối..");

        // connect to server
        new Thread(() -> {
            // call controller
            String result = RunClient.socketHandler.connect(ip, port);

            // check result
            if (result.equals("success")) {
                onSuccess();
            } else {
                String failedMsg = result.split(";")[1];
                onFailed(failedMsg);
            }
        }).start();
    }

    private void onSuccess() {
        // Kết nối thành công nhưng vẫn chờ server gửi thông báo đã nhận AES key
        // Scene sẽ được chuyển qua LoginScene khi client nhận được phản hồi từ server
        // => code chuyển scene được đưa vào socket handler, lúc listen nhận được AESKEY từ server
//        this.dispose();
//        RunClient.openScene(RunClient.SceneName.LOGIN);

        // khi kết nối thành công sẽ đợi tạo kết nối bảo mật (gửi nhận AES key)
        setLoading(true, "Đang bảo mật..");
    }

    private void onFailed(String failedMsg) {
        setLoading(false, null);
        JOptionPane.showMessageDialog(this, failedMsg, "Lỗi kết nối", JOptionPane.ERROR_MESSAGE);
    }

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
            java.util.logging.Logger.getLogger(ConnectServer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ConnectServer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ConnectServer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ConnectServer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ConnectServer().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnConnect;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JProgressBar pgbLoading;
    private javax.swing.JComboBox<String> selectServer;
    // End of variables declaration//GEN-END:variables
}
