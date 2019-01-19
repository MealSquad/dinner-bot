package com.github.mealsquad.poller;

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

    @Override
    public List<Player> poll() {
        List<Player> players = new ArrayList<>();
        List<User> currentUsers = ChannelHandler.getInstance().getUsers();
        Set<User> users = ChannelHandler.getInstance().getAddBuffer();
        users.addAll(currentUsers);
        users.stream().map(User::getName).forEach(user -> {
            try {
                //PAGINATE - request can only handle 6 people.  separate into single requests causes too many requests
                players.addAll(pb.getPlayersByNames(Platform.STEAM, user).getData());
            } catch (PubgClientException e) {
                logger.error("Failure to retrieve players");
                e.printStackTrace();
            }
        });

        players.forEach(player -> logger.info("Poll returned player: %s", player.getPlayerAttributes().getName()));
        return players;
    }
}
