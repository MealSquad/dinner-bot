package com.github.mealsquad.poller;

import com.github.mautini.pubgjava.api.PubgClient;
import com.github.mautini.pubgjava.model.match.MatchResponse;
import com.github.mautini.pubgjava.model.participant.ParticipantAttributes;
import com.github.mealsquad.filter.AbstractFilter;

import java.util.List;

public abstract class AbstractPoller<T> {

    private PubgClient pb;

    public AbstractPoller() {
        this.pb = new PubgClient();
    };

    protected PubgClient getPb() {
        return pb;
    }

    public abstract List<T> poll();
}
