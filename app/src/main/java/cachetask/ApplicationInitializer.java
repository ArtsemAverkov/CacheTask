package cachetask;

import cachetask.Configuration.ConfigurationLoader;
import cachetask.Configuration.PostgreSqlProperties;
import cachetask.db.DatabaseInitializer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ApplicationInitializer implements ServletContextListener {
    private final DatabaseInitializer initializer;
    boolean createSchema;
    boolean insertSchema;

    public ApplicationInitializer() {
        this.initializer = new DatabaseInitializer();
    }

    public void readProperties() throws SQLException, ClassNotFoundException {
        ConfigurationLoader loader = new ConfigurationLoader();
        PostgreSqlProperties postgreSqlProperties = loader
                .loadProperties("/Users/artemaverkov/clevertec_task/CacheTask/app/src/main/resources/application.yml"); //TODO проблас с путем
        if (postgreSqlProperties != null) {
            this.createSchema = Boolean.parseBoolean(postgreSqlProperties.getCreateSchema());
            this.insertSchema = Boolean.parseBoolean(postgreSqlProperties.getInsertSchema());
        } else {
            throw new RuntimeException("Failed to load properties from application.yml");
        }
    }

    @Override
        public void contextInitialized(ServletContextEvent servletContextEvent) {
        try {
            readProperties();
            if (createSchema){
                initializer.initializeDatabase();
            }
            if (insertSchema) {
                initializer.insertDatabase();
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

        @Override
        public void contextDestroyed(ServletContextEvent servletContextEvent) {
        }
    }

