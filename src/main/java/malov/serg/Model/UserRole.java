package malov.serg.Model;

public enum UserRole {
    ADMIN, USER, COOK;

    @Override
    public String toString() {
        return "ROLE_" + name();
    }
}
