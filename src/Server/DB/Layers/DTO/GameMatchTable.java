package Server.DB.Layers.DTO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class GameMatchTable {
    private server.db.layers.BUS.PlayerBUS playerBUS;
    public void addGameMatchesToPanel(JPanel jPanel3, List<server.db.layers.DTO.GameMatch> matchList) {
        // Cột tiêu đề cho bảng
        String[] columnNames = {
                "Match ID", "Player 1", "Player 2", "Winner", "Started Time"
        };
        // Dữ liệu cho bảng
        playerBUS= new server.db.layers.BUS.PlayerBUS();
        Object[][] data = new Object[matchList.size()][5]; // 8 cột tương ứng với các thuộc tính
        for (int i = 0; i < matchList.size(); i++) {
            server.db.layers.DTO.GameMatch match = matchList.get(i);
            data[i][0] = match.getId();
            int Id1= match.getPlayerID1();
            data[i][1]=playerBUS.getById(Id1).getName();
            int Id2= match.getPlayerID2();
            data[i][2] = playerBUS.getById(Id2).getName();
            int winnerId= match.getWinnerID();
            if(playerBUS.getById(winnerId)==null){
                data[i][3] = "No winner";
            }
            else{
            data[i][3] = playerBUS.getById(winnerId).getName();}
            data[i][4] = match.getStartedTime();
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
