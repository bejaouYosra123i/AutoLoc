package tn.esprit.autoloc;

import jakarta.transaction.Transactional;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.domain.CategorieVehicule;
import tn.esprit.autoloc.domain.StatutVehicule;
import tn.esprit.autoloc.domain.Vehicule;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest

public class AgenceTests {
    @Autowired
    private AgenceRepositoryMock agenceRepository;
    @Test
    @Transactional
    public void addAgence(){
        Agence agence = new Agence();
        agence.setNom("Agence ariana");
        agence.setAdresse("1 Rue Hedi");
        agence.setTelephone("71585874");
        agence.setVille("Tunis");

        Vehicule v1 = new Vehicule();
        v1.setImmatriculation("785414TU96");
        v1.setMarque("Isuzu");
        v1.setModele("DMax");
        v1.setCategorie(CategorieVehicule.SUV);
        v1.setStatut(StatutVehicule.MAINTENANCE);
        v1.setTarifJournalier(new BigDecimal("100"));
        v1.setAgence(agence);

        Vehicule v2 = new Vehicule();
        v2.setImmatriculation("785414TU95");
        v2.setMarque("Toyota");
        v2.setModele("Yaris");
        v2.setCategorie(CategorieVehicule.UTILITAIRE);
        v2.setStatut(StatutVehicule.DISPONIBLE);
        v2.setTarifJournalier(new BigDecimal("80"));
        v2.setAgence(agence);

        Set<Vehicule> vehicules = new HashSet<>();
        vehicules.add(v1);
        vehicules.add(v2);
        agence.setVehicules(vehicules);

        agenceRepository.save(agence);


    }
    @Test
    @Transactional
    public void loadAgence() {

        StringBuilder sb = new StringBuilder();

        for (Agence a : agenceRepository.findAll()) {
            sb.append(a.getIdAgence()).append(" | ").append(a.getNom()).append("\n")
                    .append("Vehicules Count : ").append(a.getVehicules().size()).append("\n");

            for (Vehicule v : a.getVehicules()) {
                sb.append("=== ").append(v.getIdVehicule()).append("|").append(v.getImmatriculation()).append("\n");
            }
        }

        Assert.fail(sb.toString());
    }


}
interface  AgenceRepositoryMock  extends CrudRepository<Agence,Long> {

}