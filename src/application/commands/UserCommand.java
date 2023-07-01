package application.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import domain.models.entities.User;
import domain.models.entities.UserRole;
import domain.services.UserService;
import picocli.CommandLine;

@CommandLine.Command(
        name = "user",
        subcommands = {CommandLine.HelpCommand.class}
)
public class UserCommand {
    private final ObjectMapper objectMapper;
    private final UserService userService;

    @Inject
    public UserCommand(ObjectMapper objectMapper, UserService userService) {
        this.objectMapper = objectMapper;
        this.userService = userService;
    }

    // ======================================================================== //

    @CommandLine.Command()
    public Integer list() throws Exception {
        var allItemTypes = userService.listUsers();
        System.out.println(objectMapper.writeValueAsString(allItemTypes));
        return 0;
    }

    @CommandLine.Command()
    Integer add(
            @CommandLine.Option(names = {"-e", "--email"}, description = "User e-mail", required = true) String email,
            @CommandLine.Option(names = {"-p", "--password"}, description = "User password", required = true) String password,
            @CommandLine.Option(names = {"-r", "--role"}, defaultValue = "COMMON") UserRole role,
            @CommandLine.Option(names = {"-f", "--firstName"}) String firstName,
            @CommandLine.Option(names = {"-l", "--lastName"}) String lastName
    ) throws Exception {
        var newUser = new User(email, password, role);
        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);

        newUser = userService.registerUser(newUser);
        System.out.println(objectMapper.writeValueAsString(newUser));
        return 0;
    }

    @CommandLine.Command()
    Integer remove(
            @CommandLine.Parameters(paramLabel = "<email>", description = "User e-mail") String email
    ) throws Exception {
        userService.removeUser(email);
        return 0;
    }
}
