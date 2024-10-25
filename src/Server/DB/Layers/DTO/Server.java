/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package server.db.layers.dto;

/**
 *
 * @author Admin
 */
public class Server {
    private int id;
    private String serverName;
    private String serverIp;
    private int serverPort;
    private String status;

    public Server(int id, String serverName, String serverIp, int serverPort, String status) {
        this.id = id;
        this.serverName = serverName;
        this.serverIp = serverIp;
        this.serverPort = serverPort;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

    public int getServerPort() {
        return serverPort;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Server{" + "id=" + id + ", serverName=" + serverName + ", serverIp=" + serverIp + ", serverPort=" + serverPort + ", status=" + status + '}';
    }

    
    
    
}
