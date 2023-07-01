package domain.models.entities;

public class User extends BaseEntity {
    private String email;
    private String password;
    private String firstName;
    private String lastName;

    private UserRole role;

    // =================================================

    public User() {
        super();
    }

    public User(String email, String password, UserRole role) {
        this();
        this.email = email;
        this.password = password;
        this.role = role;
    }
// =================================================

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
}
