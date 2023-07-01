package application.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import domain.models.DTOs.NewItemDTO;
import domain.services.ItemDonationRequestService;
import picocli.CommandLine;

@CommandLine.Command(
        name = "donation",
        subcommands = {CommandLine.HelpCommand.class}
)
public class DonationCommand {
    private final ObjectMapper objectMapper;
    private final ItemDonationRequestService donationService;

    @Inject
    public DonationCommand(ObjectMapper objectMapper, ItemDonationRequestService donationService) {
        this.objectMapper = objectMapper;
        this.donationService = donationService;
    }

    // ======================================================================== //

    @CommandLine.Command()
    public Integer request(@CommandLine.Parameters(paramLabel = "<name>", description = "Item name") String name
    ) throws Exception {
        var result = donationService.requestItemDonation(name);
        System.out.println(objectMapper.writeValueAsString(result));
        return 0;
    }

    @CommandLine.Command()
    public Integer giveUp(@CommandLine.Parameters(paramLabel = "<name>", description = "Item name") String name
    ) throws Exception {
        var result = donationService.giveUpDonationRequest(name);
        System.out.println(objectMapper.writeValueAsString(result));
        return 0;
    }

    @CommandLine.Command()
    public Integer list(@CommandLine.Parameters(paramLabel = "<name>", description = "Item name") String name
    ) throws Exception {
        var allValues = donationService.listDonationRequestsForItem(name);
        System.out.println(objectMapper.writeValueAsString(allValues));
        return 0;
    }

    @CommandLine.Command()
    Integer approve(
            @CommandLine.Parameters(paramLabel = "<item-name>", description = "Item name") String itemName,
            @CommandLine.Parameters(paramLabel = "<user-email>", description = "User email") String userEmail
    ) throws Exception {
        var value = donationService.approveDonationRequest(itemName, userEmail);
        System.out.println(objectMapper.writeValueAsString(value));
        return 0;
    }
}
