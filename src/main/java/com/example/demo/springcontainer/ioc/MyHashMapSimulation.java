package com.example.demo.springcontainer.ioc;

import java.util.Objects;

public class MyHashMapSimulation<K, V> {

    // ==========================================
    // 1. 底层节点结构
    // ==========================================
    static class Node<K, V> {
        final int hash;
        final K key;
        V value;
        Node<K, V> next; // 链表指针

        Node(int hash, K key, V value, Node<K, V> next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }

        @Override
        public String toString() {
            return "[" + key + "=" + value + "]";
        }
    }

    private Node<K, V>[] table; 
    private int size;           
    private static final int DEFAULT_CAPACITY = 16;
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;
    private int threshold = (int)(DEFAULT_CAPACITY * DEFAULT_LOAD_FACTOR); 

    public MyHashMapSimulation() {
        table = new Node[DEFAULT_CAPACITY];
    }

    public V put(K key, V value) {
        if (size >= threshold) {
            resize(); 
        }

        int hash = (key == null) ? 0 : key.hashCode();
        int index = hash & (table.length - 1); // 路由算法，算出 0~15 的下标

        Node<K, V> firstNode = table[index];

        if (firstNode == null) {
            // 格子是空的，直接放进去（调用有参构造）
            table[index] = new Node<>(hash, key, value, null);
            size++;
            return null;
        }

        // 格子有人了，开始遍历链表
        Node<K, V> currentNode = firstNode;
        while (currentNode != null) {
            if (currentNode.hash == hash && 
               (currentNode.key == key || (key != null && key.equals(currentNode.key)))) {
                V oldValue = currentNode.value;
                currentNode.value = value; // 找到了相同的Key，替换 Value
                return oldValue;
            }
            if (currentNode.next == null) {
                break; 
            }
            currentNode = currentNode.next;
        }

        // 没找到相同的Key，用尾插法挂在链表末尾（调用有参构造）
        currentNode.next = new Node<>(hash, key, value, null);
        size++;
        return null;
    }

    private void resize() {
        System.out.println("\n🔥 [触发扩容] 当前 size 达到 " + size + "，开始扩容！");
        // 简化的扩容：重置数组（真实源码会搬运数据，这里为了看结构先不做复杂搬运）
        int newCapacity = table.length * 2;
        table = new Node[newCapacity]; 
        size = 0; 
        threshold = (int)(newCapacity * DEFAULT_LOAD_FACTOR);
    }

    // ==========================================
    // 2. 专门写一个打印内存结构的方法，用来 debug
    // ==========================================
    public void printVisualStructure() {
        System.out.println("\n===== 当前 MyHashMapSimulation 内存结构图 =====");
        for (int i = 0; i < table.length; i++) {
            Node<K, V> node = table[i];
            if (node != null) {
                // 如果这个格子有元素，开始顺着链表往后打印
                System.out.print("桶位 [" + i + "]: ");
                while (node != null) {
                    System.out.print(node);
                    if (node.next != null) {
                        System.out.print(" ──> "); // 用箭头代表 next 指针
                    }
                    node = node.next;
                }
                System.out.println();
            } else {
                // 如果这个格子是空的
                System.out.println("桶位 [" + i + "]: null (空大楼)");
            }
        }
        System.out.println("===============================================\n");
    }

    // ==========================================
    // 3. 测试用的自定义类：故意让哈希值相同
    // ==========================================
    static class BadKey {
        private String name;
        public BadKey(String name) { this.name = name; }

        @Override
        public int hashCode() {
            // 😈 坏心眼：无论名字是什么，哈希值永远返回 5！
            // 这样它们进 Map 时，必然全部被分到 table[5] 这个格子里，强制制造链表！
            return 5;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            return Objects.equals(name, ((BadKey) obj).name);
        }

        @Override
        public String toString() { return name; }
    }

    // ==========================================
    // 4. MAIN 测试方法
    // ==========================================
    public static void main(String[] args) {
        // 创建我们自己写的模拟 HashMap
        MyHashMapSimulation<Object, Object> map = new MyHashMapSimulation<>();

        System.out.println("--- 步骤 1: 放入一个普通的、没有冲突的键值对 ---");
        map.put("正常苹果", "10元");
        // "正常苹果" 的 hash & 15 算出来的桶位是不确定的（假设是 3），它会独占一个格子
        map.printVisualStructure();


        System.out.println("--- 步骤 2: 连续放入 3 个哈希值相同的 BadKey（测试链表拉链法） ---");
        BadKey key1 = new BadKey("张三");
        BadKey key2 = new BadKey("李四");
        BadKey key3 = new BadKey("王五");

        map.put(key1, "张三的喜好");
        map.put(key2, "李四的喜好"); // 发现桶位5有人了，equals对比不一样，挂在张三后面
        map.put(key3, "王五的喜好"); // 发现桶位5有人了，顺着链表找到尾巴，挂在李四后面

        // 打印结构，你会清晰看到桶位 5 拉起了一条面条一样的链表！
        map.printVisualStructure();


        System.out.println("--- 步骤 3: 测试 Value 覆盖（修改张三的值） ---");
        // 再次 put 相同的 key1，内部会通过 hashCode 找到桶位5，再通过 equals 找到张三，把值替换
        map.put(key1, "张三换新喜好了！");
        map.printVisualStructure();


        System.out.println("--- 步骤 4: 疯狂灌入数据，直到触发 resize 扩容阈值（12） ---");
        for (int i = 1; i <= 9; i++) {
            map.put("测试数据" + i, i);
        }
        // 当总 size 达到 12 时，会在 put 内部触发 resize() 方法，你会看到控制台打印扩容提示
    }
}