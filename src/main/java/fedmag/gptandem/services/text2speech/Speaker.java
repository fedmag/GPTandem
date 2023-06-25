package fedmag.gptandem.services.text2speech;

import fedmag.gptandem.services.helper.Languages;

public interface Speaker {

    void speak(String text, Languages language);
    void repeatLast();
}
