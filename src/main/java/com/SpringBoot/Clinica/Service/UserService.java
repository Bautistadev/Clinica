package com.SpringBoot.Clinica.Service;

import com.SpringBoot.Clinica.Cache.Service.CacheUserService;
import com.SpringBoot.Clinica.Entity.UserEntity;
import com.SpringBoot.Clinica.Repository.UserRepository;
import com.SpringBoot.Clinica.Service.Mapper.UserMapper;
import com.SpringBoot.Clinica.model.UserDTO;
import com.SpringBoot.Clinica.model.UserRequestDTO;
import org.apache.catalina.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.IllegalFormatException;
import java.util.List;
import java.util.Objects;


@Service
public class UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private CacheUserService cacheService;

    private UserRepository userRepository;
    private UserMapper userMapper;



    @Autowired
    public UserService(UserRepository userRepository,UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    /**
     * @operation: retrieve all
     * @Return: UserDTO List
     * */

    public List<UserDTO> findAll(){

        /**
         * if the user list exists in the cache, then return the cache value
         * */
        if(this.cacheService.findAll() != null)
            return this.cacheService.findAll();


        List <UserDTO> response =  new ArrayList<>();
        Iterable<UserEntity> iterable = this.userRepository.findAll();

        iterable.forEach(i ->{
            try {
                response.add(this.userMapper.map(i));
            } catch (Exception e) {
                LOGGER.error("Error : UserService : findAll : "+ LocalDateTime.now());
                throw new RuntimeException(e);

            }
        });

        LOGGER.trace(String.format("Info : UserService : findAll : "+LocalDateTime.now()));

        /**
         * insert the list in the cache
         * */
        this.cacheService.insert(CacheUserService.FIND_ALL_KEY,response);
        return response;

    }

    private void updateList(List<UserDTO> list){
        this.cacheService.insert(CacheUserService.FIND_ALL_KEY,list);
    }

    /**
     * @operation: save
     * @param: UserRequestDTO
     * @return: UserDTO
     * */
    public UserDTO save(UserRequestDTO userRequestDTO) throws Exception {
        UserEntity userSave = null;
        UserDTO response;

        try {
            userSave = this.userMapper.map(userRequestDTO);
            response = this.userMapper.map(this.userRepository.save(userSave));
        } catch (Exception e) {
            LOGGER.error("Error : UserService : save : "+ LocalDateTime.now());
            throw new RuntimeException(e);
        }


        LOGGER.trace(String.format("Info : UserService : save : "+LocalDateTime.now()) + " : ",userSave);

        return response;
    }

    /**
     * @operation: update user
     * @param: UserDTO
     * @return: UserDTO
     * */
    public UserDTO update(UserDTO userDTO){
        UserEntity userUpdate = null;
        try {
            /**
             * if the cache value exists, then update the cache
             * */
            if(this.cacheService.findById(userDTO.getId().toString()) !=null)
                this.cacheService.update(userDTO.getId().toString(),userDTO);
            else
                System.out.println("NO ENTRA");

            userUpdate = this.userMapper.map(userDTO);
            this.userRepository.update(userUpdate);
            LOGGER.trace(String.format("Info : UserService : save : "+LocalDateTime.now()) + " : ",userUpdate);
            return userDTO;
        }catch (Exception e){
            LOGGER.error("Error : UserService : update : "+ LocalDateTime.now());
            throw new RuntimeException(e);
        }
    }

    /**
     * @operation: find user by id
     * @param: Integer
     * @return: UserDTO
     * */
    public UserDTO findById(Integer id){
        try {

            /**
             * if the user list exists in the cache, then return the cache value
             * */
            if(this.cacheService.findById(id.toString()) != null){
                System.out.println("cache");
                LOGGER.trace(String.format("Info: UserService : findById : "+LocalDateTime.now()+ " : "));
                return this.cacheService.findById(id.toString());
            }

            UserEntity userEntity = this.userRepository.findById(id).get();
            UserDTO response = this.userMapper.map(userEntity);
            LOGGER.trace(String.format("Info: UserService : findById : "+LocalDateTime.now()+ " : ",response));
            /**
             * save the value in the cache
             * */
            this.cacheService.insert(response.getId().toString(),response);

            return response;
        } catch (Exception e) {
            LOGGER.error("Error : findById : save : "+ LocalDateTime.now());
            throw new RuntimeException(e);
        }
    }

    /**
     * @operation: exists user by id
     * @param: Integer
     * @return: Boolean
     * */
    public boolean existsById(Integer id){
        LOGGER.trace("Info : UserService : existsById : "+LocalDateTime.now()+" : ",id);
        return this.userRepository.existsById(id);
    }

    /**
     * @operation: count users
     * @return: Number
     * */
    public long count(){
        LOGGER.trace("Info : UserService : existsById : "+LocalDateTime.now());
        return this.userRepository.count();
    }

    /**
     * @operation: delete by id
     * @param: Integer
     * @return: Boolean
     * */
    public Boolean deleteById(Integer id){

        try{
            this.userRepository.deleteById(id);
            this.cacheService.remove(id);
            LOGGER.trace("Info : UserRepository class : deleteById : "+LocalDateTime.now().toString() +" : ",true);
            return true;
        }catch (IllegalFormatException e){
            LOGGER.error("Error : UserRepository class : deleteById : "+LocalDateTime.now().toString());
        }catch (Exception e){
            LOGGER.error("Error : UserRepository class : deleteById : "+LocalDateTime.now().toString());
        }finally {
            return false;
        }


    }

    /**
     * @operation: delete by id
     * @param: UserDTO
     * @return: Boolean
     * */
    public boolean delete(UserDTO userDTO){
        try {
            UserEntity user = this.userMapper.map(userDTO);
            System.out.println("Eliminado : "+ user.getId());
            this.cacheService.remove(user.getId());
            this.userRepository.delete(user);
            LOGGER.trace("Info : UserRepository class : delete : "+LocalDateTime.now().toString() +" : ",true);
            return true;
        } catch (Exception e) {
            LOGGER.error("Error : UserRepository class : delete : "+LocalDateTime.now().toString());
            throw new RuntimeException(e);
        }
    }

    /**
     * @operation: delete all users by id list
     * @param: Integer List
     * @return: Boolean
     * */
    public boolean deleteAllById(List<Integer> list ){
        this.userRepository.deleteAllById(list);
        list.stream().forEach(e ->{
           this.cacheService.remove(e);
        });
        LOGGER.trace("Info : UserRepository class : deleteAllById : "+LocalDateTime.now());
        return true;
    }

    /**
     * @operation: delete all users by userEntity object list
     * @param: UserEntity object List
     * @return: Boolean
     * */
    @CacheEvict(value = "users", allEntries = true)
    public boolean deleteAll(List<UserEntity> list){
        this.userRepository.deleteAll(list);
        list.stream().forEach(e ->{
            this.cacheService.remove(e.getId());
        });
        LOGGER.trace("Info : UserRepository class : deleteAll : "+LocalDateTime.now());
        return true;
    }

    /**
     * @operation: find by username
     * @param: String username
     * @return: UserDTO
     * */
    @Cacheable(cacheManager = "only_Entity",cacheNames = "users")
    public UserDTO findByUsername(String username){
       UserEntity user = this.userRepository.findByUsername(username).get();

        try {
            UserDTO response = this.userMapper.map(user);
            LOGGER.trace(String.format("Info : UserRepository class : findByUsername : "+LocalDateTime.now()+" : ",response));
            return response;
        } catch (Exception e) {
            LOGGER.error("Error : UserRepository class : findByUsername : "+LocalDateTime.now().toString());
            throw new RuntimeException(e);
        }
    }


}
