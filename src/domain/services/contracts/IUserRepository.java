package domain.services.contracts;

import domain.models.entities.User;

public interface IUserRepository extends IRepository<User> {
    public User findByEmail(String email);
}
