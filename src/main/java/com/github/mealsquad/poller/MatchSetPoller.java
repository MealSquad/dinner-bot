package com.github.mealsquad.poller;

import com.github.mautini.pubgjava.api.PubgClient;
import com.github.mautini.pubgjava.model.match.Match;
import com.github.mealsquad.filter.TimeBasedFilter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class MatchSetPoller extends AbstractPoller<Match> {

    private static final Logger logger = LogManager.getFormatterLogger();
    private final TimeBasedFilter timeBasedFilter;

    public MatchSetPoller(PubgClient pb) {
        super(pb);
        this.timeBasedFilter = new TimeBasedFilter();
    }

    @Override
    public Collection<Match> poll() {
        PlayerPoller playerPoller = new PlayerPoller(pb);
        Set<Match> matchSet = new HashSet<>();
        playerPoller.poll().forEach(player -> {
            logger.info("Adding %s matches for player %s",
                    player.getPlayerRelationships().getMatches().getData().size(),
                    player.getPlayerAttributes().getName());
            matchSet.addAll(player.getPlayerRelationships().getMatches().getData());
        });
        return timeBasedFilter.filter(matchSet);
    }
}
