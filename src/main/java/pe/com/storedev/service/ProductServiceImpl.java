package pe.com.storedev.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.com.storedev.dto.product.ProductCreateDTO;
import pe.com.storedev.dto.product.ProductDTO;
import pe.com.storedev.dto.product.ProductUpdateDTO;
import pe.com.storedev.entity.Product;
import pe.com.storedev.exception.NotFoundException;
import pe.com.storedev.mapper.ProductMapper;
import pe.com.storedev.repository.ProductRepository;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<ProductDTO> findAll(Pageable pageable) {
        return productRepository.findAllNotDeleted(pageable)
                .map(productMapper::toDTO);
    }

    @Override
    public ProductDTO create(ProductCreateDTO dto) {
        Product product = productMapper.toEntity(dto);
        Product saved = productRepository.save(product);
        return productMapper.toDTO(saved);
    }

    @Override
    public ProductDTO update(Long id, ProductUpdateDTO dto) {
        Product existing = productRepository.findByIdNotDeleted(id)
                .orElseThrow(() -> new NotFoundException("Product not found with ID: " + id));

        productMapper.updateEntity(existing, dto);
        Product updated = productRepository.save(existing);
        return productMapper.toDTO(updated);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductDTO findById(Long id) {
        Product product = productRepository.findByIdNotDeleted(id)
                .orElseThrow(() -> new NotFoundException("Product not found with ID: " + id));
        return productMapper.toDTO(product);
    }

    @Override
    public void delete(Long id) {
        Product product = productRepository.findByIdNotDeleted(id)
                .orElseThrow(() -> new NotFoundException("Product not found with ID: " + id));

        product.setDeleted(Boolean.TRUE);
        productRepository.save(product);
    }
}
