package com.okay.ad.advice;

import com.okay.ad.exception.AdException;
import com.okay.ad.vo.CommonResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by OKali on 2019/1/19.
 */
@RestControllerAdvice
public class GlobalExceptionAdvice {

    @ExceptionHandler(value = AdException.class)
    public CommonResponse<String> handlerAdException(HttpServletRequest request,
                                                     AdException ex) {

        CommonResponse<String> response = new CommonResponse<>(-1,
               "business error");
       response.setData(ex.getMessage());

       return response;
    }
}
