package com.github.mealsquad.converter;

import com.github.mautini.pubgjava.model.participant.ParticipantStats;
import com.github.mealsquad.model.RelevantInfo;
import javafx.util.Pair;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class ParticipantStatsConverterTest {

    private final ParticipantStatsConverter sut = new ParticipantStatsConverter();
    private final LocalDateTime timeOfMatch = LocalDateTime.now();
    private ParticipantStats participantStats;
    private RelevantInfo relevantInfo;

    @Before
    public void initialize() {
        participantStats = new ParticipantStats();
        participantStats.setKills(2);
        participantStats.setWinPlace(2);
        participantStats.setDamageDealt(110.1);

        relevantInfo = new RelevantInfo(timeOfMatch, 2, 2, 110.1);
    }

    @Test
    public void testConverter() {
        assertEquals(relevantInfo, sut.apply(new Pair<>(timeOfMatch, participantStats)));
    }

}
