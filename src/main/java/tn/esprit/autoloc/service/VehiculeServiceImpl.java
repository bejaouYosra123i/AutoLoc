package tn.esprit.autoloc.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import tn.esprit.autoloc.Utils;
import tn.esprit.autoloc.calcul.OffreStrategy;
import tn.esprit.autoloc.calcul.VehiculeOffre;
import tn.esprit.autoloc.domain.Equipement;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IAgenceRepository;
import tn.esprit.autoloc.repository.IEquipementRepository;
import tn.esprit.autoloc.repository.IVehiculeRepository;

import java.util.Map;

@Service
public class VehiculeServiceImpl extends GenericServiceImpl<Vehicule, Long> implements IVehiculeService {
    private final Map<String, OffreStrategy> offres;
    private final IAgenceRepository agenceRepository;
    private final IEquipementRepository equipementRepository;

    public VehiculeServiceImpl(IVehiculeRepository repository, Map<String, OffreStrategy> offres , IAgenceRepository agenceRepository, IAgenceRepository agenceRepository1, IEquipementRepository equipementRepository) {



        super(repository);
        this.offres = offres;
        this.agenceRepository = agenceRepository1;
        this.equipementRepository = equipementRepository;
    }

    @Override
    protected String getEntityName() {
        return "Vehicule";
    }

    @Override
    protected Long getId(Vehicule vehicule) {
        return vehicule.getIdVehicule();
    }

    @Override
    protected void setFields(Vehicule source, Vehicule target) {
        target.setImmatriculation(source.getImmatriculation());
        target.setMarque(source.getMarque());
        target.setModele(source.getModele());
        target.setCategorie(source.getCategorie());
        target.setTarifJournalier(source.getTarifJournalier());
        target.setStatut(source.getStatut());
        target.setAgence(source.getAgence());
    }

    @Override
    public boolean check(Vehicule vehicule, boolean isNew) {
        return Utils.isNotEmpty(vehicule.getImmatriculation())
                && Utils.isNotEmpty(vehicule.getMarque())
                && Utils.isNotEmpty(vehicule.getModele())
                && vehicule.getCategorie() != null
                && vehicule.getTarifJournalier() != null
                && vehicule.getStatut() != null
                && vehicule.getAgence() != null;
    }
    @Override
    public double calculTarif(Vehicule vehicule, int nbJours, VehiculeOffre offre) {
        OffreStrategy strategie = offres.get(offre.name());
        if (strategie == null) {
            throw new IllegalArgumentException("Offre non supportée : " + offre);
        }
        return strategie.calculer(vehicule.getTarifJournalier().doubleValue(), nbJours);
    }

    @Override
    public Vehicule desaffecte(long id) {
        var vehicule =findById(id);
        vehicule.setAgence(null);

        return repository.save(vehicule);
    }

    @Override
    public Vehicule desaffecte(long id, long idAgence) {
        return null;
    }

    @Override
    @Transactional
    public Equipement affecteEquipement(long idVehicule, long idEquipement) {
        var vehicule = findById(idVehicule);
        var equipement = equipementRepository.findById(idEquipement)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Équipement", String.valueOf(idEquipement)));

        vehicule.getEquipements().add(equipement);   // côté propriétaire
        repository.save(vehicule);
        return equipement;
    }

    @Override
    public Vehicule desaffecte(Long id, Long idAgence) {
        var vehicule =findById(id);
        var agence =agenceRepository.findById(idAgence).orElseThrow(()
                -> new ResourceNotFoundException("Agence non trouvée avec l'id ", idAgence.toString()));

        vehicule.setAgence(agence);

        return repository.save(vehicule);
    }
}


