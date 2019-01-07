package com.github.mealsquad.converter;

import com.github.mautini.pubgjava.model.participant.ParticipantStats;
import com.github.mealsquad.model.RelevantInfo;
import com.google.common.base.Function;
import javafx.util.Pair;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.time.LocalDateTime;

public class ParticipantStatsConverter implements Function<Pair<LocalDateTime, ParticipantStats>, RelevantInfo> {

    @Nullable
    @Override
    public RelevantInfo apply(@Nullable Pair<LocalDateTime, ParticipantStats> input) {
        LocalDateTime timeOfMatch = input.getKey();
        ParticipantStats participantStats = input.getValue();
        return new RelevantInfo(timeOfMatch, participantStats.getKills(), participantStats.getWinPlace(), participantStats.getDamageDealt());
    }
}
