package fedmag.gptandem.services.speech2text;

import lombok.Getter;

@Getter
public enum Languages {
    ENGLISH("en-US"),
    GERMAN("de-DE"),
    ITALIAN("it-IT");

    private final String language;

    Languages(String language) {
        this.language = language;
    }
}
