package application.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import domain.models.DTOs.NewItemDTO;
import domain.services.ItemService;
import picocli.CommandLine;

@CommandLine.Command(
        name = "item",
        subcommands = {CommandLine.HelpCommand.class}
)
public class ItemCommand {
    private final ObjectMapper objectMapper;
    private final ItemService itemService;

    @Inject
    public ItemCommand(ObjectMapper objectMapper, ItemService itemService) {
        this.objectMapper = objectMapper;
        this.itemService = itemService;
    }

    // ======================================================================== //

    @CommandLine.Command()
    public Integer list() throws Exception {
        var allValues = itemService.listItems();
        System.out.println(objectMapper.writeValueAsString(allValues));
        return 0;
    }

    @CommandLine.Command()
    Integer add(
            @CommandLine.Option(names = {"-n", "--name"}, required = true) String name,
            @CommandLine.Option(names = {"-q", "--quantity"}) Integer quantity,
            @CommandLine.Option(names = {"-d", "--description"}) String description,
            @CommandLine.Option(names = {"-c", "--city"}) String city,
            @CommandLine.Option(names = {"-t", "--type"}, description = "Item Type", required = true) String itemType
    ) throws Exception {
        var newValue = new NewItemDTO();
        newValue.name = name;
        newValue.quantity = quantity;
        newValue.description = description;
        newValue.city = city;
        newValue.itemType = itemType;

        var newItem = itemService.registerItem(newValue);
        System.out.println(objectMapper.writeValueAsString(newItem));
        return 0;
    }

    @CommandLine.Command()
    Integer remove(@CommandLine.Parameters(paramLabel = "<name>", description = "Item name") String name
    ) throws Exception {
        itemService.removeItem(name);
        return 0;
    }

    @CommandLine.Command()
    Integer approve(@CommandLine.Parameters(paramLabel = "<name>", description = "Item name") String name
    ) throws Exception {
        var value = itemService.approveItem(name);
        System.out.println(objectMapper.writeValueAsString(value));
        return 0;
    }
}
