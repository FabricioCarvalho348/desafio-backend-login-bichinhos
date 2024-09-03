package bichinhos.desafio.DTOS;

public record UserDTO(
    Long id,
    String name,
    String email,
    String password
) {

    public UserDTO(Long id, String name, String email, String password){
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }
}
