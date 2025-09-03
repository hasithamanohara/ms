package abc.example.orderservice.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record OrderRequestDTO(
        @NotNull(message = "Item ID cannot be null")
        @Positive(message = "Item ID must be positive")
        Long productId,

        @NotNull(message = "Amount cannot be null")
        @Min(value = 1, message = "Amount must be at least 1")
        Integer orderQuantity
) {
}
