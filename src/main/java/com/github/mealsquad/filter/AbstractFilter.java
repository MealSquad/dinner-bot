package com.github.mealsquad.filter;

import java.util.List;

public abstract class AbstractFilter<T, F> {

    public abstract List<F> filter(List<T> toFilter);
}
