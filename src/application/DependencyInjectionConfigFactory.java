package application;

import Infrastructure.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.inject.*;
import domain.services.ItemDonationRequestService;
import domain.services.ItemService;
import domain.services.ItemTypeService;
import domain.services.UserService;
import domain.services.contracts.*;
import picocli.CommandLine;

import java.io.File;
import java.nio.file.Path;

public class DependencyInjectionConfigFactory implements CommandLine.IFactory {

    //    static final String DB_FOLDER_PATH = Path.of(System.getProperty("user.dir"), "database").toString();
    static final String DB_FOLDER_PATH = Path.of(new File(".").getAbsolutePath(), "database").toString();
    static final String USER_DB_FILENAME = "users.json";
    static final String ITEM_TYPE_DB_FILENAME = "item-types.json";
    static final String ITEM_DB_FILENAME = "items.json";
    static final String DONATION_REQUEST_DB_FILENAME = "donation-requests.json";

    // ============================================================================ //

    static class CLIDIModule extends AbstractModule {

        @Override
        protected void configure() {
            try {
                bind(ObjectMapper.class).toInstance(new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT));
                bind(IUserRepository.class).toInstance(new FileSystemUserRepository(DB_FOLDER_PATH, USER_DB_FILENAME));
                bind(IItemTypeRepository.class).toInstance(new FileSystemItemTypeRepository(DB_FOLDER_PATH, ITEM_TYPE_DB_FILENAME));
                bind(IItemRepository.class).toInstance(new FileSystemItemRepository(DB_FOLDER_PATH, ITEM_DB_FILENAME));
                bind(IItemDonationRequestRepository.class).toInstance(new FileSystemItemDonationRequestRepository(DB_FOLDER_PATH, DONATION_REQUEST_DB_FILENAME));
                bind(UserService.class);
                bind(ItemTypeService.class);
                bind(ItemService.class);
                bind(ItemDonationRequestService.class);
                bind(IAuthContext.class).to(FileSystemAuthContext.class);
            } catch (Exception e) {
                System.err.println("Error configuring dependency injection bindings");
                e.printStackTrace();
            }
        }
    }

    // ============================================================================ //

    private final Injector injector = Guice.createInjector(new CLIDIModule());

    @Override
    public <K> K create(Class<K> aClass) throws Exception {
        try {
            return injector.getInstance(aClass);
        } catch (ConfigurationException ex) { // no implementation found in Guice configuration
            return CommandLine.defaultFactory().create(aClass); // fallback if missing
        }
    }
}
