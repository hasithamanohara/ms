package abc.example.inventoryservice.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record InventoryRequestDTO(

        @NotNull(message = "Product id is required")
        Long productId,

        @PositiveOrZero(message = "Quantity must be non negative")
        int quantityInHand
) {
}
