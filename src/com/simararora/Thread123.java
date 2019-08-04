package com.simararora;

public class Thread123 {

    private volatile Holder numberHolder;

    public static void main(String[] args) {
        new Thread123().execute(3, 5);
    }

    private void execute(int numberThreads, int timesToPrint) {
        numberHolder = new Holder(numberThreads);
        for (int i = 0; i < numberThreads; i++) {
            new PrinterThread(numberHolder, i + 1, timesToPrint).start();
        }
    }

    private static class Holder {
        int currentNumber = 1;
        private int maxNumber;

        Holder(int maxNumber) {
            this.maxNumber = maxNumber;
        }

        void increment() {
            this.currentNumber++;
            if (this.currentNumber == maxNumber + 1) {
                this.currentNumber = 1;
            }
        }
    }

    private static class PrinterThread extends Thread {

        private final Holder currentNumberHolder;
        private int ownNumber;
        private int timesPrinted = 0;
        private int timesToPrint;

        PrinterThread(Holder currentNumberHolder, int ownNumber, int timesToPrint) {
            this.currentNumberHolder = currentNumberHolder;
            this.ownNumber = ownNumber;
            this.timesToPrint = timesToPrint;
        }

        @Override
        public void run() {
            super.run();
            synchronized (currentNumberHolder) {
                while (!isInterrupted()) {
                    if (ownNumber == currentNumberHolder.currentNumber) {
                        print();
                        currentNumberHolder.increment();
                        currentNumberHolder.notifyAll();
                        interruptIfDone();
                    } else {
                        try {
                            currentNumberHolder.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

        private void print() {
            System.out.println("Thread " + Thread.currentThread().getId() + ", " + this.currentNumberHolder.currentNumber);
            this.timesPrinted++;
        }

        private void interruptIfDone() {
            if (timesPrinted == timesToPrint) {
                interrupt();
            }
        }
    }
}
