package com.github.mealsquad;

import com.github.mealsquad.channel.ChannelHandler;
import com.github.mealsquad.task.BoardUpdate;

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

        channelHandler.addListeners();

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        final Long initialDelay = LocalDateTime.now().until(LocalDate.now().plusDays(1).atTime(12, 30),
                ChronoUnit.MINUTES);

        Long delayTime = (initialDelay > TimeUnit.DAYS.toMinutes(1))
                ? LocalDateTime.now().until(LocalDate.now().atTime(12, 30), ChronoUnit.MINUTES)
                : initialDelay;

        scheduler.scheduleAtFixedRate(
                new BoardUpdate(), 0L, TimeUnit.MINUTES.toMinutes(1), TimeUnit.MINUTES);
    }

    public static void main(String[] args) {
        Bot me = new Bot();
        me.start();
    }
}
