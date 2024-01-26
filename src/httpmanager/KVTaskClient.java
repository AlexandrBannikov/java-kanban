package httpmanager;

import exception.ManagerSaveException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class KVTaskClient {
    String url;
    String apiToken;


    public KVTaskClient(String port) {
        url = "http://localhost:" + port + "/";
        this.apiToken = register(url);
    }

    public String getToken() {
        return apiToken;
    }

    private String register(String url) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url + "register"))
                    .GET()
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != HttpURLConnection.HTTP_OK) {
                throw new ManagerSaveException("Can`t do save request, status code: " + response.statusCode());
            }
            return response.body();
        } catch (IOException | InterruptedException e) {
            throw new ManagerSaveException("Can`t do save request");
        }
    }

    public String load(String key) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url + "load/" + key + "?API_TOKEN=" + apiToken))
                    .GET()
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != HttpURLConnection.HTTP_OK) {
                throw new ManagerSaveException("Can`t do save request, status code: " + response.statusCode());
            }
            return response.body();
        } catch (IOException | InterruptedException e) {
            throw new ManagerSaveException("Can`t do save request");
        }
    }

    public void put (String key, String value) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url + "save/" + key + "?API_TOKEN=" + apiToken))
                    .POST(HttpRequest.BodyPublishers.ofString(value))
                    .build();
            HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());
            if (response.statusCode() != HttpURLConnection.HTTP_OK) {
                throw new ManagerSaveException("Can`t do save request, status code: " + response.statusCode());
            }
        } catch (IOException | InterruptedException e) {
            throw new ManagerSaveException("Can`t do save request");
        }
    }
}