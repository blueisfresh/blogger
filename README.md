# Blogger API

This is my version of the Blogger API, built using Spring Boot. This project follows the requirements outlined in the [Blogging Platform API project on roadmap.sh](https://roadmap.sh/projects/blogging-platform-api).

---

## 🚀 Project Overview

This API provides endpoints for managing blogs and their associated tags. It demonstrates a RESTful service using Spring Boot, Spring Data JPA, and a SQLite database.

**Key Features:**
*   Create, Read, Update, Delete (CRUD) operations for blogs.
*   Manage tags associated with blogs.
*   Uses DTOs for clear API contracts and separation of concerns.
*   Data validation using Jakarta Bean Validation.
*   Database interaction via Spring Data JPA and Hibernate.

**Technologies Used:**
*   **Java 17**
*   **Spring Boot 3.x**
    *   Spring Web (RESTful API)
    *   Spring Data JPA (Database access)
    *   Spring Validation (Request body validation)
*   **SQLite** (for easy local setup)
*   **Lombok** (for boilerplate code reduction)
*   **ModelMapper** (for DTO to Entity mapping)
*   **SpringDoc OpenAPI UI** (for API documentation - Swagger UI)

---

## ▶️ How to Run the Project Locally

Follow these steps to get the Blogger API up and running on your local machine.

### Prerequisites

Before you begin, ensure you have the following installed:
*   **Java Development Kit (JDK) 17 or higher**
*   **Apache Maven** (if not using an IDE with built-in Maven support)
*   A Java IDE like **IntelliJ IDEA**, **Eclipse**, or **VS Code with Java Extensions** (recommended)

### Steps

1.  **Clone the Repository:**
    ```bash
    git clone https://github.com/YourGitHubUsername/your-repo-name.git
    cd your-repo-name
    ```
    *(Replace `YourGitHubUsername` and `your-repo-name` with your actual GitHub details.)*

2.  **Build the Project:**
    Open the project in your IDE. Your IDE should automatically detect the Maven project and download dependencies.
    Alternatively, using the command line:
    ```bash
    mvn clean install
    ```

3.  **Run the Application:**
    *   **From your IDE:** Locate the `BloggerApplication.java` file (e.g., in `src/main/java/com/blueisfresh/blogger/BloggerApplication.java`) and run its `main` method.
    *   **From the command line (after building):**
        ```bash
        java -jar target/blogger-api-0.0.1-SNAPSHOT.jar # Adjust filename if necessary
        ```

4.  **Access the API:**
    The application will start on `http://localhost:8080` by default.

    *   **API Base URL:** `http://localhost:8080/api/blog`
    *   **Swagger UI (API Documentation):** You can explore and test the API endpoints using Swagger UI:
        `http://localhost:8080/swagger-ui.html`
        (Or `http://localhost:8080/swagger-ui/index.html` depending on SpringDoc version)

---

## 📚 API Endpoints

Here's a quick overview of the main API endpoints:

| Method | Endpoint      | Description                          | Request Body       | Response Body      |
| :----- | :------------ | :----------------------------------- | :----------------- | :----------------- |
| `GET`  | `/api/blog`   | Retrieve a list of all blogs         | None               | `List<BlogResponseDto>` |
| `GET`  | `/api/blog/{id}` | Retrieve a single blog by ID         | None               | `BlogResponseDto`  |
| `POST` | `/api/blog`   | Create a new blog                    | `BlogCreateDto`    | `BlogResponseDto`  |
| `PUT`  | `/api/blog/{id}` | Update an existing blog by ID        | `BlogUpdateDto`    | `BlogResponseDto`  |
| `DELETE`|`/api/blog/{id}` | Delete a blog by ID                  | None               | No Content (204)   |

*(You can expand on `BlogResponseDto`, `BlogCreateDto`, and `BlogUpdateDto` structures here if you want to provide more detail without requiring users to check Swagger UI.)*

---

## 🌐 Project Page URL (for submission)

**Your GitHub Repository:**
[https://github.com/YourGitHubUsername/your-repo-name](https://github.com/YourGitHubUsername/your-repo-name)

---

## 🤝 Contributing

Feel free to open issues or submit pull requests if you have suggestions for improvements or find any bugs.

---

## License

*(If you have a license, state it here. e.g., MIT License)*
