package com.github.mealsquad.filter;

import javafx.util.Pair;

import java.time.LocalDateTime;
import java.util.List;

public abstract class AbstractFilter<T, F> {

    public abstract List<F> filter(List<Pair<LocalDateTime, T>> toFilter);
}
