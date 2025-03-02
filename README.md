# Todo List API

A simple RESTful API for managing a To-Do List using **Spring Boot, Hibernate, and MySQL**.

## Features

- Create, Read, Update, and Delete (CRUD) To-Do items
- Uses **Spring Boot** for the REST API
- **Hibernate (JPA)** for ORM
- **MySQL** as the database
- Uses **Spring Data JPA** for repository management

## Technologies Used

- **Java 17**
- **Spring Boot 3.x**
- **Spring Data JPA**
- **Hibernate**
- **MySQL**
- **Maven**

## Prerequisites

Make sure you have the following installed:

- **Java 17** or later
- **Maven**
- **MySQL Server**

## Database Setup

1. Open MySQL and create the database:
   ```sql
   CREATE DATABASE todo_db;
   ```
2. Update `application.properties` with your MySQL credentials:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/todo_db?useSSL=false&serverTimezone=UTC
   spring.datasource.username=root
   spring.datasource.password=root
   ```
   *(Replace **`root`** with your actual MySQL username and password.)*

## How to Run the Application

1. Clone the repository:
   ```sh
   git clone https://github.com/your-username/todo-list-api.git
   cd todo-list-api
   ```
2. Build the project:
   ```sh
   mvn clean install
   ```
3. Run the application:
   ```sh
   mvn spring-boot:run
   ```
4. The API will be available at:
   ```sh
   http://localhost:8080/api/todos
   ```

## API Endpoints

| Method | Endpoint          | Description               |
| ------ | ----------------- | ------------------------- |
| GET    | `/api/todos`      | Get all todos             |
| GET    | `/api/todos/{id}` | Get a specific todo by ID |
| POST   | `/api/todos`      | Create a new todo item    |
| PUT    | `/api/todos/{id}` | Update a todo item        |
| DELETE | `/api/todos/{id}` | Delete a todo item        |

## Example Request Body

### Create a New Todo Item (POST `/api/todos`)

```json
{
  "title": "Buy groceries",
  "description": "Milk, Bread, Cheese"
}
```

### Update a Todo Item (PUT `/api/todos/{id}`)

```json
{
  "title": "Buy groceries",
  "description": "Milk, Bread, Cheese, Eggs"
}
```

## Testing the API

You can test the API using:

- **Postman**
- **cURL**
  ```sh
  curl -X GET http://localhost:8080/api/todos
  ```
## Feedback on using ChatGPT

- **It took about 2 hours to complete the task.** 
- **The code did not run after generation**
- **It better to use specific prompts to get right output.**
- **Challenging was to get all tests run with coverage > 80%.**

## License

This project is licensed under the MIT License.

---



