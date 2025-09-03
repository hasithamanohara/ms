package abc.example.productservice.controller;

import abc.example.productservice.dto.request.ProductRequestDTO;
import abc.example.productservice.dto.response.ProductResponseDTO;
import abc.example.productservice.service.ProductServiceIMPL;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/product")
@CrossOrigin
@RequiredArgsConstructor
@Validated
public class ProductController {
    private final ProductServiceIMPL productService;

    @PostMapping("/createProduct")
    public ResponseEntity<ProductResponseDTO> createProduct(@Valid @RequestBody ProductRequestDTO productDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(productDTO));
    }

    @PutMapping("/updateProduct/{productId}")
    public ResponseEntity<ProductResponseDTO> updateProduct(@Valid @PathVariable Long productId,@RequestBody ProductRequestDTO productDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.updateProduct(productId,productDTO));
    }

    @GetMapping("/getProductById/{productId}")
    public ResponseEntity<ProductResponseDTO> getProductById(@Valid @PathVariable Long productId) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.getProduct(productId));
    }

    @DeleteMapping("/deleteProduct/{productId}")
    public ResponseEntity<ProductResponseDTO> deleteProduct(@Valid @PathVariable Long productId) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.deleteProduct(productId));
    }
}
