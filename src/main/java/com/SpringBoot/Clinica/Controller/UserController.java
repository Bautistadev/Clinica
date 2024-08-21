package com.SpringBoot.Clinica.Controller;

import com.SpringBoot.Clinica.Cache.Service.CacheUserService;
import com.SpringBoot.Clinica.Service.UserService;
import com.SpringBoot.Clinica.api.UsersApiDelegate;

import com.SpringBoot.Clinica.model.UserDTO;
import com.SpringBoot.Clinica.model.UserListDTO;
import com.SpringBoot.Clinica.model.UserRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;


public class UserController implements UsersApiDelegate {



    @Autowired
    private CacheUserService cacheService;

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
        try {
            UserDTO response = this.userService.save(userRequestDTO);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (HttpClientErrorException.Unauthorized e){
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).build();
        }catch (HttpClientErrorException.BadRequest e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }catch (HttpClientErrorException.Forbidden e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }catch (HttpClientErrorException.NotFound e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public ResponseEntity<Void> removeUser(Integer userId) {

        try {
            this.userService.deleteById(userId);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (HttpClientErrorException.Unauthorized e){
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).build();
        }catch (HttpClientErrorException.BadRequest e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }catch (HttpClientErrorException.Forbidden e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }catch (HttpClientErrorException.NotFound e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResponseEntity<UserListDTO> retriveAllUser() {



        try {
            UserListDTO userListDTO = new UserListDTO().items(this.userService.findAll());
            return ResponseEntity.ok().body(userListDTO);
        }catch (HttpClientErrorException.Unauthorized e){
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).build();
        }catch (HttpClientErrorException.BadRequest e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }catch (HttpClientErrorException.Forbidden e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }catch (HttpClientErrorException.NotFound e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }


    @Override
    public ResponseEntity<UserListDTO> retriveAttributeUser(String attribute, String value) {
        return UsersApiDelegate.super.retriveAttributeUser(attribute, value);
    }

    @Override
    public ResponseEntity<UserDTO> retriveUser(Integer userId) {

        try {
            return ResponseEntity.ok().body(this.userService.findById(userId));
        }catch (HttpClientErrorException.Unauthorized e){
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).build();
        }catch (HttpClientErrorException.BadRequest e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }catch (HttpClientErrorException.Forbidden e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }catch (HttpClientErrorException.NotFound e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Override
    public ResponseEntity<UserDTO> updateUser(UserDTO userDTO) {
        try {
            return ResponseEntity.ok().body(this.userService.update(userDTO));
        }catch (HttpClientErrorException.Unauthorized e){
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).build();
        }catch (HttpClientErrorException.BadRequest e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }catch (HttpClientErrorException.Forbidden e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }catch (HttpClientErrorException.NotFound e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Override
    public ResponseEntity<Void> removeUserByUsername(UserDTO userDTO) {
        try {
            this.userService.delete(userDTO);
            return ResponseEntity.ok().build();
        }catch (HttpClientErrorException.Unauthorized e){
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).build();
        }catch (HttpClientErrorException.BadRequest e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }catch (HttpClientErrorException.Forbidden e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }catch (HttpClientErrorException.NotFound e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
