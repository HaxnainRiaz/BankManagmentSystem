# Quick Setup Guide - Add Admin Account

## Admin Account Credentials
- **Username:** `admin`
- **Password:** `12345678`

## Option 1: If Database Already Exists

Run this SQL command in your MySQL client:

```sql
USE bankproject;  -- or USE sanbank; depending on your database name

INSERT INTO `user` (`username`, `password`) VALUES ('admin', '12345678');
```

Or use the provided script:
```bash
mysql -u root -p bankproject < sanbank/database/add_admin.sql
```

## Option 2: Fresh Database Setup

Use the complete setup script that includes the admin account:

```bash
mysql -u root -p < sanbank/database/setup_database.sql
```

This will:
- Create the `bankproject` database (matching your code)
- Create all required tables
- Insert sample data including the admin account

## Option 3: Manual SQL Execution

1. Open MySQL command line or phpMyAdmin
2. Select/create database `bankproject`
3. Run the SQL from `sanbank/database/sanbank.sql` (already updated with admin account)
4. Or just run:
   ```sql
   INSERT INTO `user` (`username`, `password`) VALUES ('admin', '12345678');
   ```

## Verify Admin Account

After adding, verify with:
```sql
SELECT * FROM `user` WHERE username = 'admin';
```

You should see:
```
id | username | password
4  | admin    | 12345678
```

## Important Note About Database Name

Your Java code uses `bankproject` as the database name. Make sure:
- Database is named `bankproject`, OR
- Update all Java connection strings from `bankproject` to your actual database name

## Test Login

1. Start the application
2. Enter username: `admin`
3. Enter password: `12345678`
4. Click Login

---

**All existing accounts still work:**
- kobi / 123
- ram / 321
- John / John

