package pe.com.storedev.dto.purchase;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pe.com.storedev.dto.store.StoreProductDTO;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseDetailDTO {
    private Long id;
    private BigInteger quantity;
    private BigDecimal salePrice;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private BigDecimal purchasePrice;
    private StoreProductDTO storeProduct;
}
