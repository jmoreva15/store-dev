package pe.com.storedev.dto.product;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AssignProductDTO {
    @NotNull(message = "The product must be selected.")
    private Long productId;

    @NotNull(message = "The store must be selected.")
    private Long storeId;
}
