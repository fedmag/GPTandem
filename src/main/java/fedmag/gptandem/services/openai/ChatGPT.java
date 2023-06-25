package fedmag.gptandem.services.openai;

import com.google.gson.*;
import fedmag.gptandem.services.helper.ChatHistory;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Slf4j
public class ChatGPT implements Tandem {

    private final String apiKey;
    private final OkHttpClient client;
    private final String openAiUrl = "https://api.openai.com/v1/chat/completions";
    private String chatGptVersion = "gpt-3.5-turbo";
    @Getter
    private String lastReply;

    public static final MediaType JSON = MediaType.get("application/json");

    public ChatGPT() {
        this.apiKey = System.getenv("OPENAI_KEY");
        log.info("OpenAI key: {}", apiKey);
        client = new OkHttpClient.Builder().connectTimeout(Duration.ofSeconds(30)).build();
    }

    @Override
    public String reply(ChatHistory chatHistory) {

        RequestBody body = RequestBody.create(prepareJson(chatHistory), JSON);
        Request request = new Request.Builder()
                .url(openAiUrl)
                .header("Authorization", "Bearer " + apiKey)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            assert response.body() != null;
            lastReply = getMessageFromReply(response.body().string());
            return lastReply;
        } catch (IOException e) {
            log.error("Unable to perform the request: {}", e.getMessage());
            lastReply = "Last request did not succeeded as OpenAI failed to respond!";
        }
        return null;
    }

    private String getMessageFromReply(String string) {
        Gson gson = new Gson();
        JsonObject object = gson.fromJson(string, JsonObject.class);
        JsonArray choices = object.getAsJsonArray("choices");
        JsonElement jsonElement = choices.get(0);
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        JsonObject message = jsonObject.getAsJsonObject("message");
        String content = String.valueOf(message.get("content")).replaceAll("[\\r\\n]+", "");

        if ( content.isBlank() || content.equals("null")) log.error("Content in the reply seems null: {}", object);
        return content;
    }

    private String prepareJson(ChatHistory chatHistory) {

        Gson gson = new GsonBuilder().create();
        JsonArray messageArray = gson.toJsonTree(chatHistory.getChatHistory()).getAsJsonArray();

        // Creating a JSON object
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("model", chatGptVersion);
        jsonObject.addProperty("temperature", 0.7);
        jsonObject.add("messages", messageArray);


        // Converting JSON object to string
        String jsonString = gson.toJson(jsonObject);
        log.debug("Json String: {}", jsonString);

        return jsonString;
    }
}
