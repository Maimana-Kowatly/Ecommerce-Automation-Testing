# Ecommerce-Automation-Testing

A comprehensive **Web Automation Testing** project using **Java, Selenium, and TestNG** to automate test cases for **Demoblaze** and **Magento** websites.

## 🚀 Features

- **Automated User Signup** - Validates user registration process.
- **Cart Management** - Adds, updates, and removes items from the cart.
- **Coupon Validation** - Tests discount application with valid/invalid coupons.
- **Wishlist Functionality** - Ensures products can be moved to a wishlist.
- **Test Execution Reports** - Generates reports using **TestNG**.

## 🛠️ Tech Stack

- **Java** (Programming Language)
- **Selenium WebDriver** (Browser Automation)
- **TestNG** (Test Execution & Reporting)
- **Page Object Model (POM)** (Structured Test Design)
- **Maven** (Dependency Management)
- **GitHub** (Version Control & Collaboration)

## 📂 Project Structure

```
📦 web-automation-project
 ┣ 📂 src
 ┃ ┣ 📂 test
 ┃ ┃ ┣ 📂 pages (Page Object Model classes)
 ┃ ┃ ┣ 📂 tests (Test cases using TestNG)
 ┃ ┣ 📜 BaseTest.java
 ┣ 📜 pom.xml (Maven Dependencies)
 ┣ 📜 testng.xml (TestNG Suite Configuration)
 ┣ 📜 README.md
```

## 🚀 Installation & Setup

1. **Clone the repository**
   ```sh
   git clone https://github.com/Maimana-Kowatly/Ecommerce-Automation-Testing.git
   cd Ecommerce-Automation-Testing
   ```

2. **Install dependencies**
   ```sh
   mvn clean install
   ```

3. **Run the tests**
   ```sh
   mvn test
   ```

## 🖥️ Test Execution

- Execute all test cases:
  ```sh
  mvn test
  ```
- Run specific TestNG suite:
  ```sh
  mvn test -DsuiteXmlFile=testng.xml
  ```
- Generate test reports:
  ```sh
  mvn surefire-report:report
  ```

## 📜 Test Cases

### 🛒 User Management
- **LT-001**: Login
- **SU-001**: SignUp

### 🛒 Cart Management
- **TC-001**: Add item to cart
- **TC-002**: Update cart quantity
- **TC-003**: Remove item from cart

### 🎁 Coupon Validation
- **TC-005.1**: Apply valid coupon ✅ *(Skipped)*
- **TC-005.2**: Apply invalid coupon ❌

### 📌 Wishlist In Order  Functionality
- **TC-008.1**: Move to wishlist (Logged-in user)
- **TC-008.2**: Wishlist option disabled (Guest user)

### 📌 Wishlist Functionality
- **Ts-501**: Add Product to wishList
- **Ts-501**: Remove Product from wishList
- **Ts-503**: Empty wishList
- **Ts-504**: Share wishList
- **Ts-505**: Edit wishList


### 📌 Release Report
-[Test Report](https://docs.google.com/document/d/1jNI3xXJvJaJDAEF1pQcrnJbBXtDQnyEJ/edit?usp=sharing&ouid=111404097680853927997&rtpof=true&sd=true)

## 🔍 Bug Tracking & Risks Report
-[Bug Report](https://docs.google.com/spreadsheets/d/1t3RBQqq6bHXGX_sZuBNbo6h-hxkibMREHpQOH_Xqcu8/edit?usp=sharing)


## 🚀 Future Enhancements

- **Parallel Test Execution**
- **Integration with Jenkins CI/CD**
- **API Automation Testing**

