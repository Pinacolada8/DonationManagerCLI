package domain.services;

import com.google.inject.Inject;
import domain.models.entities.User;
import domain.services.contracts.IAuthContext;
import domain.services.contracts.IUserRepository;
import domain.utils.AuthUtils;

import java.util.List;

public class UserService {
    private final IUserRepository userRepository;
    private final IAuthContext authContext;

    @Inject
    public UserService(IUserRepository userRepository, IAuthContext authContext) {
        this.userRepository = userRepository;
        this.authContext = authContext;
    }

    public User registerUser(User newUser) throws Exception {
        AuthUtils.checkIfCurrenUserIsAdmin(authContext);
        return userRepository.add(newUser);
    }

    public boolean removeUser(String email) throws Exception {
        AuthUtils.checkIfCurrenUserIsAdmin(authContext);
        var userToDelete = userRepository.findByEmail(email);
        return userRepository.remove(userToDelete);
    }

    public List<User> listUsers() {
        return userRepository.getAll();
    }
}
