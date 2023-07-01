package Infrastructure;

import domain.models.entities.User;
import domain.models.entities.UserRole;
import domain.services.contracts.IUserRepository;

import java.io.IOException;
import java.util.Objects;

public class FileSystemUserRepository extends FileSystemRepository<User> implements IUserRepository {

    public FileSystemUserRepository(String dbFolderPath, String dbFilename) throws IOException {
        super(dbFolderPath, dbFilename);

        var allUsers = getAll();
        if(allUsers == null || allUsers.size() == 0)
            add(new User("admin", "123456", UserRole.ADMIN));
    }

    @Override
    public User findByEmail(String email) {
        return findFirst(x -> Objects.equals(x.getEmail(), email));
    }
}
