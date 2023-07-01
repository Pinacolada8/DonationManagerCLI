package domain.services;

import com.google.inject.Inject;
import domain.models.entities.ItemDonationRequest;
import domain.models.entities.ItemEntry;
import domain.models.entities.User;
import domain.services.contracts.IAuthContext;
import domain.services.contracts.IItemDonationRequestRepository;
import domain.services.contracts.IItemRepository;
import domain.services.contracts.IUserRepository;

import java.util.List;
import java.util.stream.Collectors;

public class ItemDonationRequestService {
    private final IItemDonationRequestRepository donationRequestRepository;
    private final IItemRepository itemRepository;
    private final IUserRepository userRepository;
    private final IAuthContext authContext;

    @Inject
    public ItemDonationRequestService(IItemDonationRequestRepository interestRepository, IItemRepository itemRepository, IUserRepository userRepository, IAuthContext authContext) {
        this.donationRequestRepository = interestRepository;
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
        this.authContext = authContext;
    }

    // ====================================================================================== //

    public ItemDonationRequest requestItemDonation(String itemName) {
        var currentUser = authContext.getCurrentLoggedUser();
        var item = itemRepository.findByName(itemName);

        var donationRequest = new ItemDonationRequest(currentUser.getId(), item.getId());
        return donationRequestRepository.add(donationRequest);
    }

    public boolean giveUpDonationRequest(String itemName) {
        var currentUser = authContext.getCurrentLoggedUser();
        var item = itemRepository.findByName(itemName);

        var donationRequest = donationRequestRepository.findFirst(x -> x.getUserId().equals(currentUser.getId())
                && x.getItemEntryId().equals(item.getId()));
        return donationRequestRepository.remove(donationRequest);
    }

    // ====================================================================================== //

    public List<User> listDonationRequestsForItem(String itemName) {
        var item = itemRepository.findByName(itemName);
        var donationRequests = donationRequestRepository.find(x -> x.getItemEntryId().equals(item.getId()));
        var userIds = donationRequests.stream().map(ItemDonationRequest::getUserId).collect(Collectors.toList());
        return userRepository.find(x -> userIds.contains(x.getId()));
    }

    public ItemEntry approveDonationRequest(String itemName, String requestingUserEmail) throws Exception {
        var currentUser = authContext.getCurrentLoggedUser();
        var item = itemRepository.findByName(itemName);

        if(!item.getDonorUserId().equals(currentUser.getId()))
            throw new Exception("Cannot approve a donation request of an item that doesn't belong to you");

        var requestingUser = userRepository.findByEmail(requestingUserEmail);
        var donationRequest = donationRequestRepository.findFirst(x -> x.getUserId().equals(requestingUser.getId())
                && x.getItemEntryId().equals(item.getId()));

        if(donationRequest == null)
            throw new Exception("There is no donation request by this user for this item");

        donationRequestRepository.remove(donationRequest);
        itemRepository.remove(item);
        return item;
    }
}
