package com.github.mealsquad.channel;

import com.github.mautini.pubgjava.api.PubgClient;
import com.github.mealsquad.cache.UserCache;
import com.github.mealsquad.generator.DinnerBoardGenerator;
import com.github.mealsquad.model.DinnerBoard;
import com.github.mealsquad.model.User;
import com.github.mealsquad.utility.ConfigReader;
import com.jakewharton.fliptables.FlipTable;
import com.github.mealsquad.listeners.AbstractListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class ChannelHandler {

    private static ChannelHandler singleton = null;

    private final PubgClient pb = new PubgClient();
    private final String DINNER_BOARD_REGEX = "[^a-zA-Z0-9#]+ | ^[# of Dion Dinners] | ^[Top DPS/Game] | ^[Top Kills]";
    private final String BOOTSTRAP_DINNER_BOARD_REGEX = "-|---|\n";
    private static final Logger logger = LogManager.getFormatterLogger();
    private DiscordApi api;
    private String inputChannel;
    private String outputChannel;
    private UserCache userCache;

    private ChannelHandler() {
        this.api = new DiscordApiBuilder().setToken(ConfigReader.readProperties("token")).login().join();
        this.inputChannel = ConfigReader.readProperties("inputChannel");
        this.outputChannel = ConfigReader.readProperties("outputChannel");
        this.userCache = new UserCache();
    }

    public static ChannelHandler getInstance() {
        if (singleton == null) {
            singleton = new ChannelHandler();
            logger.info("Creating new ChannelHandler");

        } else {
            logger.info("Returning static ChannelHandler");
        }
        return singleton;
    }

    public PubgClient getPb() {
        return pb;
    }

    public UserCache getUserCache() {
        return userCache;
    }

    public DinnerBoard getCurrentDinnerBoard() {
        try {
            List<String> rawDinnerBoard = splitDinnerBoard(new ArrayList<>(api.getTextChannelsByName(inputChannel))
                    .get(0).getMessages(1).get().getNewestMessage().get().getContent());
            return new DinnerBoardGenerator().apply(rawDinnerBoard);
        } catch (Exception e) {
            logger.error("Failure to retrieve dinner-board");
            e.printStackTrace();
        }
        return new DinnerBoard(new HashMap<>(), new ArrayList<>());
    }

    private List<String> splitDinnerBoard(String board) {
        boolean bootstrapBoard = !board.contains("`");
        if (bootstrapBoard) {
            logger.info("Bootstrapping dinner-board state");
        }
        List<String> extractStats = Arrays.stream(board.replaceAll(bootstrapBoard
                ? BOOTSTRAP_DINNER_BOARD_REGEX
                : DINNER_BOARD_REGEX, "+").split("[+]"))
                .map(String::trim).filter(elem -> !elem.isEmpty()).collect(Collectors.toList());
        return bootstrapBoard ? extractStats : extractStats.subList(0, extractStats.size() - 1);
    }

    public void postUpdatedDinnerBoard(DinnerBoard update) {
        DinnerBoard preview = getCurrentDinnerBoard();
        DinnerBoard newDinnerBoard = preview.add(update);

        // Format table
        String[] headers = newDinnerBoard.getHeader();
        String[][] view = newDinnerBoard.to2dArray();
        new ArrayList<>(api.getTextChannelsByName(outputChannel))
                .get(0).sendMessage(wrapInCodeBlock(FlipTable.of(headers, view)));
    }

    private String wrapInCodeBlock(String string) {
        return "```" + string + "```";
    }

    public List<User> getUsers() {
        return new ArrayList<>(userCache.getUsers());
    }

    public void addListeners(AbstractListener... listeners) {
        for (AbstractListener listener : listeners) {
            api.addMessageCreateListener(listener);
        }
    }
}
