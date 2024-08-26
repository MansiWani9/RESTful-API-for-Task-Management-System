# RESTful-API-for-Task-Management-System
Task Management System: A Java Spring Boot app with MySQL for task management. Features user authentication (JWT), task CRUD, filtering, searching, and pagination. Dockerized for consistent deployment. Includes CI/CD with GitHub Actions. See Swagger UI for API docs.

**Task Management System**

**Features**

- **User Registration and Authentication:**
Users can register with a username and password.
Passwords are securely hashed.
Users can log in and receive a JWT token for authenticated requests.

- **CRUD Operations :** Create, Read, Update, and Delete tasks.

- **Task Fields:** Each task includes an ID, title, description, status (e.g., Todo, In Progress, Done), priority, due date, and user ID (for task assignment).

- **Task Assignment:** Tasks can be assigned to specific users.

**Filtering and Searching**

- **Filtering:** Filter tasks based on status, priority, and due date.
- **Searching:** Search tasks by title or description.

**Pagination:** Tasks are paginated to handle large lists efficiently.

**Dockerization**

The application is containerized using Docker.
A Dockerfile and docker-compose.yml are provided to set up the application and its dependencies.

**CI/CD Pipeline:**

Integrated CI/CD pipeline using GitHub Actions for running tests and deploying the application.


**Prerequisites**

- Java 17
  
- Maven
  
- MySQL Database

**Setup**

- **Clone the Repository:**
git clone https://github.com/MansiWani9/task-management-system.git
cd task-management-system

- **MySQL:**

Create a database named task_management_db.

Configure database connection details in src/main/resources/application.properties.

- **Build and Run the Application**
mvn clean install

mvn spring-boot:run
The application will be accessible at http://localhost:8080.

**Approach and Assumptions**

**Approach**

**Framework and Technologies:**


- **Spring Boot:** Chosen for its rapid development features, built-in support for RESTful APIs, and ease of integration with various technologies.
  
- **MySQL:** Selected as the relational database to manage user and task data due to its robustness and support for structured data.
  
- **Springdoc OpenAPI:** Integrated for generating interactive API documentation using Swagger, allowing easy exploration and testing of API endpoints.
  
- **Docker:** Utilized for containerization to ensure a consistent development and deployment environment across different systems.
  
- **CI/CD with GitHub Actions** Implemented to automate the testing and deployment processes, ensuring consistent and reliable application delivery.
- 
**Application Structure:**

- **User Management:** Includes secure user registration and authentication using JWT (JSON Web Tokens) to handle user sessions and permissions.

- **Task Management:** Provides comprehensive CRUD (Create, Read, Update, Delete) operations for tasks, along with features to assign tasks to users, filter tasks based on various criteria, and search tasks by title or description.

- **Pagination:** Implemented to manage and display large task lists efficiently, enhancing the user experience and performance.

**Documentation and Testing:**

- **Swagger UI:** Offers a user-friendly interface for interacting with the API and reviewing its documentation.
  
- **Unit and Integration Tests:** Included to ensure the reliability and functionality of the application’s components and endpoints.

**Assumptions**

- **Environment:** Java 17, Maven, and MySQL are installed and configured on the local machine.
      
- **Database Configuration:** Database credentials and other configurations are set correctly in application.properties.

- **Security:** JWT secret key management and other sensitive configurations are handled securely.
- **Development Setup:** Developers and users have the necessary tools and access rights to clone the repository, build the application, and run it locally or in a containerized environment.


**API Documentation**

**Swagger Integration**

This project uses Springdoc OpenAPI for Swagger-based API documentation. Follow these steps to access the API documentation:

**1 Run the Application:**
  Ensure your Spring Boot application is running by executing
  
  mvn spring-boot:run
  
**2 Access Swagger UI:** Open your browser and navigate to: http://localhost:8080/swagger-ui.html

This interactive interface allows you to explore and test the API endpoints.

**3 View OpenAPI JSON:** You can also view the raw OpenAPI documentation at: http://localhost:8080/v3/api-docs

**Example Documentation**

Swagger automatically generates documentation for your API endpoints.

For instance, the GET /api/tasks endpoint will be documented as follows:

**GET /api/tasks**

- **Description** Retrieves a list of tasks.

- **Responses:**
  
- **200 OK:** Returns a list of tasks.
  
- **Response Schema:**
  
  [
    {
        "id": 1,
        "title": "Sample Task",
        "description": "This is a sample task",
        "status": "Todo",
        "priority": "High",
        "dueDate": "2024-08-26"
    }
]










**License**

MIT License. See the LICENSE file for details.

**Acknowledgments**

Special thanks to the Spring Boot and Docker communities for their resources and tools.
