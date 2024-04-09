package com.SpringBoot.Clinica.Cache.Service;

import com.SpringBoot.Clinica.model.UserDTO;
import net.sf.jsqlparser.statement.update.Update;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

@Service
public class CacheUserService implements DAO<UserDTO,Object> {

    public static final String CACHE = "users";

    public static final String FIND_ALL_KEY="findAll";

    private CacheManager cacheManager;

    public CacheUserService(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    /**
     * @OPERATION INSERT IN THE CACHE
     * @PARAM: String key, UserDTO value
     * @RETURN: -
     * */
    @Override
    public void insert(Object key, UserDTO value) {
        /**
        * insert individual object
        * */
        this.cacheManager.getCache(CACHE).put(key,value);

        /**
         * insert in list object
         * */
        if(this.findAll() != null) {
            List<UserDTO> userListCache = this.findAll();
            userListCache.add(value);
            this.update(FIND_ALL_KEY,userListCache);
        }
    }



    /**
     * @OPERATION : INSERT LIST IN THE CACHE
     * @PARAM: String key, list of UserDTO value
     * @RETURN: -
     * */
    @Override
    public void insert(Object key, List<UserDTO> value){
        this.cacheManager.getCache(CACHE).put(key,value);
    }

    /**
     * @OPERATION : UPDATE VALUE
     * @PARAM: String key, UserDTO value
     * @RETURN: -
     * */
    @Override
    public void update(Object key, UserDTO value) {
        this.cacheManager.getCache(CACHE).put(key,value);
    }

    /**
     * @OPERATION : UPDATE LIST
     * @PARAM: String key, list of UserDTO value
     * @RETURN: -
     * */
    @Override
    public void update(Object key, List<UserDTO> value) {
        this.cacheManager.getCache(CACHE).put(key,value);
    }


    public void remove(Object key){
        System.out.println("entra");
        this.cacheManager.getCache(CACHE).evict(key.toString());
    }


    /**
     * @OPERATION : FIND ALL CACHE VALUES
     * @PARAM: String id
     * @RETURN: OBJECT UserDTO
     * */
    @Override
    public UserDTO findById(Object id) {
        try {
            Cache.ValueWrapper cacheValue = this.cacheManager.getCache(CACHE).get(id);
            UserDTO response = (UserDTO) cacheValue.get();
            return response;
        }catch (NullPointerException e){
            return null;
        }
    }

    /**
     * @OPERATION : FIND ALL CACHE VALUES
     * @PARAM: String id
     * @RETURN: OBJECT UserDTO
     * */
    @Override
    public List<UserDTO> findAll() {
        try {
            Cache.ValueWrapper cacheList = this.cacheManager.getCache(CACHE).get(FIND_ALL_KEY);

            List<UserDTO> list = (List<UserDTO>) cacheList.get();

            return list;
        }catch (NullPointerException e){
            return null;
        }
    }
}
