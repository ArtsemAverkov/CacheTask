package cachetask.connect;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class DatabaseConfig {
    private String url;
    private String username;
    private String password;
    private String  driver_class_name;
}
