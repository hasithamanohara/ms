package abc.example.orderservice.service;

import abc.example.orderservice.dto.request.OrderRequestDTO;
import abc.example.orderservice.dto.response.OrderResponseDTO;

import java.util.List;

public interface OrderServiceINTF {
    OrderResponseDTO createOrder(OrderRequestDTO orderRequestDTO);
    OrderResponseDTO updateOrder(Long orderId,OrderRequestDTO orderRequestDTO);
    List<OrderResponseDTO> getOrders(String orderId);
    OrderResponseDTO getOrderById(Long orderId);
    OrderResponseDTO deleteOrder(Long orderId);
}
