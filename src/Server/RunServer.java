package server;

import server.controller.Admin;
import server.controller.Client;
import server.controller.ClientManager;
import server.controller.RoomManager;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import server.db.layers.dal.ServerDAL;
import server.db.layers.dto.Server;
import server.view.ServerHome;
import shared.helper.ServerHelper;
import shared.security.RSA;

public class RunServer {

    private static volatile ConcurrentHashMap<String, ClientManager> mapClientManager;
    private static volatile ConcurrentHashMap<String, RoomManager> mapRoomManager;
    private static volatile ConcurrentHashMap<String, ServerSocket> mapServerSocket;
    private static volatile ConcurrentHashMap<String, ThreadPoolExecutor> mapThreadPoolExecutor;
    private static volatile ConcurrentHashMap<String, Admin> map;
    private static volatile RSA serverSide;
    private static ServerDAL serverDAL;
    private static ArrayList<Server> servers;


    public RunServer() {
        mapClientManager = new ConcurrentHashMap<>();
        mapRoomManager = new ConcurrentHashMap<>();
        mapServerSocket = new ConcurrentHashMap<>();
        mapThreadPoolExecutor = new ConcurrentHashMap<>();
        serverDAL = new ServerDAL();
         Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            updateAllServersStatusToOff(servers);
        }));
    }
    

    public static void startServer(Server server) {

        try {
            String keyServer = server.getServerIp() + "," + String.valueOf(server.getServerPort());
            InetAddress inetAddress = InetAddress.getByName(server.getServerIp());
            ServerSocket ss = new ServerSocket(server.getServerPort(), 50, inetAddress);
            mapServerSocket.put(keyServer, ss);
            System.out.println("Created Server at port " + server.getServerIp() + " " + server.getServerPort() + ".");
            System.out.println(".");
            serverSide = new RSA()
                    .preparePrivateKey("src/Server/rsa_keypair/privateKey");

            ClientManager clientManager = new ClientManager();
            RoomManager roomManager = new RoomManager();
            mapClientManager.put(keyServer, clientManager);
            mapRoomManager.put(keyServer, roomManager);

            ThreadPoolExecutor executor = new ThreadPoolExecutor(
                    10, // corePoolSize
                    100, // maximumPoolSize
                    10, // thread timeout
                    TimeUnit.SECONDS,
                    new ArrayBlockingQueue<>(8) // queueCapacity
            );
            mapThreadPoolExecutor.put(keyServer, executor);
            serverDAL.updateServerStatus(server.getId(), "Bật");
            server.setStatus("Bật");
            while (server.getStatus().equals("Bật")) {
                try {
                    Socket s = ss.accept();
                    Client c = new Client(server, s);
                    clientManager.add(c);
                    executor.execute(c
                    );

                } catch (IOException ex) {
                    serverDAL.updateServerStatus(server.getId(), "Tắt");
                    server.setStatus("Tắt");
                }
            }
            serverDAL.updateServerStatus(server.getId(), "Tắt");
            System.out.println("shutingdown executor...");
            
            executor.shutdownNow();

        } catch (IOException ex) {
            Logger.getLogger(RunServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void stopServer(Server server) {
        String keyServer = ServerHelper.getKeyServer(server);
        System.out.println("Stopping server with key: " + keyServer);
        if (mapServerSocket.containsKey(keyServer)) {
            ServerSocket ss = mapServerSocket.get(keyServer);
            try {
                ss.close(); 
            } catch (IOException e) {
                e.printStackTrace();
            }
            mapServerSocket.remove(keyServer); 
        }
        if (mapThreadPoolExecutor.containsKey(keyServer)) {
            ThreadPoolExecutor executor = mapThreadPoolExecutor.get(keyServer);
            if (executor != null) {
                try {
                    List<Runnable> notExecutedTasks = executor.shutdownNow();
                    server.setStatus("Tắt");
                    serverDAL.updateServerStatus(server.getId(), "Tắt"); // Cập nhật trạng thái vào DB
                    for (Runnable task : notExecutedTasks) {
                        System.out.println("Task not executed: " + task);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Executor is null for keyServer: " + keyServer);
            }
        } else {
            System.out.println("No thread pool executor found for keyServer: " + keyServer);
        }
    }
     // Phương thức cập nhật tất cả server thành trạng thái "tắt"
    private void updateAllServersStatusToOff( ArrayList<Server> servers ) {
        for (Server server : servers) {
            serverDAL.updateServerStatus(server.getId(), "Tắt"); // Cập nhật trạng thái tắt (false)
        }
        System.out.println("Tất cả server đã được cập nhật trạng thái 'tắt'.");
    }

    public static void main(String[] args) {
        RunServer runServer = new RunServer();
        servers = serverDAL.getAllServers();
        
        ServerHome serverHome = new ServerHome(servers);
        serverHome.setVisible(true);
    }

    public static void setMapClientManager(ConcurrentHashMap<String, ClientManager> mapClientManager) {
        RunServer.mapClientManager = mapClientManager;
    }

    public static void setMapRoomManager(ConcurrentHashMap<String, RoomManager> mapRoomManager) {
        RunServer.mapRoomManager = mapRoomManager;
    }

    public static void setMapServerSocket(ConcurrentHashMap<String, ServerSocket> mapServerSocket) {
        RunServer.mapServerSocket = mapServerSocket;
    }

    public static void setMapThreadPoolExecutor(ConcurrentHashMap<String, ThreadPoolExecutor> mapThreadPoolExecutor) {
        RunServer.mapThreadPoolExecutor = mapThreadPoolExecutor;
    }

    public static void setServerSide(RSA serverSide) {
        RunServer.serverSide = serverSide;
    }

    public static void setServerDAL(ServerDAL serverDAL) {
        RunServer.serverDAL = serverDAL;
    }

    public static ConcurrentHashMap<String, ClientManager> getMapClientManager() {
        return mapClientManager;
    }

    public static ConcurrentHashMap<String, RoomManager> getMapRoomManager() {
        return mapRoomManager;
    }

    public static ConcurrentHashMap<String, ServerSocket> getMapServerSocket() {
        return mapServerSocket;
    }

    public static ConcurrentHashMap<String, ThreadPoolExecutor> getMapThreadPoolExecutor() {
        return mapThreadPoolExecutor;
    }

    public static ServerDAL getServerDAL() {
        return serverDAL;
    }

    public static RSA getServerSide() {
        return serverSide;
    }
}
