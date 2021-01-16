package com.sales.taxes.problem.exception;

public class ReadFileException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public ReadFileException() {
        super();
    }

    public ReadFileException(String message) {
        super(message);
    }

}
