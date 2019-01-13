package com.github.mealsquad.generator;

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
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class DinnerBoardGeneratorTest {

    private final DinnerBoardGenerator sut = new DinnerBoardGenerator();
    private final DinnerBoard dinnerBoard = new DinnerBoard();
    private List<String> before = new ArrayList<>();

    @Before
    public void initialize() {
        RelevantInfo relevantInfo1 = new RelevantInfo("NutellaFrisbee", 1, 2, 3, 4, 0);
        RelevantInfo relevantInfo2 = new RelevantInfo("StygianWinter", 4, 3, 2, 1, 0);
        Map<User, RelevantInfo> backing = new HashMap<>();
        backing.put(new User(relevantInfo1.getUsername()), relevantInfo1);
        backing.put(new User(relevantInfo2.getUsername()), relevantInfo2);
        List<String> header = new ArrayList<>();
        List<String> data = new ArrayList<>();

        header.add("Order");
        header.add("Kills");
        header.add("Wins");
        header.add("Top Kills");
        header.add("Top DPS/Game");
        header.add("# of Dion Dinners");

        dinnerBoard.setHeader(header);
        dinnerBoard.setDinnerBoard(backing);

        data.add("NutellaFrisbee");
        data.add("1");
        data.add("2");
        data.add("3");
        data.add("4");
        data.add("0");
        data.add("StygianWinter");
        data.add("4");
        data.add("3");
        data.add("2");
        data.add("1");
        data.add("0");

        before = Stream.concat(header.stream(), data.stream()).collect(Collectors.toList());
    }

    @Test
    public void dinnerBoardGeneratorTest() {
        assertEquals(dinnerBoard, sut.apply(before));

    }
}
