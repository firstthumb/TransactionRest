TransactionRest
=================

[![Build Status](https://travis-ci.org/firstthumb/TransactionRest.svg?branch=master)](https://travis-ci.org/firstthumb/TransactionRest)

### Dependency
* [Spring Boot](http://projects.spring.io/spring-boot/)
* [Immutables](http://immutables.github.io)
* [Lombok](https://projectlombok.org/)
* [Hamcrest](http://hamcrest.org/)

### Installation
You need to install [maven](https://maven.apache.org/) for managing dependecies

```
mvn clean install
java -jar target/transaction-rest-1.0-SNAPSHOT.jar
```
### Rest API
```
PUT /transactionservice/transaction/{transactionId}
Payload { "amount":double,"type":string, "parent_id":long }
Response { "status": "ok" }

GET /transactionservice/transaction/{transactionId}
Response { "amount":double,"type":string,"parent_id":long }

GET /transactionservice/type/{type}
Response [ ids ]

GET /transactionservice/sum/{parentId}
Response { "sum", double } 
```
