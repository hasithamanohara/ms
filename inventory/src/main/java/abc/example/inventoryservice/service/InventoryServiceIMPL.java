package abc.example.inventoryservice.service;

import abc.example.inventoryservice.dto.request.InventoryRequestDTO;
import abc.example.inventoryservice.dto.response.InventoryResponseDTO;
import abc.example.inventoryservice.exception.InventoryNotFoundException;
import abc.example.inventoryservice.mapper.InventoryMapper;
import abc.example.inventoryservice.model.Inventory;
import abc.example.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class InventoryServiceIMPL implements InventoryServiceINTF {

    private final InventoryRepository inventoryRepository;
    private final InventoryMapper inventoryMapper;

    @Override
    public InventoryResponseDTO creteInventory(InventoryRequestDTO inventoryRequestDTO) {
        Inventory newInventory = inventoryMapper.toEntity(inventoryRequestDTO);
        Inventory savedInventory = inventoryRepository.save(newInventory);
        return inventoryMapper.toDto(savedInventory);
    }

    @Override
    public InventoryResponseDTO updateInventory(Long inventoryId, InventoryRequestDTO inventoryRequestDTO) {
        Inventory existingInventory = inventoryRepository.findById(inventoryId)
                .orElseThrow(() -> new InventoryNotFoundException("Product not found with ID:" + inventoryId));

        existingInventory.setProductId(inventoryRequestDTO.productId());
        existingInventory.setQuantityInHand(inventoryRequestDTO.quantityInHand());

        Inventory updatedInventory = inventoryRepository.save(existingInventory);
        return inventoryMapper.toDto(updatedInventory);
    }

    @Override
    public InventoryResponseDTO getInventoryById(Long inventoryId) {
        Inventory existingInventory = inventoryRepository.findById(inventoryId)
                .orElseThrow(() -> new InventoryNotFoundException("Product not found with ID:" + inventoryId));
        return inventoryMapper.toDto(existingInventory);
    }

    @Override
    public List<InventoryResponseDTO> getAllInventory() {
        List<Inventory> allInventory = inventoryRepository.findAll();
        return allInventory.stream()
                .map(inventoryMapper :: toDto)
                .toList();
    }

    @Override
    public InventoryResponseDTO deleteInventory(Long inventoryId) {
        Inventory existingInventory = inventoryRepository.findById(inventoryId)
                .orElseThrow(() -> new InventoryNotFoundException("Product not found with ID:" + inventoryId));

        inventoryRepository.delete(existingInventory);
        return inventoryMapper.toDto(existingInventory);
    }
}
