package abc.example.inventoryservice.exception;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ErrorDetails {
    private String error;
    private String details;
    private String path;
    private LocalDateTime timestamp;
}
