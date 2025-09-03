package abc.example.orderservice.controller;

import abc.example.orderservice.dto.request.OrderRequestDTO;
import abc.example.orderservice.dto.response.OrderResponseDTO;
import abc.example.orderservice.kafka.producer.OrderProducer;
import abc.example.orderservice.service.OrderServiceIMPL;
import com.example.basemodule.dto.OrderEventDTOT;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Validated
public class OrdersController {

    private final OrderServiceIMPL orderServiceIMPL;
    private final OrderProducer orderProducer;
    private static final Logger log = LoggerFactory.getLogger(OrdersController.class);

    @PostMapping("/createOrder")
    public ResponseEntity<OrderResponseDTO> createOrder(@Valid @RequestBody OrderRequestDTO orderRequestDTO) {
        OrderResponseDTO orderResponseDTO;


        // First: Create Order
        try {
            orderResponseDTO = orderServiceIMPL.createOrder(orderRequestDTO);
        } catch (Exception e) {
            throw new RuntimeException("Order controller error while calling order service " + e);
        }

        // Second: Send Event (after order is created)
        try {
            OrderEventDTOT orderEvent = new OrderEventDTOT(
                    "Order Created with ID: " + orderResponseDTO.orderId(),
                    "CREATED"
            );

            orderProducer.sendMessage(orderEvent);
        } catch (Exception e) {
            log.error("Failed to send order event for Order ID {}: {}", orderResponseDTO.orderId(), e.getMessage(), e);
            throw new RuntimeException("Order controller error while sending event", e);
        }

        // Finally: Return Response
        return ResponseEntity.status(HttpStatus.CREATED).body(orderResponseDTO);
    }


    @PutMapping("/updateOrder/{orderId}")
    public ResponseEntity<OrderResponseDTO> updateOrder(@PathVariable Long orderId, @RequestBody OrderRequestDTO orderRequestDTO) {
        OrderResponseDTO response = orderServiceIMPL.updateOrder(orderId, orderRequestDTO);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getAllOrders")
    public ResponseEntity<List<OrderResponseDTO>> getAllOrders() {
        List<OrderResponseDTO> responses = orderServiceIMPL.getOrders(null);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/getOrderById/{orderId}")
    public ResponseEntity<OrderResponseDTO> getOrderById(@PathVariable Long orderId) {
        OrderResponseDTO response = orderServiceIMPL.getOrderById(orderId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/deleteOrderById/{orderId}")
    public ResponseEntity<OrderResponseDTO> deleteOrder(@PathVariable Long orderId) {
        OrderResponseDTO response = orderServiceIMPL.deleteOrder(orderId);
        return ResponseEntity.ok(response);
    }

}
