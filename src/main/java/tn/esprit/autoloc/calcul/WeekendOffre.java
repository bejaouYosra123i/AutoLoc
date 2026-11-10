package tn.esprit.autoloc.calcul;

import org.springframework.stereotype.Component;

@Component("WEEKEND")
public class WeekendOffre implements OffreStrategy {
    @Override
    public double calculer(double tarifJournalier, int nbJours) {
        return tarifJournalier * 0.8 * nbJours;      // réduction de 20 %
    }
}