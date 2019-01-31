package com.github.mealsquad.filter;

import com.github.mautini.pubgjava.model.match.Match;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(JUnit4.class)
public class TimeBasedFilterTest {
    // Subject under test
    private TimeBasedFilter sut;

    // mocks
    private Match validMatch;
    private Match invalidMatch;
    private Match nearlyInvalidMatch;

    @Before
    public void initialize() {
        sut = new TimeBasedFilter();

        validMatch = mock(Match.class, RETURNS_DEEP_STUBS);
        invalidMatch = mock(Match.class, RETURNS_DEEP_STUBS);
        nearlyInvalidMatch = mock(Match.class, RETURNS_DEEP_STUBS);

        // stubs
        when(validMatch.getMatchAttributes().getCreatedAt()).thenReturn(ZonedDateTime.now());
        when(invalidMatch.getMatchAttributes().getCreatedAt()).thenReturn(ZonedDateTime.now()
                .minus(24, ChronoUnit.HOURS)
                .minus(1, ChronoUnit.MINUTES));
        when(nearlyInvalidMatch.getMatchAttributes().getCreatedAt()).thenReturn(ZonedDateTime.now()
                .minus(23, ChronoUnit.HOURS)
                .minus(59, ChronoUnit.MINUTES));
    }

    @Test
    public void invalidMatchFilteredOut() {
        Set<Match> matches = new HashSet<>();
        matches.add(validMatch);
        matches.add(invalidMatch);

        assertEquals(1, sut.filter(matches).size());
    }

    @Test
    public void validMatchsNotFiltered() {
        Set<Match> matches = new HashSet<>();
        matches.add(validMatch);
        matches.add(nearlyInvalidMatch);

        assertEquals(2, sut.filter(matches).size());
    }
}
