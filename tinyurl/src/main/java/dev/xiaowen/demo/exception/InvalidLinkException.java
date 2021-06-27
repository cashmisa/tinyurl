package dev.xiaowen.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidLinkException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String msg;

	public InvalidLinkException() {
		super();
	}

	public InvalidLinkException(String msg) {
		super(msg);
		this.msg = msg;
	}
}
