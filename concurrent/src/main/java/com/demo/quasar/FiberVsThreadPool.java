package com.demo.quasar;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import co.paralleluniverse.fibers.Fiber;
import co.paralleluniverse.fibers.SuspendExecution;
import co.paralleluniverse.fibers.Suspendable;
import co.paralleluniverse.strands.Strand;
import co.paralleluniverse.strands.SuspendableRunnable;

/**
 * 纤程 vs 多线程
 * https://www.cnblogs.com/z-test/p/9435847.html
 *
 * 协程是比线程更轻量级的程序处理单元，也可以说是运行在线程上的线程，由自己控制
 * 1.适用于被阻塞的，且需要大量并发的场景。
 * 2.不适用于，大量计算的多线程，遇到此种情况，更好实用线程去解决。
 *
 * 虽然Java的线程的API封装的很好，使用起来非常的方便，但是使用起来也得小心。
 * 首先线程需要耗费资源，所以单个的机器上创建上万个线程很困难，其次线程之间的切换也需要耗费CPU,在线程非常多的情况下导致很多CPU资源耗费在
 * 线程切换上，通过提高线程数来提高系统的性能有时候适得其反。你可以看到现在一些优秀的框架如Netty都不会创建很多的线程，默认2倍的CPU core
 * 的线程数就已经应付的很好了，比如node.js可以使用单一的进程／线程应付高并发。
 *
 * 纤程使用的资源更少，它主要保存栈信息，所以一个系统中可以创建上万的纤程Fiber，而实际的纤程调度器只需要几个Java线程即可。
 *
 * 下面这个例子中方法m1调用m2,m2调用m3，但是m2会暂停1秒钟，用来模拟实际产品中的阻塞，m3执行了一个简单的计算。
 * 通过线程和纤程两种方式我们看看系统的吞吐率(throughput)和延迟(latency)。
 *
 * @author: lijiawei04
 * @date: 2021/6/7 8:40 下午
 */
public class FiberVsThreadPool {

    public static void main(String[] args) throws InterruptedException {
        int count = 1000;
        testFiber(count);
        testThreadPool(count);
    }

    /**
     * m1 调用 m2
     */
    @Suspendable
    static void m1() throws InterruptedException, SuspendExecution {
        String m = "m1";
        // System.out.println("m1 begin");
        m = m2();
        // System.out.println("m1 end");
        // System.out.println(m);
    }

    /**
     * m2 调用 m3 并暂停1s，模拟实际产品中的阻塞
     */
    static String m2() throws SuspendExecution, InterruptedException {
        String m = m3();
        Strand.sleep(1000);
        return m;
    }

    /**
     * m3 做一个简单的计算
     */
    @Suspendable
    static String m3() {
        List<Integer> l = Stream.of(1, 2, 3).filter(i -> i % 2 == 0).collect(Collectors.toList());
        return l.toString();
    }

    static void testThreadPool(int count) throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(count);
        ExecutorService es = Executors.newFixedThreadPool(200);
        LongAdder latency = new LongAdder();
        LongAdder counter = new LongAdder();
        long t = System.currentTimeMillis();
        LongAdder sum = new LongAdder();
        for (int i = 0; i < count; i++) {
            es.submit(() -> {
                //                Long long1=sum;
                long start = System.currentTimeMillis();
                try {
                    m1();
                } catch (InterruptedException | SuspendExecution e) {
                    e.printStackTrace();
                }
                start = System.currentTimeMillis() - start;
                latency.add(start);
                counter.add(1);
                sum.increment();
                latch.countDown();
            });
        }
        latch.await();
        t = System.currentTimeMillis() - t;
        long l = latency.longValue() / count;
        System.out.println("thread pool took: " + t + ", latency: " + l + " ms------" + counter + "sum---" + sum);
        es.shutdownNow();
    }

    static void testFiber(int count) throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(count);
        LongAdder latency = new LongAdder();
        LongAdder counter = new LongAdder();
        LongAdder sum = new LongAdder();
        long t = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            new Fiber<Void>("Caller", new SuspendableRunnable() {
                @Override
                public void run() throws SuspendExecution, InterruptedException {
                    long start = System.currentTimeMillis();
                    m1();
                    start = System.currentTimeMillis() - start;
                    counter.add(1);
                    latency.add(start);
                    sum.increment();
                    latch.countDown();
                }
            }).start();
        }
        latch.await();
        t = System.currentTimeMillis() - t;
        long l = latency.longValue() / count;
        System.out.println("fiber took: " + t + ", latency: " + l + " ms,------" + counter + "sum---" + sum);
    }

}
