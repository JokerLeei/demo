package com.demo.quasar;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

import co.paralleluniverse.fibers.Fiber;
import co.paralleluniverse.strands.Strand;

/**
 * <h1>Java纤程</h1>
 *
 * <a href = "https://www.cnblogs.com/cfas/p/11076022.html">JAVA协程 纤程 与Quasar 框架</a><br>
 * <a href = https://www.cnblogs.com/beilong/p/12253328.html>纤程的介绍</a><br>
 * <br>
 * <a href = "https://www.cnblogs.com/z-test/p/9435847.html">⚠️运行时警告「QUASAR WARNING: Quasar Java Agent isn't running.······」</a><br>
 * <ol>
 *     <li>Quasar Java Agent(Quasar java agent可以在运行时动态修改字节) 「-javaagent:${quasar.jar路径}」</li>
 *     <li>AOT(Ahead-of-Time 编译时的时候完成instrumentation)</li>
 *     <li>在Web容器中(Comsat)</li>
 * </ol>
 *
 * @author: lijiawei04
 * @date: 2021/6/7 8:08 下午
 */
public class FiberTest {

    public static void main(String[] args) throws InterruptedException {
        int FiberNumber = 1_000;
        CountDownLatch latch = new CountDownLatch(1);
        AtomicInteger counter = new AtomicInteger(0);

        for (int i = 0; i < FiberNumber; i++) {
            new Fiber<Void>(() -> {
                counter.incrementAndGet();
                if (counter.get() == FiberNumber) {
                    System.out.println("done");
                }
                Strand.sleep(1000000);
            }).start();
        }
        latch.await();
    }

}
