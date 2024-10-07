package Server.DB.Layers.DTO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class GameMatchTable {

    public void addGameMatchesToPanel(JPanel jPanel3, List<server.db.layers.DTO.GameMatch> matchList) {
        // Cột tiêu đề cho bảng
        String[] columnNames = {
                "Match ID", "Player 1 ID", "Player 2 ID", "Winner ID",
                "Play Time", "Total Moves", "Started Time", "Chat"
        };

        // Dữ liệu cho bảng
        Object[][] data = new Object[matchList.size()][8]; // 8 cột tương ứng với các thuộc tính

        for (int i = 0; i < matchList.size(); i++) {
            server.db.layers.DTO.GameMatch match = matchList.get(i);
            data[i][0] = match.getId();
            data[i][1] = match.getPlayerID1();
            data[i][2] = match.getPlayerID2();
            data[i][3] = match.getWinnerID();
            data[i][4] = match.getPlayTime();
            data[i][5] = match.getTotalMove();
            data[i][6] = match.getStartedTime(); // Đảm bảo định dạng thời gian phù hợp
            data[i][7] = match.getChat(); // Nếu có
        }

        // Tạo model cho bảng
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        JTable table = new JTable(model);
        JScrollPane jScrollPane2 = new JScrollPane(table); // Bọc JTable trong JScrollPane

        // Thiết lập bố cục cho jPanel3
        jPanel3.setLayout(new BoxLayout(jPanel3, BoxLayout.Y_AXIS)); // Bố cục theo chiều dọc

        // Thêm JScrollPane vào jPanel3
        jPanel3.add(jScrollPane2); // Thêm JScrollPane chứa JTable
        jPanel3.add(Box.createRigidArea(new Dimension(0, 5))); // Khoảng cách giữa JScrollPane và các thành phần khác
    }
}
