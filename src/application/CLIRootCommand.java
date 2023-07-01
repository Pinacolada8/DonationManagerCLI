package application;

import application.commands.DonationCommand;
import application.commands.ItemCommand;
import application.commands.ItemTypeCommand;
import application.commands.UserCommand;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import domain.services.contracts.IAuthContext;
import jakarta.inject.Inject;
import picocli.CommandLine;
import picocli.CommandLine.Parameters;

import java.util.concurrent.Callable;


@CommandLine.Command(
        name = "don",
        subcommands = { CommandLine.HelpCommand.class, UserCommand.class, ItemTypeCommand.class, ItemCommand.class, DonationCommand.class}
)
public class CLIRootCommand implements Callable<Integer> {
    private final ObjectMapper objectMapper;
    private final IAuthContext authContext;

    @Inject
    public CLIRootCommand(ObjectMapper objectMapper, IAuthContext authContext) {
        this.objectMapper = objectMapper;
        this.authContext = authContext;
    }

    // ======================================================================== //

    @CommandLine.Command(name = "login")
    Integer login(
            @Parameters(paramLabel = "<email>", description = "User e-mail") String email,
            @Parameters(paramLabel = "<password>", description = "User password") String password
    ) {
        var errMsg = authContext.login(email, password);
        if (errMsg != null) {
            System.err.println(errMsg);
            return -1;
        }
        return 0;
    }

    @CommandLine.Command(name = "logout")
    Integer logout() {
        var errMsg = authContext.logout();
        if (errMsg != null) {
            System.err.println(errMsg);
            return -1;
        }
        return 0;
    }

    @CommandLine.Command(name = "whoami")
    Integer whoami() {
        var currentUser = authContext.getCurrentLoggedUser();

        if (currentUser != null) {
            try {
                System.out.println(objectMapper.writeValueAsString(currentUser));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                return -1;
            }
        } else {
            System.out.println("You are not logged in!");
        }
        return 0;
    }

    // ======================================================================== //

    @Override
    public Integer call() throws Exception {
        System.out.println("Execute the command 'don help' to see all available commands");
        return 0;
    }
}
