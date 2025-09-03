package abc.example.productservice.service;

import abc.example.productservice.dto.request.ProductRequestDTO;
import abc.example.productservice.dto.response.ProductResponseDTO;
import abc.example.productservice.exception.ProductNotFoundException;
import abc.example.productservice.mapper.ProductMapper;
import abc.example.productservice.model.Product;
import abc.example.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceIMPL implements ProductServiceINTF {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public ProductResponseDTO createProduct(ProductRequestDTO productDTO) {
        Product newProduct = productMapper.toEntity(productDTO);
        Product savedProduct =  productRepository.save(newProduct);
        return productMapper.toDTO(savedProduct);
    }

    @Override
    public ProductResponseDTO updateProduct(Long productId,ProductRequestDTO productDTO) {
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + productId));

        existingProduct.setProductName(productDTO.productName());
        existingProduct.setProductDescription(productDTO.productDescription());
        existingProduct.setQuantity(productDTO.quantity());
        existingProduct.setForSaleOrNot(productDTO.forSaleOrNot());

        Product updatedProduct = productRepository.save(existingProduct);
        return productMapper.toDTO(updatedProduct);
    }

    @Override
    public ProductResponseDTO getProduct(Long productId) {
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + productId));
        return productMapper.toDTO(existingProduct);
    }

    @Override
    public ProductResponseDTO deleteProduct(Long productId) {
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + productId));

        productRepository.delete(existingProduct);

        return productMapper.toDTO(existingProduct);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponseDTO> getProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(productMapper::toDTO)
                .toList();
    }
}
