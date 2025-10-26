package pe.com.storedev.controller;

import jakarta.validation.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pe.com.storedev.dto.purchase.PurchaseCreateDTO;
import pe.com.storedev.dto.purchase.PurchaseDTO;
import pe.com.storedev.dto.purchase.PurchaseDetailCreateDTO;
import pe.com.storedev.service.*;
import pe.com.storedev.session.PurchaseSession;
import pe.com.storedev.util.DateUtils;

import java.time.LocalDateTime;
import java.util.Set;

@Controller
@RequestMapping("/purchases")
@RequiredArgsConstructor
public class PurchaseController {
    private final StoreService storeService;
    private final PurchaseService purchaseService;
    private final SupplierService supplierService;
    private final PurchaseSession purchaseSession;
    private final StoreProductService storeProductService;
    private final Validator validator;

    @GetMapping
    @PreAuthorize("hasAuthority('VIEW_PURCHASE')")
    public String index(@RequestParam(required = false) String startDateStr,
                        @RequestParam(required = false) String endDateStr,
                        @RequestParam(required = false) Long supplierId,
                        @RequestParam(required = false) Long storeId,
                        @RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "10") int size,
                        Model model) {
        LocalDateTime startDate = DateUtils.parseStartDate(startDateStr);
        LocalDateTime endDate = DateUtils.parseEndDate(endDateStr);

        Page<PurchaseDTO> purchases = purchaseService
                .findAllByFilters(startDate, endDate, supplierId, storeId, PageRequest.of(page, size));
        model.addAttribute("purchases", purchases);
        model.addAttribute("suppliers", supplierService.findAllActive());
        model.addAttribute("stores", storeService.findAllActive());
        return "app/purchase/index";
    }

    @GetMapping("/create")
    @PreAuthorize("hasAuthority('CREATE_PURCHASE')")
    public String create(Model model) {
        PurchaseCreateDTO purchaseCreateDTO = purchaseSession.getCurrentPurchase();

        model.addAttribute("suppliers", supplierService.findAllActive());
        model.addAttribute("stores", storeService.findAllActive());
        model.addAttribute("purchase", purchaseCreateDTO);
        model.addAttribute("products", storeProductService.findByStoreId(purchaseCreateDTO.getStoreId()));
        model.addAttribute("purchaseDetail", new PurchaseDetailCreateDTO());

        return "app/purchase/create";
    }

    @PostMapping(value = "/create", params = "addSupplier")
    @PreAuthorize("hasAuthority('CREATE_PURCHASE')")
    public String addSupplier(@RequestParam(required = false) Long supplierId) {
        purchaseSession.setSupplier(supplierId);
        return "redirect:/purchases/create";
    }

    @PostMapping(value = "/create", params = "addStore")
    @PreAuthorize("hasAuthority('CREATE_PURCHASE')")
    public String addStore(@RequestParam(required = false) Long storeId) {
        purchaseSession.setStore(storeId);
        return "redirect:/purchases/create";
    }

    @PostMapping(value = "/create", params = "addPurchaseDetail")
    @PreAuthorize("hasAuthority('CREATE_PURCHASE')")
    public String addPurchaseDetail(
            @Valid @ModelAttribute("purchaseDetail") PurchaseDetailCreateDTO purchaseDetailCreateDTO,
            BindingResult result,
            Model model) {
        if (result.hasErrors()) {
            PurchaseCreateDTO purchaseCreateDTO = purchaseSession.getCurrentPurchase();

            model.addAttribute("suppliers", supplierService.findAllActive());
            model.addAttribute("stores", storeService.findAllActive());
            model.addAttribute("purchase", purchaseCreateDTO);
            model.addAttribute("products", storeProductService.findByStoreId(purchaseCreateDTO.getStoreId()));
            return "app/purchase/create";
        }

        purchaseSession.addDetailItem(purchaseDetailCreateDTO);
        return "redirect:/purchases/create";
    }

    @GetMapping(value = "/create", params = "removePurchaseDetail")
    @PreAuthorize("hasAuthority('CREATE_PURCHASE')")
    public String removePurchaseDetail(@RequestParam(required = false) Long storeProductId) {
        purchaseSession.removeDetailItem(storeProductId);
        return "redirect:/purchases/create";
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('CREATE_PURCHASE')")
    public String createPost(Model model) {
        PurchaseCreateDTO purchase = purchaseSession.getCurrentPurchase();
        Set<ConstraintViolation<PurchaseCreateDTO>> violations = validator.validate(purchase);

        if (!violations.isEmpty()) {
            model.addAttribute("validationErrors", violations);
            model.addAttribute("stores", storeService.findAllActive());
            model.addAttribute("suppliers", supplierService.findAllActive());
            model.addAttribute("purchase", purchase);
            model.addAttribute("products", storeProductService.findByStoreId(purchase.getStoreId()));
            model.addAttribute("purchaseDetail", new PurchaseDetailCreateDTO());
            return "app/purchase/create";
        }

        purchaseService.create(purchase);
        purchaseSession.clearCurrentPurchase();
        return "redirect:/purchases";
    }
}
