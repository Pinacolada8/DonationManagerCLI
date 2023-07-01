package domain.services;

import com.google.inject.Inject;
import domain.models.DTOs.NewItemDTO;
import domain.models.entities.ItemEntry;
import domain.services.contracts.IAuthContext;
import domain.services.contracts.IItemRepository;
import domain.services.contracts.IItemTypeRepository;
import domain.utils.AuthUtils;

import java.util.List;

public class ItemService {
    private final IItemRepository itemRepository;
    private final IItemTypeRepository itemTypeRepository;
    private final IAuthContext authContext;

    @Inject
    public ItemService(IItemRepository itemRepository, IItemTypeRepository itemTypeRepository, IAuthContext authContext) {
        this.itemRepository = itemRepository;
        this.itemTypeRepository = itemTypeRepository;
        this.authContext = authContext;
    }

    // ====================================================================================== //

    public ItemEntry registerItem(NewItemDTO newItem){
        if(itemRepository.findByName(newItem.name) != null)
            return null;

        var itemEntry = new ItemEntry();
        itemEntry.setName(newItem.name);
        itemEntry.setQuantity(newItem.quantity);
        itemEntry.setDescription(newItem.description);
        itemEntry.setCity(newItem.city);

        var itemType = itemTypeRepository.findByName(newItem.itemType);
        itemEntry.setItemTypeId(itemType.getId());

        var currentUser = authContext.getCurrentLoggedUser();
        itemEntry.setDonorUserId(currentUser.getId());

        itemEntry.setApproved(false);

        return itemRepository.add(itemEntry);
    }

    public boolean removeItem(String name){
        var item = itemRepository.findByName(name);
        return itemRepository.remove(item);
    }

    public List<ItemEntry> listItems() {
        return itemRepository.getAll();
    }

    public ItemEntry approveItem(String name) throws Exception {
        AuthUtils.checkIfCurrenUserIsAdmin(authContext);

        var item = itemRepository.findByName(name);
        if(item == null)
            return null;

        item.setApproved(true);
        return itemRepository.update(item);
    }
}
