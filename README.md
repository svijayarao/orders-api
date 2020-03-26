## Orders API

### Implementation

- Tried to keep the solution simple based on the assumptions below and implemented using Spring Boot and Camel
- Added integration tests to test the end to end solution as per the requirement.
- Added unit tests for service classes but not for the controller layer due to time constraints.
- Few improvements to add are swagger doc, full unit test coverage.

### Endpoints
- POST /orders-api/orders 
- GET  /orders-api/orders

### Assumptions and Scope for Implementation

- Based on the task description, following assumptions are made in the implementation

 1. The task is intended to understand the knowledge of original skill set and along with Camel
 2. Whether he know the concepts but not expecting a complete perfect solution.

### Instructions to install and start the application

Clone the application into a folder
 
        git@github.com:svijayarao/orders-api.git
        
Testing the Application using Integration test
    
    Open the project in any IDE(Eclipse or Intellij)
    Run the test class OrderResourceITest
        
Starting the application as stand alone application using the jar

    cd orders-api        
    mvn clean package
    java -jar target/orders-api.jar

Starting the application using docker image
        
    docker run -d -p 7075:7075 svijayarao/orders-api:1.0.0
        
###Testing the Application

Once the application is started use Postman or any other REST client using the data below

####POST http://localhost:7075/orders-api/orders

- Successfully creates an Order 
- Returns 201 Created
    
         {
             "orderDesc": "iPhone X",
             "itemSku": "1004",
             "numberOfUnits": 2
         }
     
- Fails to create Order as the downstream service did not have corresponding Item
- Returns error response with http status code 200 (It can be 404 but the resource that is not found is in the downstream application, we usually do it this way).

        {
            "orderDesc": "iPhone X",
            "itemSku": "invalidSku",
            "numberOfUnits": 2
        }
    
- Fails to create Order as the order request is invalid. 
- Returns 400 Bad Request

        {
            "orderDesc": "iPhone X",
            "numberOfUnits": 2
        }
        
#### Added a GET endpoint for testing from Postman and added a simple integration test for the same in OrderResourceITest
- GET http://localhost:7075/orders-api/orders

       
