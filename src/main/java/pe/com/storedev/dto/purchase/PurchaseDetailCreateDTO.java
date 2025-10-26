package pe.com.storedev.dto.purchase;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.BigInteger;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseDetailCreateDTO {
    @NotNull(message = "Quantity must not be null")
    @Positive(message = "Quantity must be greater than zero")
    private BigInteger quantity;

    @NotNull(message = "Purchase price must not be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Purchase price must be greater than zero")
    private BigDecimal purchasePrice;

    @NotNull(message = "Sale price must not be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Sale price must be greater than zero")
    private BigDecimal salePrice;

    @NotNull(message = "Product must not be null")
    private Long productStoreId;
}
