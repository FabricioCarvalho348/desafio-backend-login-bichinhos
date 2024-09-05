package bichinhos.desafio.enums;

public enum Role {

    ADMIN("admin"),
    DEV_TEAM("dev_team");

    private String role;

    Role(String role){
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
