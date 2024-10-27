/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.game.caro;

public class History {

    int row;
    int column;
    String playerUser;

    public History(int row, int column, String playerUser) {
        this.row = row;
        this.column = column;
        this.playerUser = playerUser;
    }

    @Override
    public String toString() {
        return row + ";" + column + ";" + playerUser;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public String getPlayerUser() {
        return playerUser;
    }

    public void setPlayerUser(String playerId) {
        this.playerUser = playerId;
    }
}
