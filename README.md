# Gateway Management REST API

## Introduction

This project implements a REST service for managing gateways - master devices that control multiple peripheral devices. The service allows storing information about gateways and their associated devices in a database and provides a frontend for interacting with the data.

## Project Structure

gateway-management-service/
|-- src/
|   |-- main/
|       |-- java/
|           |-- com.engineerLee.gatewaymanagement/
|               |-- GatewayManagementApplication.java
|               |-- controller/
|                   |-- GatewayController.java
|               |-- model/
|                   |-- Gateway.java
|                   |-- PeripheralDevice.java
|               |-- service/
|                   |-- GatewayService.java
|                   |-- GatewayServiceImpl.java
|                   |-- PeripheralDeviceService.java
|                   |-- PeripheralDeviceServiceImpl.java
|               |-- repository/
|                   |-- GatewayRepository.java
|                   |-- PeripheralDeviceRepository.java
|       |-- resources/
|           |-- application.properties
|-- test/
|   |-- java/
|       |-- com.example.gatewaymanagement/
|           |-- controller/
|               |-- GatewayControllerTest.java
|           |-- service/
|               |-- GatewayServiceTest.java
|               |-- PeripheralDeviceServiceTest.java
|           |-- repository/
|               |-- GatewayRepositoryTest.java
|               |-- PeripheralDeviceRepositoryTest.java
|-- README.md


## Requirements

### Functional Requirements

- **UI Requirement:** There is a need for a UI.
- **Peripheral Device Limit:** Prevent a gateway from receiving more than 10 peripheral devices.

### Non-functional Requirements

- **Data Format:** Input/output data must be in JSON format.
- **Build and Run:** Project must be buildable and runnable.
- **Documentation:** Project must have a README file with build/run/test instructions.
- **Database:** Use a local or free database (e.g., in-memory or containerized).
- **Unit Tests:** Include unit tests for the implemented functionalities.
- **Framework:** Use a popular, up-to-date, and long-term supported framework of your choice.

## Technologies Used

- [Spring Boot](https://spring.io/projects/spring-boot): Framework for building Java-based applications.
- [MySQL](https://www.mysql.com/): A popular open-source relational database management system.

## Setup Instructions

1. **Clone the Repository:**
   ```bash
 HTTPS:  
git clone  https://github.com/engineerLiberty/gateway-management-system.git

 SSH:    
 git clone  git@github.com:engineerLiberty/gateway-management-system.git
 
cd gateway-management-service

## Build the Project:

./mvnw clean install

## Run the Application:
./mvnw spring-boot:run

## Access the API:
Open your web browser and go to http://localhost:8300/swagger-ui.html to access the Swagger UI for the API documentation.

## API Endpoints
#### GET /gateways: Get information about all stored gateways and their devices.
#### GET /gateways/{serialNumber}: Get details about a single gateway.
#### POST /gateways: Store a new gateway.
#### DELETE /gateways/{serialNumber}: Delete a gateway.
#### POST /gateways/{serialNumber}/devices: Add a peripheral device to a gateway.
#### DELETE /gateways/{serialNumber}/devices/{deviceId}: Remove a peripheral device from a gateway.

## Examples
### Storing a Gateway

curl -X POST "http://localhost:8300/gateways" -H "Content-Type: application/json" -d '{
"serialNumber": "GW123",
"name": "Main Gateway",
"ipAddress": "192.168.1.1"
}'

### Adding a Peripheral Device
curl -X POST "http://localhost:8300/gateways/GW123/devices" -H "Content-Type: application/json" -d '{
"vendor": "VendorX",
"dateCreated": "2023-01-01T00:00:00Z",
"online": true
}'

## Testing
### Run unit tests using the following command:
./mvnw test

## Contributing
Feel free to contribute to the project by submitting issues or pull requests.



