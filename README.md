# FXApp - Currency Conversion API

A robust Spring Boot application that enables currency conversion, exchange rate retrieval, and viewing transaction history with a clean RESTful API.

## :clipboard: Features

- **Currency Conversion**: Convert between different currencies using real-time exchange rates
- **Exchange Rate Lookup**: Get current exchange rates between any two supported currencies
- **Transaction History**: View past conversions with filtering and pagination
- **Interactive API Documentation**: Explore and test the API through Swagger UI
- **Containerization**: Easy deployment with Docker

## :rocket: Quick Start

### Prerequisites

- Docker and Docker Compose
- Make (for using the convenience commands)

### Running the Application

Clone the repository and start the application using the provided Makefile:

```bash
# Build the Docker image
make build

# Run the application
make run
```
## :books: API Documentation

### Interactive Swagger Documentation

Access the interactive API documentation at: 
```bash
http://localhost:8080/swagger-ui.html
```

This provides a full interactive interface where you can:
- Browse all available endpoints
- See request/response schemas
- Execute test requests directly from your browser
- View detailed information about each endpoint


## :gear: Configuration

The application uses an external exchange rate API (Fixer.io) that requires an API key.  
The key is read from an environment variable:
```bash
FIXER_API_KEY=your_api_key_here
```

- Set it in a `.env` file in the project root
- The Docker setup automatically picks this up


## :globe_with_meridians: Development

For development purposes, you can access the H2 database console at:
```bash
http://localhost:8080/h2-console
```

Connection details:
- JDBC URL: `jdbc:h2:mem:testdb`
- Username: `sa`
- Password: ` ` (empty)

## :page_facing_up: License

This project is licensed under the MIT License - see the LICENSE file for details.

