package com.github.mealsquad.model;

import lombok.NonNull;
import lombok.Value;

@Value
public class User {

    @NonNull private String name;
}
