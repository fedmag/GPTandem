package fedmag.gptandem.ui;

import fedmag.gptandem.services.helper.ChatHistory;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.event.ActionListener;
@Slf4j
public class UI extends JFrame{
    private JButton recordButton;
    private JTextArea chatArea;
    private JButton sendButton;
    private JPanel mainPanel;
    private JTextArea stateArea;

    public UI() {
        super();
        setContentPane(mainPanel);
        setTitle("GPTandem");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void displayChatHistory(ChatHistory history) {
        log.info("Setting text to: {}", history.toString());
        chatArea.setText(history.toString());
    }
    public void setRecordButtonListener(ActionListener listener) {
        recordButton.addActionListener(listener);
    }
    public void setSendButtonListener(ActionListener listener) {
        sendButton.addActionListener(listener);
    }
    public void showLogMessage(String message) {
        chatArea.append("\n LOG: " + message + "\n" );
    }
    public void setRecordButtonText(String newText) {
        recordButton.setText(newText);
    }
    public void setStateAreaText(String text) {stateArea.setText(text);}
    public void setRecordButtonActive(boolean active) {
        recordButton.setEnabled(active);
    }
    public void setSendButtonActive(boolean active) {
        sendButton.setEnabled(active);
    }

}
