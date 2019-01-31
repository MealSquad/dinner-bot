package com.github.mealsquad.poller;

import com.github.mautini.pubgjava.api.PubgClient;
import com.github.mautini.pubgjava.exception.PubgClientException;
import com.github.mautini.pubgjava.model.PlatformRegion;
import com.github.mautini.pubgjava.model.generic.Entity;
import com.github.mautini.pubgjava.model.match.Match;
import com.github.mautini.pubgjava.model.match.MatchResponse;
import com.github.mautini.pubgjava.model.participant.Participant;
import com.github.mautini.pubgjava.model.participant.ParticipantAttributes;
import com.github.mautini.pubgjava.model.participant.ParticipantStats;
import com.github.mealsquad.channel.ChannelHandler;
import com.github.mealsquad.filter.ChickenDinnerFilter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class ParticipantStatsListPoller extends AbstractPoller<ParticipantStats> {

    private static final Logger logger = LogManager.getFormatterLogger();
    private final Collection<Match> matchList;
    private final ChickenDinnerFilter filter;

    public ParticipantStatsListPoller(PubgClient pb) {
        super(pb);
        this.filter = new ChickenDinnerFilter(ChannelHandler.getInstance().getUsers());
        this.matchList = new MatchSetPoller(pb).poll();
    }

    @Override
    public Collection<ParticipantStats> poll() {
        List<MatchResponse> matchResponses = new ArrayList<>();
        List<String> matchIds = matchList.stream().map(Match::getId).collect(Collectors.toList());
        for (String id : matchIds) {
            try {
                matchResponses.add(pb.getMatch(PlatformRegion.PC_NA, id));
            } catch (PubgClientException e) {
                logger.error("Failed to retrieve match responses for match with id: %s", id);
                e.printStackTrace();
            }
        }

        List<ParticipantAttributes> participantAttributes = new ArrayList<>();
        for (MatchResponse matchResponse : matchResponses) {
            for (Entity entity : matchResponse.getIncluded()) {
                if (entity.getType().equalsIgnoreCase("participant")) {
                    participantAttributes.add(((Participant) entity).getParticipantAttributes());
                }
            }
        }
        return filter.filter(participantAttributes);
    }
}
