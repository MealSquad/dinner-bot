package com.github.mealsquad.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class RelevantInfo {

    private String username;
    private int kills;
    private int wins;
    private int topKills;
    private int topHitPoints;
    private int dionDinners;

    public RelevantInfo add(RelevantInfo other) {
        if (!this.username.equals(other.username)) {
            throw new UnsupportedOperationException("Cannot add two RelevantInfo's with differing usernames");
        }
        return new RelevantInfo(this.username,
                this.kills + other.kills,
                this.wins + other.wins,
                Integer.max(this.topKills, other.topKills),
                Integer.max(this.topHitPoints, other.topHitPoints),
                this.dionDinners + other.dionDinners);
    }

    @Override
    public String toString() {
        return "RelevantInfo{" +
                "username='" + username + '\'' +
                ", kills=" + kills +
                ", wins=" + wins +
                ", topKills=" + topKills +
                ", topHitPoints=" + topHitPoints +
                ", dionDinners=" + dionDinners +
                '}';
    }

    public String[] toArray() {
        return  new String[]{username, String.valueOf(kills), String.valueOf(wins),
                String.valueOf(topKills), String.valueOf(topHitPoints), String.valueOf(dionDinners)};
    }

    public static RelevantInfo emptyRelevantInfo(String username) {
        return new RelevantInfo(username, 0, 0, 0, 0, 0);
    }
}
