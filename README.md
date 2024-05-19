# Insurance Claim Management System - Group 21

This is a project for COSC2440 - Further Programming at RMIT University.
The project is a web application that allows users to manage insurance claims.

The application is built with the purpose of building a functional back-end with Java, JPA, PostgreSQL with a hosted
database (Supabase) with the ability to display functionalities in the front-end with JavaFX.

## Getting Started

1. Pre-requisites
    - Java 21
    - JavaFX SDK 21
    - Maven
    - Git

2. Clone the repository

In your terminal, run the following command:

```shell
git clone https://github.com/nguyenpnguyen/insurance-system-group21.git && cd insurance-system-group21
```

3. Run the application

<details>
<summary>Run the application with Maven</summary>

```shell
mvn clean javafx:run
```

</details>

<details>
<summary>Run the application with JavaFX SDK</summary>

```shell
mvn clean compile
java --module-path $PATH_TO_JAVAFX_SDK --add-modules javafx.controls,javafx.fxml -cp target/insurance-system-group21-1.0-SNAPSHOT.jar
```

</details>

#### Note:

Uncomment line

```java 
DataSeeder.seedData();
``` 

in ```./src/main/java/org/group21/insurance/App.java``` to seed data to
the database before running the application.

## Video Demo Link:

link: https://www.youtube.com/watch?v=BSSfXMR1wsU

## Contribution Points

| Name               | ID       | Contribution Point |
|--------------------|----------|--------------------|
| Pham Hoang Duong   | S3818206 | 3                  |
| Nguyen Phuc Nguyen | S4025306 | 3                  |