# How to Run the Bank Management System

## Prerequisites

1. **Java JDK 8 or higher** (Tested with Java 17)
2. **MySQL Server** (or MariaDB) running on localhost:3306
3. **MySQL Connector JAR** (already included in project)

## Step 1: Database Setup

### Option A: Use `bankproject` database (matches code)
```sql
CREATE DATABASE bankproject;
USE bankproject;
-- Then import the SQL file, but change database name from 'sanbank' to 'bankproject'
```

### Option B: Update code to use `sanbank` database
Update all connection strings in Java files from:
```java
"jdbc:mysql://localhost:3306/bankproject"
```
to:
```java
"jdbc:mysql://localhost:3306/sanbank"
```

### Import Database Schema
```bash
mysql -u root -p bankproject < sanbank/database/sanbank.sql
```

**Note:** The SQL file creates database `sanbank`, but code uses `bankproject`. You need to align them.

## Step 2: Compile the Project

```powershell
cd sanbank
$mysqlJar = "..\mysql-connector-java-5.1.49\mysql-connector-java-5.1.49\mysql-connector-java-5.1.49-bin.jar"
javac -cp "$mysqlJar" -d "build\classes" src\bank\*.java
```

## Step 3: Run the Application

```powershell
cd sanbank
$mysqlJar = "..\mysql-connector-java-5.1.49\mysql-connector-java-5.1.49\mysql-connector-java-5.1.49-bin.jar"
java -cp "build\classes;$mysqlJar" bank.login
```

## Default Login Credentials

From the database SQL file:
- **Username:** `kobi` | **Password:** `123`
- **Username:** `ram` | **Password:** `321`
- **Username:** `John` | **Password:** `John`

## Troubleshooting

### "CommunicationsException" Error
- Ensure MySQL server is running
- Check if MySQL is on port 3306
- Verify database exists and is accessible

### "Access denied" Error
- Check MySQL root password
- Update connection string if password is set
- Verify user has permissions on database

### Database Name Mismatch
- Code expects: `bankproject`
- SQL creates: `sanbank`
- **Solution:** Either rename database or update code

## Project Structure

```
sanbank/
├── src/bank/          # Source code
├── build/classes/     # Compiled classes
├── database/          # SQL schema file
└── nbproject/         # NetBeans project files
```

