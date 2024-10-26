/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Client.model;

/**
 *
 * @author Admin
 */
public class RankingAppData {

    private String userName;
    private String name;
    private double score;
    private int matchCount;
    private int winCount;
    private int drawCount;
    private int loseCount;

    @Override
    public String toString() {
        return "RankingAppData{" + "userName=" + userName + ", name=" + name + ", score=" + score + ", matchCount=" + matchCount + ", winCount=" + winCount + ", drawCount=" + drawCount + ", loseCount=" + loseCount + '}';
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public int getMatchCount() {
        return matchCount;
    }

    public void setMatchCount(int matchCount) {
        this.matchCount = matchCount;
    }

    public int getWinCount() {
        return winCount;
    }

    public void setWinCount(int winCount) {
        this.winCount = winCount;
    }

    public int getDrawCount() {
        return drawCount;
    }

    public void setDrawCount(int drawCount) {
        this.drawCount = drawCount;
    }

    public int getLoseCount() {
        return loseCount;
    }

    public void setLoseCount(int loseCount) {
        this.loseCount = loseCount;
    }

    public RankingAppData(String userName, String name, double score, int matchCount, int winCount, int drawCount, int loseCount) {
        this.userName = userName;
        this.name = name;
        this.score = score;
        this.matchCount = matchCount;
        this.winCount = winCount;
        this.drawCount = drawCount;
        this.loseCount = loseCount;
    }

}
