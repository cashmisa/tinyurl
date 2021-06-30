package dev.xiaowen.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidUrlException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String msg;

	public InvalidUrlException() {
		super();
	}

	public InvalidUrlException(String msg) {
		super(msg);
		this.msg = msg;
	}
}
