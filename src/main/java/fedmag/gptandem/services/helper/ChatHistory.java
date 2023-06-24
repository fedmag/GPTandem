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
        log.debug("Adding message: {}", message);
        chatHistory.add(message);
    }

    public String toString() {
        String result = "";
        for (Message message: chatHistory) {
            log.info("Looking at message: {}", message);
            result = result.concat("\n" + message.author + ": " + message.content);
        }
        log.info("ChatHistory to string: {}", result);
        return result;
    }
}
