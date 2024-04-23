package org.example.log;

import java.util.LinkedList;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Ограниченная очередь
 * - имеет ограниченный размер (старые записи вытесняются)
 * - потокобезопасная
 * - есть возможность доступа к части данных
 */
public class MyQueue<T> {
    private final T[] elements;
    private final int maxSize;
    private int head = 0;
    private int tail = 0;
    private final ReentrantLock lock = new ReentrantLock();

    public MyQueue(int maxSize) {
        this.maxSize = maxSize;
        elements = (T[]) new Object[maxSize];
    }

    public void add(T element) {
        lock.lock();
        try {
            elements[tail] = element;
            tail = (tail + 1) % maxSize;
            if (tail == head) {
                head = (head + 1) % maxSize;
            }
        } finally {
            lock.unlock();
        }
    }

    public T get(int index) {
        lock.lock();
        try {
            return elements[(head + index) % maxSize];
        } finally {
            lock.unlock();
        }
    }

    public int size() {
        lock.lock();
        try {
            return (tail - head + maxSize) % maxSize;
        } finally {
            lock.unlock();
        }
    }

    public Iterable<T> subList(int startFrom, int indexTo) {
        lock.lock();
        try {
            int subListSize = indexTo - startFrom + 1;
            LinkedList<T> subList = new LinkedList<>();
            for (int i = 0; i < subListSize; i++) {
                subList.add(elements[(head + startFrom + i) % maxSize]);
            }
            return subList;
        } finally {
            lock.unlock();
        }
    }
}