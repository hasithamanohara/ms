package abc.example.orderservice.service;

import abc.example.inventoryservice.dto.response.InventoryResponseDTO;
import abc.example.inventoryservice.exception.InventoryNotFoundException;
import abc.example.orderservice.dto.request.OrderRequestDTO;
import abc.example.orderservice.dto.response.OrderResponseDTO;
import abc.example.orderservice.exception.OrderNotFoundException;
import abc.example.orderservice.exception.InvalidOrderException;
import abc.example.orderservice.mapper.OrderMapper;
import abc.example.orderservice.model.Orders;
import abc.example.orderservice.repository.OrdersRepository;
import abc.example.productservice.dto.response.ProductResponseDTO;
import abc.example.productservice.exception.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceIMPL implements OrderServiceINTF {

    private final OrderMapper orderMapper;
    private final OrdersRepository ordersRepository;
    private final WebClient productWebClient;
    private final WebClient inventoryWebClient;

    @Override
    public OrderResponseDTO createOrder(OrderRequestDTO orderRequestDTO) {

        Long productId = orderRequestDTO.productId();

        if (orderRequestDTO.orderQuantity() <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }

        InventoryResponseDTO inventoryResponseDTO;
        ProductResponseDTO productResponseDTO;

        try {
            inventoryResponseDTO = inventoryWebClient
                    .get()
                    .uri("/getInventoryById/{productId}", productId)
                    .retrieve()
                    .bodyToMono(InventoryResponseDTO.class)
                    .block();

        } catch (RuntimeException e) {
            throw new RuntimeException("inventoryWebClient error while calling inventory service"+ e);
        }

        try {
            productResponseDTO = productWebClient
                    .get()
                    .uri("/getProductById/{productId}", productId)
                    .retrieve()
                    .bodyToMono(ProductResponseDTO.class)
                    .block();
        } catch (RuntimeException e) {
            throw new RuntimeException("productWebClient error while calling product service" + e);
        }

        if (inventoryResponseDTO == null) {
            throw new InvalidOrderException("Inventory service returned null for productId: " + orderRequestDTO.productId());
        }

        if (orderRequestDTO.orderQuantity() > inventoryResponseDTO.quantityInHand()) {
            throw new InvalidOrderException("Not enough stock for productId: " + orderRequestDTO.productId());
        }

        if (productResponseDTO == null) {
            throw new ProductNotFoundException("Product Unavailable productId: " + orderRequestDTO.productId());
        }

        if (productResponseDTO.forSaleOrNot()) {
            Orders newOrder = orderMapper.toEntity(orderRequestDTO);
            Orders savedOrder = ordersRepository.save(newOrder);
            return orderMapper.toDTO(savedOrder);
        } else {
            throw new InvalidOrderException("Product Not For Sale");
        }

    }

    @Override
    public OrderResponseDTO updateOrder(Long orderId, OrderRequestDTO orderRequestDTO) {
        Orders existingOrder = ordersRepository.findById(orderId).orElseThrow(
                () -> new OrderNotFoundException("Order Not Found With Id: " + orderId)
        );

        if (orderRequestDTO.productId() != null) {
            existingOrder.setProductId(orderRequestDTO.productId());
        }
        if (orderRequestDTO.orderQuantity() != null) {
            if (orderRequestDTO.orderQuantity() <= 0) {
                throw new InvalidOrderException("Amount must be positive");
            }
            existingOrder.setOrderQuantity(orderRequestDTO.orderQuantity());
        }

        Orders updatedOrder = ordersRepository.save(existingOrder);
        return orderMapper.toDTO(updatedOrder);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderResponseDTO> getOrders(String orderId) {
        List<Orders> allOrders = ordersRepository.findAll();

        if (allOrders.isEmpty()) {
            throw new OrderNotFoundException("Create Order to Show");
        }

        return allOrders.stream()
                .map(orderMapper::toDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public OrderResponseDTO getOrderById(Long orderId) {
        Orders existingOrder = ordersRepository.findById(orderId).orElseThrow(
                () -> new OrderNotFoundException("Order Not Found With Id: " + orderId)
        );

        return orderMapper.toDTO(existingOrder);
    }

    @Override
    public OrderResponseDTO deleteOrder(Long orderId) {
        Orders existingOrder = ordersRepository.findById(orderId).orElseThrow(
                () -> new OrderNotFoundException("Order Not Found With Id: " + orderId)
        );

        ordersRepository.delete(existingOrder);
        return orderMapper.toDTO(existingOrder);
    }
}
