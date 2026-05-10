# Farm Expense and Profitability Tracker

This is a beginner-friendly Java web application using Servlets, JSP, JDBC, MySQL, and a WAR build. It can run on Tomcat 9 or locally with the Maven Jetty plugin.

## Project Folders

- `src/main/java/com/farmtracker/model` has normal Java classes such as `Farm`, `Expense`, `FixedExpense`, `VariableExpense`, `CropCycle`, and `ProfitReport`.
- `src/main/java/com/farmtracker/exception` has `NegativeCostException`, which stops negative expense values.
- `src/main/java/com/farmtracker/dao` has JDBC database code for saving and reading records.
- `src/main/java/com/farmtracker/servlet` has servlet controller classes for form actions.
- `src/main/java/com/farmtracker/util` has the JDBC connection helper.
- `src/main/webapp` has JSP pages, CSS, and `WEB-INF/web.xml`.
- `database/schema.sql` creates the `farmtracker` database tables and adds sample data.
- `scripts` has helper scripts for build, deploy, Tomcat, and database setup.

## Local Settings

- App URL: `http://localhost:8081/Framtracker/index.jsp`
- Database name: `farmtracker`
- Default MySQL username: `root`
- Default MySQL password: `root`

Override database settings with:

```bash
export FARMTRACKER_DB_USER='your_mysql_user'
export FARMTRACKER_DB_PASSWORD='your_mysql_password'
export FARMTRACKER_DB_URL='jdbc:mysql://localhost:3306/farmtracker?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC'
```

For a local MySQL root account with no password, use `FARMTRACKER_DB_PASSWORD=''`.

The local Maven Jetty runner uses port `8081`.

## Run Locally on macOS/Linux

```bash
mvn package
FARMTRACKER_DB_USER=root FARMTRACKER_DB_PASSWORD='' bash scripts/init-database.sh
FARMTRACKER_DB_USER=root FARMTRACKER_DB_PASSWORD='' mvn jetty:run
```

Then open `http://localhost:8081/Framtracker/index.jsp`.

## Run Locally on Windows

Install these first:

- Java JDK 11 or newer
- Maven
- MySQL Server
- Git, if downloading from GitHub with `git clone`

Download and run:

```powershell
git clone https://github.com/lisaarosiee999/farm-tracker.git
cd farm-tracker
mvn package
```

Create the database. If your MySQL `root` user has no password:

```powershell
$env:FARMTRACKER_DB_USER = "root"
$env:FARMTRACKER_DB_PASSWORD = ""
powershell -ExecutionPolicy Bypass -File .\scripts\init-database.ps1
```

If your MySQL `root` user has a password, replace the blank password:

```powershell
$env:FARMTRACKER_DB_USER = "root"
$env:FARMTRACKER_DB_PASSWORD = "your_mysql_password"
powershell -ExecutionPolicy Bypass -File .\scripts\init-database.ps1
```

Start the app:

```powershell
mvn jetty:run
```

Open `http://localhost:8081/Framtracker/index.jsp`.

## VS Code Tasks

Open VS Code, then use:

```text
Terminal > Run Task > Java: Build WAR
Terminal > Run Task > Database: Create Tables
Terminal > Run Task > App: Run Jetty
Terminal > Run Task > Open App in Browser
```

## Common Errors

- `mysql was not found`: MySQL Server is not installed or not added to PATH.
- `Access denied for user 'root'@'localhost'`: your MySQL password is not `root`; set `FARMTRACKER_DB_USER` and `FARMTRACKER_DB_PASSWORD`.
- `Communications link failure`: MySQL Server is not installed or not running on port `3306`.
- `404`: make sure `mvn jetty:run` is still running and use `/Framtracker/index.jsp`.
- `ClassNotFoundException: com.mysql.cj.jdbc.Driver`: run `mvn package` so Maven includes the MySQL connector in the WAR.
