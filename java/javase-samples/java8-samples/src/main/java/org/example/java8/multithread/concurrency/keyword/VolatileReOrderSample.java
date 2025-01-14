package org.example.java8.multithread.concurrency.keyword;

public class VolatileReOrderSample {
    //定义四个静态变量
    private static int x = 0, y = 0;
    private static int a = 0, b = 0;

    public static void main(String[] args) throws InterruptedException {
        int i = 0;
        while (true) {
            i++;
            x = 0;
            y = 0;
            a = 0;
            b = 0;
            //开两个线程，第一个线程执行a=1;x=b;第二个线程执行b=1;y=a
            Thread thread1 = new Thread(() -> {
                //线程1会比线程2先执行，因此用nanoTime让线程1等待线程2 0.01毫秒
                shortWait(10000);
                a = 1;
                x = b;
            });
            Thread thread2 = new Thread(() -> {
                b = 1;
                y = a;
            });
            thread1.start();
            thread2.start();
            thread1.join();
            thread2.join();
            //等两个线程都执行完毕后拼接结果
            String result = "第" + i + "次执行: x=" + x + ", y=" + y;
            //如果x=0且y=0，则跳出循环
            if (x == 0 && y == 0) {
                System.out.println(result);
                break;
            } else {
                System.out.println(result);
            }
        }
    }

    //等待interval纳秒
    private static void shortWait(long interval) {
        long start = System.nanoTime();
        long end;
        do {
            end = System.nanoTime();
        } while (start + interval >= end);
    }
}