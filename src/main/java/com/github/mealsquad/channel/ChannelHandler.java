package com.github.mealsquad.channel;

import com.github.mealsquad.model.DinnerBoard;
import com.github.mealsquad.generator.DinnerBoardGenerator;
import com.github.mealsquad.model.User;
import com.github.mealsquad.utility.ConfigReader;
import com.jakewharton.fliptables.FlipTable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ChannelHandler {

    private static ChannelHandler singleton = null;

    private final String DINNER_BOARD_REGEX = "[^a-zA-Z0-9#]+ | ^[# of Dion Dinners] | ^[Top DPS/Game] | ^[Top Kills]";
    private final String BOOTSTRAP_DINNER_BOARD_REGEX = "-|---|\n";
    private final String ME = "dinner-bot";
    private static final Logger logger = LogManager.getFormatterLogger();
    private DiscordApi api;
    private String token;
    private String inputChannel;
    private String outputChannel;
    private Set<User> addBuffer;
    private Set<User> removeBuffer;
    private List<User> users;

    private ChannelHandler() {
        this.token = ConfigReader.readProperties("token");
        this.api = new DiscordApiBuilder().setToken(token).login().join();
        this.inputChannel = ConfigReader.readProperties("inputChannel");
        this.outputChannel = ConfigReader.readProperties("outputChannel");
        this.addBuffer = new HashSet<>();
        this.removeBuffer = new HashSet<>();
        this.users = new ArrayList<>();
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

    public Set<User> getAddBuffer() {
        Set<User> buffer = new HashSet<>(addBuffer);
        addBuffer.clear();
        return buffer;
    }

    public Set<User> getRemoveBuffer() {
        Set<User> buffer = new HashSet<>(removeBuffer);
        removeBuffer.clear();
        return buffer;
    }

    public DinnerBoard getCurrentDinnerBoard() {
        try {
            List<String> rawDinnerBoard = splitDinnerBoard(new ArrayList<>(api.getTextChannelsByName(inputChannel))
                    .get(0).getMessages(1).get().getNewestMessage().get().getContent());
            return new DinnerBoardGenerator().apply(rawDinnerBoard);
        } catch (Exception e) {
            logger.warn("Failure to retrieve dinner-board");
            e.printStackTrace();
        }
        return new DinnerBoard(new HashMap<>(), new ArrayList<>());
    }

    private List<String> splitDinnerBoard(String board) {
        boolean bootstrapBoard = !board.contains("`");
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
        users = new ArrayList<>(getCurrentDinnerBoard().getDinnerBoard().keySet());
        return users;
    }

    public void addListeners() {
        // Replace this with a module or class which handles all user input / bot output. Essentially a CLI
        // Add a listener which answers with "Pong!" if someone writes "!ping"
        logger.info("Adding listeners");

        api.addMessageCreateListener(event -> {
            if (!event.getMessageAuthor().getDisplayName().equals(ME)) {
                if (event.getMessage().getContent().equalsIgnoreCase("!help")) {
                    event.getChannel().sendMessage("Thank you for using dinner-bot!  If you want to be included on the dinner-board, please use the following command: ```!addUser pubgUserName```");
                } else if (event.getMessage().getContent().contains("!addUser")) {
                    String[] cmd = event.getMessageContent().split(" ");
                    if (cmd.length > 2) {
                        throw new UnsupportedOperationException("Unable to accept more than one username or a name containing a space");
                    }
                    String user = cmd[1];
                    addBuffer.add(new User(user));


                    logger.info("Adding %s to the dinner-board", user);
                    event.getChannel().sendMessage(String.format("Added new user %s to the dinner-board", user));
                }
            }
        });
    }
}
