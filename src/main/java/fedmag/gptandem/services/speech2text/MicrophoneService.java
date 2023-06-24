package fedmag.gptandem.services.speech2text;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import javax.sound.sampled.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
public class MicrophoneService implements MicrophoneRecorder {

    AtomicBoolean isRecording = new AtomicBoolean();
    @Getter
    private byte[] lastRecording;

    @Override
    public byte[] startRecording() {
        isRecording.set(true);
        try {
            AudioFormat format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
                    16000,
                    16,
                    2,
                    4,
                    16000,
                    false);
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);

            if (!AudioSystem.isLineSupported(info)) {
                System.out.println("Microphone not supported");
                System.exit(0);
            }

            TargetDataLine line = (TargetDataLine) AudioSystem.getLine(info);
            line.open(format);
            line.start();

            byte[] buffer = new byte[4096];
            System.out.println("Recording started...");

            // Create a new file to save the recorded audio
            File audioFile = new File("recorded_audio.wav");

            // Create a ByteArrayOutputStream to capture the audio data
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();


//            Thread inputThread = getInputThread();
            // Start capturing audio from the microphone
            int maxDuration = 10000;
            long startTime = System.currentTimeMillis();

            while (System.currentTimeMillis() - startTime < maxDuration && isRecording.get()) {
                log.info("Time is over? {}", System.currentTimeMillis() - startTime < maxDuration);
                log.info("isRecording? {}", isRecording.get());
                int bytesRead = line.read(buffer, 0, buffer.length);
                byteArrayOutputStream.write(buffer, 0, bytesRead);
            }
//            inputThread.join();
            log.info("Recording completed.. processing..");
            // Create an AudioInputStream from the captured audio data
            AudioInputStream audioInputStream = new AudioInputStream(new ByteArrayInputStream(
                    byteArrayOutputStream.toByteArray()),
                    format,
                    byteArrayOutputStream.size() / format.getFrameSize());

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            log.info(String.valueOf(AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE, outputStream)));
            byte[] audioData = outputStream.toByteArray();

            // Clean up resources
            line.stop();
            line.close();
            audioInputStream.close();
            outputStream.close();
            byteArrayOutputStream.close();

            lastRecording = audioData;
            return audioData;
        } catch (LineUnavailableException | IOException ex) {
            log.error("Record service not available..");
            log.error(ex.getMessage());
        }
        return null;
    }

//    private Thread getInputThread() {
//        Thread inputThread = new Thread(() -> {
//            try {
//                while (System.in.read() != 'q') {
//                    // Continue listening for key input
//                    log.info(String.valueOf(isRecording.get()));
//                }
//                System.out.println("The 'Q' key was pressed");
//                isRecording.set(false);
//            } catch (IOException e) {
//                log.error(e.getMessage());
//            }
//        });
//
//        // Start the input thread
//        inputThread.start();
//        return inputThread;
//    }

    @Override
    public void stopRecording() {
        log.info("Stopping recording");
        isRecording.set(false);
        log.info("New is recording value: {}", isRecording.get());
    }

    @Override
    public boolean isRecording() {
        return this.isRecording.get();
    }
}
