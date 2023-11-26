package cachetask.connect;

import cachetask.configuration.ConfigurationLoader;
import cachetask.configuration.PostgreSqlProperties;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectPostgresQL implements Connect{
    private Connection connection;

    @Override
    public Connection connect() throws SQLException, ClassNotFoundException {
        ConfigurationLoader loader = new ConfigurationLoader();
        PostgreSqlProperties postgreSqlProperties = loader
                .loadProperties("/Users/artemaverkov/clevertec_task/patterns_task/CacheTask/app/src/main/resources/application.yml"); //TODO проблас с путем
        if (postgreSqlProperties != null) {
            String driver = postgreSqlProperties.getDriver();
            String url = postgreSqlProperties.getUrl();
            String username = postgreSqlProperties.getUsername();
            String password = postgreSqlProperties.getPassword();

            Class.forName(driver);
            connection = DriverManager.getConnection(url, username, password);
            return connection;
        } else {
            throw new RuntimeException("Failed to load properties from application.yml");
        }
    }

    @Override
    public boolean close() {
        try {
            connection.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
}
