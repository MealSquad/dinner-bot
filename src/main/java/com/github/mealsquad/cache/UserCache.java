package com.github.mealsquad.cache;

import com.github.mealsquad.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class UserCache {

    private static final Logger logger = LogManager.getFormatterLogger();
    private Set<User> addBuffer;
    private Set<User> removeBuffer;
    private Map<User, String> userIds;

    public UserCache() {
        this.addBuffer = new HashSet<>();
        this.removeBuffer = new HashSet<>();
        this.userIds = new HashMap<>();
    }

    public String get(User key) {
        return userIds.get(key);
    }

    public void put(User key, String id) {
        logger.info("Adding user %s to cache with id %s", key.getName(), id);
        userIds.put(key, id);
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

    public Set<User> getUsers() {
        return userIds.keySet();
    }
}
