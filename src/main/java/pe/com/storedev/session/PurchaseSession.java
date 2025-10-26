package pe.com.storedev.session;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import pe.com.storedev.dto.purchase.PurchaseCreateDTO;
import pe.com.storedev.dto.purchase.PurchaseDetailCreateDTO;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.function.Function;

@Component
public class PurchaseSession {
    private static final String SESSION_KEY = "CURRENT_PURCHASE";

    private HttpSession getSession() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return attr.getRequest().getSession(true);
    }

    public PurchaseCreateDTO getCurrentPurchase() {
        HttpSession session = getSession();
        PurchaseCreateDTO purchase = (PurchaseCreateDTO) session.getAttribute(SESSION_KEY);

        if (purchase == null) {
            purchase = new PurchaseCreateDTO();
            purchase.setPurchaseDetails(new HashSet<>());
            session.setAttribute(SESSION_KEY, purchase);
        }

        return purchase;
    }

    public void clearCurrentPurchase() {
        getSession().removeAttribute(SESSION_KEY);
    }

    public void setSupplier(Long supplierId) {
        PurchaseCreateDTO current = getCurrentPurchase();
        current.setSupplierId(supplierId);
        getSession().setAttribute(SESSION_KEY, current);
    }

    public void setStore(Long storeId) {
        PurchaseCreateDTO current = getCurrentPurchase();
        current.setStoreId(storeId);
        getSession().setAttribute(SESSION_KEY, current);
    }

    public void addDetailItem(PurchaseDetailCreateDTO newItem) {
        PurchaseCreateDTO current = getCurrentPurchase();
        Set<PurchaseDetailCreateDTO> details =
                Optional.ofNullable(current.getPurchaseDetails()).orElseGet(HashSet::new);

        current.setPurchaseDetails(details);

        Optional<PurchaseDetailCreateDTO> existingOpt = details.stream()
                .filter(item -> item.getProductStoreId().equals(newItem.getProductStoreId()))
                .findFirst();

        Function<PurchaseDetailCreateDTO, BigDecimal> totalPurchase =
                item -> item.getPurchasePrice().multiply(new BigDecimal(item.getQuantity()));
        Function<PurchaseDetailCreateDTO, BigDecimal> totalSale =
                item -> item.getSalePrice().multiply(new BigDecimal(item.getQuantity()));

        if (existingOpt.isPresent()) {
            PurchaseDetailCreateDTO existing = existingOpt.get();
            BigInteger updatedQuantity = existing.getQuantity().add(newItem.getQuantity());
            existing.setQuantity(updatedQuantity);
            existing.setPurchasePrice(existing.getPurchasePrice().multiply(new BigDecimal(updatedQuantity)));
            existing.setSalePrice(existing.getSalePrice().multiply(new BigDecimal(updatedQuantity)));
        } else {
            newItem.setPurchasePrice(totalPurchase.apply(newItem));
            newItem.setSalePrice(totalSale.apply(newItem));
            details.add(newItem);
        }

        getSession().setAttribute(SESSION_KEY, current);
    }

    public void removeDetailItem(Long storeProductId) {
        PurchaseCreateDTO current = getCurrentPurchase();
        Set<PurchaseDetailCreateDTO> details = current.getPurchaseDetails();

        if (details != null && !details.isEmpty()) {
            details.removeIf(item -> item.getProductStoreId().equals(storeProductId));
        }

        getSession().setAttribute(SESSION_KEY, current);
    }
}
