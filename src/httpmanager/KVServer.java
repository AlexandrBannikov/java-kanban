package httpmanager;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import static java.nio.charset.StandardCharsets.UTF_8;
import java.util.HashMap;
import java.util.Map;

public class KVServer {
    public static final int PORT = 8080;
    private final String apiToken;
    private final HttpServer server;
    private final Map<String, String> data = new HashMap<>();

    public KVServer() throws IOException {
        apiToken = generateApiToken();
        server = HttpServer.create(new InetSocketAddress("localhost", PORT), 0);
        server.createContext("/register", this::register);
        server.createContext("/save", this::save);
        server.createContext("/register", this::load);
    }
    private void load(HttpExchange exchange) throws IOException {
        try {
            String key = exchange.getRequestURI().getPath().substring("/load".length());
            String fromData = data.get(key);
            sendText(exchange, fromData);
            System.out.println("KV - сервер отработал!");
        } finally {
            exchange.close();
        }
    }
    private void save(HttpExchange exchange) throws IOException {
        try {
            System.out.println("\n/save");
            if (!hasAuth(exchange)) {
                System.out.println("Запрос не авторизован, нужен параметр в query API_TOKEN со значением апи-ключа");
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_FORBIDDEN, 0);
                return;
            }
            if ("POST".equals(exchange.getRequestMethod())) {
                String key = exchange.getRequestURI().getPath().substring("/save/".length());
                if (key.isEmpty()) {
                    System.out.println("Key для сохранения пустой. key указывается в пути: /save/{key}");
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                    return;
                }
                String value = readText(exchange);
                if (value.isEmpty()) {
                    System.out.println("Value для сохранения пустой. value указывается в теле запроса");
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                    return;
                }
                data.put(key, value);
                System.out.println("Значение для ключа " + key + " успешно обновлено!");
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            } else {
                System.out.println("/save ждёт POST-запрос, а получил: " + exchange.getRequestMethod());
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_METHOD, 0);
            }
        } finally {
            exchange.close();
        }
    }

    private void register(HttpExchange exchange) throws IOException {
        try {
            System.out.println("\n/register");
            if ("GET".equals(exchange.getRequestMethod())) {
                sendText(exchange, apiToken);
            } else {
                System.out.println("/register ждёт GET-запрос, а получил " + exchange.getRequestMethod());
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_METHOD, 0);
            }
        } finally {
            exchange.close();
        }
    }
    public void start() {
        System.out.println("Запускаем сервер на порту " + PORT);
        System.out.println("Открываем в браузере http://localhost:" + PORT + "/");
        System.out.println("API_TOKEN: " + apiToken);
        server.start();
    }
    private String generateApiToken() {
        return "" + System.currentTimeMillis();
    }
    private boolean hasAuth(HttpExchange exchange) {
        String rawQuery = exchange.getRequestURI().getRawQuery();
        return rawQuery != null && (rawQuery.contains("API_TOKEN=" + apiToken) || rawQuery.contains("API_TOKEN=DEBUG"));
    }
    private String readText(HttpExchange h) throws IOException {
        return new String(h.getRequestBody().readAllBytes(), UTF_8);
    }
    private void sendText(HttpExchange exchange, String text) throws IOException {
        byte[] resp = text.getBytes(UTF_8);
        exchange.getResponseHeaders().add("Content-Type", "application/json");
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, resp.length);
        exchange.getResponseBody().write(resp);
    }
}





