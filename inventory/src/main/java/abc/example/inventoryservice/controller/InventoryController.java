package abc.example.inventoryservice.controller;

import abc.example.inventoryservice.dto.request.InventoryRequestDTO;
import abc.example.inventoryservice.dto.response.InventoryResponseDTO;
import abc.example.inventoryservice.service.InventoryServiceIMPL;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
@CrossOrigin
@Validated
public class InventoryController {

    private final InventoryServiceIMPL inventoryService;

    @PostMapping("/createInventory")
    public ResponseEntity<InventoryResponseDTO> createInventory(@Valid @RequestBody InventoryRequestDTO inventoryRequestDTO) {
        InventoryResponseDTO response = inventoryService.creteInventory(inventoryRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/updateInventory/{id}")
    public ResponseEntity<InventoryResponseDTO> updateInventory(
            @PathVariable("id") Long inventoryId,
            @Valid @RequestBody InventoryRequestDTO inventoryRequestDTO) {
        InventoryResponseDTO response = inventoryService.updateInventory(inventoryId, inventoryRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/getInventoryById/{id}")
    public ResponseEntity<InventoryResponseDTO> getInventoryById(@PathVariable("id") Long inventoryId) {
        InventoryResponseDTO response = inventoryService.getInventoryById(inventoryId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/getAllInventory")
    public ResponseEntity<List<InventoryResponseDTO>> getAllInventory() {
        List<InventoryResponseDTO> response = inventoryService.getAllInventory();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/deleteInventory/{id}")
    public ResponseEntity<String> deleteInventory(@PathVariable("id") Long inventoryId) {
        inventoryService.deleteInventory(inventoryId);
        return ResponseEntity.ok("Inventory with ID " + inventoryId + " deleted successfully.");
    }

}
