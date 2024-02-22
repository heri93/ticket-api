# Concert Ticket Reservation API

## Overview

The Concert Ticket Reservation API is a simple service for managing concert ticket reservations. It provides endpoints for searching available concerts and booking tickets.

## Tech Stack

- Java (min: JDK 8)
- Spring Boot
- Maven
- Hibernate JPA
- PostgreSQL (min: 16)

## Getting Started

### Prerequisites

- Java Development Kit (JDK) 8 or higher
- Maven
- PostgreSQL database (min: 16)
- Postman (min 10 or higher)

### Setup

1. Clone the repository:

   ```bash
   git clone https://github.com/heri93/ticket-api.git
2. Connfigure the PostgreSQL database by updating the application.properties file with your database details.
   ```path
   /ticket-api/src/main/resources/application.properties
4. Build and run the project (By default, the API can be accessible at `http://localhost:8080`) :
   ```bash
   cd concert-ticket-reservation-api
   mvn spring-boot:run

5. Restore the backup file `ticket-api/db-file/ticket-concert-db.tar`
## API Endpoints
### Search Available Concerts
- Endpoint: `/concerts/search`
- Method: `POST`
- Request body: JSON object
  - `location` (optional): Location of the concert
  - `date` (optional): Date of the concert (format: YYYY-MM-DD)
  - `name` (optional): Name of the concert
  Example JSON Request
   ```json
   {
    "location":"Jakar" ,
    "name":"Bruno",
    "date":"2024-06-05" 
   }
- Response:
`200 OK` with a JSON array of available concerts
`400 Bad Request` for invalid requests
### Book a Ticket
- Endpoint: `/bookings/book`
- Method: `POST`
- Request body: JSON object
  - `customerName` (mandatory): Customer Name that booking ticket
  - `phoneNumber` (mandatory): Phone Number of customer that booking ticket
  - `ticketId` (mandatory): Ticket ID
  Example JSON Request
   ```json
   {
    "customerName": "Charless",
    "phoneNumber": "081828108089809",
    "ticketId": "10"
   }
- Response:
`200 OK` with a JSON array of available concerts
`400 Bad Request` for invalid requests
## Testing
### Unit Test
- Unit tests are available for Concert Service and Booking Service. Run the following command to execute the tests:
   ```bash
    mvn test
### API Test
Import the postman files at `ticket-api/postman`
- Ticket-API-v2.0.postman_collection.json
  or
- Ticket-API-v2.1.postman_collection.json
- All of request scenarios are already to test
