package fedmag.gptandem.services.openai;

import fedmag.gptandem.services.helper.ChatHistory;

public class ChatGPT implements Tandem {
    @Override
    public String reply(ChatHistory chatHistory) {
        return "this is a fake reply";
    }
}
