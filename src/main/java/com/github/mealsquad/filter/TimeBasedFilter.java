package com.github.mealsquad.filter;

import com.github.mautini.pubgjava.model.match.Match;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.stream.Collectors;

public class TimeBasedFilter implements Filter<Match, Match>{

    private static final Logger logger = LogManager.getFormatterLogger();
    private final ZonedDateTime previousUpdateTime = ZonedDateTime.now().minus(24, ChronoUnit.HOURS);

    @Override
    public Collection<Match> filter(Collection<Match> toFilter) {
        logger.info("Filtering matches based on time. Only keep those created after %s", previousUpdateTime);
        logger.info("Size of list to filter: %s", toFilter.size());
        Collection<Match> filteredResultsList = toFilter.stream().filter(match -> match.getMatchAttributes().getCreatedAt().isAfter(previousUpdateTime)).collect(Collectors.toSet());
        logger.info("Size of list after filtering: %s", filteredResultsList.size());
        return filteredResultsList;
    }
}
