package com.example.mmsimgui;

import java.util.ArrayList;
import java.util.UUID;

public class Player implements Comparable<Player> {
    private final int name;
    private final double skill;
    private final int rating;
    private final double volatility;
    private final double confidence;
    private final int gamesWon;
    private final int gamesPlayed;
    private final UUID uuid;
    //private Set<Game> gameHistory = new HashSet<Game>();
    private final ArrayList<Integer> eloHistory;
    private int skillRank;
    private int eloRank;
    private int rankDifference;

    public Player(UUID uuid, int name, double skill, int rating, double volatility, double confidence, int gamesWon, int gamesPlayed, ArrayList<Integer> eloHistory) {
        this.uuid = uuid;
        this.name = name;
        this.skill = skill;
        this.rating = rating;
        this.volatility = volatility;
        this.confidence = confidence;
        this.gamesWon = gamesWon;
        this.gamesPlayed = gamesPlayed;
        this.eloHistory = eloHistory;
    }

    public UUID getUuid() {
        return uuid;
    }

    public int getName() {
        return name;
    }

    public double getSkill() {
        return skill;
    }

    public int getRating() {
        return rating;
    }

    public double getVolatility() {
        return volatility;
    }

    public double getConfidence() {
        return confidence;
    }

    public int getGamesWon() {
        return gamesWon;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public ArrayList<Integer> getEloHistory() {
        return eloHistory;
    }

    public void setSkillRank(int skillRank) {
        this.skillRank = skillRank;
    }

    public int getSkillRank() {
        return skillRank;
    }

    public void setEloRank(int eloRank) {
        this.eloRank = eloRank;
    }

    public int getEloRank() {
        return eloRank;
    }

    public void setRankDifference(int rankDifference) {
        this.rankDifference = rankDifference;
    }

    public int getRankDifference() {
        return rankDifference;
    }

    @Override
    public int compareTo(Player p2) {
        return this.rating - p2.getRating();
    }
}
