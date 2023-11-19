package cachetask.Configuration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class PostgreSqlProperties {
    private final String driver;
    private final String url;
    private final String username;
    private final String password;

}
