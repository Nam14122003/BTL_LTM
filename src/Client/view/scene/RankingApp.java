package client.view.scene;

import server.db.layers.bus.PlayerBUS;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.util.Vector;
import server.db.layers.bus.PlayerBUS;

public class RankingApp extends JFrame {
    private JTable rankingTable;
    private DefaultTableModel tableModel;
    private String currentUsername;  // Tên người đăng nhập
    private JLabel lblUserRank;  // Nhãn hiển thị thứ hạng người dùng hiện tại
    private JPanel panelUserInfo; // Panel để hiển thị thông tin cá nhân người dùng
    private PlayerBUS playerBus;
    public RankingApp(String currentUsername) {
        this.currentUsername = currentUsername;  // Lưu tên người đăng nhập

        setTitle("Bảng Xếp Hạng");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  // Đóng cửa sổ khi thoát bảng xếp hạng
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

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
        loadRankingData();

        setVisible(true);
    }

    // Hàm tải dữ liệu bảng xếp hạng từ cơ sở dữ liệu
    private void loadRankingData() {
        try {
             playerBus= new PlayerBUS();
            // Kết nối với cơ sở dữ liệu MySQL
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/carodb", "root", "123456");
            // Truy vấn dữ liệu từ bảng Player
            String query = "SELECT ID, Score, MatchCount, WinCount,DrawCount, LoseCount FROM Player ORDER BY Score DESC";
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery();

            // Xóa dữ liệu cũ trong bảng
            tableModel.setRowCount(0);

            // Biến để lưu thứ hạng của người đăng nhập
            int userRank = -1;
            int rank = 1;

            // Thêm dữ liệu mới vào bảng và tìm thứ hạng của người đăng nhập
            while (resultSet.next()) {
                Vector<String> row = new Vector<>();
                int id = resultSet.getInt("ID");
                String name = playerBus.getById(id).getName();
                String userName = playerBus.getById(id).getUser();
                row.add(String.valueOf(rank)); // Hạng
                row.add(name); // Tên người chơi
                row.add(String.valueOf(resultSet.getDouble("Score"))); // Điểm
                row.add(String.valueOf(resultSet.getInt("MatchCount"))); // Số trận
                row.add(String.valueOf(resultSet.getInt("WinCount"))); // Thắng
                row.add(String.valueOf(resultSet.getInt("DrawCount")));
                row.add(String.valueOf(resultSet.getInt("LoseCount"))); // Thua
                tableModel.addRow(row);

                // Kiểm tra nếu tên là người đăng nhập
                if (userName.equals(currentUsername)) {
                    userRank = rank;  // Lưu lại thứ hạng của người đăng nhập

                    // Cập nhật thông tin của người dùng
                    lblUserRank.setText("<html>Tên: " + name + "<br/>" +
                            "Hạng: " + userRank + "<br/>" +
                            "Điểm: " + resultSet.getInt("Score") + "<br/>" +
                            "Số trận: " + resultSet.getInt("MatchCount") + "<br/>" +
                            "Thắng: " + resultSet.getInt("WinCount") + "<br/>" +
                            "Hòa: " + resultSet.getInt("DrawCount") + "<br/>" +
                            "Thua: " + resultSet.getInt("LoseCount") + "</html>");
                }

                rank++;
            }

            // Hiển thị thông báo nếu không tìm thấy người dùng trong bảng xếp hạng
            if (userRank == -1) {
                lblUserRank.setText("Bạn không có trong bảng xếp hạng.");
            }

            // Đóng kết nối
            resultSet.close();
            stmt.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi kết nối cơ sở dữ liệu: " + e.getMessage());
        }
    }
}
