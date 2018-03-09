## Problem Statement
Calculate real-time statistic from the last 60 seconds

## Objective
Create 2 APIs -
1) To make a transaction
2) Get the statistic based of the transactions of the last 60 seconds

## API Endpoints
###### 1) POST /transactions

Example Request Body
```
{
"amount": 12.3,
"timestamp": 1478192204000
}
```

Example Response : 

201 - in case of success

204 - if transaction is older than 60sec		

###### 2) GET /statistics

Example Response :
```
{
"sum": 1000,
"avg": 100,
"max": 200,
"min": 50,
"count": 10
}
```
Where:
- **sum** is a double specifying the total sum of transaction value in the last 60 seconds
- **avg** is a double specifying the average amount of transaction value in the last 60
seconds
- **max** is a double specifying single highest transaction value in the last 60 seconds
- **min** is a double specifying single lowest transaction value in the last 60 seconds
- **count** is a long specifying the total number of transactions happened in the last 60
seconds

## Contraints
- GET /statistics should execute in constant time and space
- The API should be threadsafe
- The API should be able to deal with time discrepancy, which means, at any point of time, we could receive a transaction which have a timestamp of the past
- No database (Not even in-memory database)
- Endpoints have to execute in constant time and memory (O(1))

## Technologies used
Build Tool - Maven

Programming Language - Java

Frameworks - Spring Boot, REST

## Maven commands
Build - mvn clean install

Test - mvn test

## Analysis and Datastructure selection
- API should be threadsafe. May be synchronised (degrades performance) or Lock or concurrent package object? May be ConcurrentHashMap (since its the optimized version for multi-threaded apps?)
- API should be able to deal with time discrepancy. So Ordering is important. ConcurrentHashMap ordering is not guaranteed. Search further?
- Endpoints to execute in constant time and memory-O(1). So Hash-based map data structure may be?
- As per https://docs.oracle.com/javase/7/docs/api/java/util/concurrent/ConcurrentMap.html ConcurrentMap has 2 implementations -

- ConcurrentHashMap

- ConcurrentSkipListMap

## Comparison-
**ConcurrentHashMap** - unsorted, O(1), Thread-safe

**ConcurrentSkipListMap** - sorted, log(n)	for most operations, Thread-safe


ConcurrentSkipListMap also supports additional functions like TailMap() and HeadMap() which can be used for slicing map data. 
Because of these shortlisted ConcurrentSkipListMap to store the transactions.
Also, since we need to focus only on transactions in last 60 sec, used the @Scheduled annotation to clear off the old transactions every 10 sec.

