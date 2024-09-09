package bichinhos.desafio.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bichinhos.desafio.DTOS.AuthenticationDTO;
import bichinhos.desafio.DTOS.TokenDTO;
import bichinhos.desafio.DTOS.UserDTO;
import bichinhos.desafio.models.User;
import bichinhos.desafio.repositories.UserRepository;
import bichinhos.desafio.services.TokenService;
import bichinhos.desafio.services.UserServices;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping
public class AuthController {
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

    @GetMapping("/")
    public ResponseEntity<?> googleLogin(@AuthenticationPrincipal OAuth2User oAuth2User) {
        
        Map<String, Object> userdetails = oAuth2User.getAttributes();

        String name = (String) userdetails.get("name");
        String email = (String) userdetails.get("email");
        User user = new User();

        user.setEmail(email);
        user.setName(name);

        var token = tokenService.generatedToken(user);
        var res = new ResponseEntity<>(token, null, 200);

        return res;
    }
    
}
