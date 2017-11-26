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


Run the project
```
./gradlew run '-Purl=<fullUrlOfWebPage>'
```

For example:
```
./gradlew run '-Purl=https://www.monzo.com'
```

## Running the tests
```
./gradlew test
```