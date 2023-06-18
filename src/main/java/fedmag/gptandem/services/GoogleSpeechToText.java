package fedmag.gptandem.services;

// Imports the Google Cloud client library
import com.google.cloud.speech.v1.RecognitionAudio;
import com.google.cloud.speech.v1.RecognitionConfig;
import com.google.cloud.speech.v1.RecognitionConfig.AudioEncoding;
import com.google.cloud.speech.v1.RecognizeResponse;
import com.google.cloud.speech.v1.SpeechClient;
import com.google.cloud.speech.v1.SpeechRecognitionAlternative;
import com.google.cloud.speech.v1.SpeechRecognitionResult;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import com.google.protobuf.ByteString;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
@Slf4j
public class GoogleSpeechToText implements Transcriber {

    SpeechClient speechClient;
    public GoogleSpeechToText() {
        log.info("Creating GoogleSpeechToTextInstance..");
        this.speechClient = this.initClient();
        log.info("..instance created!");
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
        String gcsUri = "C:\\Users\\maggi\\IdeaProjects\\GPTandem\\recorded_audio.wav";

        Path path = Paths.get("C:\\Users\\maggi\\IdeaProjects\\GPTandem\\recorded_audio.wav"    );
        byte[] content;
        try {
            content = Files.readAllBytes(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Builds the sync recognize request
        RecognitionConfig config =
                RecognitionConfig.newBuilder()
                        .setLanguageCode("en-US")
                        .setAudioChannelCount(2)
                        .build();
        RecognitionAudio audio = RecognitionAudio.newBuilder().setContent(ByteString.copyFrom(content)).build();

        // Performs speech recognition on the audio file
        RecognizeResponse response = speechClient.recognize(config, audio); // this blocks till the response comes.
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
