package bichinhos.desafio.services;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bichinhos.desafio.DTOS.UserDTO;
import bichinhos.desafio.DTOS.UserOutputDTO;
import bichinhos.desafio.models.User;
import bichinhos.desafio.repositories.UserRepository;
import jakarta.transaction.Transactional;

@Service   
public class UserServices {
    
    @Autowired
    private UserRepository repository;

    @Transactional
    public UserOutputDTO create(UserDTO dto){
        User user = new User(dto);   

        User userCreated = repository.save(user);


        return new UserOutputDTO(userCreated.getId(), userCreated.getName(), userCreated.getEmail());
    }

    public UserOutputDTO read(Long id){
        if(repository.existsById(id)){
            User user = repository.findById(id).get();

            return new UserOutputDTO(user.getId(), user.getName(), user.getEmail());
        }
        return null;

    }
    public List<UserOutputDTO> list(Long id){
        List<User> list = repository.findAll();
        
        return list.stream().map(user -> new UserOutputDTO(user.getId(), user.getName(), user.getEmail())).toList();
    }
    
    public UserOutputDTO update(UserDTO dto){
        if(repository.existsById(dto.id())){
            User user = new User(dto);            
            User userCreated = repository.save(user);

            return new UserOutputDTO(userCreated.getId(), userCreated.getName(), userCreated.getEmail());
        }
        return null;
    }

    public void Delete(Long id){
        
        repository.deleteById(id);

    }

}
