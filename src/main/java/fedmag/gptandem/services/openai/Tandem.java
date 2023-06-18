package fedmag.gptandem.services.openai;

import fedmag.gptandem.services.helper.ChatHistory;

public interface Tandem {
    String reply(ChatHistory chatHistory);
}
