package pe.com.storedev.dto.purchase;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pe.com.storedev.dto.store.StoreDTO;
import pe.com.storedev.dto.supplier.SupplierDTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseDTO {
    private Long id;
    private StoreDTO store;
    private SupplierDTO supplier;
    private BigDecimal salePrice;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private BigDecimal purchasePrice;
}
