package com.github.mealsquad.channel;

import com.github.mealsquad.board.DinnerBoard;
import com.github.mealsquad.generator.DinnerBoardGenerator;
import com.github.mealsquad.model.User;
import com.github.mealsquad.utility.ConfigReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.javacord.api.DiscordApi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class ChannelHandler {

    private final Logger logger = LogManager.getLogger();
    private DiscordApi api;
    private String inputChannel;
    private String outputChannel;

    public ChannelHandler(DiscordApi api) {
        this.api = api;
        this.inputChannel = ConfigReader.readProperties("channel");
        this.outputChannel = ConfigReader.readProperties("output");
    }

    public DinnerBoard getCurrentDinnerBoard() {
        try {
            List<String> rawDinnerBoard = splitDinnerBoard(new ArrayList<>(api.getTextChannelsByName(inputChannel)).get(0).getMessages(1).get().getNewestMessage().get().getContent());
            DinnerBoardGenerator dbg = new DinnerBoardGenerator();
            DinnerBoard visual = dbg.apply(rawDinnerBoard);
            return visual;
        } catch (InterruptedException e) {
            logger.fatal("Failure to retrieve dinner-board");
            e.printStackTrace();
        } catch (ExecutionException e) {
            logger.fatal("Failure to retrieve dinner-board");
            e.printStackTrace();
        }
        return new DinnerBoard(new HashMap<>(), new ArrayList<>());
    }

    private List<String> splitDinnerBoard(String board) {
        return Arrays.stream(board.split("-|---|\\n")).map(String::trim).filter(elem -> !elem.isEmpty()).collect(Collectors.toList());
    }

    public void postUpdatedDinnerBoard(DinnerBoard update) {
        DinnerBoard newDinnerBoard = update.add(getCurrentDinnerBoard());
        new ArrayList<>(api.getTextChannelsByName(outputChannel)).get(0).sendMessage(newDinnerBoard.toString());
    }

    public List<User> getUsers() {
        //DinnerBoard currentBoard = getCurrentDinnerBoard();

        // List<String> users = currentBoard.subList(6, currentBoard.size() - 1).stream().filter(item -> item.matches("^[a-zA-Z]+$")).collect(Collectors.toList());

        // Dummy hardcoded list in meantime

        List<User> userList = new ArrayList<>();
        User me = new User("NutellaFrisbee");
        User kent = new User("StygianWinter");
        User ricky = new User("Drake_Akrillain");
        User bryson = new User("Holytankman");
        userList.add(me);
        userList.add(kent);
        userList.add(ricky);
        userList.add(bryson);

        //return users.stream().map(user -> new User(user, "TEST")).collect(Collectors.toList());

        return userList;
    }
}
