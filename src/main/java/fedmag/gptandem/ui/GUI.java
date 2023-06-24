package fedmag.gptandem.ui;

import fedmag.gptandem.services.helper.ChatHistory;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.event.ActionListener;
@Slf4j
public class GUI extends JFrame{
    private JButton recordButton;
    private JTextArea chatArea;
    private JButton sendButton;
    private JPanel mainPanel;

    public GUI() {
        super();
        setContentPane(mainPanel);
//        setupUI(); // this is not needed when using IntelliJ Grid Manager
        setTitle("GPTandem");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void setupUI() {
        // Set the content pane explicitly
        mainPanel.add(recordButton);
        mainPanel.add(chatArea);
        mainPanel.add(sendButton);
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

}
