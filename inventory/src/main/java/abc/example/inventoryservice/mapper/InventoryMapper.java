package abc.example.inventoryservice.mapper;

import abc.example.inventoryservice.dto.request.InventoryRequestDTO;
import abc.example.inventoryservice.dto.response.InventoryResponseDTO;
import abc.example.inventoryservice.model.Inventory;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InventoryMapper {
    Inventory toEntity(InventoryRequestDTO inventoryRequestDTO);
    InventoryResponseDTO toDto(Inventory inventory);
}
