package tn.esprit.autoloc.io;

public interface RapportWriter {
    void entete(String texte);
    void total(double total);
    String resultat();
}