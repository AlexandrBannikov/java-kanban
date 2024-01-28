package httpmanager;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import models.Epic;
import models.Subtask;
import models.Task;

import services.taskmanager.TasksManager;
import util.Managers;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.URISyntaxException;
import java.util.regex.Pattern;

import static jdk.internal.util.xml.XMLStreamWriter.DEFAULT_CHARSET;

public class HTTPTaskServer {
    public static final int PORT = 8080;
    private final HttpServer server;
    private final Gson gson;
    public TasksManager manager;
    public HTTPTaskServer() throws IOException, URISyntaxException, InterruptedException {
        this(Managers.getDefault());
    }
    public HTTPTaskServer(TasksManager manager) throws IOException {
        server = HttpServer.create(new InetSocketAddress("localhost", PORT),0);
        this.manager = manager;
        gson = new Gson();
        server.createContext("/tasks", this::handle);
    }

    public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException {
        final HTTPTaskServer server = new HTTPTaskServer();
        server.start();
    }
    public void start() {
        System.out.println("Запускаем сервер на порту " + PORT);
        System.out.println("Открываем в браузере http://localhost:" + PORT + "/tasks");
        server.start();
    }

    public void stop() {
        server.stop(0);
        System.out.println("Остановили сервер!");
    }
    public void handle(HttpExchange exchange) throws IOException {
        String path = String.valueOf(exchange.getRequestURI());
        String method = exchange.getRequestMethod();
        switch (method) {
            case "GET": {
                if (Pattern.matches("^/tasks/task/$",path)) {
                    System.out.println("Запрошены все задачи");
                    String response = gson.toJson(manager.getTasks());
                    writeResponse(exchange, response, HttpURLConnection.HTTP_OK);
                }
                if (Pattern.matches("^/tasks/task/\\?id=\\d",path)) {
                    String idInStr = path.replaceFirst("/tasks/task/\\?id=", "");
                    Integer id = Integer.parseInt(idInStr);
                    System.out.println("Запрошена задача с id " + id);
                    String response = gson.toJson(manager.getTaskById(id));
                    writeResponse(exchange, response, HttpURLConnection.HTTP_OK);
                }
                if (Pattern.matches("^/tasks/subtask/$",path)) {
                    System.out.println("Запрошены все подзадачи");
                    String response = gson.toJson(manager.getSubtasks());
                    writeResponse(exchange, response, HttpURLConnection.HTTP_OK);
                }
                if (Pattern.matches("^/tasks/subtask/\\?id=\\d",path)) {
                    String idInStr = path.replaceFirst("/tasks/subtask/\\?id=", "");
                    Integer id = Integer.parseInt(idInStr);
                    System.out.println("Запрошена подзадача с id " + id);
                    String response = gson.toJson(manager.getSubtaskById(id));
                    writeResponse(exchange, response, HttpURLConnection.HTTP_OK);
                }
                if (Pattern.matches("^/tasks/epic/$",path)) {
                    System.out.println("Запрошены все эпики");
                    String response = gson.toJson(manager.getEpics());
                    writeResponse(exchange, response, HttpURLConnection.HTTP_OK);
                }
                if (Pattern.matches("^/tasks/epic/\\?id=\\d",path)) {
                    String idInStr = path.replaceFirst("/tasks/subtask/\\?id=", "");
                    Integer id = Integer.parseInt(idInStr);
                    System.out.println("Запрошен эпик с id " + id);
                    String response = gson.toJson(manager.getEpicById(id));
                    writeResponse(exchange, response, HttpURLConnection.HTTP_OK);
                }
                if (Pattern.matches("^/tasks/subtask/epic/\\?id=\\d",path)) {
                    String idInStr = path.replaceFirst("/tasks/subtask/\\?id=", "");
                    Integer id = Integer.parseInt(idInStr);
                    System.out.println("Запрошена подзадача с id " + id);
                    String response = gson.toJson(manager.getSubtaskOfEpic(manager.getEpicById(id)));
                    writeResponse(exchange, response, HttpURLConnection.HTTP_OK);
                }
                if (Pattern.matches("^/tasks/history/$",path)) {
                    System.out.println("Запрошена история");
                    String response = gson.toJson(manager.getHistory());
                    writeResponse(exchange, response, HttpURLConnection.HTTP_OK);
                }
                if (Pattern.matches("^/tasks/$",path)) {
                    System.out.println("Запрошена история");
                    String response = gson.toJson(manager.getPrioritizedTasks());
                    writeResponse(exchange, response, HttpURLConnection.HTTP_OK);
                }
                break;
            }
            case "POST": {
                if (Pattern.matches("^/tasks/task/$",path)) {
                    System.out.println("Попытка добавить задачу");
                    try {
                        InputStream is  = exchange.getRequestBody();
                        String text = new String(is.readAllBytes(), DEFAULT_CHARSET);
                        Task task = gson.fromJson(text, Task.class);
                        if (!manager.getTasks().contains(task)) {
                            manager.addNewTask(task);
                        } else {
                            manager.updateTask(task);
                        }
                        writeResponse(exchange, "added", HttpURLConnection.HTTP_OK);
                    } catch (JsonSyntaxException e) {
                        writeResponse(exchange, "Получен некорректный JSON", HttpURLConnection.HTTP_BAD_REQUEST);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                if (Pattern.matches("^/tasks/subtask/$",path)) {
                    System.out.println("Попытка добавить подзадачу");
                    try {
                        InputStream is  = exchange.getRequestBody();
                        String text = new String(is.readAllBytes(), DEFAULT_CHARSET);
                        Subtask subtask = gson.fromJson(text, Subtask.class);
                        if (!manager.getSubtasks().contains(subtask)) {
                            manager.addNewSubtask(subtask);
                        } else {
                            manager.updateSubtask(subtask);
                        }
                        writeResponse(exchange, "added", HttpURLConnection.HTTP_OK);
                    } catch (JsonSyntaxException e){
                        writeResponse(exchange, "Получен некорректный JSON", HttpURLConnection.HTTP_BAD_REQUEST);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                if (Pattern.matches("^/tasks/epic/$",path)) {
                    System.out.println("Попытка добавить подзадачу");
                    try {
                        InputStream is  = exchange.getRequestBody();
                        String text = new String(is.readAllBytes(), DEFAULT_CHARSET);
                        Epic epic = gson.fromJson(text, Epic.class);
                        if (!manager.getEpics().contains(epic)) {
                            manager.addNewEpic(epic);
                        } else {
                            manager.updateEpic(epic);
                        }
                        writeResponse(exchange, "added", HttpURLConnection.HTTP_OK);
                    } catch (JsonSyntaxException e){
                        writeResponse(exchange, "Получен некорректный JSON", HttpURLConnection.HTTP_BAD_REQUEST);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                break;
            }
            case "DELETE": {
                if (Pattern.matches("^/tasks/task/",path)) {
                    System.out.println("Удаление всех задач");
                    manager.deleteTasks();
                    writeResponse(exchange, "Все задачи удалены" , HttpURLConnection.HTTP_OK);
                }
                if (Pattern.matches("^/tasks/task/\\?id=\\d",path)) {
                    String idInStr = path.replaceFirst("/tasks/task/\\?id=", "");
                    Integer id = Integer.parseInt(idInStr);
                    System.out.println("Запрошено удаление задачи с id " + id);
                    manager.deleteTaskById(id);
                    String response = "Удаление задачи с id " + id;
                    writeResponse(exchange, response , HttpURLConnection.HTTP_OK);
                }
                if (Pattern.matches("^/tasks/subtask/",path)) {
                    System.out.println("Удаление всех подзадач");
                    manager.deleteSubtasks();
                    writeResponse(exchange, "Все подзадачи удалены" , HttpURLConnection.HTTP_OK);
                }
                if (Pattern.matches("^/tasks/subtask/\\?id=\\d",path)) {
                    String idInStr = path.replaceFirst("/tasks/subtask/\\?id=", "");
                    Integer id = Integer.parseInt(idInStr);
                    System.out.println("Запрошено удаление подзадачи с id " + id);
                    manager.deleteSubtaskById(id);
                    String response = "Удаление подзадачи с id " + id;
                    writeResponse(exchange, response , HttpURLConnection.HTTP_OK);
                }
                if (Pattern.matches("^/tasks/epic/",path)) {
                    System.out.println("Удаление всех эпиков");
                    manager.deleteEpics();
                    writeResponse(exchange, "Все эпики удалены" , HttpURLConnection.HTTP_OK);
                }
                if (Pattern.matches("^/tasks/epic/\\?id=\\d",path)) {
                    String idInStr = path.replaceFirst("/tasks/task/\\?id=", "");
                    Integer id = Integer.parseInt(idInStr);
                    System.out.println("Запрошено удаление задачи с id " + id);
                    manager.deleteEpicById(id);
                    String response = "Удаление эпика с id " + id;
                    writeResponse(exchange, response , HttpURLConnection.HTTP_OK);
                }
                break;
            }
            default:
                writeResponse(exchange, "Такого метода не существует!", HttpURLConnection.HTTP_NOT_FOUND);
        }
    }

    private void writeResponse(HttpExchange exchange, String response, int responseCode) throws IOException {
        if (response.isBlank()) {
            exchange.sendResponseHeaders(responseCode, 0);
        } else {
            byte[] bytes = response.getBytes(DEFAULT_CHARSET);
            exchange.sendResponseHeaders(responseCode, bytes.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(bytes);
            }
        }
        exchange.close();
    }
}
