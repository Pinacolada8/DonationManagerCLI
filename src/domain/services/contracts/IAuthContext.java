package domain.services.contracts;


import domain.models.entities.User;

public interface IAuthContext {
    public User getCurrentLoggedUser();
    public User getRequiredCurrentLoggedUser() throws Exception;

    public String login(String email, String password);

    public String logout();
}
