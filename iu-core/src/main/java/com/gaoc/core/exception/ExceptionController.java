package com.gaoc.core.exception;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.gaoc.core.model.R;

import cn.hutool.http.HttpStatus;

@RestControllerAdvice
public class ExceptionController {

	@ExceptionHandler(MyException.class)
	public R handlerMyException(MyException me) {
		return R.fail(me.getCode(), me.getMessage());
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public R handlerMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		String message = e.getBindingResult().getFieldError().getDefaultMessage();
		return R.fail(HttpStatus.HTTP_BAD_REQUEST, message);
	}

}
