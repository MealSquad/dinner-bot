package com.github.mealsquad.filter;

import com.github.mautini.pubgjava.model.participant.ParticipantAttributes;
import com.github.mautini.pubgjava.model.participant.ParticipantStats;
import com.github.mealsquad.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class ChickenDinnerFilter implements Filter<ParticipantAttributes, ParticipantStats> {

    private static final Logger logger = LogManager.getFormatterLogger();
    private List<User> users;

    public ChickenDinnerFilter(List<User> users) {
        this.users = users;
    }

    @Override
    public Collection<ParticipantStats> filter(Collection<ParticipantAttributes> toFilter) {
        List<ParticipantStats> filteredResultsList = new ArrayList<>();
        logger.info("Filter matches based on win place");
        logger.info("Size of list to filter: %s", toFilter.size());
        for (ParticipantAttributes attr : toFilter) {
            if (isChickenDinner(attr) && participantStatsOfPlayerInUserlist(attr)) {
                filteredResultsList.add(attr.getParticipantStats());
                logger.info("Adding participant attributes for %s", attr.getParticipantStats().getName());
            }
        }
        logger.info("Size of list after filtering: %s", filteredResultsList.size());
        return filteredResultsList;
    }

    private boolean isChickenDinner(ParticipantAttributes participantAttributes) {
        return participantAttributes.getParticipantStats().getWinPlace().equals(1);
    }

    private boolean participantStatsOfPlayerInUserlist(ParticipantAttributes participantAttributes) {
        return users.stream().map(User::getName).collect(Collectors.toList()).contains(participantAttributes.getParticipantStats().getName());
    }
}
