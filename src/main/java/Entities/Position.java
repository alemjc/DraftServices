package Entities;

public class Position {

    private String name;
    private int agesSum;
    private int numberOfPlayers;

    public Position(String name, int agesSum, int numberOfPlayers) {
        this.name = name;
        this.agesSum = agesSum;
        this.numberOfPlayers = numberOfPlayers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAgesSum() {
        return agesSum;
    }

    public void setAgesSum(int agesSum) {
        this.agesSum = agesSum;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }
}
