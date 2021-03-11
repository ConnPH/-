package vip.ph.yygh.common.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import vip.ph.yygh.common.utils.Result;


@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(Exception.class)
    public Result error(Exception e){
        e.printStackTrace();
        return Result.fail();
    }


    /**
     * 自定义异常处理方法
     * @param e
     * @return
     */
    @ExceptionHandler(YyghException.class)
    @ResponseBody
    public Result error(YyghException e){
        return Result.build(e.getCode(), e.getMessage());
    }
}

