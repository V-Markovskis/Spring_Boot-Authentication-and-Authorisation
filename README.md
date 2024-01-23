# :feet: JWT-Authentication-and-Authorisation
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.0.2-brightgreen)](https://spring.io/projects/spring-boot)
[![Java](https://img.shields.io/badge/Java-17-brightgreen)](https://docs.oracle.com/en/java/javase/17/)

### :herb: Added features

<div>
  <img src="http://wiki.stat.ucla.edu/socr/uploads/a/a2/JAVA_animated.gif" align="right" width="200" />
</div>

- [X] Bearer Token : JWT :snowflake:
- [X] Security / Authorities :fish:
- [X] Registration validation :milky_way:
- [X] Role based :whale:
  + ADMIN 
  + MANAGER
  + USER
- [X] H2 database :whale2:
- [X] Swagger UI :dolphin:
- [X] Roles permissions :ocean:

### How to start/use application
After launching the program two users will be created: ADMIN and MANAGER for testing the application.
Their tokens will also be created.
```java
var admin = RegisterRequest.builder()
        .firstname("Admin")
        .lastname("Admin")
        .email("admin@mail.com")
        .password("password")
        .role(ADMIN)
        .build();

var manager = RegisterRequest.builder()
        .firstname("Admin")
        .lastname("Admin")
        .email("manager@mail.com")
        .password("password")
        .role(MANAGER)
        .build();
```
You can test all enpoints through Swagger UI <font color="green">&#10148; </font>
[![Swagger UI](https://img.shields.io/badge/Swagger%20UI-Open%20API%20Documentation-brightgreen)](http://localhost:8080/swagger-ui/index.html)

### H2 database access

[H2 console link](http://localhost:8080/h2-console)
```
username=sa
password=
```

![Console](http://www.h2database.com/html/images/quickstart-3.png)

Once connected, you can see all existing users. Use SQL query : ```SELECT * FROM USERS ``` and ```Run``` button.

