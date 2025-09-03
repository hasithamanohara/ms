package abc.example.productservice.mapper;

import abc.example.productservice.dto.request.ProductRequestDTO;
import abc.example.productservice.dto.response.ProductResponseDTO;
import abc.example.productservice.model.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    Product toEntity(ProductRequestDTO productRequestDTO);
    ProductResponseDTO toDTO(Product product);
}
