package com.example.demo.exception;

import com.example.demo.common.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// @RestControllerAdvice：这是一个组合注解（@ControllerAdvice + @ResponseBody）。你可以把它理解为一个“拦截器网”。它会静静地潜伏在整个 Spring Boot 的控制层（Controller）周围。
// 只要任何一个 Controller 里的方法在执行过程中抛出了异常，并且没有被 try-catch 死，这个注解标注的类就会立刻出手接住这个异常

@RestControllerAdvice // 1. 声明这是一个全局增强型控制器，会自动把返回值转换为 JSON
public class GlobalExceptionHandler {

    /**
     * 2. 拦截自定义的业务异常 (BizException)
     * 比如：手机号已存在、库存不够。这些是我们“故意”抛出的提示信息。
     */
    @ExceptionHandler(BizException.class)
    public Result<?> handleBizException(BizException e) {
        // 返回固定的 500（或者自定义状态码），msg 则是你写代码时传进去的报错话术
        return Result.error(500, e.getMessage());
    }

    /**
     * 3. 拦截系统未知的兜底异常 (Exception.class)
     * 比如：空指针异常、数据库连接超时、数组越界等我们没预料到的致命错误。
     */
    @ExceptionHandler(Exception.class)
    public Result<?> handleException(Exception e) {
        // 打印后台日志，方便程序员排查线上 Bug
        e.printStackTrace();

        // 绝对不能把原生错误（比如 NullPointerException）直接丢给用户
        // 线上对普通用户显示“系统开小差了”，内部记录真实原因
        return Result.error(500, "系统未知异常，请联系管理员");
    }
}