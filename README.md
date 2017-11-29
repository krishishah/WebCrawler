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


## Output

Site Map printed within ```sitemap.txt``` in root repository directory

For example (not a representation of full output):
```
└── https://www.monzo.com
    ├── https://monzo.com/transparency
    │   ├── https://monzo.com/about
    │   ├── https://monzo.com/annual-report/
    │   ├── https://monzo.com/
    │   ├── https://monzo.com/careers
    │   ├── https://monzo.com/help
    │   ├── https://monzo.com/invest/
    │   ├── https://monzo.com/blog
    │   ├── https://monzo.com/community
    │   ├── https://monzo.com/cookies
    │   ├── https://monzo.com/press
    │   └── https://monzo.com/download
    └── https://monzo.com/download
    ......

```
