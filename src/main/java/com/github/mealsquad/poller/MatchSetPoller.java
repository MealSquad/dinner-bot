package com.github.mealsquad.poller;

import com.github.mautini.pubgjava.model.match.Match;
import java.util.HashSet;
import java.util.Set;

public class MatchSetPoller extends AbstractPoller<Match> {

    @Override
    public Set<Match> poll() {
        PlayerPoller playerPoller = new PlayerPoller();
        Set<Match> matchSet = new HashSet<>();
        playerPoller.poll().forEach(player -> matchSet.addAll(player.getPlayerRelationships().getMatches().getData()));
        return matchSet;
    }
}
