package com.github.mealsquad.generator;

import com.github.mealsquad.model.RelevantInfo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class RelevantInfoGeneratorTest {

    private final RelevantInfoGenerator sut = new RelevantInfoGenerator();
    private final RelevantInfo relevantInfo = RelevantInfo.builder()
            .username("NutellaFrisbee")
            .kills(1)
            .wins(2)
            .topKills(3)
            .topHitPoints(4)
            .dionDinners(0)
            .build();
    private final ArrayList<String> before = new ArrayList<>();

    @Before
    public void initialize() {
        before.add("NutellaFrisbee");
        before.add("1");
        before.add("2");
        before.add("3");
        before.add("4");
        before.add("0");
    }

    @Test
    public void RelevantInfoGeneratorTest() {
        assertEquals(relevantInfo, sut.apply(before));
    }
}
