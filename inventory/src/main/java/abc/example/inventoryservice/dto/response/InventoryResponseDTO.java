package abc.example.inventoryservice.dto.response;

public record InventoryResponseDTO(
        Long inventoryId,
        Long productId,
        int quantityInHand
) {
}
