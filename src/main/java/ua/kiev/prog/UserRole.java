package ua.kiev.prog;

public enum UserRole {
    ADMIN("Admin"), MANAGER("Manager"), USER("User");

    private String name;

    private UserRole(String name){
        this.name = name;
    }
    @Override
    public String toString() {
        return "ROLE_" + name();
    }
}
