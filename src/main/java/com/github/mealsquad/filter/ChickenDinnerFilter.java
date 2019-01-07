package com.github.mealsquad.filter;

import com.github.mautini.pubgjava.model.participant.ParticipantAttributes;
import com.github.mautini.pubgjava.model.participant.ParticipantStats;
import com.github.mealsquad.model.User;
import javafx.util.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ChickenDinnerFilter extends AbstractFilter<ParticipantAttributes, ParticipantStats> {

    private static final Logger logger = LogManager.getLogger();
    private final List<User> users;

    public ChickenDinnerFilter(List<User> users) {
        this.users = users;
    }

    @Override
    public List<ParticipantStats> filter(List<Pair<LocalDateTime, ParticipantAttributes>> toFilter) {

        // TODO include LocalDateTime in sorting of filteredResultsList / filter based on it
        List<ParticipantStats> filteredResultsList = new ArrayList<>();
        logger.info(String.format("Size of list to filter: %s", toFilter.size()));
        for (Pair<LocalDateTime, ParticipantAttributes> pair : toFilter) {
            ParticipantAttributes participantAttributes = pair.getValue();
            if (isChickenDinner(participantAttributes) && participantStatsOfPlayerInUserlist(participantAttributes) && validMatchDate()) {
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

    // TODO figure out exact filtering cases based on last update to dinner-board and which ReleventInfo's should be included in this update
    private boolean validMatchDate() {
        return true;
    }
}
