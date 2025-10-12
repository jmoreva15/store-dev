package pe.com.storedev.dto.store;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StoreUpdateDTO {
    @NotBlank(message = "RUC must not be blank")
    @Size(max = 100, message = "RUC must not exceed 100 characters")
    private String ruc;

    @Size(max = 150, message = "Address must not exceed 150 characters")
    private String address;

    @Pattern(regexp = "^[0-9+\\-\\s]{6,20}$",
            message = "Phone number must contain only digits, +, -, spaces and be between 6 and 20 characters")
    private String phone;

    @NotNull(message = "Active status must not be null")
    private Boolean active;
}
