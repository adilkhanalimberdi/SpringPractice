# url-shortening-service
This is a RESTful API for a url shortening service, built as a practical exercise to master backend development and API design.

## Project Details 
This project is based on the [roadmap.sh Url Shortening Service challenge](https://roadmap.sh/projects/url-shortening-service)

I could have added a frontend, but I decided to focus on API only.
You can pass the url like: `https://localhost:8080/go/{shortcode}`, so it will be something like:
```html
<a href="http://localhost:8080/go/Z1RkA1">Visit github</a>
```

As you can see the application will generate short url that contains 6 random generated symbols. 
Each of them can be either uppercase letter, lowercase letter, or digit. 
Now calculating the maximum amount of possible combinations will give us:
```markdown
POSSIBLE_SYMBOLS = 26 (uppercase) + 26 (lowercase) + 10 (digits) = 62
POSSIBLE_COMBINATIONS = N^L (where N is 62, and L is 6 (length)) = 62^6
 = 56,800,235,584 (56 billion combinations)
```
So it is enough for simple service like this.

## Prerequisites
Before running the application, ensure you have the following installed:
* Java (JDK 21)
* Maven
* Git
* Docker & Docker Compose

## How to run:
I assume you have java, maven, git, and docker already installed on your computer

### Clone the repository
First clone the repository on your computer
```bash
git clone https://github.com/adilkhanalimberdi/spring-practice.git
cd "spring-practice/personal-blog"
```

### Run docker image for PostgreSQL DB:
Open terminal or command-prompt and run this command:
```bash
docker compose up -d
```

### Run the application:
In the terminal run this command and wait until it says "Tomcat server started on ...":
```bash
./mvnw spring-boot:run
```

### Check the application:
You can check it with whatever you want, you can check GET requests in the browser.
But it's recommended to use API testing tools like Postman, SoapUI, HTTPie, or any other online tools.

Go to the browser and go to `http://localhost:8080/`, you should see Whitelabel Error Page 
(which is a default fallback screen of Spring Boot app).
This means your application started successfully.

## Work with the API
Now you can test the API with these endpoints

### Create a new Url Record
```http request
POST /shorten
body{
  "url": "https://roadmap.sh/projects/url-shortening-service",
}
```

You should get a:
- 201 CREATED (with created url record in the response body) if the url record was created
- 400 BAD REQUEST if there was any validation error

### Update an existing Url Record
```http request
PUT /shorten/{shortCode}
{
  "url": "https://roadmap.sh",
}
```

You should get a:
- 200 OK (with updated url record in the response body) if the url record was updated
- 400 BAD REQUEST if there was any validation errors
- 404 NOT FOUND if the specified shortCode was not found

### Delete an existing Url Record
```http request
DELETE /shorten/{shortCode}
```

You should get a:
- 204 NO CONTENT if the url record was deleted successfully
- 404 NOT FOUND if the specified shortCode was not found

### Get an existing Url Record
```http request
GET /shorten/{shortCode}
```
You should get a:
- 200 OK (with found url record in the response body) if the url record was found
- 404 NOT FOUND if the specified shortCode was not found

### Get a statistics of an existing Url Record
```http request
GET /shorten/{shortCode}/stats
```

You should get a:
- 200 OK (with found statistics in the response body) if the url record was found
- 404 NOT FOUND if the specified shortCode was not found

### Get an original Url for redirecting
```http request
GET /go/{shortCode}
```

You should get a:
- 302 FOUND (with found original url in the response header) if the url record was found
- 404 NOT FOUND if the specified shortCode was not found


