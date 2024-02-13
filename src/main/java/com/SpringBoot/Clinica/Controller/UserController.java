package com.SpringBoot.Clinica.Controller;

import com.SpringBoot.Clinica.Service.UserService;
import com.SpringBoot.Clinica.api.UsersApiDelegate;
import com.SpringBoot.Clinica.model.RetriveAttributeUser200Response;
import com.SpringBoot.Clinica.model.UserDTO;
import com.SpringBoot.Clinica.model.UserListDTO;
import com.SpringBoot.Clinica.model.UserRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;


public class UserController implements UsersApiDelegate {

    @Autowired
    private CacheManager cacheManager;

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return UsersApiDelegate.super.getRequest();
    }

    @Override
    public ResponseEntity<UserDTO> createUser(UserRequestDTO userRequestDTO) {
        return UsersApiDelegate.super.createUser(userRequestDTO);
    }

    @Override
    public ResponseEntity<Void> removeUser(Integer userId) {
        return UsersApiDelegate.super.removeUser(userId);
    }

    @Override
    public ResponseEntity<UserListDTO> retriveAllUser() {
        ConcurrentHashMap<?,?> cache = (ConcurrentHashMap<?, ?>) this.cacheManager.getCache("users").getNativeCache();

        cache.forEach((key,object)->{
            System.out.println(key + " --- "+ object);
        });

        try {
            UserListDTO userListDTO = new UserListDTO().items(this.userService.findAll());
            return ResponseEntity.ok().body(userListDTO);
        }catch (HttpClientErrorException.Unauthorized e){
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(null);
        }catch (HttpClientErrorException.BadRequest e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }catch (HttpClientErrorException.Forbidden e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }catch (HttpClientErrorException.NotFound e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

    }

    @Override
    public ResponseEntity<RetriveAttributeUser200Response> retriveAttributeUser(Integer attribute, Integer value) {
        return UsersApiDelegate.super.retriveAttributeUser(attribute, value);
    }

    @Override
    public ResponseEntity<UserDTO> retriveUser(Integer userId) {
        try {
            return ResponseEntity.ok().body(this.userService.findById(userId));
        }catch (HttpClientErrorException.Unauthorized e){
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(null);
        }catch (HttpClientErrorException.BadRequest e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }catch (HttpClientErrorException.Forbidden e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }catch (HttpClientErrorException.NotFound e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @Override
    public ResponseEntity<UserDTO> updateUser(UserDTO userDTO) {
        try {
            return ResponseEntity.ok().body(this.userService.update(userDTO));
        }catch (HttpClientErrorException.Unauthorized e){
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(null);
        }catch (HttpClientErrorException.BadRequest e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }catch (HttpClientErrorException.Forbidden e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }catch (HttpClientErrorException.NotFound e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
