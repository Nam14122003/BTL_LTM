/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.db.layers.dal;

import server.db.layers.dbconnector.MysqlConnector;
import server.db.layers.dto.Player;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nguye
 */
public class PlayerDAL {

    MysqlConnector connector;

    public PlayerDAL() {

    }
    
     public ArrayList getRankingApp(){
          ArrayList<Player> result = new ArrayList<>();
         connector = new MysqlConnector();

        try {
            String qry = "SELECT * FROM player ORDER BY Score DESC";
            PreparedStatement stm = connector.getConnection().prepareStatement(qry);
            ResultSet rs = connector.sqlQry(stm);

            if (rs != null) {
                while (rs.next()) {
                    Player p = new Player(
                            rs.getInt("ID"),
                            rs.getString("Username"),
                            rs.getString("Password"),
                            rs.getString("Avatar"),
                            rs.getString("Name"),
                            rs.getString("Gender"),
                            rs.getInt("YearOfBirth"),
                            rs.getDouble("Score"),
                            rs.getInt("MatchCount"),
                            rs.getInt("WinCount"),
                            rs.getInt("DrawCount"),
                            rs.getInt("LoseCount"),
                            rs.getInt("CurrentStreak"),
                            rs.getBoolean("Blocked")
                    );
                    result.add(p);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error while trying to read Players info from database!");
        } finally {
            connector.closeConnection();
        }

        return result;
    }

    public ArrayList readDB() {
        ArrayList<Player> result = new ArrayList<>();
        connector = new MysqlConnector();

        try {
            String qry = "SELECT * FROM player;";
            PreparedStatement stm = connector.getConnection().prepareStatement(qry);
            ResultSet rs = connector.sqlQry(stm);

            if (rs != null) {
                while (rs.next()) {
                    Player p = new Player(
                            rs.getInt("ID"),
                            rs.getString("Username"),
                            rs.getString("Password"),
                            rs.getString("Avatar"),
                            rs.getString("Name"),
                            rs.getString("Gender"),
                            rs.getInt("YearOfBirth"),
                            rs.getDouble("Score"),
                            rs.getInt("MatchCount"),
                            rs.getInt("WinCount"),
                             rs.getInt("DrawCount"),
                            rs.getInt("LoseCount"),
                            rs.getInt("CurrentStreak"),
                            rs.getBoolean("Blocked")
                    );
                    result.add(p);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error while trying to read Players info from database!");
        } finally {
            connector.closeConnection();
        }

        return result;
    }

    public boolean add(Player p) {
        boolean result = false;
        connector = new MysqlConnector();

        try {
            String qry = "INSERT INTO Player(Username,Password,Avatar,Name,Gender,YearOfBirth,Score,MatchCount,WinCount,LoseCount,CurrentStreak,Blocked) "
                    + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement stm = connector.getConnection().prepareStatement(qry);
            stm.setString(1, p.getUser());
            stm.setString(2, p.getPassword());
            stm.setString(3, p.getAvatar());
            stm.setString(4, p.getName());
            stm.setString(5, p.getGender());
            stm.setInt(6, p.getYearOfBirth());
            stm.setDouble(7, p.getScore());
            stm.setInt(8, p.getMatchCount());
            stm.setInt(9, p.getWinCount());
            stm.setInt(10, p.getLoseCount());
            stm.setInt(11, p.getCurrentStreak());
            stm.setBoolean(12, p.isBlocked());

            result = connector.sqlUpdate(stm);
        } catch (SQLException ex) {
            Logger.getLogger(PlayerDAL.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connector.closeConnection();
        }

        return result;
    }

    public boolean update(Player p) {
        boolean result = false;
        connector = new MysqlConnector();

        try {
            String qry = "UPDATE Player SET "
                    + "Username=?,"
                    + "Password=?,"
                    + "Avatar=?,"
                    + "Name=?,"
                    + "Gender=?,"
                    + "YearOfBirth=?,"
                    + "Score=?,"
                    + "MatchCount=?,"
                    + "WinCount=?,"
                    +"DrawCount=?,"
                    + "LoseCount=?,"
                    + "CurrentStreak=?,"
                    + "Blocked=?"
                    + " WHERE ID=?";

            PreparedStatement stm = connector.getConnection().prepareStatement(qry);

            stm.setString(1, p.getUser());
            stm.setString(2, p.getPassword());
            stm.setString(3, p.getAvatar());
            stm.setString(4, p.getName());
            stm.setString(5, p.getGender());
            stm.setInt(6, p.getYearOfBirth());
            stm.setDouble(7, p.getScore());
            stm.setInt(8, p.getMatchCount());
            stm.setInt(9, p.getWinCount());
            stm.setInt(10, p.getDrawCount());
            stm.setInt(11, p.getLoseCount());
            stm.setInt(12, p.getCurrentStreak());
            stm.setBoolean(13, p.isBlocked());
            stm.setInt(14, p.getId());

            result = connector.sqlUpdate(stm);
        } catch (SQLException ex) {
            Logger.getLogger(PlayerDAL.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connector.closeConnection();
        }

        return result;
    }

    public boolean delete(int id) {
        boolean result = false;
        connector = new MysqlConnector();

        try {
            String qry = "DELETE FROM player WHERE ID=?";

            PreparedStatement stm = connector.getConnection().prepareStatement(qry);
            stm.setInt(1, id);

            result = connector.sqlUpdate(stm);
        } catch (SQLException ex) {
            Logger.getLogger(PlayerDAL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
}
