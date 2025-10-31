package org.example.jvm;

import java.lang.management.ManagementFactory;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.*;

/**
 * G1 GC 问题测试程序
 * 模拟常见的内存问题模式，用于G1调优测试
 */
public class G1GCProblemDemo {
    
    // 模拟内存泄漏的静态集合
    private static final List<byte[]> MEMORY_LEAK = new ArrayList<>();
    
    // 大对象集合，模拟Humongous对象分配
    private static final List<byte[]> HUMONGOUS_OBJECTS = new ArrayList<>();
    
    // 线程池，模拟并发环境
    private static final ExecutorService executor = Executors.newFixedThreadPool(50);
    
    // 随机数生成器
    private static final Random random = new Random();
    
    public static void main(String[] args) throws InterruptedException {
        System.out.println("G1 GC 问题测试程序启动...");
        System.out.println("JDK 版本: " + System.getProperty("java.version"));
        System.out.println("JVM: " + System.getProperty("java.vm.name"));
        System.out.println("GC: " + System.getProperty("java.vm.gc"));
        
        // 启动内存监控线程
        startMemoryMonitor();
        
        // 模拟不同的内存问题场景
        simulateMemoryLeak();          // 场景1: 内存泄漏
        simulateHumongousAllocations(); // 场景2: 大对象分配
        simulateHighAllocationRate();  // 场景3: 高分配速率
        simulateMixedWorkload();       // 场景4: 混合工作负载
        
        // 保持程序运行
        Thread.sleep(Long.MAX_VALUE);
    }
    
    /**
     * 场景1: 模拟内存泄漏
     * 不断向静态集合添加对象，这些对象永远不会被回收
     */
    private static void simulateMemoryLeak() {
        executor.submit(() -> {
            int counter = 0;
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    // 每100ms泄漏一个50KB的对象
                    byte[] leak = new byte[50 * 1024]; // 50KB
                    MEMORY_LEAK.add(leak);
                    
                    counter++;
                    if (counter % 100 == 0) {
                        System.out.println("内存泄漏: 已泄漏 " + counter + " 个对象, 总量: " + 
                                         (MEMORY_LEAK.size() * 50 / 1024) + "MB");
                    }
                    
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
    }
    
    /**
     * 场景2: 模拟大对象分配 (Humongous Objects)
     * G1中大于Region一半的对象会被当作Humongous对象
     */
    private static void simulateHumongousAllocations() {
        executor.submit(() -> {
            int counter = 0;
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    // 分配4MB的大对象 (通常Region大小为1-32MB，4MB可能成为Humongous对象)
                    byte[] hugeObject = new byte[4 * 1024 * 1024]; // 4MB
                    HUMONGOUS_OBJECTS.add(hugeObject);
                    
                    counter++;
                    if (counter % 10 == 0) {
                        System.out.println("大对象分配: 已分配 " + counter + " 个大对象");
                        // 偶尔释放一些，模拟部分回收
                        if (HUMONGOUS_OBJECTS.size() > 20) {
                            HUMONGOUS_OBJECTS.subList(0, 10).clear();
                        }
                    }
                    
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
    }
    
    /**
     * 场景3: 模拟高分配速率
     * 快速创建和丢弃大量小对象
     */
    private static void simulateHighAllocationRate() {
        for (int i = 0; i < 10; i++) {
            executor.submit(() -> {
                while (!Thread.currentThread().isInterrupted()) {
                    // 快速分配大量小对象
                    List<byte[]> tempList = new ArrayList<>();
                    for (int j = 0; j < 1000; j++) {
                        byte[] smallObject = new byte[random.nextInt(16 * 1024)]; // 最多16KB
                        tempList.add(smallObject);
                    }
                    // 短暂持有后释放，制造GC压力
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            });
        }
    }
    
    /**
     * 场景4: 模拟混合工作负载
     * 同时包含短生命周期和中等生命周期的对象
     */
    private static void simulateMixedWorkload() {
        List<byte[]> mediumLifeObjects = new ArrayList<>();
        
        executor.submit(() -> {
            int cycle = 0;
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    // 每个周期创建一些中等生命周期的对象
                    for (int i = 0; i < 50; i++) {
                        byte[] mediumObject = new byte[64 * 1024]; // 64KB
                        mediumLifeObjects.add(mediumObject);
                    }
                    
                    cycle++;
                    if (cycle % 5 == 0) {
                        // 每5个周期清理一次，模拟对象死亡
                        mediumLifeObjects.clear();
                        System.out.println("混合负载: 清理中等生命周期对象");
                    }
                    
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
    }
    
    /**
     * 内存监控线程
     */
    private static void startMemoryMonitor() {
        Thread monitorThread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Runtime runtime = Runtime.getRuntime();
                    long totalMemory = runtime.totalMemory() / (1024 * 1024);
                    long freeMemory = runtime.freeMemory() / (1024 * 1024);
                    long usedMemory = totalMemory - freeMemory;
                    long maxMemory = runtime.maxMemory() / (1024 * 1024);
                    
                    System.out.printf("内存使用: 已用=%dMB, 空闲=%dMB, 总量=%dMB, 最大=%dMB%n",
                            usedMemory, freeMemory, totalMemory, maxMemory);
                    
                    // 打印GC信息（如果可用）
                    printGcInfo();
                    
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        monitorThread.setDaemon(true);
        monitorThread.start();
    }
    
    /**
     * 打印GC信息（简化版）
     */
    private static void printGcInfo() {
        try {
            // 使用ManagementFactory获取GC信息
            List<com.sun.management.GarbageCollectorMXBean> gcBeans =
                ManagementFactory.getGarbageCollectorMXBeans()
                    .stream()
                    .map(bean -> (com.sun.management.GarbageCollectorMXBean) bean)
                    .collect(Collectors.toList());
            
            for (com.sun.management.GarbageCollectorMXBean gc : gcBeans) {
                System.out.printf("GC: %s - 次数=%d, 总时间=%dms%n",
                        gc.getName(), gc.getCollectionCount(), gc.getCollectionTime());
            }
        } catch (Exception e) {
            // 忽略异常，GC信息获取可能在不同JVM实现中有差异
        }
    }
}