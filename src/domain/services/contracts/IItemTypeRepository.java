package domain.services.contracts;

import domain.models.entities.ItemType;

public interface IItemTypeRepository extends IRepository<ItemType> {
    public ItemType findByName(String name);

    public ItemType add(String name);
}
