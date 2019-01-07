package com.github.mealsquad.poller;

import com.github.mautini.pubgjava.api.PubgClient;

import java.util.List;

public abstract class AbstractPoller<T> {

    private static final PubgClient pb = new PubgClient();

    protected PubgClient getClient() {
        return pb;
    }

    public abstract List<T> poll();
}
