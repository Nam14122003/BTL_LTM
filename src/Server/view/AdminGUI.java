/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package server.view;

import client.RunClient;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import javax.swing.table.TableCellRenderer;
import server.RunServer;
import server.controller.Admin;
import static server.controller.Admin.runClient;
import server.db.layers.dal.ServerDAL;
import server.db.layers.dto.GameMatch;
import server.db.layers.dto.Player;
import server.db.layers.dto.Server;

/**
 *
 * @author Admin
 */
public class AdminGUI extends javax.swing.JFrame {

    private JTable matchTable;
    private JButton btnUserCount;
    private JButton btnBestUser;
    private JButton btnShortestMatch;
    private JButton btnBlockUser;
    private JButton btnRefresh;
    private JButton btnBack;

    public AdminGUI(Admin admin) {

        setTitle("Admin Control Panel");
        setSize(800, 600);
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        // Tạo bảng hiển thị các cặp đấu
        String[] columns = {"Cặp đấu", "Player 1", "Player 2", "Thời gian(s)", "Xem trực tiếp"};
        Object[][] data = {
            {"1", "son", "tha nh1", "170", "Xem"},
            {"2", "justin bieber", "thanh2", "170", "Xem"},
            {"3", "linda", "thanh3", "170", "Xem"},
            {"4", "peter", "thanh4", "170", "Xem"},
            {"5", "lan", "thanh5", "170", "Xem"},
            {"6", "nam", "thanh6", "170", "Xem"},};

        DefaultTableModel model = new DefaultTableModel(data, columns);
        matchTable = new JTable(model);
        matchTable.setRowHeight(30);

        JScrollPane scrollPane = new JScrollPane(matchTable);
        scrollPane.setBounds(50, 50, 700, 200);
        add(scrollPane);

        // Các nút chức năng admin
        btnUserCount = new JButton("Số người online");
        btnUserCount.setBounds(50, 270, 150, 30);
        add(btnUserCount);

        btnBestUser = new JButton("User thắng nhiều nhất");
        btnBestUser.setBounds(220, 270, 150, 30);
        add(btnBestUser);

        btnShortestMatch = new JButton("Trận đấu ngắn nhất");
        btnShortestMatch.setBounds(390, 270, 150, 30);
        add(btnShortestMatch);

        btnBlockUser = new JButton("Khóa người dùng");
        btnBlockUser.setBounds(560, 270, 150, 30);
        add(btnBlockUser);

        btnRefresh = new JButton("Refresh");
        btnRefresh.setBounds(50, 320, 150, 30);
        add(btnRefresh);

        // Nút Trở về
        btnBack = new JButton("Trở về");
        btnBack.setBounds(50, 370, 150, 30);
        add(btnBack);

        btnBestUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showBestUser(admin);
            }
        });

        btnRefresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                admin.onReceiveListRoom();
            }
        });

        btnShortestMatch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showShortestMatch(admin);
            }
        });

        btnBlockUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                blockUserPrompt(admin);
            }
        });

        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Xử lý quay về màn hình chính hoặc thoát
                dispose(); // Đóng cửa sổ hiện tại
            }
        });
        //   setVisible(true);
    }

    // Phương thức xử lý số người online
//    private void showUserCount(Admin admin) {
//        // Gọi phương thức hiển thị số người online
//        System.out.println("Số người dùng đang online: " + RunServer.getClientManager().getSize());
//    }
    public void setListRoom(Vector vdata, Vector vheader, Admin admin) {
        matchTable.setModel(new DefaultTableModel(vdata, vheader));
        matchTable.getColumnModel().getColumn(3).setCellRenderer(new ButtonRendererAdmin());
        matchTable.getColumnModel().getColumn(3).setCellEditor(new ButtonEditorAdmin(new JCheckBox(), matchTable, admin));
    }

    private void showBestUser(Admin admin) {

        Player bestPlayer = admin.getBestUser();
        if (bestPlayer != null) {
            JOptionPane.showMessageDialog(this, "User thắng nhiều nhất: " + bestPlayer.getName() + " - " + bestPlayer.getWinCount() + " chiến thắng");
        } else {
            JOptionPane.showMessageDialog(this, "Không tìm thấy user thắng nhiều nhất");
        }
    }

    // Hiển thị trận đấu ngắn nhất
    private void showShortestMatch(Admin admin) {

        GameMatch match = admin.getShortestMatch();
        if (match != null) {
            JOptionPane.showMessageDialog(this, "Trận đấu ngắn nhất với thời gian: " + match.getPlayTime() + " giây");
        } else {
            JOptionPane.showMessageDialog(this, "Không tìm thấy trận đấu ngắn nhất");
        }
    }

    // Khóa người dùng
    private void blockUserPrompt(Admin admin) {
        String username = JOptionPane.showInputDialog("Nhập username để khóa:");

        String result = admin.blockUser(username);
        JOptionPane.showMessageDialog(this, result);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(AdminGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AdminGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AdminGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AdminGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
//       new AdminGUI();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}

class ButtonEditorAdmin extends DefaultCellEditor {

    protected JButton button;
    private JTable table;

    public ButtonEditorAdmin(JCheckBox checkBox, JTable table, Admin admin) {
        super(checkBox);
        this.table = table;
        button = new JButton();
        button.setOpaque(true);

        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                 int column = 0;
                 int row = table.getSelectedRow();
                if (row >= 0) {
                  String roomId = table.getModel().getValueAt(row, column).toString();
                     admin.runClient.socketHandler.watchRoom(roomId);
        
                 }  
                fireEditingStopped();
            }
        });
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        return "Hóng hớt";
    }

    @Override
    public boolean stopCellEditing() {
        return super.stopCellEditing();
    }

    @Override
    protected void fireEditingStopped() {
        super.fireEditingStopped();
    }
}

class ButtonRendererAdmin extends JButton implements TableCellRenderer {

    public ButtonRendererAdmin() {
        setOpaque(true);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

        setText("Hóng hớt");
        return this;
    }
}
