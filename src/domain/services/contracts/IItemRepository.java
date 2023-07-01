package domain.services.contracts;

import domain.models.entities.ItemEntry;

public interface IItemRepository extends IRepository<ItemEntry> {
    public ItemEntry findByName(String name);
}
