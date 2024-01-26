package httpmanager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import models.Epic;
import models.Subtask;
import models.Task;
import services.taskmanager.FileBackedTasksManager;
import services.taskmanager.TasksManager;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HTTPTasksManager extends FileBackedTasksManager implements TasksManager {
    private final KVTaskClient client;
    private final Gson gson;

    public HTTPTasksManager(String url) {
        super();
        client = new KVTaskClient(url);
        gson = new Gson();
        load();
    }

    public KVTaskClient getClient() {
        return client;
    }
    private void load() {
        ArrayList<Task> tasks = gson.fromJson(client.load("tasks"), new TypeToken<ArrayList<Task>>()
        {}.getType());
        addTasks(tasks);
        ArrayList<Epic> epics = gson.fromJson(client.load("epics"), new TypeToken<ArrayList<Epic>>(){}.getType());
        addTasks(epics);
        ArrayList<Subtask> subtasks = gson.fromJson(client.load("subtasks"), new TypeToken<ArrayList<Subtask>>()
        {}.getType());
        addTasks(subtasks);
        ArrayList<? extends Task> history = gson.fromJson(client.load("history"),
                new TypeToken<ArrayList<? extends Task>>(){}.getType());
        if (!(history == null)) {
            for (Task task: history) {
                getHistory().add(task);
            }
        }
    }

    @Override
    public void save() {
        String jsonTasks = gson.toJson(new ArrayList<>(tasks.values()));
        client.put("tasks", jsonTasks);
        String jsonEpics = gson.toJson(new ArrayList<>(epics.values()));
        client.put("epics", jsonEpics);
        String jsonSubtasks = gson.toJson(new ArrayList<>(subtasks.values()));
        client.put("subtasks", jsonSubtasks);
        String jsonHistory = gson.toJson(new ArrayList<>(getHistory().stream().map(Task::getId).collect(Collectors.toList())));
        client.put("history", jsonHistory);
    }
    protected void addTasks(List<? extends Task> tasks) {
        if (!(tasks == null)) {
            for (Task task : tasks) {
                if (task.getClass().equals(Task.class)) {
                    this.tasks.put(task.getId(), task);
                    taskTreeSet.add(task);
                }
                if (task.getClass().equals(Epic.class)) {
                    this.epics.put(task.getId(), (Epic) task);
                    taskTreeSet.add(task);
                }
                if (task.getClass().equals(Subtask.class)) {
                    this.subtasks.put(task.getId(), (Subtask) task);
                    taskTreeSet.add(task);
                }
            }
        }
    }
}
