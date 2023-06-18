package fedmag.gptandem.services.speech2text;

public interface Transcriber {

    String transcribe(byte[] record);
}
