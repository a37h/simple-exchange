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

Here are some examples of how to use the application:

```
cat src/main/resources/test1.txt | java -jar build/libs/simple-exchange-1.0-SNAPSHOT.jar | md5sum
8ff13aad3e61429bfb5ce0857e846567 -
```

```
cat src/main/resources/test2.txt | java -jar build/libs/simple-exchange-1.0-SNAPSHOT.jar | md5sum
ce8e7e5ab26ab5a7db6b7d30759cf02e -
```
