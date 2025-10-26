package pe.com.storedev.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.com.storedev.dto.purchase.PurchaseCreateDTO;
import pe.com.storedev.dto.purchase.PurchaseDTO;
import pe.com.storedev.entity.Purchase;
import pe.com.storedev.entity.PurchaseDetail;
import pe.com.storedev.entity.StoreProduct;
import pe.com.storedev.exception.NotFoundException;
import pe.com.storedev.mapper.PurchaseMapper;
import pe.com.storedev.repository.PurchaseRepository;
import pe.com.storedev.repository.StoreProductRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PurchaseServiceImpl implements PurchaseService {
    private final PurchaseRepository purchaseRepository;
    private final StoreProductRepository storeProductRepository;
    private final PurchaseMapper purchaseMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<PurchaseDTO> findAllByFilters(LocalDateTime startDate, LocalDateTime endDate, Long supplierId, Long storeId, Pageable pageable) {
        return purchaseRepository.findAllFilteredBySupplierStoreAndDate(startDate, endDate, supplierId, storeId, pageable)
                .map(purchaseMapper::toDTO);
    }

    @Override
    public PurchaseDTO findById(Long id) {
        return purchaseRepository.findById(id)
                .map(purchaseMapper::toDTO)
                .orElseThrow(() -> new NotFoundException("Purchase not found with ID: " + id));
    }

    @Override
    public PurchaseDTO create(PurchaseCreateDTO dto) {
        Purchase purchase = purchaseMapper.toEntity(dto);

        purchase.getPurchaseDetails()
                .forEach(purchaseDetail -> {
                    Long storeProductId = purchaseDetail.getStoreProduct().getId();

                    StoreProduct storeProduct = storeProductRepository
                            .findById(storeProductId)
                            .orElseThrow(() -> new NotFoundException("StoreProduct not found with ID: " + storeProductId));

                    BigDecimal quantityDecimal = new BigDecimal(purchaseDetail.getQuantity());
                    BigDecimal unitPurchasePrice = purchaseDetail.getPurchasePrice().divide(quantityDecimal, 2, RoundingMode.HALF_UP);
                    BigDecimal unitSalePrice = purchaseDetail.getSalePrice().divide(quantityDecimal, 2, RoundingMode.HALF_UP);

                    storeProduct.setStock(storeProduct.getStock().add(purchaseDetail.getQuantity()));
                    storeProduct.setPurchasePrice(unitPurchasePrice);
                    storeProduct.setSalePrice(unitSalePrice);

                    storeProductRepository.save(storeProduct);
                });

        BigDecimal purchasePrice = purchase.getPurchaseDetails().stream()
                .map(PurchaseDetail::getPurchasePrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal salePrice = purchase.getPurchaseDetails().stream()
                .map(PurchaseDetail::getSalePrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        purchase.setPurchasePrice(purchasePrice);
        purchase.setSalePrice(salePrice);

        return purchaseMapper.toDTO(purchaseRepository.save(purchase));
    }
}
