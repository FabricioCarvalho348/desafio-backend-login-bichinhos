package bichinhos.desafio.DTOS;

public record UserOutputDTO(
    Long id,
    String name,
    String email
) {

    public UserOutputDTO(Long id, String name, String email){
        this.id = id;
        this.name = name;
        this.email = email;
    }
}
