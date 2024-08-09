package org.example;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

public class Consumer {
    private final BlockingQueue<Pair> numbersQueue = new LinkedBlockingQueue<>();
    private final AtomicLong sum = new AtomicLong(0);
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private final long TIME_SLEEP = 5 * 60 * 1000;

    /**
     * Called periodically to consume an integer.
     */
    public void accept(int number) {
        numbersQueue.offer(new Pair(System.currentTimeMillis(), number));
        sum.addAndGet(number);

        scheduler.scheduleAtFixedRate(this::shiftWindow,
                0, TIME_SLEEP,
                TimeUnit.MILLISECONDS);
    }

    /**
     * Returns the mean (aka average) of numbers consumed in the
     * last 5 minutes period.
     */
    public double mean() {
        if (numbersQueue.isEmpty()) {
            return 0.0;
        }
        return (double) sum.get() / numbersQueue.size();
    }

    private void shiftWindow() {
        long currentMillis = System.currentTimeMillis();
        while (!numbersQueue.isEmpty() &&
                currentMillis - numbersQueue.peek().getTime() > TIME_SLEEP) {
            sum.addAndGet(-numbersQueue.poll().getNumber());
        }
    }
}