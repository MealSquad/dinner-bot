#!/bin/bash

# Kill current running process
if [ `pgrep -f SCREEN` ]; then
	kill -9 `pgrep -f SCREEN`
fi
screen -wipe

# Start a new screen for this instance
screen -dmS dinner-bot sh

# Run build.sh
screen -S dinner-bot -p 0 -X stuff $'./build.sh\n'
