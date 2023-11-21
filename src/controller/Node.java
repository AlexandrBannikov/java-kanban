package controller;

import models.Task;

public class Node <T> {
    /* поле узла */
    public T data;
    /* поле ссылка на следующий узел*/
    public Node<T> next;
    /* поле ссылка на предыдущий узел*/
    public Node<T> prev;


    public Node(T data, Node<T> next, Node<T> prev) {
        this.data = data;
        this.next = next;
        this.prev = prev;
    }
}