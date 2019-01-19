package com.github.mealsquad.filter;

import com.github.mealsquad.utility.Pair;

import java.time.LocalDateTime;
import java.util.List;

public interface AbstractFilter<T, F> {

    List<F> filter(List<Pair<LocalDateTime, T>> toFilter);
}
