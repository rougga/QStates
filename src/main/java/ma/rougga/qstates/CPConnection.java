package ma.rougga.qstates;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;

public class CPConnection {

    private static HikariDataSource dataSource;
    private static final String DRIVER = "org.postgresql.Driver";
    private static final String CONNECTIONSTRING = "jdbc:postgresql://localhost:5433/postgres?characterEncoding=UTF-8";
    // private final String CONNECTIONSTRING = "jdbc:postgresql://localhost:5432/postgres?characterEncoding=UTF-8";
    private static final String USER = "honyi";
    private static final String PASSWORD = "honyi123";

    static {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(CONNECTIONSTRING);
        config.setUsername(USER);
        config.setPassword(PASSWORD);
        config.setMaximumPoolSize(17);  // Adjust pool size as needed
        config.setMinimumIdle(2);
        config.setIdleTimeout(30000);
        config.setMaxLifetime(1800000);
        dataSource = new HikariDataSource(config);
    }

    public static DataSource getDataSource() {
        return dataSource;
    }

    public Statement getStatement() throws SQLException {
        return getDataSource().getConnection().createStatement();
    }

    public Connection getConnection() throws SQLException {
        return getDataSource().getConnection();
    }

}
