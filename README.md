# GPCInternship Project

This project implements a Java-based service that reads XML data representing product records and provides REST APIs to
interact with this data. It provides endpoints to count the records, list all products, and retrieve a product by its
name in JSON format.

## Getting Started

These instructions will guide you on how to set up and run the project on your local machine.

### Prerequisites

- Java JDK 21: Make sure the Java Development Kit (JDK) is installed to compile and run the Java application. (
  Installation guide: [Java JDK](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html))
- Gradle 8.7: Gradle is used as the build tool for managing dependencies and building the project. (Installation
  guide: [Gradle](https://gradle.org/install/))
- Spring Boot 3.3.0: The project uses Spring Boot for simplifying application configuration and deployment.
- IDE (Integrated Development Environment): While any IDE that supports Java and Gradle can be used, IntelliJ IDEA is
  recommended for the best experience, especially for managing Spring Boot applications. Other suitable alternatives
  include Eclipse and Visual Studio Code.

### Running the Project

To get the project up and running on your local machine, follow these detailed steps:

#### 1. Clone the Repository:

Use Git to clone the repository to your local machine. You can do this from your command line (CLI) by running the
following command:

````
git clone https://github.com/RoachOSL/GPCInternship
````

#### 2. Configure the XML File Path:

Before running the application, ensure that it is configured to point to the correct location of the Products.xml file.
This is set in the application.properties file. If you need to use a different file or location, update this file
accordingly:

- **Open the `application.properties` File:**
  Locate the `application.properties` file in the `src/main/resources/` directory of your project.

- **Modify the `product.file.path` Property:**
  Update the file path as needed to point to your XML file. For example, if your file is located in the `static` folder
  under `resources`, you should set the path as follows:

```properties
# Path to XML file with product data
product.file.path=src/main/resources/static/Products.xml
```

#### 3. Open the Project in Your IDE:

Open your preferred Integrated Development Environment (IDE) that supports Spring Boot (e.g., IntelliJ IDEA, Eclipse,
Visual Studio Code). Use the IDE's feature to open an existing project and navigate to the directory where you cloned
the repository.

#### 4. Navigate to the Main Application File: 
In your IDE’s project explorer, navigate to the GpcInternshipApplication.java file located at:

```
src/main/java/com/gpcinternship/GpcInternshipApplication.java
```

#### 5. Run the Application:

Open the GpcInternshipApplication.java file, which contains the main method that starts the Spring Boot application.
Right-click on the file in your IDE and select Run 'GpcInternshipApplication'. This starts the embedded Apache Tomcat
server.

#### 6. Access the Application:

Once the application is running, open a web browser and navigate to:

```
http://localhost:8080
```

This URL points to the default port (8080) where the Spring Boot application and the embedded Tomcat server are running.
Adjust the URL if your project uses a different port.

### API Endpoints:

#### 1. Get Count of Records:

Description: This endpoint returns the total number of product records found in the XML file.

HTTP Method: GET

Endpoint: "/"

```
http://localhost:8080
```

or

```
http://localhost:8080/
```

Example Response:

```String
Number of records found in file: 10
```

#### 2. List All Products:

Description: Retrieves a list of all products in JSON format.

HTTP Method: GET

Endpoint: "/list"

```
http://localhost:8080/list
```

Example Response:

```json
[
  {
    "id": 1,
    "name": "apple",
    "category": "fruit",
    "partNumberNR": "2303-E1A-G-M-W209B-VM",
    "companyName": "FruitsAll",
    "active": true
  },
  {
    "id": 2,
    "name": "orange",
    "category": "fruit",
    "partNumberNR": "5603-J1A-G-M-W982F-PO",
    "companyName": "FruitsAll",
    "active": false
  }
]

```

#### 3. Get Product by Name:

Description: Returns the details of a product by its name if found; otherwise, it returns a 'Product not found'
message.

HTTP Method: GET

IMPORTANT!

Required Query Parameter: productName - The name of the product to retrieve.
Just replace {Name of your product} with any valid product name in xml file.

Endpoint: /product?productName={Name of your product}

```
http://localhost:8080/product?productName=apple
```

Example Successful Response:

```json
{
  "id": 1,
  "name": "apple",
  "category": "fruit",
  "partNumberNR": "2303-E1A-G-M-W209B-VM",
  "companyName": "FruitsAll",
  "active": true
}
```

Example Not Found Response:

```
Product not found.
```

### Troubleshooting:

If you encounter issues with running the project or there are dependency errors in the code, try invalidating the
IntelliJ IDEA caches:

- Go to `File` > `Invalidate Caches...`
- Select `Invalidate and Restart`.

This should resolve issues related to the project setup in the IDE.
