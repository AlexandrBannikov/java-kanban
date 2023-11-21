package services.history;

import controller.Node;
import models.Task;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {
    private final CustomLikedList<Task> taskHistory = new CustomLikedList<>();
    private final Map<Integer, Node<Task>> nodeMap = new HashMap<>();

    @Override
    public void addTask(Task task) {
        if (nodeMap.containsKey(task.getId())) {
            taskHistory.removeNode(nodeMap.get(task.getId()));
        }
        nodeMap.put(task.getId(), taskHistory.linkLast(task));
    }

    @Override
    public void remove(int id) {
        taskHistory.removeNode(nodeMap.get(id));
        nodeMap.remove(id);
    }

    @Override
    public List<Task> getHistory() {
        return taskHistory.getTasks();
    }



    static class CustomLikedList<Task> {
        public Node<Task> head;
        public Node<Task> tail;
        private int size = 0;

        public void removeNode(Node<Task> node) {
            Node<Task> prevNode = node.prev;
            Node<Task> nextNode = node.next;
            if (prevNode == null) {
                head = nextNode;
            } else {
                prevNode.next = nextNode;
                node.prev = null;
            }
            if (nextNode == null) {
                tail = prevNode;
            } else {
                nextNode.prev = prevNode;
                node.next = null;
            }
            node.data = null;
            size--;
        }
        public Node<Task> linkLast(Task task) {
            Node<Task> oldTail = tail;
            Node<Task> newNode = new Node<>(task, null, oldTail);
            tail = newNode;
            if (oldTail == null) {
                head = newNode;
            } else {
                oldTail.next = newNode;
            }
            size++;
            return newNode;
        }
        public List<Task> getTasks() {
            List<Task> tasks = new ArrayList<>(size);
            for (Node<Task> current = head; current != null; current = current.next) {
                tasks.add(current.data);
            }
            return tasks;
        }
    }
}
