package com.github.mealsquad;

import com.github.mealsquad.model.Bot;
import com.github.mealsquad.model.User;
import com.typesafe.config.ConfigFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class Main {

    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        Bot dinner = new Bot(getUsers(), readProperties("token"));
        dinner.start();
    }

    public static String readProperties(String key) {
        String value = ConfigFactory.load().getString(key);
        logger.info(String.format("Reading in configuration property: [%s] = %s", key, value));
        return value;
    }

    private static List<User> getUsers() {
        // This should read an on disk file with usernames who opt-in.  A player opting in should also update the on disk file.
        User me = new User("NutellaFrisbee", 0, 0, 0);
        List<User> userList = new ArrayList<User>();
        userList.add(me);
        return userList;
    }

}
