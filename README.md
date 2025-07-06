# GoREST-API-Automation-Framework
A comprehensive test automation framework for the GoREST API (https://gorest.co.in) built with RestAssured and TestNG.

## 🛠️ Technologies Used
- Java 17
- RestAssured 5.5.0
- TestNG 7.10.2
- Jackson Databind 2.18.0
- JavaFaker for test data generation
## ✨ Features
- Complete CRUD operation testing
- Positive and negative test scenarios
- Dynamic test data generation
- Proper test isolation and cleanup
- Comprehensive validation of responses
- Reusable request methods

## 🚀 Getting Started
1. Clone the repository
2. Ensure Java 17+ is installed
3. Set your API token in environment variables or in `Builder.java`
4. Run tests using TestNG (`testng.xml`)

## 🔧 Configuration
- Update `base_url` in `Builder.java` if needed
- Configure test data in `Builder.java`
- Modify `testng.xml` to select test classes

## 📊 Test Coverage
- User endpoints: Create, Read, Update, Delete
- Post endpoints: Create, Read, Update, Delete
- Todo endpoints: Create, Read, Update



