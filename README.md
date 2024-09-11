# CMPT276S24_group12: Raccoon Rush
This is the github repository for Group 12, CMPT 276.  

![Raccoon Rush](https://github.sfu.ca/gna22/CMPT276S24_group12/blob/main/src/main/resources/menu/menu_title.png?raw=true)

## Game Trailer:
A trailer demonstrating highlights of the game can be viewed [here](https://www.youtube.com/watch?v=FV8iqgWPEO8).

## Group Members:
- Max Bodifee
- Dylan Cantafio
- Sahil Chand
- Jonathan Peters

## Prerequisites 
- [Java JDK](https://www.oracle.com/java/technologies/downloads/)
- [Apache Maven](https://maven.apache.org/download.cgi)

## Build and Run
First clone this repository onto your machine. Then navigate to the project directory. <br>
The game can then be built and run respectively by using the following:
```
mvn clean package
java -cp target/RaccoonRush-1.0-SNAPSHOT.jar RaccoonRush.game.Main
```
For reference, the newly compiled and packaged jar file will be located in `${basedir}/target`.

Alternatively, you can run the jar file in the `${basedir}/Artifacts/` directory by using:
```
java -cp Artifacts/RaccoonRush-1.0-SNAPSHOT.jar RaccoonRush.game.Main
```

## Test
To run the unit and integration tests for the game, use the following:
```
mvn test
```

## Javadocs
Javadocs can be generated by using the following: 
```
mvn javadoc:javadoc
```
and they will be located in `${basedir}/target/site/apidocs`. 

If you do not wish to generate new Javadocs, you can also view the Javadoc artifacts in `${basedir}/Artifacts/Javadocs`.
