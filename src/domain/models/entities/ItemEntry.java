package domain.models.entities;

public class ItemEntry extends BaseEntity {
    private String name;
    private int quantity;
    private String description;
    private String city;

    private Integer itemTypeId;
    private Integer donorUserId;

    private boolean isApproved;

    // =================================================

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getItemTypeId() {
        return itemTypeId;
    }

    public void setItemTypeId(Integer itemTypeId) {
        this.itemTypeId = itemTypeId;
    }

    public Integer getDonorUserId() {
        return donorUserId;
    }

    public void setDonorUserId(Integer donorUserId) {
        this.donorUserId = donorUserId;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public void setApproved(boolean approved) {
        isApproved = approved;
    }

    // =================================================
}
