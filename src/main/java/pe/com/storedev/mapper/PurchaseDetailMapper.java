package pe.com.storedev.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pe.com.storedev.dto.purchase.PurchaseDetailCreateDTO;
import pe.com.storedev.entity.PurchaseDetail;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PurchaseDetailMapper {
    private final StoreProductMapper storeProductMapper;

    public PurchaseDetail toEntity(PurchaseDetailCreateDTO dto) {
        if (dto == null) return null;

        PurchaseDetail detail = new PurchaseDetail();
        detail.setQuantity(dto.getQuantity());
        detail.setPurchasePrice(dto.getPurchasePrice());
        detail.setSalePrice(dto.getSalePrice());
        detail.setStoreProduct(storeProductMapper.toDTO(dto.getProductStoreId()));
        return detail;
    }

    public Set<PurchaseDetail> toEntity(Set<PurchaseDetailCreateDTO> dtos) {
        if (dtos == null) return null;
        return dtos.stream().map(this::toEntity).collect(Collectors.toSet());
    }
}
