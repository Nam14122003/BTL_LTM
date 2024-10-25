/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package server.view;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.util.Vector;
import javax.imageio.ImageIO;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import server.controller.Admin;
import static server.controller.Admin.joinRoom;
import server.db.layers.dto.GameMatch;
import server.db.layers.dto.Player;

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

        setTitle("Caro Đại Chiến - Quản lý server");
        try {
            URL url = new URL("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQLw-jhsZE2DjYrRMcg7lXaRnhiYpjwKkRJ2w&s");
            Image logo = ImageIO.read(url);
            setIconImage(logo);
        } catch (IOException e) {
            System.out.println("Không thể tải logo.");
        }
        this.setBackground(new Color(240, 240, 240));
        this.setSize(1200, 800);
        this.setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Server " + admin.server.getServerName(), JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));

        titleLabel.setPreferredSize(new Dimension(204, 40));

        titleLabel.setOpaque(true);
        titleLabel.setBackground(new Color(135, 206, 250)); // Light blue background
        titleLabel.setForeground(Color.WHITE); // White text color

        add(titleLabel, BorderLayout.NORTH);

        // Tạo bảng hiển thị các cặp đấu
        String[] columns = {"Cặp đấu", "Player 1", "Player 2", "Thời gian(s)", "Xem trực tiếp"};
        Object[][] data = {
            {"1", "son", "tha nh1", "170", "Xem"},
            {"2", "justin bieber", "thanh2", "170", "Xem"},
            {"3", "linda", "thanh3", "170", "Xem"},
            {"4", "peter", "thanh4", "170", "Xem"},
            {"5", "lan", "thanh5", "170", "Xem"},
            {"6", "nam", "thanh6", "170", "Xem"}
        };

        DefaultTableModel model = new DefaultTableModel(data, columns);
        matchTable = new JTable(model);
        matchTable.setRowHeight(30);

        // Tùy chỉnh header của bảng
        Font headerFont = new Font("Tahoma", Font.BOLD, 18);
        matchTable.getTableHeader().setFont(headerFont);
        matchTable.getTableHeader().setReorderingAllowed(false);
        matchTable.getTableHeader().setBackground(new Color(220, 230, 242));
        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) matchTable.getTableHeader().getDefaultRenderer();
        headerRenderer.setHorizontalAlignment(JLabel.CENTER);

        JScrollPane scrollPane = new JScrollPane(matchTable);
        add(scrollPane, BorderLayout.CENTER);

        // Tạo panel chứa các nút chức năng
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Padding giữa các nút

        Font buttonFont = new Font("Tahoma", Font.PLAIN, 16);

        // Nút Số người online
        btnUserCount = new JButton("Số người online");
        btnUserCount.setFont(buttonFont);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        buttonPanel.add(btnUserCount, gbc);

        // Nút User thắng nhiều nhất
        btnBestUser = new JButton("User thắng nhiều nhất");
        btnBestUser.setFont(buttonFont);
        gbc.gridx = 1;
        gbc.gridy = 0;
        buttonPanel.add(btnBestUser, gbc);

        // Nút Trận đấu ngắn nhất
        btnShortestMatch = new JButton("Trận đấu ngắn nhất");
        btnShortestMatch.setFont(buttonFont);
        gbc.gridx = 2;
        gbc.gridy = 0;
        buttonPanel.add(btnShortestMatch, gbc);

        // Nút Khóa người dùng
        btnBlockUser = new JButton("Khóa người dùng");
        btnBlockUser.setFont(buttonFont);
        gbc.gridx = 0;
        gbc.gridy = 1;
        buttonPanel.add(btnBlockUser, gbc);

        // Nút Refresh
        btnRefresh = new JButton("Refresh");
        btnRefresh.setFont(buttonFont);
        gbc.gridx = 1;
        gbc.gridy = 1;
        buttonPanel.add(btnRefresh, gbc);

        // Nút Trở về
        btnBack = new JButton("Trở về");
        btnBack.setFont(buttonFont);
        gbc.gridx = 2;
        gbc.gridy = 1;
        buttonPanel.add(btnBack, gbc);

        add(buttonPanel, BorderLayout.SOUTH);

        // Sự kiện các nút
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
        
        btnUserCount.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showUserCount(admin);
            }
        });

        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Đóng cửa sổ hiện tại
            }
        });

        setVisible(true);
    }


    private void showUserCount(Admin admin) {
        JOptionPane.showMessageDialog(this, "Số người dùng đang online: " + admin.getUserOnline());
    }

    public void setListRoom(Vector vdata, Vector vheader, Admin admin) {
        matchTable.setModel(new DefaultTableModel(vdata, vheader));
        matchTable.getColumnModel().getColumn(3).setCellRenderer(new ButtonRendererAdmin());
        matchTable.getColumnModel().getColumn(3).setCellEditor(new ButtonEditorAdmin(new JCheckBox(), matchTable, admin));
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        TableColumnModel columnModel = matchTable.getColumnModel();

        for (int i = 0; i < columnModel.getColumnCount(); i++) {
            if (i != 3) {
                columnModel.getColumn(i).setCellRenderer(centerRenderer);
            }
        }
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
                    admin.joinRoom.connect(admin.server.getServerIp(), admin.server.getServerPort());
                    admin.joinRoom.login("admin1", "Bcvt.son2003");
                    admin.joinRoom.watchRoom(roomId);

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
        setForeground(Color.WHITE);
        setFont(new Font("Tahoma", Font.BOLD, 16));
        setBackground(new Color(135, 206, 250)); // Màu mặc định là xanh dương nhạt
        setText("Hóng hớt");
        return this;
    }
}
