package com.demo.juc.volatilepkg;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import lombok.SneakyThrows;

/**
 * 参考：https://mp.weixin.qq.com/s/Oa3tcfAFO9IgsbE22C5TEg
 *
 * 「共享变量」：实例变量、类变量
 * 「局部变量」：方法内定义的变量
 *
 * @author: lijiawei04
 * @date: 2021/6/2 1:45 下午
 */
public class VolatileTest {

    public static void main(String[] args) {
//        test1();
//        test2();
        test3();;
    }

    /**
     * 线程局部变量对其他线程不可见
     *
     * 线程对共享变量的所有的操作(读，取)都必须在线程自己的工作内存中完成，而不能直接读写主内存中的变量。
     * 不同线程之间不能直接访问对方工作内存中的共享变量，线程间共享变量的值的传递需要通过主内存中转来完成
     */
    public static void test1() {
        Go go = new Go("Thread-Go");
        go.start();
        for (;;) {
            if (go.getFlag()) {
                System.out.println("有点东西");
                sleep(1000);
            }
        }
    }

    /**
     * 线程局部变量可见行解决方案1
     *
     * 为啥加锁可以解决可见性问题呢？
     * 因为某一个线程进入synchronized代码块前后，线程会获得锁，清空工作内存，从主内存拷贝共享变量最新的值到工作内存成为副本，执行代码，将修改后的副本的值刷新回主内存中，线程释放锁。
     * 而获取不到锁的线程会阻塞等待，所以变量的值肯定一直都是最新的。
     */
    public static void test2() {
        Go go = new Go("Thread-Go");
        go.start();
        for (;;) {
            synchronized (go) {
                if (go.getFlag()) {
                    System.out.println("有点东西");
                    sleep(1000);
                }
            }
        }
    }

    /**
     * 线程局部变量可见行解决方案2
     *
     * 每个线程操作数据的时候会把数据从主内存读取到自己的工作内存，如果他操作了数据并且写回了主内存，其他已经读取的线程的变量副本就会失效了，需要对数据进行操作又要再次去主内存中读取了。
     * volatile保证不同线程对共享变量操作的可见性，也就是说一个线程修改了volatile修饰的变量，当修改写回主内存时，另外一个线程立即看到最新的值。
     */
    public static void test3() {
        Go2 go = new Go2("Thread-Go2");
        go.start();
        for (;;) {
            if (go.getFlag()) {
                System.out.println("有点东西");
                sleep(1000);
            }
        }
    }

    @SneakyThrows
    public static void sleep(long time) {
        TimeUnit.MILLISECONDS.sleep(time);
    }

}

class Go extends Thread {

    // 共享变量
    private boolean flag = false;

    public Go(String name) {
        super(name);
    }

    public boolean getFlag() {
        return flag;
    }

    @Override
    public void run() {
        VolatileTest.sleep(1000);
        flag = true;
        System.out.println("flag:" + flag);
    }
}

class Go2 extends Thread {

    // 共享变量
    private volatile boolean flag = false;

    public Go2(String name) {
        super(name);
    }

    public boolean getFlag() {
        return flag;
    }

    @Override
    public void run() {
        VolatileTest.sleep(1000);
        flag = true;
        System.out.println("flag:" + flag);
    }

    public static void main(String[] args) {
        System.out.println(getDateStringFromMilliSeconds(1622798720379L));
    }

    public static String getDateStringFromMilliSeconds(long milliseconds) {
        LocalDateTime localDateTime = new Date(milliseconds).toInstant().atOffset(ZoneOffset.of("+8")).toLocalDateTime();
        return DateTimeFormatter.ofPattern("MM月dd日").format(localDateTime);
    }
}
