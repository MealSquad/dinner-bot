package com.github.mealsquad.poller;

import com.github.mautini.pubgjava.api.PubgClient;
import com.github.mautini.pubgjava.model.match.Match;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashSet;
import java.util.Set;

public class MatchSetPoller extends AbstractPoller<Match> {

    private static final Logger logger = LogManager.getFormatterLogger();

    public MatchSetPoller(PubgClient pb) {
        super(pb);
    }

    @Override
    public Set<Match> poll() {
        PlayerPoller playerPoller = new PlayerPoller(pb);
        Set<Match> matchSet = new HashSet<>();
        playerPoller.poll().forEach(player -> {
            logger.info("Adding %i matches for player %s",
                    player.getPlayerRelationships().getMatches().getData().size(),
                    player.getPlayerAttributes().getName());
            matchSet.addAll(player.getPlayerRelationships().getMatches().getData());
        });
        return matchSet;
    }
}
