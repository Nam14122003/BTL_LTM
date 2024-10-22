/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.controller;

import client.RunClient;
import client.controller.SocketHandler;
import java.io.IOException;
import java.util.ArrayList;
import server.RunServer;
import java.util.Scanner;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import server.db.layers.bus.GameMatchBUS;
import server.db.layers.bus.PlayerBUS;
import server.db.layers.dal.ServerDAL;
import server.db.layers.dto.GameMatch;
import server.db.layers.dto.Player;
import server.db.layers.dto.Server;
import server.view.AdminGUI;
import server.view.ServerHome;
import shared.constant.StreamData;
import shared.helper.ServerHelper;

/**
 *
 * @author Hoang Tran < hoang at 99.hoangtran@gmail.com >
 */
public class Admin implements Runnable {

    GameMatchBUS gameMatchBus;
    PlayerBUS playerBus;
    Server server;
    AdminGUI runServer;
    public static RunClient runClient;
    public Admin(Server server) {
        this.server = server;
        runClient = new RunClient(1);
    }

    @Override
    public void run() {
        ServerDAL serverDAL = new ServerDAL();
        // ArrayList<Server> servers=serverDAL.getAllServers();
        runServer = new AdminGUI(this);

        runServer.setVisible(true);
        runClient.socketHandler.connect(server.getServerIp(), server.getServerPort());
        runClient.socketHandler.login("admin1", "Bcvt.son2003");
     //   runClient.closeAllScene();
        onReceiveListRoom();

//        while (!RunServer.isIsShutDown()) {
//            System.out.print("AdminCommand> ");
//            inp = s.nextLine();
//            try {
//                if (inp.equalsIgnoreCase("user-count")) {
//                    System.out.println("> " + RunServer.getClientManager().getSize());
//                } else if (inp.equalsIgnoreCase("best-user")) {
//                    showBestPlayerInfo(getBestUser());
//                } else if (inp.equalsIgnoreCase("shortest-match")) {
//                    showShortestMatch(getShortestMatch());
//                } else if (inp.indexOf("block") == 0) {
//                    System.out.println(blockUser(inp.split(" ")[1]));
//                } else if (inp.indexOf("log") == 0) {
//                    showGameMatchDetails(inp.split(" ")[1]);
//                } else if (inp.equalsIgnoreCase("room-count")) {
//                    System.out.println("> " + RunServer.getRoomManager().getSize());
//                } else if (inp.equalsIgnoreCase("shutdown")) {
//                    System.out.println("shuting down...");
//                    RunServer.setIsShutDown(true);
//                }
//            }catch(ArrayIndexOutOfBoundsException e){
//                System.out.println("Thiếu tham số !!!");
//            }
//
//            if (inp.equalsIgnoreCase("help")) {
//                System.out.println("===[List commands]======================\n"
//                        + "======= Thiết yếu =======\n"
//                        + "user-count:        số người đang online\n"
//                        + "best-user:         thông tin user thắng nhiều nhất\n"
//                        + "shortest-match:    thông tin trận đấu có thời gian ngắn nhất\n"
//                        + "block <username>: block user có username là <username khỏi hệ thống>\n"
//                        + "log <match-id>:    xem thông tin trận đấu có mã là <match-id>\n"
//                        + "======= Thêm =======\n"
//                        + "room-count: số phòng đang mở\n"
//                        + "shutdown: tắt server\n"
//                        + "=======================================");
//            }
//        }
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

    public void showBestPlayerInfo(Player p) {
        System.out.println("Player with the most win count: "
                + p.getName() + " - " + p.getUser());
        System.out.println("Win count: " + p.getWinCount());
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

    public void showShortestMatch(GameMatch m) {
        playerBus = new PlayerBUS();
        Player p1 = new Player(playerBus.getById(m.getPlayerID1()));
        Player p2 = new Player(playerBus.getById(m.getPlayerID2()));
        System.out.println("The match with shortest play time: ");
        System.out.println("Player 1: " + p1.getName());
        System.out.println("Player 1: " + p2.getName());
        System.out.println("Play time: " + m.getPlayTime() + " second");
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

    // Get Game match with provide id
    public void showGameMatchDetails(String id) {
        gameMatchBus = new GameMatchBUS();
        playerBus = new PlayerBUS();
        GameMatch m = gameMatchBus.getById(Integer.parseInt(id));
        System.out.println("Match id: " + m.getId());
        System.out.println("    + Player 1: " + playerBus.getById(m.getPlayerID1()).getName());
        System.out.println("    + Player 2: " + playerBus.getById(m.getPlayerID2()).getName());
        System.out.println("    + Winner: " + playerBus.getById(m.getWinnerID()).getName());
        System.out.println("    + Play time in second: " + m.getPlayTime());
        System.out.println("    + Total move: " + m.getTotalMove());
    }

    public static void main(String[] args) {
        System.out.println("son");
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

        runServer.setListRoom(vdata, vheader,this);
    }

}
