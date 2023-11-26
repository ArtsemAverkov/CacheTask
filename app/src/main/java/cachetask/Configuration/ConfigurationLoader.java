package cachetask.configuration;

import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;

public class ConfigurationLoader {

    public CacheProperties loadCacheProperties(String filePath) {
        try {
            InputStream input = new FileInputStream(filePath);
            Yaml yaml = new Yaml();
            Map<String, Object> root = yaml.load(input);

            Map<String, Object> cacheConfig = (Map<String, Object>) root.get("cache");

            int maxSize = (int) cacheConfig.get("max-size");
            String algorithm = (String) cacheConfig.get("algorithm");

            return new CacheProperties(maxSize, algorithm);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public PostgreSqlProperties loadProperties(String filePath) {
        try {
            InputStream input = new FileInputStream(filePath);
            Yaml yaml = new Yaml();
            Map<String, Object> root = yaml.load(input);
            Map<String, Object> cacheConfig = (Map<String, Object>) root.get("database");

            String driver = (String)cacheConfig.get("driver-class-name");
            String url = (String)cacheConfig.get("url");
            String username = (String)cacheConfig.get("username");
            String password = (String)cacheConfig.get("password");

            return new PostgreSqlProperties(driver,url,username,password);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}

