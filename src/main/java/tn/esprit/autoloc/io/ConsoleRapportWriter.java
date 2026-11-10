package tn.esprit.autoloc.io;

import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;

@RequiredArgsConstructor
public class ConsoleRapportWriter implements RapportWriter {

    @Delegate
    private final RapportWriter delegate;

    @Override
    public void entete(String texte) {
        System.out.println(texte);
    }
}