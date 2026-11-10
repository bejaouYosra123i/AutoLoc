package tn.esprit.autoloc.io;

public final class RapportWriterFactory {

    private RapportWriterFactory() {
    }

    public static RapportWriter create(RapportType type) {
        RapportWriter base = new TexteRapportWriter();
        if (type == RapportType.CONSOLE) {
            return new ConsoleRapportWriter(base);
        }
        return base;
    }
}