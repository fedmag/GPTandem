package fedmag.gptandem.services.speech2text;

import fedmag.gptandem.services.helper.Languages;

public interface Transcriber {
    String transcribe(byte[] record, Languages language);

    String getLastTranscription();
}
