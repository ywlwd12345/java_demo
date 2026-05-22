package com.example.demo.common;

import lombok.Data;

@Data
public class Result<T> {
    private Integer code;
    private String msg;
    private T data; // 成功时存放的数据

    /**
     * 成功快捷方法 1：有返回数据的成功 (最常用)
     * 
     * @param data 要带给前端的数据（可以是对象、List、Map 等）
     */
    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setCode(200); // 企业默认成功状态码
        result.setMsg("操作成功"); // 默认成功提示
        result.setData(data); // 👈 把传进来的数据塞给 data 属性
        return result;
    }

    /**
     * 成功快捷方法 2：没有返回数据的成功 (重载方法)
     * 用于：删除、修改等不需要返回数据的接口
     */
    public static <T> Result<T> success() {
        // 直接调用上面的方法，把 data 传成 null 即可
        return success(null);
    }

    // 快捷返回错误的方法
    public static <T> Result<T> error(Integer code, String msg) {
        Result<T> result = new Result<>();

        result.setCode(code);
        result.setMsg(msg);
        return result;
    }
}