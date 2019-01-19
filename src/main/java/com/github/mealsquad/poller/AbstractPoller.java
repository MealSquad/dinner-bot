package com.github.mealsquad.poller;

import com.github.mautini.pubgjava.api.PubgClient;

import java.util.Collection;

public abstract class AbstractPoller<T> {

    protected static final PubgClient pb = new PubgClient();

    public abstract Collection<T> poll();
}
