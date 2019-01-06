# dinner-bot
A discord bot to query pubg api for stats to post to a discord channel

## Usage
This bot will query pubg api for statistics of users who opt-in

## Basic Bot Lifecycle
- Bot joins channel and begins lifetime loop
- Every XX minutes, query pubg api for data on users who opt-in
- At any point, a user can add themselves to the bots "field of view"
- Format data into dinner-display friendly printout
- Repeat

### Project tenets
- Unit test everything
- Have code reviewed and interated on
