# Insurance Claim Management System - Group 21

This is a project for COSC2440 - Further Programming at RMIT University.
The project is a web application that allows users to manage insurance claims.

The application is built with the purpose of building a functional back-end with Java, JPA, PostgreSQL with a hosted
database (Supabase) with the ability to display functionalities in the front-end with JavaFX.

## Getting Started

1. Pre-requisites
    - Java 21
    - Maven
    - Git

2. Clone the repository

In your terminal, run the following command:

```shell
git clone https://github.com/nguyenpnguyen/insurance-system-group21.git && cd insurance-system-group21
```

3. Run the application

```shell
mvn clean javafx:run
```

#### Note:

Uncomment line

```java 
DataSeeder.seedData();
``` 

in ```./src/main/java/org/group21/insurance/App.java``` to seed data to
the database before running the application.