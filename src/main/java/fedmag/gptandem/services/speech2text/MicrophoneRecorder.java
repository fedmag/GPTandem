package fedmag.gptandem.services.speech2text;

public interface MicrophoneRecorder {

    byte[] startRecording();
    void stopRecording();
}

