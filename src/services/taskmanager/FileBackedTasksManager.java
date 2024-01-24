package services.taskmanager;

import exception.ManagerSaveException;
import model.enums.Status;
import models.Epic;
import models.Subtask;
import models.Task;
import services.history.HistoryManager;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;

public class FileBackedTasksManager extends InMemoryTasksManager implements TasksManager {
    final File bootFile;
    public FileBackedTasksManager(File file) {
        this.bootFile = file;
    }
    public static void main(String[] args) {
        FileBackedTasksManager manager = new FileBackedTasksManager(new File("resources/file.csv"));

        System.out.println("Создаем объекты задач!");
        Task task1 = new Task("Создаем задачу Task #1"
                , "Описание Description task1", Status.New
                , 10, LocalDateTime
                .of(2006,1,1,1,0));
        Task task2 = new Task("Создаем задачу Task #2"
                , "Описание Description task2"
                , Status.New, 13, LocalDateTime
                .of(2002,2,2,2,23));
        manager.addNewTask(task1);
        manager.addNewTask(task2);



        Epic epic1 = new Epic("Создаем задачу Epic №1"
                , " Большая основная задача Big Epic 1", Status.New);
        manager.addNewEpic(epic1);

        manager.getEpicById(2);


        Subtask subtask1 =
                new Subtask("Создаем подзадачу Subtask #1"
                        , "Описание подзадачи № 1 к Epic", Status.New, epic1.getId()
                        , 15,
                LocalDateTime.of(2014,2,1,0,30));


        Subtask subtask2 = new Subtask("Создаем подзадачу Subtask #2"
                , "Описание подзадачи № 2 к Epic", Status.New, epic1.getId()
                , 13,
                LocalDateTime.of(2015,6,1,0,35));
        Subtask subtask3 = new Subtask("Создаем подзадачу Subtask #3"
                , "Описание подзадачи № 3 к Epic", Status.New, epic1.getId()
                , 13,
                LocalDateTime.of(2016,7,4,12,40));
        manager.addNewSubtask(subtask1);
        manager.addNewSubtask(subtask2);
        manager.addNewSubtask(subtask3);

        System.out.println();
        //System.out.println(manager.getHistory());
        System.out.println("Получаем список задач Task!");
        for (Task task : manager.getTasks()) {
            System.out.println(task);
        }

        System.out.println("Получить Task по id - " + manager.getTaskById(1));
        System.out.println();
        System.out.println("Получаем список Epic.");
        System.out.println("Получаем список Epic - " + manager.getEpics());
        System.out.println("Получить Epic по id  - " + manager.getEpicById(3));
        System.out.println();
        System.out.println("Получаем список подзадач.");
        for (Subtask subtask : manager.getSubtasks()) {
            System.out.println(subtask);
        }
        System.out.println();
        System.out.println("Получаем Subtask по id - " + manager.getSubtaskById(4));

        System.out.println("Удаляем подзадачу по id - " + subtask1.getId());
        manager.deleteSubtaskById(4);
        for (Subtask subtask : manager.getSubtasks()) {
            System.out.println("Получаем все подзадачи после удаления - " + subtask);
        }
        System.out.println("Очищаем список Subtask...");
        manager.deleteSubtasks();
        System.out.print("Получаем список подзадач после удаления...");
        System.out.println(manager.getSubtasks());
        System.out.println();
        System.out.println("Удаляем Task по id.");
        manager.deleteTaskById(2);
        System.out.println("Список задач Task - " + manager.getTasks());
        manager.updateTask(task2);
        System.out.println(manager.getHistory());
    }
    public void save() {

        String title = "id,type,name,status,description,duration,start_time,epic";
        try {
            PrintWriter writer = new PrintWriter(bootFile);
            writer.println(title);
            for (Task task : tasks.values()) {
                String s = task.toString();
                writer.println(s);
            }
            for (Epic epic : epics.values()) {
                String s = epic.toString();
                writer.println(s);
            }
            for (Subtask subtask : subtasks.values()) {
                String s = subtask.toString();
                writer.println(s);
            }
            writer.println(historyToString(historyManager));
            writer.close();
        } catch (IOException e) {
            throw new ManagerSaveException("Файл не сохранен!" + e.getMessage());
        }
    }

    public static FileBackedTasksManager loadFromFile(File file) {
        try {
            FileBackedTasksManager loadedManager = new FileBackedTasksManager(file);
            String contentWithHead = Files.readString(Path.of(file.getPath()));
            String content = contentWithHead.substring(57);
            String[] stringsFromContent = content.split("\n");

            int i = 0;
            while (i < stringsFromContent.length && !stringsFromContent[i].isBlank()) {
                String str = stringsFromContent[i];
                String[] substrings = str.split(",");
                switch (substrings[1]) {
                    case "TASK":
                        Task task = fromString(str);
                        assert task != null;
                        loadedManager.tasks.put(task.getId(), task);
                        loadedManager.taskTreeSet.add(task);
                        System.out.println("Создали Task!");
                        break;
                    case "EPIC":
                        Epic epic = (Epic) fromString(str);
                        assert epic != null;
                        loadedManager.epics.put(epic.getId(), epic);
                        break;
                    case "SUBTASK":
                        Subtask subtask = (Subtask) fromString(str);
                        assert subtask != null;
                        loadedManager.subtasks.put(subtask.getId(), subtask);
                        loadedManager.taskTreeSet.add(subtask);
                        break;
                }
                i++;
                if (i == stringsFromContent.length) {
                    break;
                }
            }
//            for (Subtask subtask : loadedManager.subtasks.values()) {
//                if (!loadedManager.epics.get(subtask.getEpicId()).getSubtaskIds().contains(subtask.getId())) {
//                    loadedManager.epics.get(subtask.getEpicId()).addSubtaskId(subtask.getId());
//                }
//                loadedManager.updateEpicStatus(subtask.getEpicId());
//                loadedManager.updateEpicTimeParams(subtask.getEpicId());
//            }
//            if (stringsFromContent.length > 0 && stringsFromContent[stringsFromContent.length - 1].isBlank()) {
//                List<Integer> history = historyFromString(stringsFromContent[stringsFromContent.length - 1]);
//                for (Integer id : history) {
//                    if (loadedManager.tasks.containsKey(id)) {
//                        loadedManager.historyManager.addTask(loadedManager.getTaskById(id));
//                    } else if (loadedManager.subtasks.containsKey(id)) {
//                        loadedManager.historyManager.addTask(loadedManager.getSubtaskById(id));
//                    } else if (loadedManager.epics.containsKey(id)) {
//                        loadedManager.historyManager.addTask(loadedManager.getEpicById(id));
//                    }
//                }
//            }
            return loadedManager;
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка загрузки");
        }
    }
    public static Task fromString(String string) {
        String[] parts = string.split(",");
        switch (parts[1]) {
            case "TASK":
                Task task = new Task(parts[2], parts[4], Status.valueOf(parts[3]),
                        parseInt(parts[5]), LocalDateTime.parse(parts[6]));
                task.setId(parseInt(parts[0]));
                return task;
            case "EPIC":
                Epic epic = new Epic(parts[2], parts[4], Status.valueOf(parts[3]));
                epic.setId(parseInt(parts[0]));
                return epic;
            case "SUBTASK":
                Subtask subtask = new Subtask(parts[2], parts[4], Status.valueOf(parts[3]), parseInt(parts[7]),
                        parseInt(parts[5]), LocalDateTime.parse(parts[6]));
                subtask.setId(parseInt(parts[0]));
                return subtask;
        }
        return null;
    }
    /*
    Вот что про в ТЗ написано про методы и параметры.

    Напишите статические методы static String historyToString(HistoryManager manager)
    и static List<Integer> historyFromString(String value)
    для сохранения и восстановления менеджера истории из CSV.
     */
    static String historyToString(HistoryManager manager) { //
        StringBuilder sb = new StringBuilder();
        for(Task task: manager.getHistory()){
            sb.append(task.getId());
            sb.append(",");
        }
        if(sb.length() > 0) {
            sb.setLength(sb.length() - 1);
        }
        return String.valueOf(sb);
    }
    static List<Integer> historyFromString(String str) {
        String[] history = str.split(",");
        List<Integer> listResult = new ArrayList<>();
        for (String item: history) {
            if (item.matches("\\d")) listResult.add(parseInt(item));
        }
        return listResult;
    }



    @Override
    public Task getTaskById(int id) {
        Task task = super.getTaskById(id);
        save();
        return task;
    }

    @Override
    public Subtask getSubtaskById(int id) {
        Subtask subtask = super.getSubtaskById(id);
        save();
        return subtask;
    }

    @Override
    public Epic getEpicById(int id) {
        Epic epic = super.getEpicById(id);
        save();
        return epic;
    }

    @Override
    public int addNewTask(Task task) {
        int t = super.addNewTask(task);
        save();
        return t;
    }

    @Override
    public int addNewEpic(Epic epic) {
        int e = super.addNewEpic(epic);
        save();
        return e;
    }

    @Override
    public int addNewSubtask(Subtask subtask) {
        int s = super.addNewSubtask(subtask);
        save();
        return s;
    }

    @Override
    public void updateTask(Task task) {
        super.updateTask(task);
        save();
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        super.updateSubtask(subtask);
        save();
    }

    @Override
    public void updateEpic(Epic epic) {
        super.updateEpic(epic);
        save();
    }

    @Override
    public void deleteTaskById(int id) {
        super.deleteTaskById(id);
        save();
    }

    @Override
    public void deleteEpicById(int id) {
        super.deleteEpicById(id);
        save();
    }

    @Override
    public void deleteSubtaskById(int id) {
        super.deleteSubtaskById(id);
        save();
    }

    @Override
    public void deleteTasks() {
        super.deleteTasks();
        save();
    }

    @Override
    public void deleteEpics() {
        super.deleteEpics();
        save();
    }

    @Override
    public void deleteSubtasks() {
        super.deleteSubtasks();
        save();
    }
}
