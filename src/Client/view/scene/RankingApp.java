package client.view.scene;

import Client.model.RankingAppData;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Vector;

public class RankingApp extends JFrame {

    private JTable rankingTable;
    private DefaultTableModel tableModel;
    private String currentUsername;  // Tên người đăng nhập
    private JLabel lblUserRank;  // Nhãn hiển thị thứ hạng người dùng hiện tại
    private JPanel panelUserInfo; // Panel để hiển thị thông tin cá nhân người dùng

    public RankingApp() {
        setTitle("Bảng Xếp Hạng");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  // Đóng cửa sổ khi thoát bảng xếp hạng
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
    }

    // Hàm tải dữ liệu bảng xếp hạng từ cơ sở dữ liệu
    public void loadRankingData(ArrayList<RankingAppData> rankingAppDatas, String userName) {
        this.currentUsername = userName;
        //
        // Tạo bảng và mô hình dữ liệu
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Hạng");
        tableModel.addColumn("Tên");
        tableModel.addColumn("Điểm");
        tableModel.addColumn("Số trận");
        tableModel.addColumn("Thắng");
        tableModel.addColumn("Hòa");
        tableModel.addColumn("Thua");
        rankingTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(rankingTable);
        add(scrollPane, BorderLayout.CENTER);
        // Panel để hiển thị thông tin cá nhân của người dùng
        panelUserInfo = new JPanel();
        panelUserInfo.setLayout(new BoxLayout(panelUserInfo, BoxLayout.Y_AXIS));
        panelUserInfo.setBorder(BorderFactory.createTitledBorder("Thông tin của bạn"));
        lblUserRank = new JLabel();
        panelUserInfo.add(lblUserRank);
        add(panelUserInfo, BorderLayout.SOUTH);
        // Tải dữ liệu bảng xếp hạng
        // Xóa dữ liệu cũ trong bảng
        tableModel.setRowCount(0);
        // Biến để lưu thứ hạng của người đăng nhập
        int userRank = -1;
        int rank = 1;
        // Thêm dữ liệu mới vào bảng và tìm thứ hạng của người đăng nhập
        for(RankingAppData rankingAppData : rankingAppDatas){
            Vector<String> row = new Vector<>();
            row.add(String.valueOf(rank)); // Hạng
            row.add(rankingAppData.getName()); // Tên người chơi
            row.add(String.valueOf(rankingAppData.getScore())); // Điểm
            row.add(String.valueOf(rankingAppData.getMatchCount())); // Số trận
            row.add(String.valueOf(rankingAppData.getWinCount())); // Thắng
            row.add(String.valueOf(rankingAppData.getDrawCount()));
            row.add(String.valueOf(rankingAppData.getLoseCount())); // Thua
            tableModel.addRow(row);
            
            // Kiểm tra nếu tên là người đăng nhập
            if (rankingAppData.getUserName().equals(currentUsername)) {
                userRank = rank;  // Lưu lại thứ hạng của người đăng nhập
                
                // Cập nhật thông tin của người dùng
                lblUserRank.setText("<html>Tên: " + rankingAppData.getName() + "<br/>"
                        + "Hạng: " + userRank + "<br/>"
                                + "Điểm: " + rankingAppData.getScore() + "<br/>"
                                        + "Số trận: " + rankingAppData.getMatchCount() + "<br/>"
                                                + "Thắng: " + rankingAppData.getWinCount() + "<br/>"
                                                        + "Hòa: " + rankingAppData.getDrawCount() + "<br/>"
                                                                + "Thua: " + rankingAppData.getLoseCount() + "</html>");
            }
            
            rank++;
        }
        // Hiển thị thông báo nếu không tìm thấy người dùng trong bảng xếp hạng
        if (userRank == -1) {
            lblUserRank.setText("Bạn không có trong bảng xếp hạng.");
        }
    }
}
