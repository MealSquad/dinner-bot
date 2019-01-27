package com.github.mealsquad.task;

import com.github.mautini.pubgjava.exception.PubgClientException;
import com.github.mautini.pubgjava.model.Platform;
import com.github.mautini.pubgjava.model.player.Player;
import com.github.mealsquad.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CacheUpdate extends AbstractUpdate {

    private static final Logger logger = LogManager.getFormatterLogger();

    @Override
    public void run() {
        List<String> players = channelHandler.getUserCache().getAddBuffer().stream().map(User::getName).collect(Collectors.toList());
        String[] playerarr = new String[players.size()];
        players.toArray(playerarr);
        List<Player> playerInfos = new ArrayList<>();
        try {
            playerInfos = channelHandler.getPb().getPlayersByNames(Platform.STEAM, playerarr).getData();
        } catch (PubgClientException e) {
            for (String name : playerarr) {
                logger.info("Failed to retrieve players information for cache update for players %s", name);
                e.printStackTrace();
            }
        }
        playerInfos.forEach(player -> channelHandler.getUserCache().put(new User(player.getPlayerAttributes().getName()), player.getId()));
    }
}
