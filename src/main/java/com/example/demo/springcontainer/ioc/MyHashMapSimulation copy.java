public class MyHashMapSimulation<K, V> {

    public static void main(String[] args) {
        System.out.println("--- 准备创建数组 ---");
        Node[] table = new Node[16]; // 🌟 执行这行，控制台是【没有任何打印】的！因为没有调用构造方法。

        System.out.println("--- 准备创建单个对象并放入数组 ---");
        table[0] = new Node(1, "张三", 18, null); // 🌟 执行这行，控制台才会打印：“👉 触发了有参构造方法！”
    }

    // 1. 对应你说的：“直接放入 Node 类型的数组中”
    static class Node<K, V> {
        final int hash;
        final K key;
        V value;
        Node<K, V> next; // 对应你说的：不一样时，用链表连起来

        Node(int hash, K key, V value, Node<K, V> next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    private Node<K, V>[] table; // 核心底层数组
    private int size; // 当前存放的元素个数

    // 2. 对应你说的：“初始化容量是 16，默认加载因子是 0.75，阈值是 12”
    private static final int DEFAULT_CAPACITY = 16;
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;
    private int threshold = (int) (DEFAULT_CAPACITY * DEFAULT_LOAD_FACTOR); // 12

    public MyHashMapSimulation() {
        table = new Node[DEFAULT_CAPACITY];
    }

    /**
     * 核心模拟：put(k, v) 方法
     */
    public V put(K key, V value) {
        // 扩容检查：对应你说的“当元素加到 12 的时候，底层会进行扩容，扩容为原来的 2倍”
        if (size >= threshold) {
            resize();
        }

        // a. 根据 k1 的 hashCode 方法来决定在数组中存放的位置
        int hash = (key == null) ? 0 : key.hashCode();
        // 路由算法：把哈希值映射到数组下标 (0 到 15)
        int index = hash & (table.length - 1);

        Node<K, V> firstNode = table[index];

        // b. 如果这个位置没有其它元素，直接放入
        if (firstNode == null) {
            table[index] = new Node<>(hash, key, value, null);
            size++;
            return null;
        }

        // c. 如果该位置已经有其它元素（发生哈希冲突）
        Node<K, V> currentNode = firstNode;
        while (currentNode != null) {
            // 调用 k1 的 equals 方法和已有 key 进行比较
            if (currentNode.hash == hash &&
                    (currentNode.key == key || (key != null && key.equals(currentNode.key)))) {

                // 如果结果相同：替换旧的 value
                V oldValue = currentNode.value;
                currentNode.value = value;
                return oldValue; // 返回被替换的旧值
            }

            // 如果不相同，继续往链表后方寻找
            if (currentNode.next == null) {
                break; // 查到链表末尾了，说明确实没有相同的 Key
            }
            currentNode = currentNode.next;
        }

        // d. 对应最后一句乱码：如果返回值为 false，两个元素不一样，用链表（尾插法）挂在后面
        currentNode.next = new Node<>(hash, key, value, null);
        size++;

        // 💡 补充：在真正的 JDK 8 中，如果这个链表长度超过了 8，
        // 且数组长度大于等于 64，这个链表还会扭转变成一棵【红黑树】。

        return null;
    }

    /**
     * 模拟扩容：容量翻倍
     */
    private void resize() {
        System.out.println("触发扩容！当前 size: " + size + "，数组扩容为原来的2倍。");
        int newCapacity = table.length * 2;
        Node<K, V>[] newTable = new Node[newCapacity];

        // 真实源码中还会包含繁琐的：将旧数组数据 rehash（重新迁移）到新数组的过程
        this.table = newTable;
        this.threshold = (int) (newCapacity * DEFAULT_LOAD_FACTOR);
    }
}