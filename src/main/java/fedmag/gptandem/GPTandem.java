package fedmag.gptandem;

import fedmag.gptandem.services.GoogleSpeechToText;
import fedmag.gptandem.services.MicrophoneService;
import lombok.extern.slf4j.Slf4j;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
@Slf4j
public class GPTandem {
    public static void main(String[] args) {
        // Press Alt+Enter with your caret at the highlighted text to see how
        // IntelliJ IDEA suggests fixing it.
        log.info("Hello and welcome!");

        MicrophoneService microphoneService = new MicrophoneService();
        microphoneService.record();
        GoogleSpeechToText s2t = new GoogleSpeechToText();
        log.info(s2t.transcribe());
    }
}