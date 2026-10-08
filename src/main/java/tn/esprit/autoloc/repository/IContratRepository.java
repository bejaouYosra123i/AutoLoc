package tn.esprit.autoloc.repository;

import org.springframework.data.repository.CrudRepository;
import tn.esprit.autoloc.domain.Client;

public interface IContratRepository extends CrudRepository<Client,Long> {
}
