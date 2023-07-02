package Infrastructure;

import com.google.inject.Inject;
import domain.models.entities.User;
import domain.services.contracts.IAuthContext;
import domain.services.contracts.IUserRepository;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;

public class FileSystemAuthContext implements IAuthContext {

    protected File authContextFile = new File(".", ".donAuth");

    private final IUserRepository userRepository;

    // ================================================================= //

    @Inject
    public FileSystemAuthContext(IUserRepository userRepository) {

        this.userRepository = userRepository;
    }

    // ================================================================= //

    private void setCurrentUserId(@Nullable Integer id) {
        try {
            if (id == null)
                this.authContextFile.delete();
            else
                Files.write(this.authContextFile.toPath(), id.toString().getBytes());
        } catch (IOException e) {
            System.err.println("Error saving current user id");
            e.printStackTrace();
        }
    }

    @Override
    public User getRequiredCurrentLoggedUser() throws Exception {
        try {
            var fileContent = new String(Files.readAllBytes(this.authContextFile.toPath()));
            var currentUserId = Integer.parseInt(fileContent);
            return userRepository.find(currentUserId);
        } catch (Exception e) {
            throw new Exception("There is no current logged user", e);
        }
    }

    @Override
    public User getCurrentLoggedUser() {
        try {
            return this.getRequiredCurrentLoggedUser();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String login(String email, String password) {
        if (getCurrentLoggedUser() != null)
            return "There is an user already logged in.";

        var user = userRepository.findByEmail(email);
        if (!Objects.equals(user.getPassword(), password))
            return "Wrong Password";

        setCurrentUserId(user.getId());
        return null;
    }

    @Override
    public String logout() {
        var currentUser = getCurrentLoggedUser();

        if (currentUser == null)
            return "There is no user logged in.";

        setCurrentUserId(null);

        return null;
    }
}
