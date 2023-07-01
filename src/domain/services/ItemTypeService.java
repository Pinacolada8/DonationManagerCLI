package domain.services;

import com.google.inject.Inject;
import domain.models.entities.ItemType;
import domain.services.contracts.IItemTypeRepository;

import java.util.List;

public class ItemTypeService {
    private final IItemTypeRepository itemTypeRepository;

    @Inject
    public ItemTypeService(IItemTypeRepository itemTypeRepository) {
        this.itemTypeRepository = itemTypeRepository;
    }

    public ItemType registerItemType(String itemTypeName){
        return itemTypeRepository.add(itemTypeName);
    }

    public boolean removeItemType(String itemTypeName){
        var itemType = itemTypeRepository.findByName(itemTypeName);
        return itemTypeRepository.remove(itemType);
    }

    public List<ItemType> listItemTypes(){
        return itemTypeRepository.getAll();
    }
}
