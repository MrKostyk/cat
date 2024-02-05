package com.example.demo15;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        try {
            String catFact = getCatFact();
            welcomeText.setText(catFact);
        } catch (IOException e) {
            e.printStackTrace();
            welcomeText.setText("Помилка отримання факту про кота");
        }
    }

    private String getCatFact() throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://cat-fact.herokuapp.com/facts/random")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            JsonElement jsonResponse = JsonParser.parseString(response.body().string());
            JsonObject factObject = jsonResponse.getAsJsonObject();
            String fact = factObject.get("text").getAsString();

            return fact;
        }
    }
}