package bank;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Utility class that provides simple file-based persistence for credentials,
 * transactions, and reports. This acts as a fallback mechanism when the
 * database is unavailable.
 */
public final class FileStorage {

    private static final Path BASE_DIR = Paths.get("fallback_data");
    private static final Path CREDENTIALS_FILE = BASE_DIR.resolve("credentials.txt");
    private static final Path TRANSACTIONS_FILE = BASE_DIR.resolve("transactions.txt");
    private static final Path REPORTS_FILE = BASE_DIR.resolve("reports.txt");
    private static final DateTimeFormatter TIMESTAMP_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    static {
        initialize();
    }

    private FileStorage() {
    }

    private static void initialize() {
        try {
            Files.createDirectories(BASE_DIR);
            createFileIfMissing(CREDENTIALS_FILE);
            createFileIfMissing(TRANSACTIONS_FILE);
            createFileIfMissing(REPORTS_FILE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void createFileIfMissing(Path file) throws IOException {
        if (Files.notExists(file)) {
            Files.createFile(file);
        }
    }

    public static synchronized void appendCredential(String username, String password) {
        if (username == null || password == null || username.trim().isEmpty() || password.trim().isEmpty()) {
            return;
        }
        if (credentialExists(username.trim())) {
            return;
        }
        String record = username.trim() + "," + password.trim() + System.lineSeparator();
        try {
            Files.write(CREDENTIALS_FILE, record.getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean credentialExists(String username) {
        if (username == null || username.trim().isEmpty()) {
            return false;
        }
        return isCredentialValid(username.trim(), null, false);
    }

    public static boolean isCredentialValid(String username, String password) {
        return isCredentialValid(username, password, true);
    }

    private static boolean isCredentialValid(String username, String password, boolean requirePassword) {
        if (username == null || username.trim().isEmpty()) {
            return false;
        }
        try {
            List<String> lines = Files.readAllLines(CREDENTIALS_FILE, StandardCharsets.UTF_8);
            for (String line : lines) {
                String[] parts = line.split(",", 2);
                if (parts.length < 1) {
                    continue;
                }
                String storedUser = parts[0].trim();
                String storedPass = parts.length > 1 ? parts[1].trim() : "";
                if (storedUser.equalsIgnoreCase(username.trim())) {
                    if (!requirePassword) {
                        return true;
                    }
                    if (password != null && storedPass.equals(password.trim())) {
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static synchronized void logTransaction(String type, String message) {
        String resolvedType = type == null ? "GENERAL" : type;
        String resolvedMessage = message == null ? "" : message;
        String record = String.format("%s [%s] %s%s",
                LocalDateTime.now().format(TIMESTAMP_FORMAT),
                resolvedType,
                resolvedMessage,
                System.lineSeparator());
        try {
            Files.write(TRANSACTIONS_FILE, record.getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static synchronized void saveCustomerReport(List<String[]> rows) {
        StringBuilder builder = new StringBuilder();
        builder.append("Timestamp: ")
                .append(LocalDateTime.now().format(TIMESTAMP_FORMAT))
                .append(System.lineSeparator());
        if (rows != null) {
            for (String[] row : rows) {
                builder.append(String.join(",", row))
                        .append(System.lineSeparator());
            }
        }
        try {
            Files.write(REPORTS_FILE,
                    builder.toString().getBytes(StandardCharsets.UTF_8),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING,
                    StandardOpenOption.WRITE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String[]> readCustomerReport() {
        try {
            List<String> lines = Files.readAllLines(REPORTS_FILE, StandardCharsets.UTF_8);
            if (lines.isEmpty()) {
                return Collections.emptyList();
            }
            List<String[]> rows = new ArrayList<>();
            for (int i = 1; i < lines.size(); i++) {
                String line = lines.get(i);
                if (line == null || line.trim().isEmpty()) {
                    continue;
                }
                rows.add(line.split(",", -1));
            }
            return rows;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
}

