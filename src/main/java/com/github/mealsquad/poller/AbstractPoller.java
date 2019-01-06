package com.github.mealsquad.poller;

import com.github.mautini.pubgjava.api.PubgClient;

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
