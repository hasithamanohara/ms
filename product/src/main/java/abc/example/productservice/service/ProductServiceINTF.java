package abc.example.productservice.service;

import abc.example.productservice.dto.request.ProductRequestDTO;
import abc.example.productservice.dto.response.ProductResponseDTO;

import java.util.List;

public interface ProductServiceINTF {
    ProductResponseDTO createProduct(ProductRequestDTO productDTO);
    ProductResponseDTO updateProduct(Long productId,ProductRequestDTO productDTO);
    ProductResponseDTO getProduct(Long productId);
    ProductResponseDTO deleteProduct(Long productId);
    List<ProductResponseDTO> getProducts();
}
