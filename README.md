# CMPT276S24_group12
This is the github repository for Group 12, CMPT 276.  

## Group Members:
- Max Bodifee
- Dylan Cantafio
- Sahil Chand
- Jonathan Peters

## Prerequisites 
- [Java JDK](https://www.oracle.com/java/technologies/downloads/)
- [Apache Maven](https://maven.apache.org/download.cgi)

## Build and Play
First clone this repository onto your machine. Then navigate to the project directory. <br>
The game can then be built and run respectively by using the following:
```
mvn clean package
java -cp target/RaccoonRush-1.0-SNAPSHOT.jar RaccoonRush.game.Main
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
