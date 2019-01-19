package com.github.mealsquad.task;

import com.github.mautini.pubgjava.model.participant.ParticipantStats;
import com.github.mealsquad.converter.ParticipantStatsConverter;
import com.github.mealsquad.model.DinnerBoard;
import com.github.mealsquad.model.RelevantInfo;
import com.github.mealsquad.model.User;
import com.github.mealsquad.poller.ParticipantStatsListPoller;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.SetMultimap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BoardUpdate extends AbstractUpdate implements Runnable {

    private final Logger logger = LogManager.getFormatterLogger();

    @Override
    public void run() {
        // Query for update to dinner-board
        logger.info("Starting board update");

        List<ParticipantStats> participantStats = new ParticipantStatsListPoller().poll();
        // Map {User -> {Match1, match2, ...}}
        SetMultimap<String, RelevantInfo> userSpecificRelevantInfo = HashMultimap.create();
        // Adds queried match information per user already present in table
        participantStats.stream().map(new ParticipantStatsConverter()).forEach(relevantInfo -> userSpecificRelevantInfo.put(relevantInfo.getUsername(), relevantInfo));
        Map<User, RelevantInfo> boardUpdate = new HashMap<>();
        userSpecificRelevantInfo.keySet().forEach(key ->
                boardUpdate.put(new User(key), RelevantInfo.builder()
                        .username(key)
                        .kills(0)
                        .wins(0)
                        .topKills(0)
                        .topHitPoints(0)
                        .dionDinners(0)
                        .build()));
        for (User key : boardUpdate.keySet()) {
            RelevantInfo currentInfo = boardUpdate.get(key);
            Set<RelevantInfo> updatedInfos = userSpecificRelevantInfo.get(key.getName());
            for (RelevantInfo ri : updatedInfos) {
                currentInfo = currentInfo.add(ri);
                logger.info("Updating dinner-board information for %s", ri.getUsername());
            }
            boardUpdate.put(key, currentInfo);
        }

        DinnerBoard update = new DinnerBoard(boardUpdate, headerInfo);

        // Update the dinner board
        channelHandler.postUpdatedDinnerBoard(update);
    }
}
