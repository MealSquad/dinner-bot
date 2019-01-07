package com.github.mealsquad.model;

import com.github.mautini.pubgjava.model.match.Match;
import com.github.mautini.pubgjava.model.participant.ParticipantStats;
import com.github.mautini.pubgjava.model.player.Player;
import com.github.mealsquad.filter.ChickenDinnerFilter;
import com.github.mealsquad.poller.ParticipantStatsListPoller;
import com.github.mealsquad.poller.PlayerListPoller;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Bot {

    private final Logger logger = LogManager.getLogger();
    private final String token;
    private List<User> users;

    public Bot(List<User> users, String token) {
        this.users = users;
        this.token = token;
    }

    public List<User> getUsers() {
        return users;
    }

    public String getToken() {
        return token;
    }

    public void start() {
        DiscordApi api = new DiscordApiBuilder().setToken(token).login().join();

        logger.info("Successfully join text channel");
        PlayerListPoller playerListPoller = new PlayerListPoller(users);

        Set<Match> matchSet = constructMatchSet(playerListPoller.poll());
        ParticipantStatsListPoller participantStatsListPoller = new ParticipantStatsListPoller(matchSet, new ChickenDinnerFilter(users));
        List<ParticipantStats> participantStats = participantStatsListPoller.poll();

        // Replace this with a module or class which handles all user input / bot output. Essentially a CLI
        // Add a listener which answers with "Pong!" if someone writes "!ping"
        api.addMessageCreateListener(event -> {
            if (event.getMessage().getContent().equalsIgnoreCase("!ping")) {
                event.getChannel().sendMessage(String.format("In most recent chicken dinner game, NutellaFrisbee got %s kills!", participantStats.get(0).getKills().toString()));
            }
        });
    }

    private Set<Match> constructMatchSet(List<Player> players) {
        Set<Match> matchSet = new HashSet<>();
        players.stream()
                .forEach(player -> player.getPlayerRelationships().getMatches().getData().stream()
                        .forEach(match -> matchSet.add(match)));
        return matchSet;
    }
}
