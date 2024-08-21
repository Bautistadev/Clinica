package com.SpringBoot.Clinica.Cache.Service;

import java.util.List;

public interface DAO<E,K> {

    public void insert(K key, E value);

    public void insert(K key, List<E> value);

    public void update(K key, E value);

    public void update(K key, List<E> value);

    public E findById(K id);

    public List<E> findAll();
}
