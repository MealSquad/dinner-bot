package com.github.mealsquad.poller;

import com.github.mautini.pubgjava.api.PubgClient;

import java.util.Collection;

public abstract class AbstractPoller<T> {

    protected PubgClient pb;

    public AbstractPoller(PubgClient pb) {
        this.pb = pb;
    }

    public abstract Collection<T> poll();
}
