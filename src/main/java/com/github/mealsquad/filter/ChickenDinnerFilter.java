package com.github.mealsquad.filter;

import com.github.mautini.pubgjava.model.generic.Entity;
import com.github.mautini.pubgjava.model.match.MatchResponse;
import com.github.mautini.pubgjava.model.participant.Participant;
import com.github.mautini.pubgjava.model.participant.ParticipantAttributes;
import com.github.mautini.pubgjava.model.participant.ParticipantStats;
import com.github.mealsquad.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
    public List<ParticipantStats> filter(List<ParticipantAttributes> toFilter) {
        List<ParticipantStats> filteredResultsList = new ArrayList<>();
        for (ParticipantAttributes participantAttributes : toFilter) {
            if (participantAttributes.getParticipantStats().getWinPlace().equals(Integer.valueOf(1))
                    && users.stream().map(User::getName).collect(Collectors.toList()).contains(participantAttributes.getParticipantStats().getName())) {
                filteredResultsList.add(participantAttributes.getParticipantStats());
                logger.info(String.format("Adding participant attributes for %s", participantAttributes.getParticipantStats().getName()));
            }
        }
        return filteredResultsList;
    }
}
