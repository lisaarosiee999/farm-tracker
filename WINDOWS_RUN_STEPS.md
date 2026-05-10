# Run Farm Tracker on Windows

This guide explains how to download and run the Farm Expense and Profitability Tracker on a Windows laptop.

## 1. Install Required Software

Install these before running the project:

- Java JDK 11 or newer
- Maven
- MySQL Server
- Git

Why this is needed:

- Java runs the web application.
- Maven downloads Java dependencies and starts the local server.
- MySQL stores farms, crop cycles, expenses, and reports.
- Git downloads the project from GitHub.

## 2. Download the Project

Open PowerShell and run:

```powershell
git clone https://github.com/lisaarosiee999/farm-tracker.git
cd farm-tracker
```

What this does:

- `git clone` downloads the project from GitHub.
- `cd farm-tracker` moves PowerShell into the project folder.

## 3. Build the Project

Run:

```powershell
mvn package
```

What this does:

- Maven checks the Java code.
- Maven downloads required libraries.
- Maven builds the web application.

If this command succeeds, the Java project is set up correctly.

## 4. Create the MySQL Database

The project expects a MySQL database named `farmtracker`.

If your MySQL `root` user has no password, run:

```powershell
$env:FARMTRACKER_DB_USER = "root"
$env:FARMTRACKER_DB_PASSWORD = ""
powershell -ExecutionPolicy Bypass -File .\scripts\init-database.ps1
```

If your MySQL `root` user has a password, run this instead:

```powershell
$env:FARMTRACKER_DB_USER = "root"
$env:FARMTRACKER_DB_PASSWORD = "your_mysql_password"
powershell -ExecutionPolicy Bypass -File .\scripts\init-database.ps1
```

Replace `your_mysql_password` with your real MySQL password.

What this does:

- `FARMTRACKER_DB_USER` tells the app which MySQL user to use.
- `FARMTRACKER_DB_PASSWORD` tells the app the MySQL password.
- `init-database.ps1` creates the database tables and adds sample data.

## 5. Start the App

Run:

```powershell
mvn jetty:run
```

What this does:

- Starts a local web server.
- Runs the Farm Tracker app on port `8081`.

Keep this PowerShell window open while using the app.

## 6. Open the App in Browser

Open this link:

```text
http://localhost:8081/Framtracker/index.jsp
```

You should see the Farm Expense and Profitability Tracker dashboard.

## Common Problems

### `mvn` is not recognized

Maven is not installed or not added to PATH. Install Maven and reopen PowerShell.

### `java` is not recognized

Java JDK is not installed or not added to PATH. Install JDK 11 or newer and reopen PowerShell.

### MySQL access denied

The MySQL username or password is wrong. Set the correct password:

```powershell
$env:FARMTRACKER_DB_PASSWORD = "your_mysql_password"
```

Then run the database setup command again.

### Page does not open

Make sure `mvn jetty:run` is still running. The app works only while the local server is running.

