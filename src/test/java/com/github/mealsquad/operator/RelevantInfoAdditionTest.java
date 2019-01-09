package com.github.mealsquad.operator;

import com.github.mealsquad.model.RelevantInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class RelevantInfoAdditionTest {

    private final RelevantInfo relevantInfo1 = new RelevantInfo("NutellaFrisbee", 1, 2, 3, 4, 0);
    private final RelevantInfo relevantInfo2 = new RelevantInfo("NutellaFrisbee", 4, 3, 2, 1, 0);
    private final RelevantInfo unsupported = new RelevantInfo("StygianWinter", 1, 2, 3, 4, 0);

    private RelevantInfo added = new RelevantInfo("NutellaFrisbee", 5, 5, 3, 4, 0);

    @Test
    public void testRelevantInfoAdd() {
        assertEquals(added, relevantInfo1.add(relevantInfo2));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testUnsupportedOperation() {
        relevantInfo1.add(unsupported);
    }


}
