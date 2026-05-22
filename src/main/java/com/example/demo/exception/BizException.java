package com.example.demo.exception;

// 必须继承 RuntimeException（运行时异常），这样我们在抛出时不需要显式在方法头上 throws
public class BizException extends RuntimeException {
    
    // 构造方法：把错误信息传给父类
    public BizException(String msg) {
        super(msg);
    }
    
}