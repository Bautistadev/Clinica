package com.SpringBoot.Clinica.Repository;


import com.SpringBoot.Clinica.model.UserDTO;
import org.springframework.data.repository.CrudRepository;


import java.util.Optional;



public class UserRepository implements  CrudRepository<UserDTO,Integer>{


    @Override
    public <S extends UserDTO> S save(S entity) {
        return null;
    }

    @Override
    public <S extends UserDTO> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<UserDTO> findById(Integer integer) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Integer integer) {
        return false;
    }

    @Override
    public Iterable<UserDTO> findAll() {
        return null;
    }

    @Override
    public Iterable<UserDTO> findAllById(Iterable<Integer> integers) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Integer integer) {

    }

    @Override
    public void delete(UserDTO entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Integer> integers) {

    }

    @Override
    public void deleteAll(Iterable<? extends UserDTO> entities) {

    }

    @Override
    public void deleteAll() {

    }
}
