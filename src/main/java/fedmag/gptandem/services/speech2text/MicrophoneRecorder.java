package fedmag.gptandem.services.speech2text;

public interface MicrophoneRecorder {

    byte[] startRecording(); //TODO decide if this return value is actually to be kept
    byte[] getLastRecording();
    void stopRecording();
    boolean isRecording();
}

