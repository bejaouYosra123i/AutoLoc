package tn.esprit.autoloc.service;

import tn.esprit.autoloc.calcul.VehiculeOffre;
import tn.esprit.autoloc.domain.Agence;

public interface IAgenceSimulationService {
    String simuler(Agence agence, int nbJours, VehiculeOffre offre);
}