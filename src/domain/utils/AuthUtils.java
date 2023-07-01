package domain.utils;

import domain.models.entities.User;
import domain.models.entities.UserRole;
import domain.services.contracts.IAuthContext;

public class AuthUtils {
    public static User checkIfCurrenUserIsAdmin(IAuthContext authContext) throws Exception {
        var currentUser = authContext.getRequiredCurrentLoggedUser();
        if(currentUser == null || currentUser.getRole() != UserRole.ADMIN)
            throw new Exception("Current user doesn't have permission to execute this operation");

        return currentUser;
    }
}
