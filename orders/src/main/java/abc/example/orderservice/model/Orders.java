package abc.example.orderservice.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Orders {

    @Id
    @Column(nullable=false, unique=true)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long orderId;

    @Column(nullable=false)
    private Long productId;

    @Column(nullable=false)
    private int orderQuantity;

    @Column(nullable=false)
    private LocalDateTime orderDate;

    @PrePersist
    public void prePersist() {
        this.orderDate = LocalDateTime.now();
    }
}
