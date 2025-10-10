package pe.com.storedev.dto.rol;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoleCreateDTO {
    @NotBlank(message = "Role name is required.")
    @Size(max = 100, message = "Role name must not exceed 100 characters.")
    private String name;

    @NotNull(message = "Active status is required.")
    private Boolean active;
}
