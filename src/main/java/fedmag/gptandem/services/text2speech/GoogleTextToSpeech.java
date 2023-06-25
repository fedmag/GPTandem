package fedmag.gptandem.services.text2speech;

import com.google.cloud.texttospeech.v1.*;
import com.google.protobuf.ByteString;
import fedmag.gptandem.services.helper.Languages;
import javazoom.jl.player.Player;
import lombok.extern.slf4j.Slf4j;

import javax.sound.sampled.*;
import java.io.*;

@Slf4j
public class GoogleTextToSpeech implements Speaker {
    private final TextToSpeechClient client;
    private final AudioConfig audioConfig;

    public GoogleTextToSpeech() {
        try {
            this.client = TextToSpeechClient.create();
        } catch (IOException e) {
            log.error("Unable to initialize the client! {}", e.getMessage());
            throw new RuntimeException(e);
        }

        // Select the type of audio file you want returned
        audioConfig = AudioConfig.newBuilder()
                        .setAudioEncoding(AudioEncoding.MP3) // MP3 audio.
                        .build();

    }

    @Override
    public void speak(String text, Languages language) {
        synthesizeText(text, language);
    }

    /**
     * Demonstrates using the Text to Speech client to synthesize text or ssml.
     *
     * @param text     the raw text to be synthesized. (e.g., "Hello there!")
     * @param language
     * @throws Exception on TextToSpeechClient Errors.
     */
    private ByteString synthesizeText(String text, Languages language) {
        // Set the text input to be synthesized
        SynthesisInput input = SynthesisInput.newBuilder().setText(text).build();

        // Build the voice request
        VoiceSelectionParams voice =
                VoiceSelectionParams.newBuilder()
                        .setLanguageCode(language.getLanguage()) // languageCode = "en_us"
                        .setSsmlGender(SsmlVoiceGender.FEMALE) // ssmlVoiceGender = SsmlVoiceGender.FEMALE
                        .build();

        log.info("Performing speech request..");
        // Perform the text-to-speech request
        SynthesizeSpeechResponse response = client.synthesizeSpeech(input, voice, audioConfig);

        log.info("Synthesizer response received");

        // Get the audio contents from the response
        ByteString audioContents = response.getAudioContent();

        playAudio(new ByteArrayInputStream(audioContents.toByteArray()));
        return audioContents;
    }

    public static void playAudio(InputStream stream) {
        try {
            Player player = new Player(stream);
            player.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
