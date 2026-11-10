package tn.esprit.autoloc.service;

import java.util.List;

public interface IGenericService<T, ID>
        extends ICheckGenericService<T, ID>,IReadGenericService<T, ID>,IWriteGenericService<T, ID>
{

}
