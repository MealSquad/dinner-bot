package com.github.mealsquad.model;

import com.github.mautini.pubgjava.model.match.Match;
import com.github.mautini.pubgjava.model.participant.ParticipantStats;
import com.github.mautini.pubgjava.model.player.Player;
import com.github.mealsquad.converter.ParticipantStatsConverter;
import com.github.mealsquad.filter.ChickenDinnerFilter;
import com.github.mealsquad.poller.ParticipantStatsListPoller;
import com.github.mealsquad.poller.PlayerListPoller;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.SetMultimap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Bot {

    private final Logger logger = LogManager.getLogger();
    private final ParticipantStatsConverter psc = new ParticipantStatsConverter();
    private final String token;
    private List<User> users;

    public Bot(List<User> users, String token) {
        this.users = users;
        this.token = token;
    }

    public void start() {
        DiscordApi api = new DiscordApiBuilder().setToken(token).login().join();
        logger.info("Successfully join text channel");

        // Query for update to dinner-board
        PlayerListPoller playerListPoller = new PlayerListPoller(users);
        Set<Match> matchSet = constructMatchSet(playerListPoller.poll());
        ParticipantStatsListPoller participantStatsListPoller = new ParticipantStatsListPoller(matchSet, new ChickenDinnerFilter(users));
        List<ParticipantStats> participantStats = participantStatsListPoller.poll();
        SetMultimap<String, RelevantInfo> userSpecificRelevantInfo = HashMultimap.create();
        participantStats.stream().map(stats -> psc.apply(stats)).forEach(relevantInfo -> userSpecificRelevantInfo.put(relevantInfo.getUsername(), relevantInfo));
        Map<String, RelevantInfo> boardUpdate = new HashMap<>();
        userSpecificRelevantInfo.keySet().stream().forEach(key -> boardUpdate.put(key, new RelevantInfo(key, 0, 0,0, 0, 0)));
        for (String key : boardUpdate.keySet()) {
            RelevantInfo currentInfo = boardUpdate.get(key);
            Set<RelevantInfo> updatedInfos = userSpecificRelevantInfo.get(key);
            for (RelevantInfo ri : updatedInfos) {
                currentInfo = currentInfo.add(ri);
            }
            boardUpdate.put(key, currentInfo);
        }

        // Parse the dinner-board for current state of the world

        // Update the dinner board

        // Replace this with a module or class which handles all user input / bot output. Essentially a CLI
        // Add a listener which answers with "Pong!" if someone writes "!ping"
        api.addMessageCreateListener(event -> {
            if (event.getMessage().getContent().equalsIgnoreCase("!help")) {
                event.getChannel().sendMessage("Thank you for using dinner-bot!  If you want to be included on the dinner-board, please use the following command: !addUser pubgUserName --alias dinnerBoardUserName");
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
