package dev.bliss.api.auth;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.net.URI;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class AuthenticationApi {
    private static String hwid;
    private WebSocket webSocket;

    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    private static String getHwidFromDB(String username) {
        try (MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017")) {
            MongoDatabase database = mongoClient.getDatabase("Project0");
            MongoCollection<Document> collection = database.getCollection("Bliss:users");

            Document user = collection.find(new Document("_id", username.toLowerCase())).first();
            if (user != null) {
                return user.getString("hwid");
            }
        }
        return null;
    }

    public static String getHwid(){
        hwid = System.getProperty("os.name") + System.getProperty("user.name") + Runtime.getRuntime().availableProcessors() + System.getProperty("os.arch") + System.getenv("COMPUTERNAME");
        return bytesToHex(hwid.getBytes());
    }

    private void initWebSocket() {
        HttpClient client = HttpClient.newHttpClient();
        CompletableFuture<WebSocket> wsFuture = client.newWebSocketBuilder()
                .buildAsync(URI.create("ws://localhost:8080"), new WebSocket.Listener() {
                    @Override
                    public void onOpen(WebSocket webSocket) {
                        System.out.println("WebSocket connection opened");
                        AuthenticationApi.this.webSocket = webSocket;
                    }

                    @Override
                    public CompletionStage<?> onText(WebSocket webSocket, CharSequence data, boolean last) {
                        System.out.println("Received response: " + data);
                        return WebSocket.Listener.super.onText(webSocket, data, last);
                    }

                    @Override
                    public CompletionStage<?> onClose(WebSocket webSocket, int statusCode, String reason) {
                        System.out.println("WebSocket connection closed");
                        return WebSocket.Listener.super.onClose(webSocket, statusCode, reason);
                    }
                });

        webSocket = wsFuture.join();
    }

    private void sendMessage() {
        initWebSocket();
        String username = System.getProperty("user.name");
        String hwid = getHwidFromDB(username);
        if (hwid != null) {
            webSocket.sendText(hwid, true);
        } else {
            System.out.println("User not found in the database");
            // I will implement a way to shut down the client here
        }
    }

    public static void main(String[] args) {
        System.out.println(getHwid());
        // will send a message to the server
    }
}