package fedmag.gptandem.services;

import lombok.extern.slf4j.Slf4j;

import javax.sound.sampled.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

@Slf4j
public class MicrophoneService implements MicRecorder{
    @Override
    public void record() {
        try {
            AudioFormat format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
                    44100,
                    16,
                    2,
                    4,
                    44100,
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
            AudioFileFormat.Type fileType = AudioFileFormat.Type.WAVE;

            // Create a ByteArrayOutputStream to capture the audio data
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

            // Start capturing audio from the microphone
            int duration = 15000;
            long startTime = System.currentTimeMillis();
            while (System.currentTimeMillis() - startTime < duration) {
                int bytesRead = line.read(buffer, 0, buffer.length);
                byteArrayOutputStream.write(buffer, 0, bytesRead);
            }

            // Create an AudioInputStream from the captured audio data
            AudioInputStream audioInputStream = new AudioInputStream(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()), format,
                    byteArrayOutputStream.size() / format.getFrameSize());

            // Write the audio stream to the file
            AudioSystem.write(audioInputStream, fileType, audioFile);

            // Clean up resources
             line.stop();
             line.close();
             audioInputStream.close();
             byteArrayOutputStream.close();

        } catch (LineUnavailableException | IOException ex) {
            log.error("Record service not available..");
            log.error(ex.getMessage());
        }
    }
}
