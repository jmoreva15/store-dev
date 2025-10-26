package pe.com.storedev.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pe.com.storedev.dto.purchase.PurchaseDetailCreateDTO;
import pe.com.storedev.dto.purchase.PurchaseDetailDTO;
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
        detail.setStoreProduct(storeProductMapper.toEntity(dto.getProductStoreId()));
        return detail;
    }

    public Set<PurchaseDetail> toEntity(Set<PurchaseDetailCreateDTO> dtos) {
        if (dtos == null) return null;
        return dtos.stream().map(this::toEntity).collect(Collectors.toSet());
    }

    public PurchaseDetailDTO toDTO(PurchaseDetail purchaseDetail) {
        if (purchaseDetail == null) return null;

        PurchaseDetailDTO dto = new PurchaseDetailDTO();
        dto.setId(purchaseDetail.getId());
        dto.setQuantity(purchaseDetail.getQuantity());
        dto.setPurchasePrice(purchaseDetail.getPurchasePrice());
        dto.setSalePrice(purchaseDetail.getSalePrice());
        dto.setStoreProduct(storeProductMapper.toDTO(purchaseDetail.getStoreProduct()));
        dto.setCreatedAt(purchaseDetail.getCreatedAt());
        dto.setUpdatedAt(purchaseDetail.getUpdatedAt());
        return dto;
    }

    public Set<PurchaseDetailDTO> toDTO(Set<PurchaseDetail> purchaseDetails) {
        if (purchaseDetails == null) return null;
        return purchaseDetails.stream().map(this::toDTO).collect(Collectors.toSet());
    }
}
