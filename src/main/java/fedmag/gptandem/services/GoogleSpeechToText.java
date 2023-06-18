package fedmag.gptandem.services;

// Imports the Google Cloud client library
import com.google.cloud.speech.v1.RecognitionAudio;
import com.google.cloud.speech.v1.RecognitionConfig;
import com.google.cloud.speech.v1.RecognitionConfig.AudioEncoding;
import com.google.cloud.speech.v1.RecognizeResponse;
import com.google.cloud.speech.v1.SpeechClient;
import com.google.cloud.speech.v1.SpeechRecognitionAlternative;
import com.google.cloud.speech.v1.SpeechRecognitionResult;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
@Slf4j
public class GoogleSpeechToText implements Transcriber {

    SpeechClient speechClient;
    public GoogleSpeechToText() {
        this.speechClient = this.initClient();
    }

    private SpeechClient initClient() {
        try {
            return SpeechClient.create();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String transcribe() {
        // The path to the audio file to transcribe
        String gcsUri = "gs://cloud-samples-data/speech/brooklyn_bridge.raw";

        // Builds the sync recognize request
        RecognitionConfig config =
                RecognitionConfig.newBuilder()
                        .setEncoding(AudioEncoding.LINEAR16)
                        .setSampleRateHertz(16000)
                        .setLanguageCode("en-US")
                        .build();
        RecognitionAudio audio = RecognitionAudio.newBuilder().setUri(gcsUri).build();

        // Performs speech recognition on the audio file
        RecognizeResponse response = speechClient.recognize(config, audio);
        List<SpeechRecognitionResult> results = response.getResultsList();

        String transcription = results.get(0).getAlternativesList().get(0).getTranscript();;
        for (SpeechRecognitionResult result : results) {
            // There can be several alternative transcripts for a given chunk of speech. Just use the
            // first (most likely) one here.
            SpeechRecognitionAlternative alternative = result.getAlternativesList().get(0);
            System.out.printf("Transcription: %s%n", alternative.getTranscript());
        }
        return transcription;
    }
}
