package com.github.mealsquad.poller;

import com.github.mautini.pubgjava.exception.PubgClientException;
import com.github.mautini.pubgjava.model.Platform;
import com.github.mautini.pubgjava.model.player.Player;
import com.github.mealsquad.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class PlayerListPoller extends AbstractPoller<Player> {

    private static final Logger logger = LogManager.getLogger();
    private final List<User> users;

    public PlayerListPoller(List<User> users) {
        this.users = users;
    }

    @Override
    public List<Player> poll() {
        List<Player> players = new ArrayList<>();
        try {
            players = getPb().getPlayersByNames(Platform.STEAM, users.stream().map(User::getName).toArray(String[]::new)).getData();
        } catch(PubgClientException e) {
            logger.error("Failure to retrieve players");
            e.printStackTrace();
        }
        return players;
    }
}
