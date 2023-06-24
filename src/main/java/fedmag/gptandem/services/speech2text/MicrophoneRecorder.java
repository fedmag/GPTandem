package fedmag.gptandem.services.speech2text;

public interface MicrophoneRecorder {

    byte[] startRecording();
    byte[] getLastRecording();
    void stopRecording();
    boolean isRecording();
}

