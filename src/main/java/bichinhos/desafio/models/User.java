package bichinhos.desafio.models;

import bichinhos.desafio.DTOS.UserDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor @NoArgsConstructor @Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 60)
    private String name;
    private String email;
    private String password;

    public User(UserDTO dto ){
        this.id = dto.id();
        this.email = dto.email();
        this.name = dto.name();
        this.password = dto.password();
    
    }
}
