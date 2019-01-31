package com.github.mealsquad.filter;

import java.util.Collection;

public interface Filter<T, F> {
    Collection<F> filter(Collection<T> toFilter);
}
