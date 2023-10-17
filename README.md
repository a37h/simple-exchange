## Simple Exchange

This is a simple exchange verifier for a small exchange that supports only one feature, the limit order.

To run the application, you can use the following command:

```
./gradlew build
```

Once the application is built, you can run it by using the following command:

```
java -jar build/libs/simple-exchange-1.0-SNAPSHOT.jar
```

The application will read the input from the standard input and print the output to the standard output.

To run the unit tests, you can use the following command:

```
./gradlew test
```

Here are some examples of how to use the application:

```
cat src/main/resources/test1.txt | java -jar build/libs/simple-exchange-1.0-SNAPSHOT.jar 
     50,000     99 |    100         500
     25,500     98 |    100      10,000
                   |    103         100
                   |    105      20,000
```

```
cat src/main/resources/test2.txt | java -jar build/libs/simple-exchange-1.0-SNAPSHOT.jar 
trade 10006,10001,100,500
trade 10006,10002,100,10000
trade 10006,10004,103,100
trade 10006,10005,105,5400
     50,000     99 |    105      14,600
     25,500     98 |                   
```

And here's how to get MD5 of the output

```
cat src/main/resources/test1.txt | java -jar build/libs/simple-exchange-1.0-SNAPSHOT.jar | md5sum
8ff13aad3e61429bfb5ce0857e846567 -
```

```
cat src/main/resources/test2.txt | java -jar build/libs/simple-exchange-1.0-SNAPSHOT.jar | md5sum
ce8e7e5ab26ab5a7db6b7d30759cf02e -
```
