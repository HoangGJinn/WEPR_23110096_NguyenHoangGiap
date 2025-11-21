package giap.hcmute.vn.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.SQLException;
 
public class DBMySqlConnection {
 
    private static final String HOST_NAME = "localhost";
    private static final String DB_NAME = "schooldb";
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "hoanggiap1597";
    private static final String CONNECTION_URL = "jdbc:mysql://" + HOST_NAME + ":3306/" + DB_NAME;
    
    private static HikariDataSource dataSource;
    
    static {
        try {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(CONNECTION_URL);
            config.setUsername(USER_NAME);
            config.setPassword(PASSWORD);
            config.setDriverClassName("com.mysql.cj.jdbc.Driver");
            
            // Connection pool settings
            config.setMaximumPoolSize(10);          // Tối đa 10 connection
            config.setMinimumIdle(2);               // Tối thiểu 2 connection idle
            config.setConnectionTimeout(30000);      // Timeout 30s
            config.setIdleTimeout(600000);          // Idle timeout 10 phút
            config.setMaxLifetime(1800000);         // Max lifetime 30 phút
            
            // Performance settings
            config.addDataSourceProperty("cachePrepStmts", "true");
            config.addDataSourceProperty("prepStmtCacheSize", "250");
            config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
            
            dataSource = new HikariDataSource(config);
            System.out.println("HikariCP Connection Pool initialized successfully");
        } catch (Exception e) {
            System.err.println("Failed to initialize HikariCP: " + e.getMessage());
            e.printStackTrace();
        }
    }
 
    public static Connection getConnection() throws SQLException {
        if (dataSource == null) {
            throw new SQLException("DataSource is not initialized");
        }
        return dataSource.getConnection();
    }
    
    public static void closePool() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
            System.out.println("HikariCP Connection Pool closed");
        }
    }
    
    public static void main(String[] args) {
        try {
            Connection conn = DBMySqlConnection.getConnection();
            if (conn == null) {
                System.out.println("Something wrong!");
            } else {
                System.out.println("Connecting OK");
                System.out.println("Active connections: " + dataSource.getHikariPoolMXBean().getActiveConnections());
                System.out.println("Idle connections: " + dataSource.getHikariPoolMXBean().getIdleConnections());
                conn.close(); // Trả connection về pool
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            closePool();
        }
    }
}