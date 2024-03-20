package com.SpringBoot.Clinica.Repository;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public class TelephoneRepository implements CrudRepository<TelephoneRepository,Integer> {
    @Override
    public <S extends TelephoneRepository> S save(S entity) {
        return null;
    }

    @Override
    public <S extends TelephoneRepository> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<TelephoneRepository> findById(Integer integer) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Integer integer) {
        return false;
    }

    @Override
    public Iterable<TelephoneRepository> findAll() {
        return null;
    }

    @Override
    public Iterable<TelephoneRepository> findAllById(Iterable<Integer> integers) {
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
    public void delete(TelephoneRepository entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Integer> integers) {

    }

    @Override
    public void deleteAll(Iterable<? extends TelephoneRepository> entities) {

    }

    @Override
    public void deleteAll() {

    }
}
