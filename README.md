# 3apedia '22 - Hibernate Reactive & Spring Webflux - Talk & Workshop

### Requirements
* JDK 17
  * You can install this with sdkman, for example: `sdk install java 17.0.2-tem`
* docker

### Introduction
This is a largely complete, but basic CRUD HTTP api for orders and products written in Webflux & R2DBC.
Your task is to convert this to Hibernate Reactive & then apply an async operation or two.

### Instructions
#### Workshop 1a
* There are 3 TODOs in the code in the ./workshop-1a/ folder, complete them in order and write / run the tests 
  to confirm they work as required.
  * Uni to Mono conversion `<my-uni>.convert().with(toMono())`

#### Workshop 1b
* There are 3 TODOs in the code in the ./workshop-1a/ folder, complete them in order and write / run the tests 
  to confirm they work as required.
  * Uni to Mono conversion `<my-uni>.convert().with(toMono())`

#### Workshop 1b
Coming tomorrow

### Running (once implemented)
To launch the site with postgres simply run:
```shell script
> gradle[w] bootRun
```
To run tests:
```shell script
> gradle[w] test
```

To stop the running docker postgres instance:
```shell script
> docker-compose -f resources/postgresql.yml down
```

Note that postgres persisted data is stored in `./resources/postgres-data` with owner set to the current uid

### Troubleshooting
Should there be an issue with docker, you can completely reset your docker environment with:
```shell script
docker stop `docker ps -a -q`; docker rm `docker ps -a -q`; docker system prune -f
```
