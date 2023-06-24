package fedmag.gptandem.services.speech2text;

// Imports the Google Cloud client library
import com.google.cloud.speech.v1.RecognitionAudio;
import com.google.cloud.speech.v1.RecognitionConfig;
import com.google.cloud.speech.v1.RecognizeResponse;
import com.google.cloud.speech.v1.SpeechClient;
import com.google.cloud.speech.v1.SpeechRecognitionAlternative;
import com.google.cloud.speech.v1.SpeechRecognitionResult;

import java.util.List;

import com.google.protobuf.ByteString;
import fedmag.gptandem.services.helper.Languages;
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
    public String transcribe(byte[] record, Languages language) {
        // Builds the sync recognize request
        RecognitionConfig config =
                RecognitionConfig.newBuilder()
                        .setLanguageCode(language.getLanguage())
                        .setAudioChannelCount(2)
                        .build();
        RecognitionAudio audio = RecognitionAudio.newBuilder().setContent(ByteString.copyFrom(record)).build();

        // Performs speech recognition on the audio file
        RecognizeResponse response = speechClient.recognize(config, audio); // this blocks till the response comes.
        List<SpeechRecognitionResult> results = response.getResultsList();
        if (results.isEmpty()) log.error("0 results have been returned...");
        String transcription = results.get(0).getAlternativesList().get(0).getTranscript();;
        for (SpeechRecognitionResult result : results) {
            // There can be several alternative transcripts for a given chunk of speech. Just use the
            // first (most likely) one here.
            SpeechRecognitionAlternative alternative = result.getAlternativesList().get(0);
        }
        return transcription;
    }
}
