/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.db.layers.bus;

import server.db.layers.dal.PlayerDAL;
import server.db.layers.dto.Player;
import java.util.ArrayList;
import shared.constant.Code;

/**
 *
 * @author nguye
 */
public class PlayerBUS {

    private ArrayList<Player> listPlayer = new ArrayList<>();
    private ArrayList<Player> listPlayerSortByScore = new ArrayList<>();
    private PlayerDAL playerDAL = new PlayerDAL();

    public ArrayList<Player> getListPlayerSortByScore() {
        return listPlayerSortByScore;
    }

    public PlayerBUS() {
        readDB();
    }

    public void readDB() {
        listPlayer = playerDAL.readDB();
        listPlayerSortByScore = playerDAL.getRankingApp();
    }

    public boolean add(Player p) {
        boolean status = playerDAL.add(p);

        if (status == true) {
            listPlayer.add(p);
        }

        return status;
    }

    public boolean delete(int id) {
        boolean status = playerDAL.delete(id);

        if (status == true) {
            for (int i = (listPlayer.size() - 1); i >= 0; i--) {
                if (listPlayer.get(i).getId() == id) {
                    listPlayer.remove(i);
                }
            }
        }

        return status;
    }

    public boolean update(Player p) {
        boolean status = playerDAL.update(p);

        if (status == true) {
            listPlayer.forEach((pl) -> {
                pl = new Player(p);
            });
        }

        return status;
    }

    public Player getById(int id) {
        for (Player p : listPlayer) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }

    public Player getByUser(String username) {
        for (Player p : listPlayer) {
            if (p.getUser().equals(username)) {
                return p;
            }
        }
        return null;
    }

    public ArrayList<Player> getList() {
        return listPlayer;
    }

    public String checkLogin(String username, String password) {
        // code vòng for như getByUser là được, nhưng netbeans nó hiện bóng đèn sáng ấn vào thì ra code này
        // thấy "ngầu" nên để lại :))
        // return listPlayer.stream().anyMatch((p) -> (p.getUser().equals(username) && p.getPassword().equals(password)));
        // nhưng chợt nhận ra có block player nữa, nên phải trả về String chứ ko được boolean :(

        // check user
        Player p = getByUser(username);
        if (p == null) {
            return "failed;" + Code.ACCOUNT_NOT_FOUND;
        }

        // check password
        if (!p.getPassword().equals(password)) {
            return "failed;" + Code.WRONG_PASSWORD;
        }

        // check blocked
        if (p.isBlocked()) {
            return "failed;" + Code.ACCOUNT_BLOCKED;
        }

        return "success;" + username;
    }

    public String changePassword(String username, String oldPassword, String newPassword) {
        // check user
        Player p = getByUser(username);
        if (p == null) {
            return "failed;" + Code.ACCOUNT_NOT_FOUND;
        }

        // check password
        if (!p.getPassword().equals(oldPassword)) {
            return "failed;" + Code.WRONG_PASSWORD;
        }

        // đặt pass mới
        p.setPassword(newPassword);
        boolean status = update(p);
        if (!status) {
            // lỗi không xác định
            return "failed;Lỗi khi đổi mật khẩu";
        }

        return "success";
    }

    public String signup(String username, String password, String avatar, String name, String gender, int yearOfBirth) {

        // check user 
        Player p = getByUser(username);
        if (p != null) {
            return "failed;" + Code.USERNAME_EXISTED;
        }

        // thêm vào db
        boolean status = add(new Player(username, password, avatar, name, gender, yearOfBirth));
        if (!status) {
            // lỗi ko xác định
            return "failed;Lỗi khi đăng ký";
        }

        return "success";
    }

    public String editProfile(String username, String name, String avatar, int yearOfBirth, String gender) {


        // check user
        Player p = getByUser(username);
        if (p == null) {
            return "failed;" + Code.ACCOUNT_NOT_FOUND;
        }

        // set data
        p.setName(name);
        p.setAvatar(avatar);
        p.setYearOfBirth(yearOfBirth);
        p.setGender(gender);

        // update
        boolean status = update(p);

        if (!status) {
            return "failed;Lỗi khi cập nhật thông tin cá nhân";
        }

        return "success;";
    }
}
