/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Client.model;

/**
 *
 * @author Admin
 */
public class ListHistory {
    private int id;
    private String namePlayer1;
    private String namePlayer2;
    private String namePlayerWinner;
    private String startedTime;

    public ListHistory(int id, String namePlayer1, String namePlayer2, String namePlayerWinner, String startedTime) {
        this.id = id;
        this.namePlayer1 = namePlayer1;
        this.namePlayer2 = namePlayer2;
        this.namePlayerWinner = namePlayerWinner;
        this.startedTime = startedTime;
    }

    @Override
    public String toString() {
        return "ListHistory{" + "id=" + id + ", namePlayer1=" + namePlayer1 + ", namePlayer2=" + namePlayer2 + ", namePlayerWinner=" + namePlayerWinner + ", startedTime=" + startedTime + '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNamePlayer1() {
        return namePlayer1;
    }

    public void setNamePlayer1(String namePlayer1) {
        this.namePlayer1 = namePlayer1;
    }

    public String getNamePlayer2() {
        return namePlayer2;
    }

    public void setNamePlayer2(String namePlayer2) {
        this.namePlayer2 = namePlayer2;
    }

    public String getNamePlayerWinner() {
        return namePlayerWinner;
    }

    public void setNamePlayerWinner(String namePlayerWinner) {
        this.namePlayerWinner = namePlayerWinner;
    }

    public String getStartedTime() {
        return startedTime;
    }

    public void setStartedTime(String startedTime) {
        this.startedTime = startedTime;
    }
    
    
    
}
