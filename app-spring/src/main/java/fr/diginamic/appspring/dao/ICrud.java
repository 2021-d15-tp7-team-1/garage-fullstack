package fr.diginamic.appspring.dao;

import java.util.List;

public interface ICrud<T> {
    void add(T o) ;
    void update(T o) ;
    void delete(T o) ;
    default void deleteAll(){} ;
    List<T> selectAll() ;
    T selectOne (long id) ;
}
