package cachetask.entity;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;


@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private Long id;

    @NotEmpty
    @Size(min = 5, max = 10)
    @Pattern(regexp = "[а-яА-ЯёЁ\\s]+")
    private String name;

    @NotEmpty
    @Size(min = 5, max = 10)
    @Pattern(regexp = "[а-яА-ЯёЁ\\s]+")
    private String lastName;

    @Email
    private String email;

    public User(long userId, String name, String lastName, String email) {
    }

    public User(Long id, String name, String lastName, String email) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
