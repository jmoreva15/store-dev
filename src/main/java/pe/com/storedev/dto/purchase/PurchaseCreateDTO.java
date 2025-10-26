package pe.com.storedev.dto.purchase;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseCreateDTO {
    @NotNull(message = "Supplier must not be null")
    private Long supplierId;

    @NotNull(message = "Store must not be null")
    private Long storeId;

    @NotNull(message = "Purchase details must not be null")
    @Size(min = 1, message = "Purchase must have at least one detail")
    private Set<PurchaseDetailCreateDTO> purchaseDetails;
}
