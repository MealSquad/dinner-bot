package com.github.mealsquad.generator;

import com.github.mealsquad.model.RelevantInfo;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class RelevantInfoGenerator implements Function<List<String>, RelevantInfo> {

    // TODO make this not terrible and horrible
    @Override
    public RelevantInfo apply(List<String> strings) {
        List<Integer> ints = strings.subList(1, strings.size()).stream().map(Integer::valueOf).collect(Collectors.toList());
        return new RelevantInfo(strings.get(0), ints.get(0), ints.get(1), ints.get(2), ints.get(3), ints.get(4));
    }
}
