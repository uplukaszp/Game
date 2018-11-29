# Game
This repo contains sample game built with [FXGL](https://github.com/AlmasB/FXGL) Game Library.
The goal of the game is to shoot down as many enemy aircraft as possible, before the ammunition runs out.
Shooting and aiming is done using the mouse's scroll. 
When you shoot down gray planes, you earn points, when black you lose them.

## Build
```bash
cd PROJECT_NAME
mvn package
```
This will produce a standalone executable with that project in `target`.
## Run
```bash
cd target/
java -jar PROJECT_NAME-VERSION.jar
```
OR simply double-click the jar file if the extensions are correctly set on your machine.
