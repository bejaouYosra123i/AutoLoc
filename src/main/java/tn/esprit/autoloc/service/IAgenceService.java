package tn.esprit.autoloc.service;

import tn.esprit.autoloc.calcul.VehiculeOffre;
import tn.esprit.autoloc.domain.Agence;

public interface IAgenceService extends IGenericService<Agence, Long> {

    boolean hasVehicules(Long id);
    String simuler(Agence agence, int nbJours, VehiculeOffre offre);
    Agence updateAvecEmployes(Long id, Agence agence);
}