#!/bin/bash

# Pull newest changes
git pull

# Clean build space
./gradlew clean

# Build
./gradlew build

# Run
./gradlew run
