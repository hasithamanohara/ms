package abc.example.orderservice.dto.response;

import java.time.LocalDateTime;

public record OrderResponseDTO(
        Long orderId,
        Long productId,
        int orderQuantity,
        LocalDateTime orderDate
) {
}
