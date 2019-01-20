package com.github.mealsquad.operator;

import com.github.mealsquad.model.RelevantInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class RelevantInfoAdditionTest {

    private final RelevantInfo relevantInfo1 = RelevantInfo.builder()
            .username("NutellaFrisbee")
            .kills(1)
            .wins(2)
            .topKills(3)
            .topHitPoints(4)
            .dionDinners(0)
            .build();
    private final RelevantInfo relevantInfo2 = RelevantInfo.builder()
            .username("NutellaFrisbee")
            .kills(4)
            .wins(3)
            .topKills(2)
            .topHitPoints(1)
            .dionDinners(0)
            .build();
    private final RelevantInfo unsupported = RelevantInfo.builder()
            .username("StygianWinter")
            .kills(5)
            .wins(5)
            .topKills(3)
            .topHitPoints(4)
            .dionDinners(0)
            .build();
    private final RelevantInfo added = RelevantInfo.builder()
            .username("NutellaFrisbee")
            .kills(5)
            .wins(5)
            .topKills(3)
            .topHitPoints(4)
            .dionDinners(0)
            .build();

    @Test
    public void testRelevantInfoAdd() {
        assertEquals(added, relevantInfo1.add(relevantInfo2));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testUnsupportedOperation() {
        relevantInfo1.add(unsupported);
    }


}
