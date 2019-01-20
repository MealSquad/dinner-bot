package com.github.mealsquad.filter;

import com.github.mautini.pubgjava.model.participant.ParticipantAttributes;
import com.github.mautini.pubgjava.model.participant.ParticipantStats;
import com.github.mealsquad.model.User;
import com.github.mealsquad.utility.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ChickenDinnerFilter implements AbstractFilter<ParticipantAttributes, ParticipantStats> {

    private static final Logger logger = LogManager.getLogger();
    private final LocalDateTime previousUpdateTime = LocalDateTime.now().minus(24, ChronoUnit.HOURS);
    private List<User> users;

    public ChickenDinnerFilter(List<User> users) {
        this.users = users;
    }

    @Override
    public List<ParticipantStats> filter(List<Pair<LocalDateTime, ParticipantAttributes>> toFilter) {
        List<ParticipantStats> filteredResultsList = new ArrayList<>();
        logger.info(String.format("Size of list to filter: %s", toFilter.size()));
        for (Pair<LocalDateTime, ParticipantAttributes> pair : toFilter) {
            ParticipantAttributes participantAttributes = pair.getValue();
            if (isChickenDinner(participantAttributes) && participantStatsOfPlayerInUserlist(participantAttributes) && validMatchDate(pair.getKey(), previousUpdateTime)) {
                filteredResultsList.add(participantAttributes.getParticipantStats());
                logger.info(String.format("Adding participant attributes for %s", participantAttributes.getParticipantStats().getName()));
            }
        }
        logger.info(String.format("Size of list after filtering: %s", filteredResultsList.size()));
        return filteredResultsList;
    }

    private boolean isChickenDinner(ParticipantAttributes participantAttributes) {
        return participantAttributes.getParticipantStats().getWinPlace().equals(1);
    }

    private boolean participantStatsOfPlayerInUserlist(ParticipantAttributes participantAttributes) {
        return users.stream().map(User::getName).collect(Collectors.toList()).contains(participantAttributes.getParticipantStats().getName());
    }

    private boolean validMatchDate(LocalDateTime matchTime, LocalDateTime previousUpdateTime) {
        return matchTime.isAfter(previousUpdateTime);
    }
}
