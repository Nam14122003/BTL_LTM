package server.db.layers.dto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class GameMatchTable {
    private server.db.layers.bus.PlayerBUS playerBUS;
    public void addGameMatchesToPanel(JPanel jPanel3, List<server.db.layers.dto.GameMatch> matchList) {
        // Cột tiêu đề cho bảng
        String[] columnNames = {
                "Match ID", "Player 1", "Player 2", "Winner", "Started Time"
        };

        // Dữ liệu cho bảng
        playerBUS = new server.db.layers.bus.PlayerBUS();
        Object[][] data = new Object[matchList.size()][5]; // 5 cột
        for (int i = 0; i < matchList.size(); i++) {
            server.db.layers.dto.GameMatch match = matchList.get(i);
            data[i][0] = match.getId();
            int Id1 = match.getPlayerID1();
            data[i][1] = playerBUS.getById(Id1).getName();
            int Id2 = match.getPlayerID2();
            data[i][2] = playerBUS.getById(Id2).getName();
            int winnerId = match.getWinnerID();
            if (playerBUS.getById(winnerId) == null) {
                data[i][3] = "No winner";
            } else {
                data[i][3] = playerBUS.getById(winnerId).getName();
            }
            data[i][4] = match.getStartedTime();
        }

        // Tạo model cho bảng
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        JTable table = new JTable(model);

        // Thiết lập cỡ chữ cho bảng
        table.setFont(new Font("Dialog", Font.PLAIN, 16));
        table.getTableHeader().setFont(new Font("Dialog", Font.BOLD, 18));
        table.setIntercellSpacing(new Dimension(10, 5));
        table.setRowHeight(30); // Chiều cao hàng
        table.setShowGrid(true);
        table.setGridColor(Color.BLACK);

        // Tạo JScrollPane
        JScrollPane jScrollPane2 = new JScrollPane(table);
        jScrollPane2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED); // Cung cấp thanh cuộn khi cần
        jScrollPane2.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED); // Cung cấp thanh cuộn khi cần

        // Thiết lập bố cục cho JPanel
        jPanel3.setLayout(new BoxLayout(jPanel3, BoxLayout.Y_AXIS)); // Bố cục dọc
        jPanel3.add(jScrollPane2); // Thêm JScrollPane vào JPanel
        jPanel3.add(Box.createRigidArea(new Dimension(0, 5))); // Khoảng cách
        jPanel3.revalidate(); // Cập nhật layout
        jPanel3.repaint(); // Vẽ lại JPanel
    }


}
