package com.simararora;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConsumerProducer {

    public static void main(String[] args) {
        new ConsumerProducer().execute();
    }

    private MyBlockingQueue<Task> myBlockingQueue = new MyBlockingQueue<>(2);

    private void execute() {
        ExecutorService executorService = Executors.newFixedThreadPool(4);

        executorService.execute(new Producer(1));
        executorService.execute(new Producer(2));
        executorService.execute(new Producer(3));
        executorService.execute(new Consumer());
        executorService.execute(new Consumer());
        executorService.execute(new Producer(4));
        executorService.execute(new Consumer());
        executorService.execute(new Consumer());

    }

    private class Consumer implements Runnable {

        @Override
        public void run() {
            try {
                processTask(myBlockingQueue.takeTask());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        private void processTask(Task task) {
            System.out.println("Task " + task.id + " Consumed Successfully");
        }
    }

    private class Producer implements Runnable {

        private int id;

        Producer(int id) {
            this.id = id;
        }

        @Override
        public void run() {
            try {
                myBlockingQueue.addTask(new Task(id));
                System.out.println("Task " + id + " Added Successfully");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static class Task {
        private int id;

        Task(int id) {
            this.id = id;
        }
    }

    private static class MyBlockingQueue<T> {

        private Queue<T> taskQueue;
        private int maxSize;

        MyBlockingQueue(int maxSize) {
            taskQueue = new LinkedList<>();
            this.maxSize = maxSize;
        }

        synchronized void addTask(T task) throws InterruptedException {
            while (taskQueue.size() == maxSize) {
                wait();
            }
            taskQueue.add(task);
            notifyAll();
        }

        synchronized T takeTask() throws InterruptedException {
            while (taskQueue.size() == 0) {
                wait();
            }
            T t = taskQueue.remove();
            notifyAll();
            return t;

        }
    }
}
