package com.github.mealsquad.converter;

import com.github.mautini.pubgjava.model.participant.ParticipantStats;
import com.github.mealsquad.model.RelevantInfo;
import com.google.common.base.Function;

import org.checkerframework.checker.nullness.qual.Nullable;


public class ParticipantStatsConverter implements Function<ParticipantStats, RelevantInfo> {

    @Nullable
    @Override
    public RelevantInfo apply(@Nullable ParticipantStats input) {
        return RelevantInfo.builder()
                .username(input.getName())
                .kills(input.getKills())
                .wins(input.getWinPlace() == 1 ? 1 : 0)
                .topKills(input.getKills())
                .topHitPoints(input.getDamageDealt().intValue())
                .dionDinners((input.getWinPlace() == 1 && input.getDamageDealt() == 0) ? 1 : 0)
                .build();
    }
}
