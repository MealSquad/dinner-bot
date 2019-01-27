package com.github.mealsquad.poller;

import com.github.mautini.pubgjava.api.PubgClient;
import com.github.mautini.pubgjava.model.match.Match;
import java.util.HashSet;
import java.util.Set;

public class MatchSetPoller extends AbstractPoller<Match> {

    public MatchSetPoller(PubgClient pb) {
        super(pb);
    }

    @Override
    public Set<Match> poll() {
        PlayerPoller playerPoller = new PlayerPoller(pb);
        Set<Match> matchSet = new HashSet<>();
        playerPoller.poll().forEach(player -> matchSet.addAll(player.getPlayerRelationships().getMatches().getData()));
        return matchSet;
    }
}
