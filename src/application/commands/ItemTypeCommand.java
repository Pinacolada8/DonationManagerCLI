package application.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import domain.services.ItemTypeService;
import picocli.CommandLine;

@CommandLine.Command(
        name = "itemType",
        subcommands = {CommandLine.HelpCommand.class}
)
public class ItemTypeCommand {
    private final ObjectMapper objectMapper;
    private final ItemTypeService itemTypeService;

    @Inject
    public ItemTypeCommand(ObjectMapper objectMapper, ItemTypeService itemTypeService) {
        this.objectMapper = objectMapper;
        this.itemTypeService = itemTypeService;
    }

    // ======================================================================== //

    @CommandLine.Command()
    public Integer list() throws Exception {
        var allItemTypes = itemTypeService.listItemTypes();
        System.out.println(objectMapper.writeValueAsString(allItemTypes));
        return 0;
    }

    @CommandLine.Command()
    Integer add(
            @CommandLine.Parameters(paramLabel = "<name>", description = "Item type name") String name
    ) throws Exception {
        var value = itemTypeService.registerItemType(name);
        System.out.println(objectMapper.writeValueAsString(value));
        return 0;
    }

    @CommandLine.Command()
    Integer remove(
            @CommandLine.Parameters(paramLabel = "<name>", description = "Item type name") String name
    ) throws Exception {
        itemTypeService.removeItemType(name);
        return 0;
    }
}
