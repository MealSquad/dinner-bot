package com.github.mealsquad.filter;

import com.github.mautini.pubgjava.model.participant.ParticipantAttributes;
import com.github.mautini.pubgjava.model.participant.ParticipantStats;
import com.github.mealsquad.model.User;
import com.github.mealsquad.utility.Pair;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(JUnit4.class)
public class ChickenDinnerFilterTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    // Subject Under Test
    private ChickenDinnerFilter sut;

    // Mocks
    private ParticipantAttributes validParticipant;
    private ParticipantAttributes invalidParticipant;

    private final LocalDateTime validDate = LocalDateTime.now();
    private final LocalDateTime invalidDate = LocalDateTime.now().minus(25, ChronoUnit.HOURS);

    @Before
    public void initialize() {
        List<User> users = new ArrayList<>();
        users.add(new User("NutellaFrisbee"));
        sut = new ChickenDinnerFilter();

        // Mocks
        validParticipant = mock(ParticipantAttributes.class, RETURNS_DEEP_STUBS);
        invalidParticipant = mock(ParticipantAttributes.class, RETURNS_DEEP_STUBS);

        // Stubs for valid participant
        when(validParticipant.getParticipantStats().getWinPlace()).thenReturn(1);
        when(validParticipant.getParticipantStats().getName()).thenReturn(users.get(0).getName());

        // Stubs for an invalid participant
        when(invalidParticipant.getParticipantStats().getWinPlace()).thenReturn(-1);

    }

    @Test
    public void testFilter() {
        // Mock a List<MatchResponses> with size 2.
        List<Pair<LocalDateTime, ParticipantAttributes>> participantAttributes = new ArrayList<>();
        participantAttributes.add(new Pair<>(validDate, validParticipant));
        participantAttributes.add(new Pair<>(validDate, invalidParticipant));

        List<ParticipantStats> participantStats = sut.filter(participantAttributes);

        // Assert only one set of participant stats (the valid one) passes through the filter
        assertEquals(1, participantStats.size());
    }

    @Test
    public void testMatchWithInvalidDateExpectNoUpdate() {
        List<Pair<LocalDateTime, ParticipantAttributes>> participantAttributes = new ArrayList<>();
        participantAttributes.add(new Pair<>(invalidDate, validParticipant));
        participantAttributes.add(new Pair<>(invalidDate, invalidParticipant));

        List<ParticipantStats> participantStats = sut.filter(participantAttributes);

        assertEquals(0, participantStats.size());
    }
}
