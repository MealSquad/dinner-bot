#!/bin/bash

# Timestamp
echo "*************************************"
echo "Updating dinner-bot on $(date)"
echo "*************************************"

# Pull newest changes
echo "Fetching newest updates"
git pull

# Clean build space
echo "Cleaning workspace"
./gradlew clean

# Build
echo "Building workspace"
./gradlew build

echo "Starting dinner-bot"
# Run
./gradlew run
