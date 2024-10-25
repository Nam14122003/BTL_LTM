/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package shared.helper;

import server.db.layers.dto.Server;

/**
 *
 * @author Admin
 */
public class ServerHelper {
    public static String getKeyServer(Server server ){
        return  server.getServerIp() + "," + String.valueOf(server.getServerPort());
    }
    
    public static String getKeyServerByIpAndPort(String ip, String port){
        return  ip + "," + port;
    }
    
}
