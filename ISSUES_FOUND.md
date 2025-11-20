# Issues Found During Testing

## Critical Issues

### 1. Database Name Mismatch ⚠️
**Issue:** Code uses `bankproject` but SQL file creates `sanbank`

**Location:** All Java files in `sanbank/src/bank/`

**Impact:** Application cannot connect to database if names don't match

**Solution:** 
- Option 1: Rename database in SQL file from `sanbank` to `bankproject`
- Option 2: Update all connection strings in Java files from `bankproject` to `sanbank`

**Files Affected:** All 10 Java files with database connections

---

### 2. SQL Schema Mismatch in user1.java ⚠️
**Issue:** Code tries to insert `name` column that doesn't exist in `user` table

**Location:** `sanbank/src/bank/user1.java` line 219

**Code:**
```java
insert = con1.prepareStatement("insert into user (name,username,password)values(?,?,?)");
```

**SQL Schema:**
```sql
CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL
)
```

**Problem:** The `user` table doesn't have a `name` column, only `id`, `username`, and `password`.

**Impact:** Adding new admin users will fail with SQL error

**Solution:** 
- Remove `name` parameter from INSERT statement, OR
- Add `name` column to `user` table in database schema

---

## Minor Issues

### 3. Error Handling
**Issue:** Database connection errors only print to console, not shown to user

**Location:** Multiple files (login.java, etc.)

**Example:** `login.java` line 165:
```java
catch (Exception e) {
    System.out.println("Failed " + e);
}
```

**Impact:** Users won't see helpful error messages if database is unavailable

**Recommendation:** Show error in JOptionPane dialog

---

### 4. Hardcoded Database Credentials
**Issue:** Database connection details hardcoded throughout codebase

**Location:** All Java files

**Security Concern:** 
- Root user with empty password
- Connection string hardcoded

**Recommendation:** 
- Move to configuration file
- Use environment variables
- Implement proper password management

---

### 5. Deprecated MySQL Driver
**Issue:** Uses deprecated `com.mysql.jdbc.Driver`

**Location:** All Java files

**Current:**
```java
Class.forName("com.mysql.jdbc.Driver");
```

**Recommended:**
```java
Class.forName("com.mysql.cj.jdbc.Driver");
```

**Note:** Works with current connector, but will be removed in future versions

---

## Testing Status

✅ **Compilation:** Successful
- All 51 class files compiled
- No compilation errors
- Minor warnings (deprecated API, unchecked operations)

⚠️ **Runtime:** Requires Database Setup
- Application launches
- Cannot connect to database (MySQL not running/configured)
- Code structure is correct

---

## Recommendations for Production

1. **Fix database name mismatch** (Critical)
2. **Fix user table INSERT statement** (Critical)
3. **Add proper error dialogs** (Important)
4. **Move credentials to config file** (Security)
5. **Update to new MySQL driver** (Future-proofing)
6. **Add input validation** (Data integrity)
7. **Implement password hashing** (Security)
8. **Add connection pooling** (Performance)

---

## Quick Fixes Needed

### Fix 1: Database Name
Update SQL file or Java connection strings to match.

### Fix 2: user1.java INSERT
Change line 219 from:
```java
insert = con1.prepareStatement("insert into user (name,username,password)values(?,?,?)");
insert.setString(1,name);
insert.setString(2,user);
insert.setString(3,password);
```

To:
```java
insert = con1.prepareStatement("insert into user (username,password)values(?,?)");
insert.setString(1,user);
insert.setString(2,password);
```

And remove `txtname` field usage, or add `name` column to database.

