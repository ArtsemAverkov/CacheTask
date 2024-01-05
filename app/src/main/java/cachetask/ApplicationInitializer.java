package cachetask;

import cachetask.Configuration.ConfigurationLoader;
import cachetask.Configuration.PostgreSqlProperties;
import cachetask.db.DatabaseInitializer;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.sql.SQLException;

@Component
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
            this.createSchema = Boolean.parseBoolean(String.valueOf(postgreSqlProperties.isCreateSchema()));
            this.insertSchema = Boolean.parseBoolean(String.valueOf(postgreSqlProperties.isInsertSchema()));
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

