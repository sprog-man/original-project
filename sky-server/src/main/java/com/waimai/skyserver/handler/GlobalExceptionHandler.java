package com.waimai.skyserver.handler;


import com.waimai.skycommon.constant.MessageConstant;
import com.waimai.skycommon.exception.BaseException;
import com.waimai.skycommon.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常处理器，处理项目中抛出的业务异常
 *
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     *捕获业务异常
     *
     * @param ex
     * @return
     */

    @ExceptionHandler
    public Result<String> exceptionHandler(BaseException ex){
        log.error("异常信息：{}", ex.getMessage());
        return Result.error(ex.getMessage());
    }


    @ExceptionHandler
    public Result<String> exceptionHandler(SQLIntegrityConstraintViolationException ex){
        log.error("异常信息：{}", ex.getMessage());
        String errorMsg=ex.getMessage();
        // 判断错误信息
        if (errorMsg.contains("Duplicate entry")){
            String[] errorList = errorMsg.split(" ");
            return Result.error(errorList[2]+ MessageConstant.ACCOUNT_ALREADY_EXIST);
        }else {
            return Result.error(MessageConstant.UNKNOWN_ERROR);
        }
    }

}
