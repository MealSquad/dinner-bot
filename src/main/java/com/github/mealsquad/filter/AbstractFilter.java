package com.github.mealsquad.filter;

import java.util.List;

public abstract class AbstractFilter<T, F> {

    public AbstractFilter() {
    }

    public abstract List<F> filter(List<T> toFilter);
}
