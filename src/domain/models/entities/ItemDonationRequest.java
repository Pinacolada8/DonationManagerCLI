package domain.models.entities;

public class ItemDonationRequest extends BaseEntity {
    private Integer userId;
    private Integer itemEntryId;

    // =================================================

    public ItemDonationRequest() {
        super();
    }

    public ItemDonationRequest(Integer userId, Integer itemEntryId) {
        this();
        this.userId = userId;
        this.itemEntryId = itemEntryId;
    }

    // =================================================

    public Integer getItemEntryId() {
        return itemEntryId;
    }

    public void setItemEntryId(Integer itemEntryId) {
        this.itemEntryId = itemEntryId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
