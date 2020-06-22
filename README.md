## PersonManagement
	PersonManagement is a Spring WebFlux project to manage persons and their addresses. 
	Detailed requirements can be found at https://github.com/shishira10/accelaPersonMgmt/blob/CodingExercise/Requirements.pdf
	Current active branch and the branch to be used is - CodingExercise
	
  All the data related to person and their corresponding addresses will stored in an In-memory SQL DB(H2 DB). Two tables are used to implement the requirements and they are "Person" and "Address".
  
  Table structure is as below
  
## **Person**
 <img width="500" src="https://github.com/shishira10/accelaPersonMgmt/blob/CodingExercise/Images/Person.png">

## **Address**
 <img width="500" src="https://github.com/shishira10/accelaPersonMgmt/blob/CodingExercise/Images/Address.png">


The DB console can be accessed using the link - http://localhost:8443/h2-console (username=AccelaAdmin, password=pass123)

Sample data is as below
 <img width="1000" src="https://github.com/shishira10/accelaPersonMgmt/blob/CodingExercise/Images/PersonAndAddress.png">

## Steps to run the code
1. Download source code and Import into an IDE (Ex: Eclipse) and run it as Spring boot app
	- Command to run: "clean install spring-boot:run"
2. Download the source code and run either of the below mentioned commands
	- mvn clean install spring-boot:run (To run with test cases and check their coverage)
	- mvn clean install -DskipTests=true spring-boot:run (To skip the test cases)

## API Details
1. Postman collection - https://github.com/shishira10/accelaPersonMgmt/blob/CodingExercise/Accela.postman_collection.json
	- Please export the collection as is to get pre configured API requests (For all address types and variations)
2. URL details with sample request and response - https://github.com/shishira10/accelaPersonMgmt/blob/CodingExercise/API%20Doc

## Additional points to note
	- A generic response object with fields "status", "errorMessage" and "apiName" is used for all DML operations to provide a flexibility of error management at web layer. "errorMessage" can be configured easily in the code to throw pre defined errors or to generalise scenarios.
	- For the purpose of storing multiple addresses for a person. There are 4 valid addressTypes i.e. shippingAddress, billingAddress, homeAddress, officeAddress. Addresses other than these types are not allowed and adding a new addressType will be a minor change.
	
