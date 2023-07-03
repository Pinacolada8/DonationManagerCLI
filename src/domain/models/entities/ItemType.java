package domain.models.entities;

public class ItemType extends BaseEntity {
    private String name;

    public ItemType() {
        super();
    }

    public ItemType(String name) {
        super();
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
