package pe.com.storedev.dto.customer;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pe.com.storedev.entity.DocumentType;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerCreateDTO {
    @NotNull(message = "Document type must not be null")
    private DocumentType documentType;

    @NotBlank(message = "Document number must not be blank")
    @Size(max = 50, message = "Document number must not exceed 50 characters")
    private String documentNumber;

    @NotBlank(message = "Full name must not be blank")
    @Size(max = 100, message = "Full name must not exceed 100 characters")
    private String name;

    @Size(max = 150, message = "Address must not exceed 150 characters")
    private String address;

    @Pattern(regexp = "^[0-9+\\-\\s]{6,20}$", message = "Phone number must contain only digits, +, -, spaces and be between 6 and 20 characters")
    private String phone;
}
