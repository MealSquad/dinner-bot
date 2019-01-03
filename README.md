# dinner-bot
A discord bot to scrape op.gg for stats to post to a discord channel

## Usage
This bot will query OP.GG for statistics of users who opt-in

## Basic Bot Lifecycle
- Bot joins channel and begins lifetime loop
- Every XX minutes, query OP.GG for list of users who opt-in
- At any point, a user can add themselves to the bots "field of view"
- Format data into dinner-display friendly printout
- Repeat

### Project tenets
- Unit test everything
- Bring on second developer to help write/review code
- Have code reviewed and interated on


### Resources
 - https://github.com/Javacord/Javacord#ide-setup
 - https://javacord.org/wiki/getting-started/welcome/
 - https://medium.freecodecamp.org/how-to-securely-store-api-keys-4ff3ea19ebda
