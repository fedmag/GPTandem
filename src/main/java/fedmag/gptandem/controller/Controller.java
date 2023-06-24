package fedmag.gptandem.controller;

import fedmag.gptandem.services.helper.ChatHistory;
import fedmag.gptandem.services.helper.Languages;
import fedmag.gptandem.services.helper.Message;
import fedmag.gptandem.services.openai.ChatGPT;
import fedmag.gptandem.services.openai.Tandem;
import fedmag.gptandem.services.speech2text.GoogleSpeechToText;
import fedmag.gptandem.services.speech2text.MicrophoneRecorder;
import fedmag.gptandem.services.speech2text.MicrophoneService;
import fedmag.gptandem.services.speech2text.Transcriber;
import fedmag.gptandem.ui.GUI;
import lombok.extern.slf4j.Slf4j;
import javax.swing.*;
import java.io.IOException;

@Slf4j
public class Controller {

    private final GUI gui;
    private final Transcriber speech2text;
    private final MicrophoneRecorder microphoneService;
    private final ChatHistory chatHistory;
    private Languages sessionLanguage;
    private final Tandem tandem;


    public Controller(Languages language) {
        // TODO maybe we could even make all these parameters in the main?
        this.sessionLanguage = language;
        this.gui = new GUI();
        this.speech2text = new GoogleSpeechToText();
        this.microphoneService = new MicrophoneService();
        this.chatHistory = new ChatHistory();
        chatHistory.addMessage(new Message("system", "you are an helpful assistant that is helping me learning " + language.getLongLanguageName() + " and will reply in such a language."));
        this.tandem = new ChatGPT();
        initController();
    }

    void setSessionLanguage(Languages newLanguage) {this.sessionLanguage = newLanguage;} // TODO need a dropdown to select the language, probably the controller should not encapsulate this logic but should be demanded to the individual components.

    public void initController() {
        gui.setRecordButtonListener( e -> {
            log.info("Record button pressed");
            if (microphoneService.isRecording()) {
                microphoneService.stopRecording();
            } else {
                gui.setRecordButtonText("Stop");
                SwingWorker<Void, Void> worker = new SwingWorker<>() {
                    @Override
                    protected Void doInBackground() throws Exception {
                        // Perform the long-lasting process here
                        // This will execute in the background thread

                        gui.showLogMessage("Starting recording on second thread");
                        microphoneService.startRecording();
                        return null;
                    }

                    @Override
                    protected void done() {
                        // Executed on the EDT after the doInBackground() method completes
                        // Update the UI or perform any necessary post-processing

                        gui.showLogMessage("Record captured!");
                        gui.setRecordButtonText("Record");
                    }
                };

                worker.execute();
            }
        });

        gui.setSendButtonListener(e -> {
            log.info("Send button pressed");
            String transcription = speech2text.transcribe(microphoneService.getLastRecording(), this.sessionLanguage);
//            String transcription = "this is a fake transcription";
            chatHistory.addMessage(new Message("user", transcription));
            // TODO maybe I want to pass the string directly?
            gui.displayChatHistory(chatHistory);
            // send to OpanAI
            String reply = null;
            try {
                reply = tandem.reply(chatHistory);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            chatHistory.addMessage(new Message("assistant", reply));
            gui.displayChatHistory(chatHistory);
        });
    }


//    byte[] record = microphoneService.startRecording();

//    String transcription = speech2text.transcribe(record);
//    chatHistory.addMessage(new Message("user", transcription));
//    log.info(transcription);
}