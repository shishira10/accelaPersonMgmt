## PersonManagement
	PersonManagement is a SpringBoot application which has APIs for given requirements that can be invoked through a REST client such as Postman.
  
  All the data are stored in an In-memory SQL DB(H2 DB). Two tables are used to implement the requirements are "Person" and "Address"(https://github.com/shishira10/accelaPersonMgmt/blob/CodingExercise/Requirements.pdf)
  
  Table structure is as below
  
 **Person**
 <img width="964" src="https://github.com/shishira10/accelaPersonMgmt/blob/CodingExercise/Images/Person.png">

**Address**
 <img width="964" src="https://github.com/shishira10/accelaPersonMgmt/blob/CodingExercise/Images/Address.png">


The DB console can be accessed using the link - http://localhost:8443/h2-console (username=AccelaAdmin, password=pass123)

Sample data is as below
 <img width="964" src="https://github.com/shishira10/accelaPersonMgmt/blob/CodingExercise/Images/PersonAndAddress.png">

## Steps to run the code
1. Download source code and Import into an IDE (Ex: Eclipse) and run it as Spring boot app
	- Command to run: "clean install spring-boot:run"
2. Download the source code and run either of the below mentioned commands
	- mvn clean install spring-boot:run (To run with test cases and check their coverage)
	- mvn clean install -DskipTests=true spring-boot:run (To skip the test cases)

## API Details
1. Postman collection - https://github.com/shishira10/accelaPersonMgmt/blob/CodingExercise/Accela.postman_collection.json
	- Please export the collection as is to get pre configured API requests (For all address types and variations)
2. URL details with sample request and response - 
