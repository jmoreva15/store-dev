package pe.com.storedev.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pe.com.storedev.dto.purchase.PurchaseCreateDTO;
import pe.com.storedev.dto.purchase.PurchaseDTO;
import pe.com.storedev.entity.Purchase;

@Component
@RequiredArgsConstructor
public class PurchaseMapper {
    private final SupplierMapper supplierMapper;
    private final StoreMapper storeMapper;
    private final PurchaseDetailMapper purchaseDetailMapper;

    public Purchase toEntity(PurchaseCreateDTO dto) {
        if (dto == null) return null;

        Purchase purchase = new Purchase();
        purchase.setSupplier(supplierMapper.toEntity(dto.getSupplierId()));
        purchase.setStore(storeMapper.toEntity(dto.getStoreId()));
        purchase.setPurchaseDetails(purchaseDetailMapper.toEntity(dto.getPurchaseDetails()));
        purchase.getPurchaseDetails().forEach(purchaseDetail -> purchaseDetail.setPurchase(purchase));
        return purchase;
    }

    public Purchase toEntity(Long id) {
        if (id == null) return null;

        Purchase purchase = new Purchase();
        purchase.setId(id);
        return purchase;
    }

    public PurchaseDTO toDTO(Purchase entity) {
        if (entity == null) return null;

        PurchaseDTO dto = new PurchaseDTO();
        dto.setId(entity.getId());
        dto.setPurchasePrice(entity.getPurchasePrice());
        dto.setSalePrice(entity.getSalePrice());
        dto.setSupplier(supplierMapper.toDTO(entity.getSupplier()));
        dto.setStore(storeMapper.toDTO(entity.getStore()));
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        dto.setPurchaseDetails(purchaseDetailMapper.toDTO(entity.getPurchaseDetails()));
        return dto;
    }
}
