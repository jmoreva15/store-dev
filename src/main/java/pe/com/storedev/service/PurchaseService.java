package pe.com.storedev.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.com.storedev.dto.purchase.PurchaseCreateDTO;
import pe.com.storedev.dto.purchase.PurchaseDTO;

import java.time.LocalDateTime;

public interface PurchaseService {
    Page<PurchaseDTO> findAllByFilters(LocalDateTime startDate, LocalDateTime endDate, Long supplierId, Long storeId, Pageable pageable);
    PurchaseDTO findById(Long id);
    PurchaseDTO create(PurchaseCreateDTO purchaseCreateDTO);
}
