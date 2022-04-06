# Splittermond Roll-Butler
## a JDA Discord Applet
Discord-Bot built on JDA for use in Discord-based online PnP servers. Created for the german Pen-And-Paper "Splittermond" but useful for other PNP as well.

## Features
* dice throwing (1W10, 4W6, etc)
* arithmetics supported (2W10 + 14)
* secret gmroll and gm messaging features
* bullshit-bingo for selecting a random predefined item
* battle-map for keeping track everyones turn during battles

## Setup
### Dependencies
- [Discord JDA](https://github.com/DV8FromTheWorld/JDA.git) (duh)
- [Apache Commons Lang](https://mvnrepository.com/artifact/org.apache.commons/commons-lang3)
- [MockNeat](https://github.com/nomemory/mockneat)
- [libsplittermond](https://www.rpgframework.de/index.php/de/entwicklung/libsplittermond/) currently not used
- Java 14 or above

### Windows
- download latest .jar from releases and move it into its own folder
- add bottoken.txt with only your bottoken to the same folder
- execute program with java

### Linux
- WIP

### Wanna build from source?
- Clone the repo and use the src-folder
- the mainclass is located in the main-package as BotStartup.java

