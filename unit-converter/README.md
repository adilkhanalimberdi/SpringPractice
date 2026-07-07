# unit-converter
This is a simple unit converter application, built as a practical exercise to master backend development, API design, and asynchronous frontend interactions.

## Project Details
This project is based on the [roadmap.sh Unit Converter challenge](https://roadmap.sh/projects/unit-converter)

I used thymeleaf as a template engine, tailwind for styling, npm for package managing.
Project features an instant, buttonless UX where conversion results update in real-time as the user types or changes unit selections.

The backend is built with Spring Boot, ensuring precise calculations using `BigDecimal` to handle large numbers and floating-point arithmetic without losing accuracy. It also includes a unified global exception handling mechanism that sends structured error responses back to the client.

## Prerequisites
Before running the application, ensure you have the following installed:
* Java (JDK 21)
* Maven
* Git
* Node.js

## How to run:
I assume you have java, maven, git, and node.js already installed on your computer

### Clone the repository
First clone the repository on your computer
```bash
git clone [https://github.com/adilkhanalimberdi/spring-practice.git](https://github.com/adilkhanalimberdi/spring-practice.git)
cd "spring-practice/unit-converter"
```

### Install npm dependencies
*Note: If you only want to run and test the application without changing any styles, you can skip the npm steps entirely, as the pre-compiled, minified CSS is already included in the project.*

We use Tailwind CSS for styling and managing the responsive layout.
Install the dependencies and start the Tailwind compiler:
```bash
npm install
npm run watch
```
*Note: npm run watch will keep running in the background to monitor style changes.
Please open a new terminal window/tab in the same folder for the next steps.*

### Run the application
Run the Spring Boot application using the Maven Wrapper:
```bash
./mvnw spring-boot:run
```
*Wait until the logs output Tomcat server started on port 8080.*

### Check the application

This is a Spring Boot MVC application with a built-in frontend, so you can open it directly in your browser:

Go to the browser and go to http://localhost:8080/, you should see the home page.