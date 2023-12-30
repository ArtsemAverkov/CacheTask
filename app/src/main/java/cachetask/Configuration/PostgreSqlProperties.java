package cachetask.configuration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class PostgreSqlProperties {
    private final String driver;
    private final String url;
    private final String username;
    private final String password;
    private final boolean createSchema;
    private final boolean insertSchema;
}
