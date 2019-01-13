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
        RelevantInfo relevantInfo1 = new RelevantInfo("NutellaFrisbee", 1, 2, 3, 4, 0);
        RelevantInfo relevantInfo2 = new RelevantInfo("StygianWinter", 4, 3, 2, 1, 0);
        backing1.put(new User(relevantInfo1.getUsername()), relevantInfo1);
        backing1.put(new User(relevantInfo2.getUsername()), relevantInfo2);

        RelevantInfo relevantInfo3 = new RelevantInfo("NutellaFrisbee", 5, 0, 5, 6, 0);
        RelevantInfo relevantInfo4 = new RelevantInfo("StygianWinter", 7, 3, 1, 0, 0);
        backing2.put(new User(relevantInfo3.getUsername()), relevantInfo3);
        backing2.put(new User(relevantInfo4.getUsername()), relevantInfo4);

        RelevantInfo relevantInfo5 = new RelevantInfo("NutellaFrisbee", 6, 2, 5, 6, 0);
        RelevantInfo relevantInfo6 = new RelevantInfo("StygianWinter", 11, 6, 2, 1, 0);
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
