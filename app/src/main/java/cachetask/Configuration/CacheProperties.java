package cachetask.Configuration;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class CacheProperties {
    private final int maxSize;
    private final String algorithm;
}

