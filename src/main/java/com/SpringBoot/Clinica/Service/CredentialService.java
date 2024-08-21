package com.SpringBoot.Clinica.Service;

import com.SpringBoot.Clinica.Entity.CredentialEntity;
import com.SpringBoot.Clinica.Repository.CredentialRepository;
import com.SpringBoot.Clinica.Service.Mapper.CredentialMapper;
import com.SpringBoot.Clinica.model.CredentialDTO;
import com.SpringBoot.Clinica.model.CredentialRequestDTO;
import io.swagger.models.auth.In;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.events.Event;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
public class CredentialService {

    private static Logger LOGGER = LoggerFactory.getLogger(CredentialService.class);

    private CredentialRepository credentialRepository;

    private CredentialMapper credentialMapper;

    public CredentialService(CredentialRepository credentialRepository, CredentialMapper credentialMapper) {
        this.credentialRepository = credentialRepository;
        this.credentialMapper = credentialMapper;
    }

    /**
     * @operation: save
     * @param: CredentialRequestDTO
     * @return: CredentialDTO
     * */
    //@CacheEvict(cacheManager = "cacheManagerOnly", cacheNames = "credentials",allEntries = true)
    public CredentialDTO save(CredentialRequestDTO credentialRequestDTO){
        CredentialEntity credentialSave = null;
        CredentialDTO response = null;

        try{
            credentialSave = this.credentialMapper.map(credentialRequestDTO);
            response = this.credentialMapper.map(this.credentialRepository.save(credentialSave));
        }catch (Exception e){
            LOGGER.error("Error : CredentialService : save : "+ LocalDateTime.now());
            throw new RuntimeException(e);
        }
        LOGGER.trace(String.format("Info : CredentialService : save : "+ LocalDateTime.now()+" : ",credentialRequestDTO));

        return response;
    }

    /**
     * @operation: find by id
     * @param: Integer id
     * @return: CredentialDTO
     * */
   // @Cacheable(cacheManager = "cacheManagerOnly",cacheNames = "credentials", key = "'findById'")
    public CredentialDTO findById(Integer id){
        CredentialDTO response;

        try {
            response = this.credentialMapper.map(credentialRepository.findById(id).get());
            LOGGER.trace(String.format("Info: CredentialService : findById : "+LocalDateTime.now()+ " : ",response));
            return response;
        }catch (Exception e){
            LOGGER.error("Error : CredentialService : findById : "+ LocalDateTime.now());
            throw new RuntimeException(e);
        }
    }

    /**
     * @operation: exists by id
     * @param: Integer id
     * @return: Boolean
     * */
   public Boolean existsById(Integer integer){
        LOGGER.trace("Info : CredentialService : existsById : "+ LocalDateTime.now() + " : "+ integer);
        return this.credentialRepository.existsById(integer);
   }

    /**
     * @operation: count
     * @return: Number
     * */
   public Long count(){
       LOGGER.trace("Info : CredentialRepository : count : "+ LocalDateTime.now());
       return this.credentialRepository.count();
   }

    /**
     * @operation: findAll
     * @return: CredentialDTO
     * */
   //@Cacheable(cacheManager = "cacheManagerList",cacheNames = "credentials", key = "'findAll'")
   public List<CredentialDTO> findAll(){
       List<CredentialDTO> response = new ArrayList<>();
       Iterable<CredentialEntity> iterable = this.credentialRepository.findAll();

       iterable.forEach(i ->{
           try {
               response.add(this.credentialMapper.map(i));
           }catch (Exception e){
               LOGGER.error("Error : CredentialService : findAll : "+ LocalDateTime.now());
               throw new RuntimeException(e);
           }
       });

       LOGGER.trace("Info : CredentialService : findAll : "+ LocalDateTime.now());

       return response;
   }

    /**
     * @operation: delete
     * @param: CredentialDTO
     * @return: Boolean
     * */
   // @CacheEvict(cacheManager = "cacheManagerOnly",cacheNames = "credentials", allEntries = true)
    public Boolean deleteById(Integer id){
        try {
            this.credentialRepository.deleteById(id);
            LOGGER.trace(String.format("Info : CredentialService : deleteById : " + LocalDateTime.now()+" : ",id));
        } catch (Exception e){
            LOGGER.error("Error : CredentialService : deleteById : "+ LocalDateTime.now());
            throw new RuntimeException(e);
        }

        return true;
    }

    /**
     * @operation: delete
     * @param: CredentialDTO
     * @return: Boolean
     * */
  // @CacheEvict(cacheManager = "cacheManagerOnly",cacheNames = "credentials", allEntries = true)
   public Boolean delete(CredentialDTO credentialDTO){
       try {
           this.credentialRepository.delete(this.credentialMapper.map(credentialDTO));
           LOGGER.trace(String.format("Info : CredentialService : delete : " + LocalDateTime.now()+" : ",credentialDTO));
       } catch (Exception e){
           LOGGER.error("Error : CredentialService : delete : "+ LocalDateTime.now());
           throw new RuntimeException(e);
       }

       return true;
   }

    /**
     * @operation: update
     * @param: CredentialDTO
     * @return: CredentialDTO
     * */
   //@CacheEvict(cacheManager = "cacheManagerOnly",cacheNames = "credentials", allEntries =  true)
   public CredentialDTO update (CredentialDTO credentialDTO){
       try{
           this.credentialRepository.update(this.credentialMapper.map(credentialDTO));
           LOGGER.trace(String.format("Info : CredentialService : update : " + LocalDateTime.now()+" : ",credentialDTO));
           return credentialDTO;
       }catch (Exception e){
           LOGGER.error("Error : CredentialService : update : "+ LocalDateTime.now());
           throw new RuntimeException(e);
       }
   }
}
