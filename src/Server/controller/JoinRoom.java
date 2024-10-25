/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package server.controller;

import server.controller.Admin;
import client.RunClient;
import client.model.ChatItem;
import client.model.PlayerInGame;
import client.model.ProfileData;
import client.view.scene.MainMenu;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import shared.constant.StreamData;
import static shared.constant.StreamData.Type.*;
import shared.helper.MyHash;
import shared.security.AES;
import shared.security.RSA;


public class JoinRoom {

    Socket s;
    DataInputStream dis;
    DataOutputStream dos;

    String loginUser = "admin1"; // lưu tài khoản đăng nhập hiện tại
    String roomId = null; // lưu room hiện tại

    Thread listener = null;
    AES aes;

    public String connect(String addr, int port) {
        try {
            // getting ip 
            InetAddress ip = InetAddress.getByName(addr);

            // establish the connection with server port 
            s = new Socket();
            s.connect(new InetSocketAddress(ip, port), 4000);
            System.out.println("Connected to " + ip + ":" + port + ", localport:" + s.getLocalPort());

            // obtaining input and output streams
            dis = new DataInputStream(s.getInputStream());
            dos = new DataOutputStream(s.getOutputStream());

            // close old listener
            if (listener != null && listener.isAlive()) {
                listener.interrupt();
            }

            // listen to server
            listener = new Thread(this::listen);
            listener.start();

            // security
            initSecurityAES();

            // connect success
            return "success";

        } catch (IOException e) {

            // connect failed
            return "failed;" + e.getMessage();
        }
    }

    private void listen() {
        boolean running = true;

        while (running) {
            try {
                // receive the data from server
                String received = dis.readUTF();

                // decrypt data if needed
                if (aes != null) {
                    received = aes.decrypt(received);
                }

                System.out.println("RECEIVED: " + received);

                // process received data
                StreamData.Type type = StreamData.getTypeFromData(received);

                switch (type) {

                    case LOGOUT:
                        onReceiveLogout(received);
                        break;



                    case WATCH_ROOM:
                        onReceiveWatchRoom(received);
                        break;


                    case DATA_ROOM:
                        onReceiveDataRoom(received);
                        break;

                    case CHAT_ROOM:
                        onReceiveChatRoom(received);
                        break;

                    case LEAVE_ROOM:
                        onReceiveLeaveRoom(received);
                        break;

                    case CLOSE_ROOM:
                        onReceiveCloseRoom(received);
                        break;
                    case GAME_EVENT:
                        onReceiveGameEvent(received);
                        break;

                    case EXIT:
                        running = false;
                }

            } catch (IOException ex) {
                Logger.getLogger(JoinRoom.class.getName()).log(Level.SEVERE, null, ex);
                running = false;
            }
        }

        try {
            // closing resources
            s.close();
            dis.close();
            dos.close();
        } catch (IOException ex) {
            Logger.getLogger(JoinRoom.class.getName()).log(Level.SEVERE, null, ex);
        }

        // alert if connect interup
        JOptionPane.showMessageDialog(null, "Mất kết nối tới server", "Lỗi", JOptionPane.ERROR_MESSAGE);
        Admin.closeAllScene();
    }
    private void onReceiveJoinRoom(String received) {
        String[] splitted = received.split(";");
        String roomId = splitted[2];

        // save room id
        this.roomId = roomId;

        // change scene
        Admin.openScene(Admin.SceneName.INGAME);
        Admin.inGameScene.setTitle("Phòng #" + roomId);

        // get room data
        dataRoom(roomId);
    }
    
    private void onReceiveLogout(String received) {
        // xóa user login
        this.loginUser = null;

        // chuyển scene
        Admin.closeAllScene();
    }

    private void onReceiveWatchRoom(String received) {
        String[] splitted = received.split(";");
        String status = splitted[1];

        if (status.equals("failed")) {
            String failedMsg = splitted[2];
            JOptionPane.showMessageDialog(Admin.runServer, failedMsg, "Lỗi", JOptionPane.ERROR_MESSAGE);

        } else if (status.equals("success")) {
            onReceiveJoinRoom(received);
        }
    }

 

    // in game
    private void onReceiveDataRoom(String received) {
        String[] splitted = received.split(";");
        String status = splitted[1];

        if (status.equals("failed")) {
            String failedMsg = splitted[2];
            JOptionPane.showMessageDialog(Admin.runServer, failedMsg, "Không lấy được dữ liệu phòng", JOptionPane.ERROR_MESSAGE);

        } else if (status.equals("success")) {
            // vị trí đọc hiện tại (trong mảng splitted)
            int index = 2;

            // player
            PlayerInGame p1 = new PlayerInGame(splitted[index++], splitted[index++], splitted[index++]);
            PlayerInGame p2 = new PlayerInGame(splitted[index++], splitted[index++], splitted[index++]);
            Admin.inGameScene.setPlayerInGame(p1, p2);

            // list player+viewer
            int playersCount = Integer.parseInt(splitted[index++]);
            ArrayList<PlayerInGame> listUser = new ArrayList<>();

            for (int i = 0; i < playersCount; i++) {
                listUser.add(new PlayerInGame(splitted[index++], splitted[index++], splitted[index++]));
            }
            Admin.inGameScene.setListUser(listUser);

            // timer data
            int matchTimerLimit = Integer.parseInt(splitted[index++]);
            int matchTimerTick = Integer.parseInt(splitted[index++]);
            int turnTimerLimit = Integer.parseInt(splitted[index++]);
            int turnTimerTick = Integer.parseInt(splitted[index++]);

            Admin.inGameScene.startGame(turnTimerLimit, matchTimerLimit);
            Admin.inGameScene.setTurnTimerTick(turnTimerTick);
            Admin.inGameScene.setMatchTimerTick(matchTimerTick);

            // board data
            // TODO array demension
            int historyCount = Integer.parseInt(splitted[index++]);

            for (int i = 0; i < historyCount; i++) {
                Admin.inGameScene.addPoint(
                        Integer.parseInt(splitted[index++]),
                        Integer.parseInt(splitted[index++]),
                        splitted[index++]
                );
            }

            // change turn
            Admin.inGameScene.changeTurnFrom(splitted[index - 1]);
        }
    }

    private void onReceiveChatRoom(String received) {
        String[] splitted = received.split(";");
        ChatItem c = new ChatItem(splitted[1], splitted[2], splitted[3]);
        Admin.inGameScene.addChat(c);
    }

    private void onReceiveLeaveRoom(String received) {
        String[] splitted = received.split(";");
        String status = splitted[1];

        if (status.equals("failed")) {
            String failedMsg = splitted[2];
            JOptionPane.showMessageDialog(Admin.inGameScene, failedMsg, "Không thể thoát phòng", JOptionPane.ERROR_MESSAGE);

        } else if (status.equals("success")) {
            Admin.closeScene(Admin.SceneName.INGAME);
        }
        logout();
    }

    private void onReceiveCloseRoom(String received) {
        String[] splitted = received.split(";");
        String reason = splitted[1];

        // change scene
        Admin.closeScene(Admin.SceneName.INGAME);

        // show noti
        JOptionPane.showMessageDialog(
                Admin.runServer,
                "Phòng " + this.roomId + " đã đóng do " + reason, "Đóng",
                JOptionPane.INFORMATION_MESSAGE
        );

        // remove room id
        this.roomId = null;
        logout();
    }


    // game events
    public void onReceiveGameEvent(String received) {
        String[] splitted = received.split(";");
        StreamData.Type gameEventType = StreamData.getType(splitted[1]);

        switch (gameEventType) {
            case START:
                int turnTimeLimit = Integer.parseInt(splitted[2]);
                int matchTimeLimit = Integer.parseInt(splitted[3]);

                Admin.inGameScene.startGame(turnTimeLimit, matchTimeLimit);
                break;

            case MOVE:
                int row = Integer.parseInt(splitted[2]);
                int column = Integer.parseInt(splitted[3]);
                String _user = splitted[4];

                Admin.inGameScene.addPoint(row, column, _user);
                Admin.inGameScene.changeTurnFrom(_user);
                break;

            case WIN:
                String winUser = splitted[2];
                Admin.inGameScene.setWin(winUser);
                break;

            case TURN_TICK:
                int turnValue = Integer.parseInt(splitted[2]);
                Admin.inGameScene.setTurnTimerTick(turnValue);
                break;

            case TURN_TIMER_END:
                String winnerUser = splitted[2];
                Admin.inGameScene.setWin(winnerUser);
                break;

            case MATCH_TICK:
                int matchValue = Integer.parseInt(splitted[2]);
                Admin.inGameScene.setMatchTimerTick(matchValue);
                break;

            case MATCH_TIMER_END:
                Admin.inGameScene.setWin(null);
                break;
        }
    }

    // ============= functions ===============
    // auth
    private void initSecurityAES() {
        // create new key
        aes = new AES();

        // encrypt aes key using rsa with server's public key 
        RSA clientSideRSA = new RSA()
                .preparePublicKey("publicKey");

        String aesKey = aes.getSecretKey();
        String aesKeyEncrypted = clientSideRSA.encrypt(aesKey);

        // send to server
        sendPureData(StreamData.Type.AESKEY.name() + ";" + aesKeyEncrypted);
    }

    public void login(String username, String password) {
        // hasing password
        String passwordHash = MyHash.hash(password);

        // prepare data
        String data = StreamData.Type.LOGIN.name() + ";" + username + ";" + passwordHash;

        // send data
        sendData(data);
    }

    public void logout() {
        // prepare data
        String data = StreamData.Type.LOGOUT.name();

        // send data
        sendData(data);
    }

    // main menu
    public void listRoom() {
        sendData(StreamData.Type.LIST_ROOM.name());
    }

    public void watchRoom(String roomId) {
        sendData(StreamData.Type.WATCH_ROOM.name() + ";" + roomId);
    }

    // pair match
    public void findMatch() {
        sendData(StreamData.Type.FIND_MATCH.name());
    }

    public void cancelFindMatch() {
        sendData(StreamData.Type.CANCEL_FIND_MATCH.name());
    }

    public void declinePairMatch() {
        sendData(StreamData.Type.REQUEST_PAIR_MATCH.name() + ";no");
    }

    public void acceptPairMatch() {
        sendData(StreamData.Type.REQUEST_PAIR_MATCH.name() + ";yes");
    }

    // in game
    public void dataRoom(String roomId) {
        sendData(StreamData.Type.DATA_ROOM.name() + ";" + roomId);
    }

    public void chatRoom(String chatMsg) {
        sendData(StreamData.Type.CHAT_ROOM.name() + ";" + chatMsg);
    }

    public void leaveRoom() {
        sendData(StreamData.Type.LEAVE_ROOM.name());
    }

    // profile
    public void changePassword(String oldPassword, String newPassword) {
        // hasing password
        String oldPasswordHash = MyHash.hash(oldPassword);
        String newPasswordHash = MyHash.hash(newPassword);

        // prepare data
        String data = StreamData.Type.CHANGE_PASSWORD.name() + ";" + oldPasswordHash + ";" + newPasswordHash;

        // send data
        sendData(data);
    }

    public void getProfile(String username) {
        // prepare data
        String data = StreamData.Type.GET_PROFILE.name() + ";" + username;
        System.out.println("Prepar send data: "+ data);
        // send data
        sendData(data);
        System.out.println("Sent data");
    }

    public void editProfile(String name, String avatar, String yearOfBirth, String gender) {
        // prepare data
        String data = StreamData.Type.EDIT_PROFILE + ";"
                + name + ";"
                + avatar + ";"
                + yearOfBirth + ";"
                + gender;

        // send data
        sendData(data);
    }

    // game event
    public void sendGameEvent(String gameEventData) {
        sendData(StreamData.Type.GAME_EVENT.name() + ";" + gameEventData);
    }

    public void move(int x, int y) {
        sendGameEvent(StreamData.Type.MOVE + ";" + x + ";" + y);
    }

    // send data
    public void sendPureData(String data) {
        try {
            dos.writeUTF(data);

        } catch (IOException ex) {
            Logger.getLogger(JoinRoom.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void sendData(String data) {
        try {

            String encrypted = aes.encrypt(data);
            System.out.println("Encrypted data: " + encrypted);
            dos.writeUTF(encrypted);


        } catch (IOException ex) {
            Logger.getLogger(JoinRoom.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    // get set
    public String getLoginUser() {
        return loginUser;
    }

    public void setLoginUser(String user) {
        this.loginUser = user;
    }
}
