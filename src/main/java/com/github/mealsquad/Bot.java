package com.github.mealsquad;

import com.github.mealsquad.channel.ChannelHandler;
import com.github.mealsquad.task.BoardUpdate;
import com.github.mealsquad.task.CacheBootstrap;
import com.github.mealsquad.task.CacheUpdate;
import com.github.mealsquad.listeners.HelpListener;
import com.github.mealsquad.listeners.UserListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Bot {

    private final Logger logger = LogManager.getLogger();

    private void start() {
        logger.info("Successfully join text channel");
        ChannelHandler channelHandler = ChannelHandler.getInstance();

        channelHandler.addListeners(new HelpListener(), new UserListener());

        scheduleTasks();
    }

    public static void main(String[] args) {
        Bot me = new Bot();
        me.start();
    }

    private void scheduleTasks() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        final Long initialDelay = LocalDateTime.now().until(LocalDate.now().plusDays(1).atTime(12, 30),
                ChronoUnit.MINUTES);

        Long delayTime = (initialDelay > TimeUnit.DAYS.toMinutes(1))
                ? LocalDateTime.now().until(LocalDate.now().atTime(12, 30), ChronoUnit.MINUTES)
                : initialDelay;

        scheduler.scheduleAtFixedRate(
                new BoardUpdate(), delayTime, TimeUnit.DAYS.toMinutes(1), TimeUnit.MINUTES);

        final Long cacheInitialDelay = LocalDateTime.now().until(LocalDate.now().plusDays(1).atTime(12, 30),
                ChronoUnit.MINUTES);

        Long cacheDelayTime = (initialDelay > TimeUnit.DAYS.toMinutes(1))
                ? LocalDateTime.now().until(LocalDate.now().atTime(12, 25), ChronoUnit.MINUTES)
                : cacheInitialDelay;

        // Fill cache when bot starts
        scheduler.execute(new CacheBootstrap());

        scheduler.scheduleAtFixedRate(
                new CacheUpdate(), cacheDelayTime, TimeUnit.HOURS.toMinutes(1), TimeUnit.MINUTES);
    }
}
