package com.github.mealsquad.operator;

import com.github.mealsquad.model.DinnerBoard;
import com.github.mealsquad.model.RelevantInfo;
import com.github.mealsquad.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class DinnerBoardAdditionTest {

    private DinnerBoard dinnerBoard1 = new DinnerBoard();
    private DinnerBoard dinnerBoard2 = new DinnerBoard();
    private DinnerBoard expected = new DinnerBoard();
    private Map<User, RelevantInfo> backing1 = new HashMap<>();
    private Map<User, RelevantInfo> backing2 = new HashMap<>();
    private Map<User, RelevantInfo> backing3 = new HashMap<>();

    @Before
    public void initialize() {
        RelevantInfo relevantInfo1 = RelevantInfo.builder()
                .username("NutellaFrisbee")
                .kills(1)
                .wins(2)
                .topKills(3)
                .topHitPoints(4)
                .dionDinners(0)
                .build();
        RelevantInfo relevantInfo2 = RelevantInfo.builder()
                .username("StygianWinter")
                .kills(5)
                .wins(5)
                .topKills(3)
                .topHitPoints(4)
                .dionDinners(0)
                .build();
        backing1.put(new User(relevantInfo1.getUsername()), relevantInfo1);
        backing1.put(new User(relevantInfo2.getUsername()), relevantInfo2);

        RelevantInfo relevantInfo3 = RelevantInfo.builder()
                .username("NutellaFrisbee")
                .kills(5)
                .wins(0)
                .topKills(5)
                .topHitPoints(6)
                .dionDinners(0)
                .build();
        RelevantInfo relevantInfo4 = RelevantInfo.builder()
                .username("StygianWinter")
                .kills(7)
                .wins(3)
                .topKills(1)
                .topHitPoints(0)
                .dionDinners(0)
                .build();
        backing2.put(new User(relevantInfo3.getUsername()), relevantInfo3);
        backing2.put(new User(relevantInfo4.getUsername()), relevantInfo4);

        RelevantInfo relevantInfo5 = RelevantInfo.builder()
                .username("NutellaFrisbee")
                .kills(6)
                .wins(2)
                .topKills(5)
                .topHitPoints(6)
                .dionDinners(0)
                .build();
        RelevantInfo relevantInfo6 = RelevantInfo.builder()
                .username("StygianWinter")
                .kills(12)
                .wins(8)
                .topKills(3)
                .topHitPoints(4)
                .dionDinners(0)
                .build();
        backing3.put(new User(relevantInfo5.getUsername()), relevantInfo5);
        backing3.put(new User(relevantInfo6.getUsername()), relevantInfo6);

        List<String> header = new ArrayList<>();

        header.add("Order");
        header.add("Kills");
        header.add("Wins");
        header.add("Top Kills");
        header.add("Top DPS/Game");
        header.add("# of Dion Dinners");

        dinnerBoard1.setHeader(header);
        dinnerBoard2.setHeader(header);
        dinnerBoard1.setDinnerBoard(backing1);
        dinnerBoard2.setDinnerBoard(backing2);
        expected.setHeader(header);
        expected.setDinnerBoard(backing3);
    }

    @Test
    public void testDinnerBoardAddition() {
        assertEquals(expected, dinnerBoard1.add(dinnerBoard2));
    }
}
