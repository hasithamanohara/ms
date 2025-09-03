package abc.example.productservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record ProductRequestDTO(
        @NotBlank(message = "Product name is required")
        String productName,

        @NotBlank(message = "Description is required")
        String productDescription,

        @PositiveOrZero(message = "Quantity must be non negative")
        int quantity,

        @NotNull(message ="required forSaleOrNot")
        boolean forSaleOrNot
) {
}
