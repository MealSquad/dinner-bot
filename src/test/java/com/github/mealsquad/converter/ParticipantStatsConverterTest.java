package com.github.mealsquad.converter;

import com.github.mautini.pubgjava.model.participant.ParticipantStats;
import com.github.mealsquad.model.RelevantInfo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class ParticipantStatsConverterTest {

    private final ParticipantStatsConverter sut = new ParticipantStatsConverter();
    private ParticipantStats participantStats;
    private RelevantInfo relevantInfo;

    @Before
    public void initialize() {
        participantStats = new ParticipantStats();
        participantStats.setWinPlace(1);
        participantStats.setKills(2);
        participantStats.setDamageDealt(110.1);
        participantStats.setName("NutellaFrisbee");

        relevantInfo = new RelevantInfo("NutellaFrisbee", 2, 1, 2, 110,0);
    }

    @Test
    public void testConverter() {
        assertEquals(relevantInfo, sut.apply(participantStats));
    }

}
