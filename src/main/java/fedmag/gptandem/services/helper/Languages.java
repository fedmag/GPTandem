package fedmag.gptandem.services.helper;

import lombok.Getter;

@Getter
public enum Languages {
    ENGLISH("en-US", "English"),
    GERMAN("de-DE", "German"),
    ITALIAN("it-IT", "Italian");

    private final String language;
    private final String longLanguageName;

    Languages(String language, String longLanguageName) {
        this.language = language;
        this.longLanguageName = longLanguageName;
    }
}
