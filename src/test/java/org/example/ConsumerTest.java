package org.example;

import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit test for simple App.
 */
public class ConsumerTest {

    @Test
    void testMeanFirstCase() throws InterruptedException {
        Consumer consumer = new Consumer();

        consumer.accept(10);
        TimeUnit.MINUTES.sleep(2);
        consumer.accept(20);
        TimeUnit.MINUTES.sleep(3);
        consumer.accept(30);
        TimeUnit.MINUTES.sleep(4);
        double mean = consumer.mean();
        assertEquals(30, mean, 0.001);


    }

    @Test
    void testMeanSecondCase() throws InterruptedException {
        Consumer consumer = new Consumer();

        consumer.accept(10);
        consumer.accept(20);
        consumer.accept(30);
        double mean = consumer.mean();
        assertEquals(20, mean, 0.001);
        TimeUnit.SECONDS.sleep(5);

        consumer.accept(40);
        TimeUnit.MINUTES.sleep(1);
        mean = consumer.mean();
        assertEquals(40, mean, 0.001);
        consumer.accept(50);
        TimeUnit.MINUTES.sleep(3);
        consumer.accept(50);
        consumer.accept(50);
        consumer.accept(50);
        consumer.accept(0);

        mean = consumer.mean();
        assertEquals(40, mean, 0.001);
        TimeUnit.MINUTES.sleep(6);
        assertEquals(0, consumer.mean(), 0.001);

    }
}
