# personal-blog
This is a simple personal blog application, built as a practical exercise to master backend development and API design.

## Project Details
This project is based on the [roadmap.sh Personal Blog challenge](https://roadmap.sh/projects/personal-blog)

I used thymeleaf as a template engine, tailwind for styling, npm for package managing.
Project uses basic http authentication with JDBC storage, when you start the application right after cloning it, 
you may face that in your database there is no ADMIN user which means you cannot create, delete, or update blog posts.

For this reason I already prepared initializer that checks for default admin user, and if it cannot find,
it will create default ADMIN user with default credentials defined in application properties.

Using these credentials you can log in to the system and access admin pages such as `/admin`, `/new`, `/edit/...`, `/delete/...` for managing blog posts.

## Prerequisites
Before running the application, ensure you have the following installed:
* Java (JDK 21)
* Maven
* Git
* Docker & Docker Compose
* Node.js

## How to run:
I assume you have java, maven, git, node.js, and docker already installed on your computer

### Clone the repository
First clone the repository on your computer
```bash
git clone https://github.com/adilkhanalimberdi/spring-practice.git
cd "spring-practice/personal-blog"
```

### Install npm dependencies
We use Tailwind CSS for styling and the typography plugin for rendering Markdown. 
Install the dependencies and start the Tailwind compiler:
```bash
npm install
npm run watch
```

*Note: npm run watch will keep running in the background to monitor style changes. 
Please open a new terminal window/tab in the same folder for the next steps.*

### Start the PostgreSQL Database
In your new terminal window (and with Docker running), start the database container in the background:
```bash
docker compose up -d
```

### Run the application
Run the Spring Boot application using the Maven Wrapper:
```bash
./mvnw spring-boot:run
```
*Wait until the logs output Tomcat server started on port 8080.*

### Check the application
This is a Spring Boot MVC application with a built-in frontend, so you can open it directly in your browser:

Go to the browser and go to `http://localhost:8080/`, you should see the home page.

