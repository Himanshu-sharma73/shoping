# Project Name 
    Shopping

## Getting Started
To get started with this project, you will need to have the following installed on your local machine:

* JDK 17+
* Maven 3+


To build and run the project, follow these steps:

* Clone the repository: `git clone https://github.com/Himanshu-sharma73/shopping.git`
* Navigate to the project directory: cd shopping
* Add database "shopping_db" to MySQL 
* Build the project: mvn clean install
* Run the project: mvn spring-boot:run 

-> The application will be available at http://localhost:8081.

***If you want to use this projet with JWT token please switch to jwtAuth2 
  to use this branch you have to run the below commond  
    INSERT INTO roles(name) VALUES('ROLE_USER');
    INSERT INTO roles(name) VALUES('ROLE_MODERATOR');
    INSERT INTO roles(name) VALUES('ROLE_ADMIN');

## Swagger Documentation

The API is documented using Swagger. You can access the Swagger documentation (http://localhost:8081/swagger-ui.html).   ** 



Contributing
We welcome contributions! If you want to contribute to this project, please fork the repository and follow these guidelines:

Clone the forked repository to your local machine.
Create a new branch for your feature or bug fix.
Make your changes and commit them with clear and concise commit messages.
Push your changes to your forked repository.
Submit a pull request to the original repository.
We appreciate your contributions!
