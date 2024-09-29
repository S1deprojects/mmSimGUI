package com.example.mmsimgui;

import javafx.beans.property.*;

import java.util.ArrayList;
import java.util.UUID;

public class Player implements Comparable<Player> {
    private IntegerProperty name;
    private DoubleProperty skill;
    private IntegerProperty rating;
    private DoubleProperty volatility;
    private DoubleProperty confidence;
    private IntegerProperty gamesWon;
    private IntegerProperty gamesPlayed;
    private StringProperty uuid;
    //private Set<Game> gameHistory = new HashSet<Game>();
    private final ArrayList<Integer> eloHistory;
    private IntegerProperty skillRank;
    private IntegerProperty eloRank;
    private IntegerProperty rankDifference;

    public Player(UUID uuid, int name, double skill, int rating, double volatility, double confidence, int gamesWon, int gamesPlayed, ArrayList<Integer> eloHistory) {
        this.uuid = new SimpleStringProperty(uuid.toString());
        this.name = new SimpleIntegerProperty(name);
        this.skill = new SimpleDoubleProperty(skill);
        this.rating = new SimpleIntegerProperty(rating);
        this.volatility = new SimpleDoubleProperty(volatility);
        this.confidence = new SimpleDoubleProperty(confidence);
        this.gamesWon = new SimpleIntegerProperty(gamesWon);
        this.gamesPlayed = new SimpleIntegerProperty(gamesPlayed);
        this.eloHistory = eloHistory;
    }

    public StringProperty getUuid() {
        return uuid;
    }

    public IntegerProperty getName() {
        return name;
    }

    public DoubleProperty getSkill() {
        return skill;
    }

    public double getSkillValue() {
        return skill.getValue();
    }

    public IntegerProperty getRating() {
        return rating;
    }

    public int getRatingValue() {
        return rating.getValue();
    }

    public DoubleProperty getVolatility() {
        return volatility;
    }

    public DoubleProperty getConfidence() {
        return confidence;
    }

    public IntegerProperty getGamesWon() {
        return gamesWon;
    }

    public int getGamesWonValue() {
        return gamesWon.getValue();
    }

    public IntegerProperty getGamesPlayed() {
        return gamesPlayed;
    }

    public int getGamesPlayedValue() {
        return gamesPlayed.getValue();
    }

    public ArrayList<Integer> getEloHistory() {
        return eloHistory;
    }

    public void setSkillRank(int skillRank) {
        this.skillRank = new SimpleIntegerProperty(skillRank);
    }

    public IntegerProperty getSkillRank() {
        return skillRank;
    }

    public int getSkillRankValue() {
        return skillRank.getValue();
    }

    public void setEloRank(int eloRank) {
        this.eloRank = new SimpleIntegerProperty(eloRank);
    }

    public IntegerProperty getEloRank() {
        return eloRank;
    }

    public int getEloRankValue() {
        return eloRank.getValue();
    }

    public void setRankDifference(int rankDifference) {
        this.rankDifference = new SimpleIntegerProperty(rankDifference);
    }

    public IntegerProperty getRankDifference() {
        return rankDifference;
    }

    public int getRankDifferenceValue() {
        return rankDifference.getValue();
    }

    @Override
    public int compareTo(Player p2) {
        return this.rating.getValue() - p2.getRatingValue();
    }
}
