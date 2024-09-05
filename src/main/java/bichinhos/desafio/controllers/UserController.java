package bichinhos.desafio.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bichinhos.desafio.DTOS.UserDTO;
import bichinhos.desafio.DTOS.UserOutputDTO;
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

        service.Delete(id);

        return ResponseEntity.noContent().build();

    }
    
}
