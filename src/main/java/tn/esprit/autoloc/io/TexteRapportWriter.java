package tn.esprit.autoloc.io;

public class TexteRapportWriter implements RapportWriter {

    private final StringBuilder sb = new StringBuilder();

    @Override
    public void entete(String texte) {
        sb.append(texte).append("\n");
    }

    @Override
    public void total(double total) {
        sb.append("total : ").append(total);
    }

    @Override
    public String resultat() {
        return sb.toString();
    }
}