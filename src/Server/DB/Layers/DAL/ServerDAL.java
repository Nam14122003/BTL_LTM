/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package server.db.layers.dal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import server.db.layers.dbconnector.MysqlConnector;
import server.db.layers.dto.Server;

public class ServerDAL {

    private MysqlConnector connector;

    public ServerDAL() {
        this.connector = new MysqlConnector();
    }

    public void addServer(Server server) {
        String query = "INSERT INTO servers (server_ip, server_port, status) VALUES (?, ?, ?)";

        try {
            PreparedStatement stmt = connector.getConnection().prepareStatement(query);
            stmt.setString(1, server.getServerIp());
            stmt.setInt(2, server.getServerPort());
            stmt.setString(3, server.getStatus());

            stmt.executeUpdate();
            System.out.println("Thêm server thành công!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Server> getAllServers() {
        ArrayList<Server> servers = new ArrayList<>();
        String query = "SELECT * FROM servers";

        try (
                PreparedStatement stmt = connector.getConnection().prepareStatement(query); ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String serverIp = rs.getString("server_ip");
                int serverPort = rs.getInt("server_port");
                String status = rs.getString("status");

                servers.add(new Server(id, serverIp, serverPort, status));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return servers;
    }

    public Server getServerById(Integer serverId) {
        String query = "SELECT * FROM servers WHERE id = ?";
        Server server = null; // Khởi tạo server là null

        try {
            PreparedStatement stmt = connector.getConnection().prepareStatement(query);
            stmt.setInt(1, serverId); // Thiết lập giá trị cho tham số
            ResultSet rs = stmt.executeQuery(); // Thực hiện câu lệnh trên stmt

            while (rs.next()) {
                int id = rs.getInt("id");
                String serverIp = rs.getString("server_ip");
                int serverPort = rs.getInt("server_port");
                String status = rs.getString("status");

                server = new Server(id, serverIp, serverPort, status); // Lưu thông tin server
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return server; // Trả về server hoặc null nếu không tìm thấy
    }

    public void deleteServer(int id) {
        String query = "DELETE FROM servers WHERE id = ?";

        try (PreparedStatement stmt = connector.getConnection().prepareStatement(query);) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("Xóa server thành công!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateServer(Server server) {
        String query = "UPDATE servers SET server_ip = ?, server_port = ?, status = ? WHERE id = ?";

        try (PreparedStatement stmt = connector.getConnection().prepareStatement(query);) {

            stmt.setString(1, server.getServerIp());
            stmt.setInt(2, server.getServerPort());
            stmt.setString(3, server.getStatus());
            stmt.setInt(4, server.getId());

            stmt.executeUpdate();
            System.out.println("Cập nhật server thành công!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateServerStatus(int serverId, String status) {
        String query = "UPDATE servers SET status = ? WHERE id = ?";

        try (PreparedStatement stmt = connector.getConnection().prepareStatement(query);) {
            stmt.setString(1, status);
            stmt.setInt(2, serverId);

            stmt.executeUpdate();
          
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
