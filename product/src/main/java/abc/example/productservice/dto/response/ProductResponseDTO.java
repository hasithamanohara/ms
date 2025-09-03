package abc.example.productservice.dto.response;

public record ProductResponseDTO(
        Long productId,
        String productName,
        String productDescription,
        int quantity,
        boolean forSaleOrNot
) {
}
