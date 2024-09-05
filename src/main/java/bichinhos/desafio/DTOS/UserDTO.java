package bichinhos.desafio.DTOS;

import bichinhos.desafio.enums.Role;

public record UserDTO(
    Long id,
    String name,
    String email,
    String password,
    Role role
) {

    public UserDTO(Long id, String name, String email, String password, Role role){
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }
}
