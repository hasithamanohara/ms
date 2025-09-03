package abc.example.inventoryservice.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inventoryId;

    @Column(nullable = false)
    private Long productId;

    @Column(nullable = false)
    private int quantityInHand;
}
