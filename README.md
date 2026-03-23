# Ride-The-Bus-Family-Edition


Our digital version of Ride the Bus will include all the structure of the original game with players competing through multiple rounds of guessing cards; the game can be played with one solo player, or be set up for all players to compete for the lowest score.


Patterns: 

Strategy Pattern: used in IScoringStrategy to swap the scoring rules between Family and Adult classes.

Factory Patter: DeckFactory creates the standard deck without the caller knowing how it's built

Observer Pattern: In the IGame Observer and IGameSubject interfaces. Isn't implemented yet, but will help keep the UI components
updated when the game state changes

State Pattern: IGameState. Not implemented yet but will manage the transition between the different phases of the game