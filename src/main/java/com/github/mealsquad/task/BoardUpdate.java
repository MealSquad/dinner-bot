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

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class BoardUpdate extends AbstractUpdate implements Runnable {

    private final Logger logger = LogManager.getFormatterLogger();

    @Override
    public void run() {
        // Query for update to dinner-board
        logger.info("Starting board update");

        Collection<ParticipantStats> participantStats = new ParticipantStatsListPoller(channelHandler.getPb()).poll();
        // Map {User -> {Match1, match2, ...}}
        SetMultimap<String, RelevantInfo> userSpecificRelevantInfo = HashMultimap.create();
        // Adds queried match information per user already present in table
        participantStats.stream().map(new ParticipantStatsConverter()).forEach(relevantInfo -> userSpecificRelevantInfo.put(relevantInfo.getUsername(), relevantInfo));
        Map<User, RelevantInfo> boardUpdate = new HashMap<>();
        userSpecificRelevantInfo.keySet().forEach(key -> boardUpdate.put(new User(key), RelevantInfo.emptyRelevantInfo(key)));
        for (User key : boardUpdate.keySet()) {
            RelevantInfo currentInfo = boardUpdate.get(key);
            Set<RelevantInfo> updatedInfos = userSpecificRelevantInfo.get(key.getName());
            for (RelevantInfo ri : updatedInfos) {
                currentInfo = currentInfo.add(ri);
            }
            logger.info("Updating dinner-board information for %s", key.getName());
            boardUpdate.put(key, currentInfo);
        }

        DinnerBoard update = new DinnerBoard(boardUpdate, headerInfo);

        // Update the dinner board only if there is an actual difference
        if (!update.equals(channelHandler.getCurrentDinnerBoard())) {
            channelHandler.postUpdatedDinnerBoard(update);
        }
    }
}
