package bichinhos.desafio.controllers;

import bichinhos.desafio.DTOS.*;
import bichinhos.desafio.models.User;
import bichinhos.desafio.repositories.UserRepository;
import bichinhos.desafio.services.TokenService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bichinhos.desafio.services.UserServices;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
@RequestMapping("/user")
public class UserController {
    
    @Autowired
    UserServices service;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository repository;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationDTO dto){
        try{
            var usernamePassword = new UsernamePasswordAuthenticationToken(dto.email(), dto.password());
            var auth = this.authenticationManager.authenticate(usernamePassword);

            var token = tokenService.generatedToken((User) auth.getPrincipal());

            return new ResponseEntity<>(new TokenDTO(token), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> cadastrar(@RequestBody UserDTO dto){
        if(this.repository.findByEmail(dto.email()) != null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(dto.password());
        User novousuario = new User(dto.id(), dto.name(), dto.email(), encryptedPassword, dto.role());

        this.repository.save(novousuario);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/")
    public ResponseEntity create(@RequestBody UserDTO dto) {
        
        UserOutputDTO userOutputDTO = service.create(dto);
        
        
        ResponseEntity<UserOutputDTO> res = new ResponseEntity<UserOutputDTO>(userOutputDTO, null, 201);
        return res;
    }

    @GetMapping("/")
    public ResponseEntity list() {
        
        List<UserOutputDTO> list = service.list();

        ResponseEntity<List<UserOutputDTO>> res = new ResponseEntity<>(list, null, 200);
        
        return res;
    }

    @GetMapping("/{id}")
    public ResponseEntity read(@PathVariable Long id) {

        UserOutputDTO user = service.read(id);

        ResponseEntity<UserOutputDTO> res = new ResponseEntity<UserOutputDTO>(user, null, 200);
        
        return res;

    }
    
    @PutMapping("/")
    public ResponseEntity Update(@RequestBody UserDTO dto) {
                
        UserOutputDTO userOutputDTO = service.update(dto);
        
        ResponseEntity<UserOutputDTO> res = new ResponseEntity<UserOutputDTO>(userOutputDTO, null, 201);
        return res;
    }

    @DeleteMapping("{id}")
    public ResponseEntity Delete(@PathVariable Long id){

        service.delete(id);

        return ResponseEntity.noContent().build();

    }
    
}
