package abc.example.inventoryservice.service;

import abc.example.inventoryservice.dto.request.InventoryRequestDTO;
import abc.example.inventoryservice.dto.response.InventoryResponseDTO;

import java.util.List;

public interface InventoryServiceINTF {
    InventoryResponseDTO creteInventory(InventoryRequestDTO inventoryRequestDTO);
    InventoryResponseDTO updateInventory(Long inventoryId,InventoryRequestDTO inventoryRequestDTO);
    InventoryResponseDTO getInventoryById(Long inventoryId);
    List<InventoryResponseDTO> getAllInventory();
    InventoryResponseDTO deleteInventory(Long inventoryId);
}
