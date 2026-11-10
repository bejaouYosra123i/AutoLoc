package tn.esprit.autoloc.service;

import java.util.List;

public interface IReadGenericService<T, ID>
{
    T findById(ID id);
    List<T> findAll();

}
