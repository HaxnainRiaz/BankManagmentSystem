# How to Run the Bank Management System

## Prerequisites

1. **Java JDK 8 or higher** (Check: `java -version`)
2. **MySQL Server** running on localhost:3306
3. **MySQL Connector JAR** (already included in project)

---

## Step-by-Step Instructions

### Step 1: Check Java Installation

Open PowerShell/Command Prompt and verify Java is installed:

```powershell
java -version
javac -version
```

You should see Java version information. If not, install Java JDK first.

---

### Step 2: Start MySQL Server

Make sure MySQL/MariaDB is running:

**Windows:**
- Check if MySQL service is running in Services
- Or start XAMPP/WAMP and start MySQL
- Or run: `net start MySQL` (if installed as service)

**Verify MySQL is accessible:**
```bash
mysql -u root -p
```

---

### Step 3: Set Up Database

#### Option A: Create Database and Import SQL

1. **Create the database** (your code uses `bankproject`):
```sql
CREATE DATABASE bankproject;
USE bankproject;
```

2. **Import the SQL file:**
```bash
mysql -u root -p bankproject < sanbank/database/sanbank.sql
```

Or manually copy and paste the SQL from `sanbank/database/sanbank.sql` into MySQL.

#### Option B: Use phpMyAdmin

1. Open phpMyAdmin (usually http://localhost/phpmyadmin)
2. Create new database: `bankproject`
3. Select the database
4. Go to Import tab
5. Choose file: `sanbank/database/sanbank.sql`
6. Click Go

**Important:** The SQL file creates database `sanbank`, but your code uses `bankproject`. Either:
- Import to `bankproject` database, OR
- Change database name in SQL file from `sanbank` to `bankproject`

---

### Step 4: Compile the Project

Open PowerShell in the project root directory and run:

```powershell
cd sanbank
$mysqlJar = "..\mysql-connector-java-5.1.49\mysql-connector-java-5.1.49\mysql-connector-java-5.1.49-bin.jar"
javac -cp "$mysqlJar" -d "build\classes" src\bank\*.java
```

**Expected output:** Some warnings about deprecated API (this is normal).

---

### Step 5: Run the Application

```powershell
cd sanbank
$mysqlJar = "..\mysql-connector-java-5.1.49\mysql-connector-java-5.1.49\mysql-connector-java-5.1.49-bin.jar"
java -cp "build\classes;$mysqlJar" bank.login
```

The login window should appear!

---

## Login Credentials

Use any of these accounts:

| Username | Password |
|----------|----------|
| `admin` | `12345678` |
| `kobi` | `123` |
| `ram` | `321` |
| `John` | `John` |

---

## Quick Run Scripts

I've created helper scripts to make this easier (see below).

---

## Troubleshooting

### Error: "CommunicationsException" or "Cannot connect to database"

**Solutions:**
1. Check MySQL is running: `mysql -u root -p`
2. Verify database exists: `SHOW DATABASES;`
3. Check database name matches (code uses `bankproject`)
4. Verify MySQL is on port 3306
5. Check if root password is set (code uses empty password)

### Error: "ClassNotFoundException: com.mysql.jdbc.Driver"

**Solution:** Make sure MySQL connector JAR path is correct in the command.

### Error: "Access denied for user 'root'@'localhost'"

**Solution:** 
- If you have a MySQL password, you need to update the connection strings in Java files
- Or create a MySQL user without password: `CREATE USER 'root'@'localhost' IDENTIFIED BY '';`

### Database Name Mismatch

**Problem:** Code uses `bankproject`, SQL creates `sanbank`

**Solution:** 
- Import SQL to `bankproject` database, OR
- Update all Java files to use `sanbank` instead of `bankproject`

---

## Project Structure

```
Java-Bank_Management_System/
├── sanbank/
│   ├── src/bank/          # Source code
│   ├── build/classes/     # Compiled classes (created after compilation)
│   ├── database/          # SQL files
│   └── nbproject/         # NetBeans project files
├── mysql-connector-java-5.1.49/  # MySQL driver
└── README.md
```

---

## Using NetBeans IDE (Alternative)

If you have NetBeans:

1. Open NetBeans
2. File → Open Project
3. Select the `sanbank` folder
4. Right-click project → Build
5. Right-click project → Run

Make sure MySQL connector is added to project libraries.

---

## Next Steps After Login

Once logged in, you can:
- Add customers
- Create accounts
- Deposit/Withdraw money
- Transfer between accounts
- Check balances
- View reports
- Manage admin accounts

