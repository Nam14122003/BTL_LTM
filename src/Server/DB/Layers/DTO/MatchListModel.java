package Server.DB.Layers.DTO;

import javax.swing.AbstractListModel;
import java.util.ArrayList;
import java.util.List;

public class MatchListModel extends AbstractListModel<String> {
    private List<server.db.layers.DTO.GameMatch> matches;

    public MatchListModel(List<server.db.layers.DTO.GameMatch> matches) {
        this.matches = matches;
    }

    @Override
    public int getSize() {
        return matches.size();
    }

    @Override
    public String getElementAt(int index) {
        server.db.layers.DTO.GameMatch match = matches.get(index);
        // Tạo chuỗi để hiển thị thông tin chi tiết trận đấu
        return "Match ID: " + match.getId() +
                ", Player 1: " + match.getPlayerID1() +
                ", Player 2: " + match.getPlayerID2() +
                ", Winner: " + match.getWinnerID() +
                ", Play Time: " + match.getPlayTime() +
                ", Total Moves: " + match.getTotalMove() +
                ", Started: " + match.getStartedTime();
    }
}
