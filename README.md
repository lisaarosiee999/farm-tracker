# Farm Expense and Profitability Tracker

This is a beginner-friendly Java web application using Servlets, JSP, JDBC, MySQL, and Apache Tomcat 9.

## Project Folders

- `src/com/farmtracker/model` has normal Java classes such as `Farm`, `Expense`, `FixedExpense`, `VariableExpense`, `CropCycle`, and `ProfitReport`.
- `src/com/farmtracker/exception` has `NegativeCostException`, which stops negative expense values.
- `src/com/farmtracker/dao` has JDBC database code for saving and reading records.
- `src/com/farmtracker/servlet` has servlet controller classes for form actions.
- `src/com/farmtracker/thread` has the report thread used for farm reports.
- `WebContent` has JSP pages, CSS, `WEB-INF/web.xml`, compiled classes, and the MySQL JDBC jar.
- `database/schema.sql` creates the `farmtracker` database tables and adds sample data.
- `scripts` has helper PowerShell scripts for compile, deploy, Tomcat, and database setup.

## Local Settings

- Tomcat home: `C:\apache-tomcat-9.0.117`
- App URL: `http://localhost:8081/Framtracker/index.jsp`
- Database name: `farmtracker`
- MySQL username: `root`
- MySQL password: `root`

Tomcat 9 uses port `8081` because another Tomcat process was already using `8080`.

## VS Code Tasks

Open VS Code, then use:

```text
Terminal > Run Task > Java: Compile
Terminal > Run Task > Database: Create Tables
Terminal > Run Task > Tomcat: Start
Terminal > Run Task > Tomcat: Deploy Framtracker
Terminal > Run Task > Open App in Browser
```

## Common Errors

- `mysql.exe was not found`: MySQL Server is not installed or not added to PATH.
- `Communications link failure`: MySQL Server is not installed or not running on port `3306`.
- `404`: Run `Tomcat: Deploy Framtracker`.
- `ClassNotFoundException: com.mysql.cj.jdbc.Driver`: Check that `WebContent/WEB-INF/lib/mysql-connector-j-8.4.0.jar` exists.
