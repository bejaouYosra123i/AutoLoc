package tn.esprit.autoloc.service;

import tn.esprit.autoloc.calcul.VehiculeOffre;
import tn.esprit.autoloc.domain.Equipement;
import tn.esprit.autoloc.domain.Vehicule;

public interface IVehiculeService extends IGenericService<Vehicule, Long> {

     double  calculTarif(Vehicule vehicule, int nb_jours, VehiculeOffre offre);
     Vehicule desaffecte(long id);
    Vehicule desaffecte(long id,long idAgence);
    Equipement affecteEquipement(long idVehicule, long idEquipement);

}
