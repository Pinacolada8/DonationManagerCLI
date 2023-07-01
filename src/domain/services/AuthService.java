package domain.services;

import domain.models.entities.User;
import domain.services.contracts.IUserRepository;

public class AuthService {
    private IUserRepository userRepository;

    public AuthService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User login(String email, String password) {
        return null;
    }
}
