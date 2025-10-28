# Automation-Framework-Project-Swaglabs
Architected and implemented a professional-grade, end-to-end Hybrid Automation Framework utilizing Java, Selenium WebDriver, TestNG, and Maven. Key features include full Page Object Model (POM) compliance, a dedicated Action Driver for reusable methods, Data-Driven Testing (DDT) capabilities, advanced logging (Log4j), and comprehensive reporting (Extent Reports). Specialized in driving high code quality and maximizing test coverage through architectural excellence.

# 🧪 Hybrid Test Automation Framework Structure

This document outlines the directory and file structure for the **Hybrid Framework (Selenium/TestNG/Maven)**, detailing the purpose of each component.

---

## 🏗️ Project Directory Structure

The following structure is used for the framework, organized primarily under the `src` directory as per standard Maven convention.

### **1. Source Code and Main Resources (`src/main/java` & `src/main/resources`)**

This section contains the core logic and configuration files of the framework.

| Path | Description | Type | Key Component |
| :--- | :--- | :--- | :--- |
| **`src/main/java`** | All main framework code, organized into packages. | Directory | Core Framework |
| &nbsp; ├── `com.actiondriver` | **[A1: Action Driver]** Contains utility class for common Selenium actions (e.g., clicks, sendsKeys) to enhance reliability and logging. | Package | Utilities |
| &nbsp; ├── `com.base` | **[A2: Framework Core - Base Class]** Contains the base class for framework setup and teardown (e.g., initializing WebDriver, reading properties). | Package | Core Logic |
| &nbsp; ├── `com.listeners` | **[A3: TestNG Listeners]** Classes for reporting, logging, and handling events like test start/end, including **Screenshots** on failure. | Package | Reporting/Listeners |
| &nbsp; ├── `com.pages` | **[A4: Page Object Model (POM) Pages]** Stores classes representing web pages, housing element locators and page-specific methods. | Package | POM |
| &nbsp; └── `com.utilities` | **[A5: Utility Classes]** General helpers like **Data Providers**, **Excel Reader**, **Logging** (`LoggerManager`), **Extent Report Manager**, and **Retry** mechanisms. | Package | Utilities |
| **`src/main/resources`** | Configuration files for the application and framework. | Directory | Configurations |
| &nbsp; ├── `config.properties` | **[B: Configuration Settings]** Key-value pairs for framework parameters (e.g., browser type, base URL, implicit waits). | File | Settings |
| &nbsp; └── `log4j2.xml` | **[C: Logging Configuration]** Configuration file for the **Log4j2** framework to manage logging levels and output format. | File | Logging |

---

### **2. Test Code and Resources (`src/test/java` & `src/test/resources`)**

This section houses the actual test scripts and the data/configuration required to execute them.

| Path | Description | Type | Key Component |
| :--- | :--- | :--- | :--- |
| **`src/test/java`** | Test code, organized into packages. | Directory | Test Scripts |
| &nbsp; └── `com.test` | **[C1: Test Scripts / Test Classes]** TestNG classes containing `@Test` methods that invoke methods from the POM pages. | Package | Test Scripts |
| **`src/test/resources`** | Resources used by the tests. | Directory | Test Resources |
| &nbsp; ├── `ExtentReport` | **[D1: Output - Extent Report Directory]** Directory where the final `ExtentReport.html` is generated. | Directory | Reporting Output |
| &nbsp; ├── `screenshots` | **[D2: Output - Screenshots Directory]** Location where screenshots are saved, typically on test failure. | Directory | Artifacts |
| &nbsp; ├── `testdata` | **[E: Data Driven Test Data]** Holds data sources like **Excel** files used for data-driven testing. | Directory | Test Data |
| &nbsp; └── `testing.xml` | **[C2: TestNG Execution Suite]** The main XML file used to define, group, and execute test cases. | File | Execution Suite |

---

### **3. Root Directory Files and Output Artifacts**

These files govern the project dependencies and configuration, and directories store the generated output.

| Path | Description | Type | Key Component |
| :--- | :--- | :--- | :--- |
| `pom.xml` | **[F: Project Dependencies & Management (Maven)]** Defines all project dependencies (e.g., Selenium, TestNG, Log4j2) and build life-cycle. | File | Dependency Management |
| `.gitignore` | Specifies intentionally untracked files/directories (like `target`, `logs`, IDE files) to ignore by Git. | File | Version Control |
| **`target`** | **[D4: Output - Maven Build Output]** Default output directory for all compiled classes and build artifacts. | Directory | Build Output |
| **`test-output`** | **[D5: Output - TestNG Default Report]** Default directory for TestNG-generated reports and execution data. | Directory | TestNG Output |
| **`logs`** | **[D3: Output - Log Files]** Directory where the framework's runtime log files are saved (e.g., as configured in `log4j2.xml`). | Directory | Artifacts |
| **`log4j2.xml`** | Configuration file for the **Log4j2** logging framework. | File | Logging Configuration |

---

### **System Dependencies**

* **JRE System Library [Java SE 17]**: The core Java Runtime Environment.
* **Maven Dependencies**: Libraries downloaded and managed by Maven based on the `pom.xml`.
* **TestNG**: The unit testing framework used for test execution and reporting.

<img width="1845" height="769" alt="image" src="https://github.com/user-attachments/assets/94682149-c078-4407-bb92-e9cc610e5f55" />



# 🚀 Hybrid Automation Framework Structure

This project uses a robust **Hybrid Test Automation Framework** leveraging **Selenium**, **TestNG**, and **Maven**.

---

## 🎯 Core Framework Structure

| Path | 💡 Purpose | 🔑 Key Components |
| :--- | :--- | :--- |
| `src/main/java` | The **core brains** of the framework. | ⚙️ **ActionDriver** (Reusable actions), 🧱 **Base Class** (Setup/Teardown), 📝 **Listeners** (Reporting/Screenshots). |
| `src/main/resources` | Framework-level configurations. | ⚙️ `config.properties`, 🪵 `log4j2.xml` (Logging config). |
| `src/test/java` | The **Test Scripts** that drive the automation. | 🧪 `com.test` (Your main test classes). |
| `src/test/resources` | Resources needed for test execution. | 📊 `testdata` (Excel/CSV), 📝 `testing.xml` (TestNG execution suite). |
| `src/main/java/com.pages` | **[A4: POM]** Holds all **Page Object Model** classes. | 🏠 Page classes (e.g., `LoginPage.java`). |
| `src/main/java/com.utilities` | General-purpose helper classes. | 📚 Excel Reader, Extent Manager, Data Provider, Retry Mechanism. |

---

## 📁 Output & Management Files

| Path | ✨ Description | Artifact |
| :--- | :--- | :--- |
| `pom.xml` | **Maven Magic!** Defines all project dependencies (Selenium, TestNG, etc.). | 📦 **Dependencies** |
| `target/` | Maven's default build directory. | 🛠️ **Compiled Classes** |
| `test-output/` | TestNG's default output location. | 📃 **TestNG Report** |
| `logs/` | All runtime logs generated by Log4j2. | 🪵 **Log Files** |
| `src/test/resources/ExtentReport` | Dedicated folder for detailed reporting. | 📈 **ExtentReport.html** |
| `src/test/resources/screenshots` | Stores screenshots captured on test failure. | 📸 **Screenshots** |
| `.gitignore` | Ensures we keep **only code** and ignore outputs like `target` and `logs`. | 🚫 **Version Control** |

---

## 🛠️ Tech Stack

* **Language:** Java 17+
* **Automation:** Selenium WebDriver
* **Testing Framework:** TestNG
* **Build/Dependency:** Maven
* **Reporting:** Extent Reports & TestNG Listeners
* **Logging:** Log4j2
