# Just another web crawler


## Dependencies

Java 8 <br />
Gradle 3.5 - Included within project

## Build Instructions

Enter project directory
```
cd WebCrawler
```

Build all project modules (main and test)
```
./gradlew build
```
Please note that the Gradle build will not succeed unless all tests pass


Run project using JAR executable
```
java -jar build/libs/WebCrawler-1.0.jar <inputFileDirectory>
```

For example:
``
java -jar build/libs/WebCrawler-1.0.jar monzo.com
```

## Running the tests
```
./gradlew test
```