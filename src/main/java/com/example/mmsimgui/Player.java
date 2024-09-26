package com.example.mmsimgui;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.UUID;
import java.util.Deque;

public class Player implements Comparable<Player> {
    private final String name;
    private final double skill;
    private final int rating;
    private final double volatility;
    private final double confidence;
    private final int gamesWon;
    private final int gamesPlayed;
    private final UUID uuid;
    //private Set<Game> gameHistory = new HashSet<Game>();
    private final ArrayList<Integer> eloHistory;

    public Player(UUID uuid, String name, double skill, int rating, double volatility, double confidence, int gamesWon, int gamesPlayed, ArrayList<Integer> eloHistory) {
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

    public String getName() {
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

    @Override
    public int compareTo(Player p2)
    {
        return this.rating - p2.getRating();
    }
}
