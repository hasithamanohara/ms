package abc.example.orderservice.mapper;

import abc.example.orderservice.dto.request.OrderRequestDTO;
import abc.example.orderservice.dto.response.OrderResponseDTO;
import abc.example.orderservice.model.Orders;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    Orders toEntity(OrderRequestDTO orderRequestDTO);
    OrderResponseDTO toDTO(Orders orders);
}
