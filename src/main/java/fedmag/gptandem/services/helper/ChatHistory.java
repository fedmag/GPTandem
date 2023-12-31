package fedmag.gptandem.services.helper;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;

@NoArgsConstructor
@Getter
@Slf4j
public class ChatHistory {
    LinkedList<Message> chatHistory = new LinkedList<>();

    public void addMessage(Message message) {
        chatHistory.add(message);
    }

    public String toString() {
        String result = "";
        for (Message message: chatHistory) {
            result = result.concat("\n" + message.role + " -> " + message.content + "\n\n");
        }
        return result;
    }
}
