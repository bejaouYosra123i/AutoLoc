package tn.esprit.autoloc.service;

public interface ICheckGenericService<T, ID> {
    boolean check(T entity, boolean isNew);

}
