package com.github.mealsquad.poller;

import com.github.mautini.pubgjava.exception.PubgClientException;
import com.github.mautini.pubgjava.model.PlatformRegion;
import com.github.mautini.pubgjava.model.generic.Entity;
import com.github.mautini.pubgjava.model.match.Match;
import com.github.mautini.pubgjava.model.match.MatchResponse;
import com.github.mautini.pubgjava.model.participant.Participant;
import com.github.mautini.pubgjava.model.participant.ParticipantAttributes;
import com.github.mautini.pubgjava.model.participant.ParticipantStats;
import com.github.mealsquad.filter.AbstractFilter;
import javafx.util.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ParticipantStatsListPoller extends AbstractPoller<ParticipantStats> {

    private static final Logger logger = LogManager.getLogger();
    private final Set<Match> matchList;
    private final AbstractFilter<ParticipantAttributes, ParticipantStats> filter;

    public ParticipantStatsListPoller(Set<Match> matchList, AbstractFilter<ParticipantAttributes, ParticipantStats> filter) {
        this.matchList = matchList;
        this.filter = filter;
    }

    @Override
    public List<ParticipantStats> poll() {
        List<MatchResponse> matchResponses = new ArrayList<>();
        List<String> matchIds = matchList.stream().map(Match::getId).collect(Collectors.toList());
        try {
            for (String id : matchIds) {
                matchResponses.add(getClient().getMatch(PlatformRegion.PC_NA, id));
            }
        } catch (PubgClientException e) {
            logger.error("Failed to retrieve match responses");
            e.printStackTrace();
        }

        List<Pair<LocalDateTime, ParticipantAttributes>> participantAttributes = new ArrayList<>();
        for (MatchResponse matchResponse : matchResponses) {
            for (Entity entity : matchResponse.getIncluded()) {
                if (entity.getType().equalsIgnoreCase("participant")) {
                    participantAttributes.add(new Pair<>(matchResponse.getData().getMatchAttributes().getCreatedAt().toLocalDateTime(),
                            ((Participant) entity).getParticipantAttributes()));
                }
            }
        }
        return filter.filter(participantAttributes);
    }
}
