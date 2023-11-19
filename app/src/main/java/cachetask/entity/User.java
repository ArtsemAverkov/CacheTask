package cachetask.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Builder
@Data
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
}
