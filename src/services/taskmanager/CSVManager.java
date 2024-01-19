package services.taskmanager;

import exception.ManagerSaveException;
import model.enums.Status;
import models.Epic;
import models.Subtask;
import models.Task;
import services.history.HistoryManager;
import services.history.InMemoryHistoryManager;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;


public class CSVManager {
//    InMemoryTasksManager tasksManager;
//    InMemoryHistoryManager historyManager;
//    File bootFile = new File("resources/file.csv");
//    public void save() {
//        String title = "id,type,name,status,description,duration,start_time,epic";
//        try {
//            PrintWriter writer = new PrintWriter(bootFile);
//            writer.println(title);
//            for (Task task : tasksManager.tasks.values()) {
//                String s = task.toString();
//                writer.println(s);
//            }
//            for (Epic epic : tasksManager.epics.values()) {
//                String s = epic.toString();
//                writer.println(s);
//            }
//            for (Subtask subtask : tasksManager.subtasks.values()) {
//                String s = subtask.toString();
//                writer.println(s);
//            }
//            writer.println(historyToString(historyManager));
//            writer.close();
//        } catch (IOException e) {
//            throw new ManagerSaveException("Файл не сохранен!" + e.getMessage());
//        }
//    }
//    public static FileBackedTasksManager loadFromFile(File file) {
//        try {
//            FileBackedTasksManager loadedManager = new FileBackedTasksManager(file);
//            String contentWithTitle = Files.readString(Path.of(file.getPath()));
//            String content = contentWithTitle.substring(58);
//                String[] substring = content.split(",");
//                switch (substring[1]) {
//                    case "TASK":
//                        Task task = fromString(content);
//                        assert task != null;
//                        loadedManager.tasks.put(task.getId(), task);
//                        loadedManager.taskTreeSet.add(task);
//                        break;
//                    case "EPIC":
//                        Epic epic = (Epic) fromString(content);
//                        assert epic != null;
//                        loadedManager.epics.put(epic.getId(), epic);
//                        break;
//                    case "SUBTASK":
//                        Subtask subtask = (Subtask) fromString(content);
//                        assert subtask != null;
//                        loadedManager.subtasks.put(subtask.getId(), subtask);
//                        loadedManager.taskTreeSet.add(subtask);
//                        break;
//                }
//            return loadedManager;
//        } catch (IOException e) {
//            throw new ManagerSaveException("Ошибка загрузки!" + e.getMessage());
//        }
//    }
//    public static Task fromString(String string) {
//        String[] parts = string.split(",");
//        switch (parts[1]) {
//            case "TASK":
//                Task task = new Task(parts[2], parts[4], Status.valueOf(parts[3]),
//                        parseInt(parts[5]), LocalDateTime.parse(parts[6]));
//                task.setId(parseInt(parts[0]));
//                return task;
//            case "EPIC":
//                Epic epic = new Epic(parts[2], parts[4], Status.valueOf(parts[3]));
//                epic.setId(parseInt(parts[0]));
//                return epic;
//            case "SUBTASK":
//                Subtask subtask = new Subtask(parts[2], parts[4], Status.valueOf(parts[3]), parseInt(parts[7]),
//                        parseInt(parts[5]), LocalDateTime.parse(parts[6]));
//                subtask.setId(parseInt(parts[0]));
//                return subtask;
//        }
//        return null;
//    }
    /*
    Вот что про в ТЗ написано про методы и параметры.

    Напишите статические методы static String historyToString(HistoryManager manager)
    и static List<Integer> historyFromString(String value)
    для сохранения и восстановления менеджера истории из CSV.
     */
//    static String historyToString(HistoryManager manager) { //
//        StringBuilder sb = new StringBuilder();
//        for(Task task: manager.getHistory()){
//            sb.append(task.getId());
//            sb.append(",");
//        }
//        if(sb.length() > 0) {
//            sb.setLength(sb.length() - 1);
//        }
//        return String.valueOf(sb);
//    }
//    static List<Integer> historyFromString(String str) {
//        String[] history = str.split(",");
//        List<Integer> listResult = new ArrayList<>();
//        for (String item: history) {
//            listResult.add(parseInt(item));
//        }
//        return listResult;
//    }
}
