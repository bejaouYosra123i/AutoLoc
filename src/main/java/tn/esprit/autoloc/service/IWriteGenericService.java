package tn.esprit.autoloc.service;

import java.util.List;

public interface IWriteGenericService<T, ID>
{
    T create(T entity);
    T update(ID id, T entity);
    void deleteById(ID id);
}
