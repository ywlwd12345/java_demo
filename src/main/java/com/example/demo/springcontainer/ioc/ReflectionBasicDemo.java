package com.example.demo.springcontainer.ioc;

// ============================================================
// 文件名：ReflectionBasicDemo.java
// 作用：获取 Class 对象、查看类信息（反射入门）
// 所有关键代码都配有详尽中文注释
// ============================================================
import java.lang.reflect.*; // 导入反射相关类：Field、Method、Constructor 等

public class ReflectionBasicDemo {

    public static void main(String[] args) throws Exception {

        // ============================
        // 一、获取 Class 对象的三种方式
        // ============================

        // 方式一：类名.class（编译时确定，不会触发类的初始化）
        Class<String> c1 = String.class;

        Class<TscPrinter> tscClass = TscPrinter.class;

        TscPrinter tsc1 = new TscPrinter();

        Class<? extends TscPrinter> tsc2 = tsc1.getClass();

        Class<?> tsc3 = Class.forName("com.example.demo.springcontainer.ioc.TscPrinter");

        System.out.println("tscClass == tsc2: " + (tscClass == tsc2)); // true
        System.out.println("tsc2 == tsc3: " + (tsc2 == tsc3)); // true

        // 方式二：对象.getClass()（通过已有实例获取）
        String str = "hello";
        Class<?> c2 = str.getClass(); // 返回 Class<?>，因为编译时不知道具体类型

        // 方式三：Class.forName("全限定类名")（最灵活，运行时动态加载）
        // 全限定类名 = 包名.类名，如 java.lang.String
        Class<?> c3 = Class.forName("java.lang.String");

        // 三种方式获取的是同一个 Class 对象（JVM 中每个类只有一个 Class 实例）
        System.out.println("c1 == c2: " + (c1 == c2)); // true
        System.out.println("c2 == c3: " + (c2 == c3)); // true

        // ============================
        // 二、从 Class 对象获取类的基本信息
        // ============================

        // 获取 Person 类的 Class 对象
        Class<Person> personClass = Person.class;

        // getName()：获取全限定类名（含包名）
        System.out.println("全限定类名: " + personClass.getName());
        // getSimpleName()：获取简单类名（不含包名）
        System.out.println("简单类名: " + personClass.getSimpleName());
        // getSuperclass()：获取父类的 Class 对象
        System.out.println("父类: " + personClass.getSuperclass().getSimpleName());

        // getInterfaces()：获取实现的所有接口
        Class<?>[] interfaces = personClass.getInterfaces();
        System.out.print("实现的接口: ");

        for (Class<?> iface : interfaces) {
            System.out.print(iface.getSimpleName() + " ");
        }
        System.out.println();

        // ============================
        // 三、获取所有字段信息
        // ============================

        System.out.println("\n--- 所有字段（含 private） ---");
        // getDeclaredFields()：获取本类声明的所有字段（含 private），不含继承的
        // getFields() 只获取 public 字段（含继承的）
        Field[] fields = personClass.getDeclaredFields();

        for (Field f : fields) {
            // getModifiers()：返回修饰符的 int 编码，用 Modifier.toString 转成可读字符串
            String modifiers = Modifier.toString(f.getModifiers());
            // getType()：字段的类型
            String type = f.getType().getSimpleName();
            // getName()：字段名
            String name = f.getName();
            // 打印格式：修饰符 类型 字段名
            System.out.println("  " + modifiers + " " + type + " " + name);
        }

        // ============================
        // 四、获取所有方法信息
        // ============================

        System.out.println("\n--- 所有方法（含 private） ---");
        // getDeclaredMethods()：获取本类声明的所有方法（含 private），不含继承的
        Method[] methods = personClass.getDeclaredMethods();
        for (Method m : methods) {
            // 修饰符
            String modifiers = Modifier.toString(m.getModifiers());
            // 返回类型
            String returnType = m.getReturnType().getSimpleName();
            // 方法名
            String name = m.getName();
            // 参数类型列表
            Class<?>[] paramTypes = m.getParameterTypes();
            StringBuilder params = new StringBuilder();

            for (int i = 0; i < paramTypes.length; i++) {
                if (i > 0)
                    params.append(", ");
                params.append(paramTypes[i].getSimpleName());
            }
            // 打印格式：修饰符 返回类型 方法名(参数类型列表)
            System.out.println("  " + modifiers + " " + returnType + " " + name + "(" + params + ")");
        }

        // ============================
        // 五、获取所有构造方法信息
        // ============================

        System.out.println("\n--- 所有构造方法 ---");
        // getDeclaredConstructors()：获取所有构造方法（含 private）
        Constructor<?>[] constructors = personClass.getDeclaredConstructors();
        for (Constructor<?> con : constructors) {
            // 打印构造方法名和参数类型
            System.out.print("  " + con.getName() + "(");
            Class<?>[] paramTypes = con.getParameterTypes();
            for (int i = 0; i < paramTypes.length; i++) {
                if (i > 0)
                    System.out.print(", ");
                System.out.print(paramTypes[i].getSimpleName());
            }
            System.out.println(")");
        }

        // ============================
        // 六、用反射动态创建对象（预览，下节详讲）
        // ============================

        // 通过无参构造创建 Person 对象
        // getDeclaredConstructor()：获取无参构造方法
        // newInstance()：调用构造方法创建对象
        Person p = personClass.getDeclaredConstructor().newInstance();
        System.out.println("\n反射创建对象: " + p);
    }
}

// 用于演示反射的示例类
class Person implements java.io.Serializable {

    // public 字段
    public String name;
    // private 字段
    private int age;
    // protected 字段
    protected String address;

    // 无参构造
    public Person() {
        this.name = "未知";
        this.age = 0;
    }

    // 有参构造
    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    // public 方法
    public void sayHello() {
        System.out.println("你好，我是 " + name);
    }

    // private 方法
    private String getSecret() {
        return "这是私密信息";
    }

    // getter
    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return "Person{name=" + name + ", age=" + age + "}";
    }
}
