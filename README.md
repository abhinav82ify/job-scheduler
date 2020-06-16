# Job Scheduler

A minimal job scheduling solution exposed via a web API.
To enqueue an array of numbers to be sorted in the background
and to query the state of any previously enqueued job.

## Pre requisites

1. JDK 1.8 or above
2. Maven

## Installation

To install the required jars and run unit tests

Windows:

```cmd
mvn clean install
```

To run without tests

```cmd
mvn clean install -Dmaven.test.skip=true
```
## Author
[Abhinav Sharma](https://github.com/abhinav82ify)
