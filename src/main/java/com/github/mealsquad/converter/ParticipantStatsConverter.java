package com.github.mealsquad.converter;

import com.github.mautini.pubgjava.model.participant.ParticipantStats;
import com.github.mealsquad.model.RelevantInfo;
import com.google.common.base.Function;

import org.checkerframework.checker.nullness.qual.Nullable;


public class ParticipantStatsConverter implements Function<ParticipantStats, RelevantInfo> {

    @Nullable
    @Override
    public RelevantInfo apply(@Nullable ParticipantStats input) {
        return new RelevantInfo(input.getKills(), input.getWinPlace(), input.getDamageDealt());
    }
}
