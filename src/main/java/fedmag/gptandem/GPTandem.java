package fedmag.gptandem;

import fedmag.gptandem.services.helper.ChatHistory;
import fedmag.gptandem.services.helper.Message;
import fedmag.gptandem.services.speech2text.GoogleSpeechToText;
import fedmag.gptandem.services.speech2text.MicrophoneRecorder;
import fedmag.gptandem.services.speech2text.MicrophoneService;
import fedmag.gptandem.services.speech2text.Transcriber;
import lombok.extern.slf4j.Slf4j;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
@Slf4j
public class GPTandem {
    public static void main(String[] args) {

        log.info("Hello and welcome!");
        log.info("Creating empty chat history..");
        ChatHistory chatHistory = new ChatHistory();

        MicrophoneRecorder microphoneService = new MicrophoneService();
        byte[] record = microphoneService.startRecording();

        Transcriber speech2text = new GoogleSpeechToText();
        String transcription = speech2text.transcribe(record);
//        chatHistory.addMessage(new Message("user", transcription));
        log.info(transcription);

    }
}