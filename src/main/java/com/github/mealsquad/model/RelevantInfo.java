package com.github.mealsquad.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class RelevantInfo {

    private LocalDateTime timeOfMatch;
    private int kills;
    private int rank;
    private double damangeDealt;

    public RelevantInfo(LocalDateTime timeOfMatch, int kills, int rank, double damangeDealt) {
        this.timeOfMatch = timeOfMatch;
        this.kills = kills;
        this.rank = rank;
        this.damangeDealt = damangeDealt;
    }

    public LocalDateTime getTimeOfMatch() {
        return timeOfMatch;
    }

    public int getKills() {
        return kills;
    }

    public int getRank() {
        return rank;
    }

    public double getDamangeDealt() {
        return damangeDealt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RelevantInfo that = (RelevantInfo) o;
        return kills == that.kills &&
                rank == that.rank &&
                Double.compare(that.damangeDealt, damangeDealt) == 0 &&
                Objects.equals(timeOfMatch, that.timeOfMatch);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timeOfMatch, kills, rank, damangeDealt);
    }
}
