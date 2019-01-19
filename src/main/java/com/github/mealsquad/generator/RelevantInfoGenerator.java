package com.github.mealsquad.generator;

import com.github.mealsquad.model.RelevantInfo;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class RelevantInfoGenerator implements Function<List<String>, RelevantInfo> {

    // TODO make this not terrible and horrible
    @Override
    public RelevantInfo apply(List<String> strings) {
        Integer[] ints = new Integer[5];
        strings.subList(1, strings.size()).stream().map(Integer::valueOf).collect(Collectors.toList()).toArray(ints);
        return RelevantInfo.builder()
                .username(strings.get(0))
                .kills(ints[0])
                .wins(ints[1])
                .topKills(ints[2])
                .topHitPoints(ints[3])
                .dionDinners(ints[4])
                .build();
    }
}
