package client.view.scene;

import Client.model.RankingAppData;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Vector;
import javax.imageio.ImageIO;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;

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
         try {
            URL url = new URL("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQLw-jhsZE2DjYrRMcg7lXaRnhiYpjwKkRJ2w&s");
            Image logo = ImageIO.read(url);
            setIconImage(logo);
        } catch (IOException e) {
            System.out.println("1");
        }
    }

    // Hàm tải dữ liệu bảng xếp hạng từ cơ sở dữ liệu
    public void loadRankingData(ArrayList<RankingAppData> rankingAppDatas, String userName) {
        this.currentUsername = userName;

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

        // Thiết lập cỡ chữ cho bảng
        rankingTable.setFont(new Font("Dialog", Font.PLAIN, 16)); // Font chữ cho các ô
        rankingTable.getTableHeader().setFont(new Font("Dialog", Font.BOLD, 18)); // Font chữ cho tiêu đề
        rankingTable.setIntercellSpacing(new Dimension(10, 5)); // Khoảng cách giữa các ô
        rankingTable.setRowHeight(30); // Chiều cao hàng
        rankingTable.setShowGrid(true); // Hiện lưới
        rankingTable.setGridColor(Color.BLACK); // Màu lưới

        // Thiết lập màu sắc cho tiêu đề
        rankingTable.getTableHeader().setForeground(Color.WHITE); // Màu chữ trắng cho tiêu đề
        rankingTable.getTableHeader().setReorderingAllowed(false);

        JScrollPane scrollPane = new JScrollPane(rankingTable);
        add(scrollPane, BorderLayout.CENTER);

        // Panel để hiển thị thông tin cá nhân của người dùng
        panelUserInfo = new JPanel();
        panelUserInfo.setLayout(new BoxLayout(panelUserInfo, BoxLayout.Y_AXIS));
        Font borderFont = new Font("Dialog", Font.BOLD, 16); // Font cho border
        Border border = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLACK), // Đường viền cho border
                "Thông tin của bạn",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                borderFont, // Thiết lập phông chữ cho tiêu đề
                Color.BLACK // Màu chữ cho tiêu đề
        );

        // Thiết lập border cho panel
        panelUserInfo.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(10, 10, 10, 10))); // Thêm khoảng trống quanh border
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
        for (RankingAppData rankingAppData : rankingAppDatas) {
            Vector<String> row = new Vector<>();
            row.add(String.valueOf(rank)); // Hạng
            row.add(rankingAppData.getName()); // Tên người chơi
            row.add(String.valueOf(rankingAppData.getScore())); // Điểm
            row.add(String.valueOf(rankingAppData.getMatchCount())); // Số trận
            row.add(String.valueOf(rankingAppData.getWinCount())); // Thắng
            row.add(String.valueOf(rankingAppData.getDrawCount())); // Hòa
            row.add(String.valueOf(rankingAppData.getLoseCount())); // Thua
            tableModel.addRow(row);

            // Kiểm tra nếu tên là người đăng nhập
            if (rankingAppData.getUserName().equals(currentUsername)) {
                userRank = rank;  // Lưu lại thứ hạng của người đăng nhập

                lblUserRank.setFont(new Font("Dialog", Font.PLAIN, 16)); // Thiết lập font chữ cho lblUserRank
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
