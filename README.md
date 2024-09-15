# User Access Management

## Project Setup

1. Clone the repository.
2. Navigate to the project directory.
3. Run `mvn clean install` to build the project.
4. Start the application using `mvn spring-boot:run`.
5. The application will start on `http://localhost:8080`.

## Accessing H2 Database

- H2 Console URL: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:h2db`
- Username: `sa`
- Password: `password`

## Initial Admin User

- Username: `admin`
- Password: `admin`

## API Endpoints

- `POST /api/users/create` - Create a new user (Admin only).
- `DELETE /api/users/delete/{id}` - Delete a user by ID (Admin only).
- `GET /api/users/list` - List all users (Admin and User roles).

## Security

- API access is restricted based on user roles.
- Admin users can create and delete users.
- Regular users can only view the user list.