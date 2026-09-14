# Essay Web

A simple essay management web app built with **Java**, **Spring Boot**, **Thymeleaf**, **Spring Data JPA**, and **MariaDB**.

## Features

- **Input Essay** — save an essay to MariaDB
- **Essay List** — view all saved essays
- **Essay Detail** — click an essay to display it
- **i18n** — switch between English and Chinese (中文)
- **REST API** — JSON endpoints under `/api/essays`

## Tech Stack

- Backend: Java 21 + Spring Boot 3 (Spring MVC + Thymeleaf + Spring Data JPA/Hibernate)
- Frontend: Server-rendered Thymeleaf templates
- Database: MariaDB
- Build: Maven (wrapper included — no Maven install needed)

## Project Structure

```
essay_web/
├── src/main/java/com/example/essayweb/
│   ├── EssayWebApplication.java   # Spring Boot entry point
│   ├── Essay.java                 # JPA entity (essays table)
│   ├── EssayRepository.java       # Spring Data JPA repository
│   ├── EssayController.java       # Web pages (Thymeleaf)
│   ├── EssayApiController.java    # REST API (/api/essays)
│   ├── EssayDtos.java             # API request/response records
│   ├── ApiExceptionHandler.java   # JSON error responses for the API
│   ├── PageModelAdvice.java       # Adds currentUri to page models
│   └── WebConfig.java             # i18n: cookie locale + ?lang= switcher
├── src/main/resources/
│   ├── templates/                 # Thymeleaf pages + shared header fragment
│   ├── static/                    # CSS, favicon, icons
│   ├── messages.properties        # English translations
│   ├── messages_zh.properties     # Chinese translations
│   └── application.properties
├── essay_web.sql                  # Database schema
├── pom.xml
├── .env.example
└── .env
```

## Prerequisites

- JDK 21 or newer
- MariaDB running locally (or a reachable MariaDB instance)

## Database Setup

1. Create a database and user:

```sql
CREATE DATABASE IF NOT EXISTS essay_web
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

CREATE USER IF NOT EXISTS 'essay_app'@'localhost'
  IDENTIFIED VIA mysql_native_password USING PASSWORD('your_app_password');

GRANT ALL PRIVILEGES ON essay_web.* TO 'essay_app'@'localhost';
FLUSH PRIVILEGES;
```

2. Create the `essays` table (or import `essay_web.sql`):

```sql
CREATE TABLE IF NOT EXISTS essays (
  id INT AUTO_INCREMENT PRIMARY KEY,
  title VARCHAR(255) NOT NULL,
  content TEXT,
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
```

## Environment Variables

Copy `.env.example` to `.env` and fill in your values:

```bash
PORT=3001
DB_HOST=localhost
DB_PORT=3306
DB_USER=essay_app
DB_PASSWORD=your_app_password
DB_NAME=essay_web
```

The app loads `.env` automatically (`spring.config.import` in `application.properties`). Real environment variables work too and take precedence.

> `.env` is git-ignored and should never be committed.

## Run

```bash
# Windows
mvnw.cmd spring-boot:run

# Linux/macOS
./mvnw spring-boot:run
```

Then open: http://localhost:3001

The same port serves the web pages, static assets, and the JSON API.

## Production Build

```bash
./mvnw package        # or: mvnw.cmd package on Windows
java -jar target/essay-web-0.0.1.jar
```

## Pages

| Path          | Description              |
| ------------- | ------------------------ |
| `/`           | Essay input form         |
| `/essays`     | List of all essays       |
| `/essays/{id}`| Essay detail             |

Append `?lang=zh` or `?lang=en` to any page to switch language (stored in a cookie).

## API Endpoints

| Method | Path              | Description               |
| ------ | ----------------- | ------------------------- |
| POST   | `/api/essays`     | Create a new essay        |
| GET    | `/api/essays`     | List all essays           |
| GET    | `/api/essays/{id}`| Get one essay by ID       |

### Request body (POST)

```json
{
  "title": "My Essay",
  "content": "Essay body..."
}
```

## i18n

Translations live in `src/main/resources/messages*.properties`. Use the language link in the top navigation to toggle between English and Chinese.

To add another language, create `messages_<lang>.properties` and visit any page with `?lang=<lang>`.
