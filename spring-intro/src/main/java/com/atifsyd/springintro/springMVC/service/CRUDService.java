package com.atifsyd.springintro.springMVC.service;

import java.util.List;

public interface CRUDService<T> {
    List<?> listAll();
    T getById(Integer id);
    T saveOrUpdate(T modelObject);
    void remove(Integer id);
}
