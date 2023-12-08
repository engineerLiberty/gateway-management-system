package com.engineerLee.gatewaymanagement.exception;

import com.engineerLee.gatewaymanagement.dto.ApiResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Log4j2
public class GlobalExceptionHandlers  {

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ApiResponse<String> handleNotFoundException(RuntimeException ex){
        return  new ApiResponse<>("Failed","Error: " + ex.getMessage(), null);
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiResponse<String> handleValidationException(ValidationException ex){
        log.error(ex);
        return  new ApiResponse<>("Failed","Error: "+ex.getMessage(), null);
    }

}
