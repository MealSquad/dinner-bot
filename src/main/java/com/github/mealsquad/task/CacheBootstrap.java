package com.github.mealsquad.task;

import com.github.mautini.pubgjava.exception.PubgClientException;
import com.github.mautini.pubgjava.model.Platform;
import com.github.mealsquad.model.User;
import com.google.common.collect.Lists;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.stream.Collectors;

public class CacheBootstrap extends AbstractUpdate {

    private static final Logger logger = LogManager.getFormatterLogger();
    private final int PARTITION_SIZE = 6;
    private final int DELAY_TIME = 60*1000 + 5;

    @Override
    public void run() {
        List<String> currentPlayers = channelHandler.getCurrentDinnerBoard().getDinnerBoard().keySet().stream()
                .map(User::getName).collect(Collectors.toList());
        List<List<String>> paginatedRequest = Lists.partition(currentPlayers, PARTITION_SIZE);
        paginatedRequest.forEach(sizedRequest -> {
            String[] players = new String[PARTITION_SIZE];
            sizedRequest.toArray(players);
            try {
                channelHandler.getPb().getPlayersByNames(Platform.STEAM, players).getData()
                        .forEach(player -> channelHandler.getUserCache()
                                .put(new User(player.getPlayerAttributes().getName()), player.getId()));
            } catch (PubgClientException e) {
                for (String name : players) {
                    logger.info("Failed to retrieve players information for cache update for players %s", name);
                    e.printStackTrace();
                }
            }
            try {
                Thread.sleep(DELAY_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
}
