package fedmag.gptandem.services.helper;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.LinkedList;

@NoArgsConstructor
@Getter
public class ChatHistory {
    LinkedList<Message> chatHistory;

    public void addMessage(Message message) {
        chatHistory.add(message);
    }
}
