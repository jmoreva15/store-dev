package pe.com.storedev.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.com.storedev.dto.product.ProductCreateDTO;
import pe.com.storedev.dto.product.ProductDTO;
import pe.com.storedev.dto.product.ProductUpdateDTO;

import java.util.List;

public interface ProductService {
    Page<ProductDTO> findAll(Pageable pageable);
    List<ProductDTO> findAllActive();
    ProductDTO create(ProductCreateDTO productCreateDTO);
    ProductDTO update(Long id, ProductUpdateDTO productUpdateDTO);
    ProductDTO findById(Long id);
    void delete(Long id);
}
