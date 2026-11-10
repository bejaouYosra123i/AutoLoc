package tn.esprit.autoloc.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tn.esprit.autoloc.Utils;
import tn.esprit.autoloc.calcul.VehiculeOffre;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.domain.Employe;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.io.RapportType;
import tn.esprit.autoloc.io.RapportWriter;
import tn.esprit.autoloc.io.RapportWriterFactory;
import tn.esprit.autoloc.repository.IAgenceRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
@Primary
public class AgenceServiceImpl extends GenericServiceImpl<Agence, Long>
        implements IAgenceService, IAgenceSimulationService {

    private final IVehiculeService vehiculeService;
    private final RapportType rapportType;
    private final IEmployeService employeService;

    public AgenceServiceImpl(IAgenceRepository repository,
                             IVehiculeService vehiculeService,
                             @Value("${autoloc.rapport.type:TEXTE}") RapportType rapportType, IEmployeService employeService) {
        super(repository);
        this.vehiculeService = vehiculeService;
        this.rapportType = rapportType;
        this.employeService = employeService;
    }

    @Override
    protected String getEntityName() {
        return "Agence";
    }

    @Override
    protected Long getId(Agence agence) {
        return agence.getIdAgence();
    }

    @Override
    protected void setFields(Agence source, Agence target) {
        target.setNom(source.getNom());
        target.setAdresse(source.getAdresse());
        target.setTelephone(source.getTelephone());
        target.setVille(source.getVille());
    }

    @Override
    public Vehicule desaffecte(Long id, Long idAgence) {
        return null;
    }

    @Override
    public boolean check(Agence agence, boolean isNew) {
        return Utils.isNotEmpty(agence.getNom())
                && Utils.isNotEmpty(agence.getTelephone())
                && Utils.isNotEmpty(agence.getVille());
    }

    @Override
    @Transactional(readOnly = true)
    public boolean hasVehicules(Long id) {
        return !findById(id).getVehicules().isEmpty();
    }

    @Override
    @Transactional(readOnly = true)
    public String simuler(Agence agence, int nbJours, VehiculeOffre offre) {
        RapportWriter rapport = RapportWriterFactory.create(rapportType);

        rapport.entete("id : " + agence.getIdAgence()
                + " | nom : " + agence.getNom()
                + " | tel : " + agence.getTelephone());

        double total = 0;
        for (Vehicule vehicule : agence.getVehicules()) {
            total += vehiculeService.calculTarif(vehicule, nbJours, offre);
        }
        rapport.total(total);

        return rapport.resultat();
    }
    @Override
    @Transactional
    public Agence updateAvecEmployes(Long id, Agence agence) {
        // 1. mettre à jour l'agence d'abord
        Agence saved = update(id, agence);

        // 2. insérer les employés avec le service des employés
        for (Employe employe : agence.getEmployes()) {
            saved.addEmploye(employe);          // relie les deux côtés
            employeService.create(employe);
        }

        // 3. effectif = taille de la liste reçue
        saved.setEffectif(agence.getEmployes().size());
        return repository.save(saved);
    }
}