/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.controller;

import client.view.helper.LookAndFeel;
import java.util.ArrayList;
import java.util.Vector;
import server.RunServer;
import server.db.layers.bus.GameMatchBUS;
import server.db.layers.bus.PlayerBUS;
import server.db.layers.dal.ServerDAL;
import server.db.layers.dto.GameMatch;
import server.db.layers.dto.Player;
import server.db.layers.dto.Server;
import server.view.AdminGUI;
import server.view.InGameAdmin;
import shared.helper.ServerHelper;

public class Admin implements Runnable {

    public enum SceneName {
        INGAME
    }
    GameMatchBUS gameMatchBus;
    PlayerBUS playerBus;
    public Server server;
    public static AdminGUI runServer;
    public static InGameAdmin inGameScene;
    public static JoinRoom joinRoom;

    public Admin(Server server) {
        LookAndFeel.setNimbusLookAndFeel();
        this.server = server;
        inGameScene = new InGameAdmin();
        joinRoom = new JoinRoom();
    }

    @Override
    public void run() {
        ServerDAL serverDAL = new ServerDAL();
        runServer = new AdminGUI(this);
        runServer.setVisible(true);
        onReceiveListRoom();
    }

    public static void openScene(Admin.SceneName sceneName) {
        if (null != sceneName) {
            switch (sceneName) {
                case INGAME:
                    inGameScene = new InGameAdmin();
                    inGameScene.setVisible(true);
                    break;
                default:
                    break;
            }
        }
    }

    public static void closeScene(Admin.SceneName sceneName) {
        if (null != sceneName) {
            switch (sceneName) {
                case INGAME:
                    inGameScene.dispose();
                    break;
                default:
                    break;
            }
        }
    }
    public static void closeAllScene() {
        inGameScene.dispose();
    }

    // Get player with the most win count
    public Player getBestUser() {
        Player bestPlayer = null;
        playerBus = new PlayerBUS();
        int max = 0;
        for (Player p : playerBus.getList()) {
            if (p.getWinCount() > max) {
                max = p.getWinCount();
                bestPlayer = new Player(p);
            }
        }
        return bestPlayer;
    }
    
    public int getUserOnline(){
        return RunServer.getMapClientManager().get(ServerHelper.getKeyServer(server)).getSize();
    }

    // Get the match with the shortest play time
    public GameMatch getShortestMatch() {
        gameMatchBus = new GameMatchBUS();
        GameMatch shortestMatch = null;
        int min = gameMatchBus.getList().get(0).getTotalMove();
        for (GameMatch m : gameMatchBus.getList()) {
            if (m.getPlayTime() < min) {
                min = m.getPlayTime();
                shortestMatch = new GameMatch(m);
            }
        }
        return shortestMatch;
    }

    // Block user with provided user
    public String blockUser(String username) {
        playerBus = new PlayerBUS();
        for (Player p : playerBus.getList()) {
            if (p.getUser().equalsIgnoreCase(username)) {
                p.setBlocked(true);
                return playerBus.update(p) ? "Success" : "Fail";
            }
        }
        return "Cant find user with provided username!";
    }

    public static void main(String[] args) {
        System.out.println("1");
    }

    public void onReceiveListRoom() {
        ArrayList<Room> listRoom = RunServer.getMapRoomManager().get(ServerHelper.getKeyServer(server)).getRooms();
        int roomCount = listRoom.size();

        Vector vheader = new Vector();
        vheader.add("Mã");
        vheader.add("Cặp đấu");
        vheader.add("Số người");
        vheader.add("Hành động");

        Vector vdata = new Vector();

        for (Room room : listRoom) {
            String pairData
                    = ((room.getClient1() != null) ? room.getClient1().getLoginPlayer().getNameId() : "_")
                    + " VS "
                    + ((room.getClient2() != null) ? room.getClient2().getLoginPlayer().getNameId() : "_");
            String roomId = room.id;
            String title = pairData;
            int clientCount = room.clients.size();

            Vector vrow = new Vector();
            vrow.add(roomId);
            vrow.add(title);
            vrow.add(clientCount);
            vrow.add("Hóng hớt");
            vdata.add(vrow);
        }        
        runServer.setListRoom(vdata, vheader, this);
    }

}
