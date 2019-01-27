package com.github.mealsquad.poller;

import com.github.mautini.pubgjava.api.PubgClient;
import com.github.mautini.pubgjava.exception.PubgClientException;
import com.github.mautini.pubgjava.model.Platform;
import com.github.mautini.pubgjava.model.player.Player;
import com.github.mealsquad.channel.ChannelHandler;
import com.github.mealsquad.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class PlayerPoller extends AbstractPoller<Player> {

    private static final Logger logger = LogManager.getFormatterLogger();

    public PlayerPoller(PubgClient pb) {
        super(pb);
    }

    @Override
    public List<Player> poll() {
        ChannelHandler channelHandler = ChannelHandler.getInstance();
        List<Player> players = new ArrayList<>();
        List<User> currentUsers = channelHandler.getUsers();
        Set<User> users = channelHandler.getUserCache().getAddBuffer();
        users.addAll(currentUsers);
        users.stream().map(user -> channelHandler.getUserCache().get(user)).forEach(user -> {
            try {
                players.add(pb.getPlayer(Platform.STEAM, user).getData());
            } catch (PubgClientException e) {
                logger.error("Failure to retrieve player %s", user);
                e.printStackTrace();
            }
        });

        players.forEach(player -> logger.info("Poll returned player: %s", player.getPlayerAttributes().getName()));
        return players;
    }
}
