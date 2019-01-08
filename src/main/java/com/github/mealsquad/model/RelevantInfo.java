package com.github.mealsquad.model;

import java.util.Objects;

public class RelevantInfo {

    private String username;
    private int kills;
    private int wins;
    private int topKills;
    private int topHitPoints;
    private int dionDinners;

    public RelevantInfo(String username, int kills, int wins, int topKills, int topHitPoints, int dionDinners) {
        this.username = username;
        this.kills = kills;
        this.wins = wins;
        this.topKills = topKills;
        this.topHitPoints = topHitPoints;
        this.dionDinners = dionDinners;
    }

    public String getUsername() {
        return username;
    }

    public int getKills() {
        return kills;
    }

    public int getWins() {
        return wins;
    }

    public int getTopKils() {
        return topKills;
    }

    public int getTopHitPoints() {
        return topHitPoints;
    }

    public int getDionDinners() {
        return dionDinners;
    }

    public RelevantInfo add(RelevantInfo other) {
        return new RelevantInfo(this.username,
                this.kills + other.kills,
                this.wins + other.wins,
                Integer.max(this.topKills, other.topKills),
                Integer.max(this.topHitPoints, other.topHitPoints),
                this.dionDinners + other.dionDinners);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RelevantInfo that = (RelevantInfo) o;
        return kills == that.kills &&
                wins == that.wins &&
                topKills == that.topKills &&
                topHitPoints == that.topHitPoints &&
                dionDinners == that.dionDinners &&
                Objects.equals(username, that.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, kills, wins, topKills, topHitPoints, dionDinners);
    }
}
