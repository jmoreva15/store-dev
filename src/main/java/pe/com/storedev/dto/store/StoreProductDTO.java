package pe.com.storedev.dto.store;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pe.com.storedev.dto.product.ProductDTO;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class StoreProductDTO {
    private Long id;
    private BigInteger stock;
    private BigDecimal purchasePrice;
    private BigDecimal salePrice;
    private ProductDTO product;
    private StoreDTO store;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
