package fedmag.gptandem.ui;

import fedmag.gptandem.services.helper.ChatHistory;
import fedmag.gptandem.services.helper.Message;
import fedmag.gptandem.services.speech2text.GoogleSpeechToText;
import fedmag.gptandem.services.speech2text.MicrophoneRecorder;
import fedmag.gptandem.services.speech2text.MicrophoneService;
import fedmag.gptandem.services.speech2text.Transcriber;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
@Slf4j
public class Controller {

    private final GUI gui;
    private final Transcriber speech2text;
    private final MicrophoneRecorder microphoneService;
    private final ChatHistory chatHistory;
    //    private final Tandem tandem;


    public Controller() {
        this.gui = new GUI();
        this.speech2text = new GoogleSpeechToText();
        this.microphoneService = new MicrophoneService();
        this.chatHistory = new ChatHistory();
        initController();
    }

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
            String transcription = speech2text.transcribe(microphoneService.getLastRecording());
//                String transcription = "speech2text.transcribe(lastRecord)";
            chatHistory.addMessage(new Message("federico", transcription));
            // TODO maybe I want to pass the string directly?
            gui.displayChatHistory(chatHistory);
        });
    }


//    byte[] record = microphoneService.startRecording();

//    String transcription = speech2text.transcribe(record);
//    chatHistory.addMessage(new Message("user", transcription));
//    log.info(transcription);
}
