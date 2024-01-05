package cachetask.Configuration;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class CacheProperties {
    private int maxSize;
    private String algorithm;

    public CacheProperties() {
    }

    public CacheProperties(int maxSize, String algorithm) {
        this.maxSize = maxSize;
        this.algorithm = algorithm;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }
}

